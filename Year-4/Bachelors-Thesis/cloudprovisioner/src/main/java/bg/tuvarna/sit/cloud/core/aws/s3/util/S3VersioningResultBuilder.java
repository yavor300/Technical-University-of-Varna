package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3VersioningStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;

import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;

public class S3VersioningResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketVersioningResponse response) {

    return StepResult.<S3Output>builder()
        .stepName(S3VersioningStep.class.getName())
        .put(S3Output.VERSIONING_STATUS, response.status().toString())
        .build();
  }
}
