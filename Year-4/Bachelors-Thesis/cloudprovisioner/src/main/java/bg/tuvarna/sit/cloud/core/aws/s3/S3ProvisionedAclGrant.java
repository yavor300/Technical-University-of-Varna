package bg.tuvarna.sit.cloud.core.aws.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class S3ProvisionedAclGrant {

    private S3ProvisionedAclGrantee grantee;
    private String permission;
}
