package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3OwnershipControlsStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;

public class S3OwnershipControlsResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketOwnershipControlsResponse response) {

    String actualValue = getOwnershipAsString(response);

    return StepResult.<S3Output>builder()
        .stepName(S3OwnershipControlsStep.class.getName())
        .put(S3Output.TYPE, actualValue)
        .build();
  }

  public static String getOwnershipAsString(GetBucketOwnershipControlsResponse response) {

    return  response
        .ownershipControls()
        .rules()
        .getFirst()
        .objectOwnershipAsString();
  }
}
