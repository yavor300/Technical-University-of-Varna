package bg.tuvarna.sit.cloud.core.aws.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class S3ProvisionedAclGrantee {

    private String type;
    private String identifier;
}
