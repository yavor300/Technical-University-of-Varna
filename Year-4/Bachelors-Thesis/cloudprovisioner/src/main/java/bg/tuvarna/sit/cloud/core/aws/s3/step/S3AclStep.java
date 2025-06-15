package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAcl;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3AclResultBuilder;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3AclType;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclGrant;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclGrantee;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclOwner;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3OwnershipControlsResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.s3.model.AccessControlPolicy;
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.Type;

import java.util.List;

@Slf4j
@ProvisionOrder(7)
public class S3AclStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;

  @Inject
  public S3AclStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    if (S3OwnershipControlsResultBuilder.getOwnershipAsString(s3.getOwnershipControls(bucket))
        .equals(S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue())) {
      log.info("ACL configuration skipped for bucket '{}': ownership is set to {}, which disables ACLs",
          bucket, S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());
      return S3AclResultBuilder.fromResponse(s3.getAcl(bucket), null);
    }

    S3BucketConfig.AclConfig acl = config.getAcl();

    S3BucketConfig.AccessControlPolicy configAccessControlPolicy = acl.getAccessControlPolicy();
    S3AclType cannedAcl = acl.getCanned();

    if (configAccessControlPolicy != null) {
      AccessControlPolicy policy = buildAccessControlPolicy(configAccessControlPolicy);
      s3.putAcl(bucket, policy, null);
      log.info("Successfully applied acl policy '{}' to bucket '{}'", policy, bucket);
    }

    if (cannedAcl != null) {
      s3.putAcl(bucket, null, cannedAcl.toSdkAcl());
      log.info("Successfully applied canned acl '{}' to bucket '{}'", cannedAcl.getValue(), bucket);
    }

    GetBucketAclResponse aclResponse = s3.getAcl(bucket);
    log.info("Successfully verified acl configuration for bucket '{}'", bucket);

    return S3AclResultBuilder.fromResponse(aclResponse, cannedAcl);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    S3BucketConfig.AccessControlPolicy policy = config.getAcl().getAccessControlPolicy();
    if (policy != null) {
      return buildStepResultFromPolicy(policy);
    }

    S3AclType acl = config.getAcl().getCanned();
    if (acl != null) {
      return StepResult.<S3Output>builder()
          .stepName(this.getClass().getName())
          .put(S3Output.VALUE_NODE, new S3ProvisionedAcl(null, null, acl))
          .build();
    }

    return fetchAclOrFallbackTo(bucket, S3AclType.PRIVATE);
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    return fetchAclOrFallbackTo(bucket, null);
  }

  @Override
  public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    if (S3OwnershipControlsResultBuilder.getOwnershipAsString(s3.getOwnershipControls(bucket))
        .equals(S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue())) {

      return S3AclResultBuilder.fromResponse(s3.getAcl(bucket), null);
    }

    s3.putAcl(bucket, null, S3AclType.PRIVATE.toSdkAcl());

    GetBucketAclResponse response = s3.getAcl(bucket);
    return S3AclResultBuilder.fromResponse(response, S3AclType.PRIVATE);
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) throws CloudResourceStepException {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    S3ProvisionedAcl revert = (S3ProvisionedAcl) previous.getOutputs().get(S3Output.VALUE_NODE);

    S3AclType cannedAcl = revert.getCannedAcl();
    if (cannedAcl != null) {
      s3.putAcl(bucket, null, cannedAcl.toSdkAcl());
      GetBucketAclResponse response = s3.getAcl(bucket);

      return S3AclResultBuilder.fromResponse(response, cannedAcl);
    }

    S3ProvisionedAclOwner ownerDto = revert.getOwner();
    List<S3ProvisionedAclGrant> grantsDto = revert.getGrants();

    List<Grant> grants = grantsDto.stream()
        .map(g -> Grant.builder()
            .grantee(buildSdkGrantee(g))
            .permission(Permission.fromValue(g.getPermission()))
            .build())
        .toList();

    Owner owner = Owner.builder()
        .id(ownerDto.getId())
        .displayName(ownerDto.getDisplayName())
        .build();

    AccessControlPolicy policy = AccessControlPolicy.builder()
        .grants(grants)
        .owner(owner)
        .build();

    s3.putAcl(bucket, policy, null);

    GetBucketAclResponse aclResponse = s3.getAcl(bucket);
    return S3AclResultBuilder.fromResponse(aclResponse, null);
  }

  private StepResult<S3Output> fetchAclOrFallbackTo(String bucket, S3AclType fallback) {

    try {
      GetBucketAclResponse response = s3.getAcl(bucket);
      log.info("Successfully fetched ACL configuration for bucket '{}'", bucket);
      return S3AclResultBuilder.fromResponse(response, null);

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof S3Exception cause && cause.statusCode() == HttpStatus.SC_NOT_FOUND) {
        StepResult.Builder<S3Output> builder = StepResult.<S3Output>builder()
            .stepName(this.getClass().getName());

        if (fallback != null) {
          builder.put(S3Output.VALUE_NODE, new S3ProvisionedAcl(null, null, fallback));
        }

        return builder.build();
      }

      throw e;
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

  private Grantee buildSdkGrantee(S3ProvisionedAclGrant provisioned) {

    Grantee.Builder builder = Grantee.builder()
        .type(Type.fromValue(provisioned.getGrantee().getType()));

    String id = provisioned.getGrantee().getIdentifier();
    if (id != null) {
      builder.id(id);
    }

    String uri = provisioned.getGrantee().getUri();
    if (uri != null) {
      builder.uri(uri);
    }

    String email = provisioned.getGrantee().getEmailAddress();
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
          S3BucketConfig.Grantee fromConfig = g.getGrantee();

          S3ProvisionedAclGrantee grantee = new S3ProvisionedAclGrantee(
              fromConfig.getType(),
              fromConfig.getId(),
              fromConfig.getUri(),
              fromConfig.getEmailAddress()
          );

          return new S3ProvisionedAclGrant(grantee, g.getPermission());
        })
        .toList();

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.VALUE_NODE, new S3ProvisionedAcl(ownerDto, grantDtos, null))
        .build();
  }
}
