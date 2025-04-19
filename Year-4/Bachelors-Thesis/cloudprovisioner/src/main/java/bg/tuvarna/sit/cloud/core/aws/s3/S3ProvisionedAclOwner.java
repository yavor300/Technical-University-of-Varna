package bg.tuvarna.sit.cloud.core.aws.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class S3ProvisionedAclOwner {

    private String id;
    private String displayName;
}
