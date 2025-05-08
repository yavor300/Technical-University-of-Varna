package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionedAclGrant;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionedAclGrantee;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionedAclOwner;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3AclStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.Owner;

import java.util.List;
import java.util.Optional;

public class S3AclResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketAclResponse response) {

    Owner owner = response.owner();
    S3ProvisionedAclOwner ownerDto = new S3ProvisionedAclOwner(owner.id(), owner.displayName());

    List<S3ProvisionedAclGrant> grantDtos = response.grants().stream()
        .map(grant -> {
          Grantee grantee = grant.grantee();
          String identifier = Optional.ofNullable(grantee.id())
              .orElse(Optional.ofNullable(grantee.uri()).orElse(grantee.emailAddress()));
          return new S3ProvisionedAclGrant(
              new S3ProvisionedAclGrantee(grantee.typeAsString(), identifier),
              grant.permissionAsString()
          );
        })
        .toList();

    return StepResult.<S3Output>builder()
        .stepName(S3AclStep.class.getName())
        .put(S3Output.OWNER, ownerDto)
        .put(S3Output.GRANTS, grantDtos)
        .build();
  }
}
