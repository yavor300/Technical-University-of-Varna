package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAcl;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3AclType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclGrant;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclGrantee;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclOwner;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3AclStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.Owner;

import java.util.List;

public class S3AclResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketAclResponse response, S3AclType cannedAcl) {

    Owner owner = response.owner();
    S3ProvisionedAclOwner ownerDto = new S3ProvisionedAclOwner(owner.id(), owner.displayName());

    List<S3ProvisionedAclGrant> grantDtos = response.grants().stream()
        .map(grant -> {
          Grantee grantee = grant.grantee();
          String identifier = grantee.id();
          String uri = grantee.uri();
          String emailAddress = grantee.emailAddress();
          return new S3ProvisionedAclGrant(
              new S3ProvisionedAclGrantee(grantee.typeAsString(), identifier, uri, emailAddress),
              grant.permissionAsString()
          );
        })
        .toList();

    return StepResult.<S3Output>builder()
        .stepName(S3AclStep.class.getName())
        .put(S3Output.VALUE_NODE, new S3ProvisionedAcl(ownerDto, grantDtos, cannedAcl))
        .build();
  }
}
