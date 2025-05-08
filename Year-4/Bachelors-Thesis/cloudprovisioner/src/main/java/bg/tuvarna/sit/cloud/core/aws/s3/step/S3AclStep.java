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
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.Type;

import java.util.List;
import java.util.Optional;

@ProvisionAsync
@Slf4j
public class S3AclStep implements S3ProvisionStep {

  private final S3SafeClient s3;
  private final S3BucketConfig s3BucketConfig;

  @Inject
  public S3AclStep(S3SafeClient s3, S3BucketConfig s3BucketConfig) {
    this.s3 = s3;
    this.s3BucketConfig = s3BucketConfig;
  }

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config)
      throws BucketAclProvisioningException {

    String bucketName = config.getName();
    S3BucketConfig.AccessControlPolicy configAccessControlPolicy = s3BucketConfig.getAccessControlPolicy();

    if (configAccessControlPolicy == null && config.getAcl() == null) {
      config.setAcl(S3AclType.PRIVATE);
    }

    if (configAccessControlPolicy != null) {
      AccessControlPolicy policy = buildAccessControlPolicy(configAccessControlPolicy);
      s3.putAcl(bucketName, policy, null);
    }

    S3AclType acl = config.getAcl();
    if (acl != null) {
      s3Client.putAcl(bucketName, null, acl.toSdkAcl());
    }

    GetBucketAclResponse aclResponse = s3Client.getAcl(bucketName);

    return S3AclResultBuilder.fromResponse(aclResponse);
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

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
  public StepResult<S3Output> getCurrentState(S3SafeClient client, S3BucketConfig config) {

    GetBucketAclResponse response = client.getAcl(config.getName());

    return S3AclResultBuilder.fromResponse(response);
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
