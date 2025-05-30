package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3AclResultBuilder;
import bg.tuvarna.sit.cloud.exception.BucketAclProvisioningException;
import bg.tuvarna.sit.cloud.core.aws.s3.S3AclType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionedAclGrant;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionedAclGrantee;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionedAclOwner;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.AccessControlPolicy;
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.Type;

import java.util.List;
import java.util.Optional;

@ProvisionAsync
@Slf4j
public class S3AclStep extends S3ProvisionStep {

  @Inject
  public S3AclStep(S3SafeClient s3, S3BucketConfig config) {
    super(s3, config);
  }

  @Override
  public StepResult<S3Output> apply()
      throws BucketAclProvisioningException {

    String bucketName = config.getName();
    S3BucketConfig.AccessControlPolicy configAccessControlPolicy = config.getAccessControlPolicy();

    if (configAccessControlPolicy == null && config.getAcl() == null) {
      config.setAcl(S3AclType.PRIVATE);
    }

    if (configAccessControlPolicy != null) {
      AccessControlPolicy policy = buildAccessControlPolicy(configAccessControlPolicy);
      s3.putAcl(bucketName, policy, null);
    }

    S3AclType acl = config.getAcl();
    if (acl != null) {
      s3.putAcl(bucketName, null, acl.toSdkAcl());
    }

    GetBucketAclResponse aclResponse = s3.getAcl(bucketName, false);

    return S3AclResultBuilder.fromResponse(aclResponse);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    S3BucketConfig.AccessControlPolicy policy = config.getAccessControlPolicy();
    if (policy != null) {
      return buildStepResultFromPolicy(policy);
    }

    StepResult.Builder<S3Output> resultBuilder = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.VALUE_NODE, S3AclType.PRIVATE.getValue());

    S3AclType acl = config.getAcl();
    if (acl != null) {
      return resultBuilder
          .put(S3Output.VALUE_NODE, acl.getValue())
          .build();
    }

    return resultBuilder.build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    try {
      GetBucketAclResponse response = s3.getAcl(config.getName(), true);
      return S3AclResultBuilder.fromResponse(response);

    } catch (BucketAclProvisioningException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return StepResult.<S3Output>builder()
            .stepName(S3AclStep.class.getName())
            .build();
      }
      // TODO Think what to return in case of different exception is thrown
      return null;
    }
  }

  private Grantee buildSdkGrantee(S3BucketConfig.Grantee g) {

    Grantee.Builder builder = Grantee.builder()
        .type(Type.fromValue(g.getType()));

    String id = g.getId();
    if (id != null) {
      builder.id(id);
    }

    String uri = g.getUri();
    if (uri != null) {
      builder.uri(uri);
    }

    String email = g.getEmailAddress();
    if (email != null) {
      builder.emailAddress(email);
    }

    return builder.build();
  }

  private AccessControlPolicy buildAccessControlPolicy(S3BucketConfig.AccessControlPolicy policyConfig) {

    List<Grant> grants = policyConfig.getGrants().stream()
        .map(g -> Grant.builder()
            .grantee(buildSdkGrantee(g.getGrantee()))
            .permission(Permission.fromValue(g.getPermission()))
            .build())
        .toList();

    S3BucketConfig.Owner configOwner = policyConfig.getOwner();
    Owner owner = Owner.builder()
        .id(configOwner.getId())
        .displayName(configOwner.getDisplayName())
        .build();

    return AccessControlPolicy.builder()
        .grants(grants)
        .owner(owner)
        .build();
  }

  private StepResult<S3Output> buildStepResultFromPolicy(S3BucketConfig.AccessControlPolicy policyConfig) {

    S3BucketConfig.Owner configOwner = policyConfig.getOwner();
    S3ProvisionedAclOwner ownerDto = new S3ProvisionedAclOwner(
        configOwner.getId(),
        configOwner.getDisplayName()
    );

    List<S3ProvisionedAclGrant> grantDtos = policyConfig.getGrants().stream()
        .map(g -> {
          S3BucketConfig.Grantee configGrantee = g.getGrantee();
          String identifier = Optional.ofNullable(configGrantee.getId())
              .orElse(Optional.ofNullable(configGrantee.getUri())
                  .orElse(configGrantee.getEmailAddress()));

          S3ProvisionedAclGrantee grantee = new S3ProvisionedAclGrantee(
              configGrantee.getType(),
              identifier
          );

          return new S3ProvisionedAclGrant(grantee, g.getPermission());
        })
        .toList();

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.OWNER, ownerDto)
        .put(S3Output.GRANTS, grantDtos)
        .build();
  }
}
