package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.config.S3BucketConfig;
import software.amazon.awssdk.services.s3.S3Client;

public interface S3ProvisionStep {

    void apply(S3Client s3Client, S3BucketConfig config);
}
