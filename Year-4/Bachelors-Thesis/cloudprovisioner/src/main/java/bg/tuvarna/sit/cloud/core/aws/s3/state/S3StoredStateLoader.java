package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAcl;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3AclStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3TaggingStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.core.provisioner.BaseStoredStateLoader;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class S3StoredStateLoader extends BaseStoredStateLoader<S3Output> {

  private final ObjectMapper jsonMapper;

  @Inject
  public S3StoredStateLoader(@Named("jsonMapperAcceptSingleValueAsArray") ObjectMapper mapper,
                             @Named("jsonMapper") ObjectMapper jsonMapper) {
    super(mapper);
    this.jsonMapper = jsonMapper;
  }

  @Override
  protected void transform(StepResult<S3Output> result) {

    if (result.getStepName().equals(S3TaggingStep.class.getName())) {
      Object raw = result.getOutputs().get(S3Output.VALUE_NODE);
      S3ProvisionedTags tags = jsonMapper.convertValue(raw, S3ProvisionedTags.class);
      if (tags != null) {
        result.getOutputs().put(S3Output.VALUE_NODE, tags);
      }
    }

    if (result.getStepName().equals(S3AclStep.class.getName())) {
      Object raw = result.getOutputs().get(S3Output.VALUE_NODE);
      S3ProvisionedAcl acl = jsonMapper.convertValue(raw, S3ProvisionedAcl.class);
      if (acl != null) {
        result.getOutputs().put(S3Output.VALUE_NODE, acl);
      }
    }
  }
}
