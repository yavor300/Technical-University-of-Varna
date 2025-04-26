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

    S3AclType acl = config.getAcl();
    if (acl != null) {
      PutBucketAclRequest request = PutBucketAclRequest.builder()
          .bucket(bucketName)
          .acl(acl.toSdkAcl())
          .build();

      s3Client.putBucketAcl(request);

      String aclValue = acl.getValue();
      log.info("Set canned ACL '{}' for bucket '{}'", aclValue, bucketName);

      return result.put(S3Output.VALUE_NODE, aclValue).build();
    }

    return result.build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    S3BucketConfig.AccessControlPolicy policy = config.getAccessControlPolicy();
    if (policy != null) {
      return buildStepResultFromPolicy(policy);
    }

    S3AclType acl = config.getAcl();
    String stepName = this.getClass().getName();

    StepResult.Builder<S3Output> resultBuilder = StepResult.<S3Output>builder()
        .stepName(stepName);

    if (acl != null) {
      return resultBuilder
          .put(S3Output.VALUE_NODE, acl.getValue())
          .build();
    }

    return resultBuilder.build();
  }

  private StepResult<S3Output> applyAccessControlPolicy(S3Client s3Client, String bucketName, S3BucketConfig config) {

    S3BucketConfig.AccessControlPolicy policyConfig = config.getAccessControlPolicy();

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
