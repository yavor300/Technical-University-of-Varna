package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddons;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroups;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterAddonStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterNodeGroupStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterTaggingStep;
import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.provisioner.BaseStoredStateLoader;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.utils.NamedInjections;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class EksClusterStoredStateLoader extends BaseStoredStateLoader<EksClusterOutput> {

  private final ObjectMapper jsonMapper;

  @Inject
  public EksClusterStoredStateLoader(@Named(NamedInjections.JSON_MAPPER_SINGLE_VALUE_ARRAY) ObjectMapper mapper,
                             @Named(NamedInjections.JSON_MAPPER) ObjectMapper jsonMapper) {
    super(mapper);
    this.jsonMapper = jsonMapper;
  }

  @Override
  protected void transform(StepResult<EksClusterOutput> result) {

    if (result.getStepName().equals(EksClusterTaggingStep.class.getName())) {
      Object raw = result.getOutputs().get(EksClusterOutput.TAGS);
      ProvisionedTags tags = jsonMapper.convertValue(raw, ProvisionedTags.class);
      if (tags != null) {
        result.getOutputs().put(EksClusterOutput.TAGS, tags);
      }
    }

    if (result.getStepName().equals(EksClusterStep.class.getName())) {
      Object raw = result.getOutputs().get(EksClusterOutput.SUBNETS);
      SubnetIdSet subnets = jsonMapper.convertValue(raw, SubnetIdSet.class);
      if (subnets != null) {
        result.getOutputs().put(EksClusterOutput.SUBNETS, subnets);
      }
    }

    if (result.getStepName().equals(EksClusterAddonStep.class.getName())) {

      Object raw = result.getOutputs().get(EksClusterOutput.ADDONS);
      EksClusterAddons addons = jsonMapper.convertValue(raw, EksClusterAddons.class);
      if (addons != null) {
        result.getOutputs().put(EksClusterOutput.ADDONS, addons);
      }
    }

    if (result.getStepName().equals(EksClusterNodeGroupStep.class.getName())) {
      Object raw = result.getOutputs().get(EksClusterOutput.NODE_GROUPS);
      EksClusterNodeGroups nodeGroups = jsonMapper.convertValue(raw, EksClusterNodeGroups.class);
      if (nodeGroups != null) {
        result.getOutputs().put(EksClusterOutput.NODE_GROUPS, nodeGroups);
      }
    }
  }
}
