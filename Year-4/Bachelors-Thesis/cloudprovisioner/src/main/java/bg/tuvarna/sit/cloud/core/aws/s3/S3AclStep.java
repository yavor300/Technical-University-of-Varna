package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AccessControlPolicy;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.PutBucketAclRequest;
import software.amazon.awssdk.services.s3.model.Type;

import java.util.List;
import java.util.Optional;

@ProvisionAsync
@Slf4j
public class S3AclStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    String bucketName = config.getName();

    if (config.getAccessControlPolicy() != null) {
      return applyAccessControlPolicy(s3Client, bucketName, config);
    }

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (config.getAcl() != null) {
      PutBucketAclRequest request = PutBucketAclRequest.builder()
          .bucket(bucketName)
          .acl(config.getAcl().toSdkAcl())
          .build();

      s3Client.putBucketAcl(request);

      log.info("Set canned ACL '{}' for bucket '{}'", config.getAcl().getValue(), bucketName);

      return result.put(S3Output.VALUE_NODE, config.getAcl().getValue()).build();
    }

    return result.build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    if (config.getAccessControlPolicy() != null) {
      return buildStepResultFromPolicy(config.getAccessControlPolicy());
    }

    if (config.getAcl() != null) {
      return StepResult.<S3Output>builder()
          .stepName(this.getClass().getName())
          .put(S3Output.VALUE_NODE, config.getAcl().getValue())
          .build();
    }

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .build();
  }

  private StepResult<S3Output> applyAccessControlPolicy(S3Client s3Client, String bucketName, S3BucketConfig config) {

    S3BucketConfig.AccessControlPolicy policyConfig = config.getAccessControlPolicy();

    List<Grant> grants = policyConfig.getGrants().stream()
        .map(g -> Grant.builder()
            .grantee(buildSdkGrantee(g.getGrantee()))
            .permission(Permission.fromValue(g.getPermission()))
            .build())
        .toList();

    Owner owner = Owner.builder()
        .id(policyConfig.getOwner().getId())
        .displayName(policyConfig.getOwner().getDisplayName())
        .build();

    AccessControlPolicy policy = AccessControlPolicy.builder()
        .grants(grants)
        .owner(owner)
        .build();

    s3Client.putBucketAcl(PutBucketAclRequest.builder()
        .bucket(bucketName)
        .accessControlPolicy(policy)
        .build());

    log.info("Applied detailed access control policy to bucket '{}'", bucketName);
    return buildStepResultFromPolicy(policyConfig);
  }

  private Grantee buildSdkGrantee(S3BucketConfig.Grantee g) {

    Grantee.Builder builder = Grantee.builder()
        .type(Type.fromValue(g.getType()));

    if (g.getId() != null) builder.id(g.getId());
    if (g.getUri() != null) builder.uri(g.getUri());
    if (g.getEmailAddress() != null) builder.emailAddress(g.getEmailAddress());

    return builder.build();
  }

  private StepResult<S3Output> buildStepResultFromPolicy(S3BucketConfig.AccessControlPolicy policyConfig) {

    S3ProvisionedAclOwner ownerDto = new S3ProvisionedAclOwner(
        policyConfig.getOwner().getId(),
        policyConfig.getOwner().getDisplayName()
    );

    List<S3ProvisionedAclGrant> grantDtos = policyConfig.getGrants().stream()
        .map(g -> {
          String identifier = Optional.ofNullable(g.getGrantee().getId())
              .orElse(Optional.ofNullable(g.getGrantee().getUri())
                  .orElse(g.getGrantee().getEmailAddress()));

          S3ProvisionedAclGrantee grantee = new S3ProvisionedAclGrantee(
              g.getGrantee().getType(),
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
