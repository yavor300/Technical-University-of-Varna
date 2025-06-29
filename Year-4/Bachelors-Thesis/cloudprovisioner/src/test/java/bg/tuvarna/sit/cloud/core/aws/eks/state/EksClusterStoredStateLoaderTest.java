package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddons;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroups;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.utils.TempFileUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static org.gradle.internal.impldep.org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EksClusterStoredStateLoaderTest {

  private ObjectMapper standardMapper;
  private ObjectMapper jsonMapper;
  private EksClusterStoredStateLoader loader;

  @BeforeEach
  void setUp() {
    standardMapper = new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    jsonMapper = new ObjectMapper();
    loader = new EksClusterStoredStateLoader(standardMapper, jsonMapper);
  }

  @Test
  public void testLoad_TransformsTags() throws Exception {
    String json = """
          [{
            "stepName": "bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterTaggingStep",
            "outputs" : {
              "tags" : {
                "environment" : "dev",
                "team" : "storage"
              }
            }
          }]
        """;

    File file = TempFileUtil.writeJsonToTempFile(json);
    List<StepResult<EksClusterOutput>> results = loader.load(file, EksClusterOutput.class);

    assertEquals(1, results.size());
    Object tags = results.getFirst().getOutputs().get(EksClusterOutput.TAGS);
    assertTrue(tags instanceof ProvisionedTags);
    assertEquals("dev", ((ProvisionedTags) tags).getTags().get("environment"));
  }

  @Test
  public void testLoad_TransformsSubnets() throws Exception {

    String json = """
          [{
            "stepName": "bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterStep",
            "outputs": {
              "subnets": ["subnet-123", "subnet-456"]
            }
          }]
        """;

    File file = TempFileUtil.writeJsonToTempFile(json);
    List<StepResult<EksClusterOutput>> results = loader.load(file, EksClusterOutput.class);

    Object subnets = results.getFirst().getOutputs().get(EksClusterOutput.SUBNETS);
    assertTrue(subnets instanceof SubnetIdSet);
    assertTrue(((SubnetIdSet) subnets).getSubnetIds().contains("subnet-123"));
  }

  @Test
  public void testLoad_TransformsAddons() throws Exception {
    String json = """
          [{
            "stepName": "bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterAddonStep",
            "outputs": {
                "addons": [
                  {"name": "vpc-cni", "version": "v1.12.0"}
                ]
            }
          }]
        """;

    File file = TempFileUtil.writeJsonToTempFile(json);
    List<StepResult<EksClusterOutput>> results = loader.load(file, EksClusterOutput.class);

    Object addons = results.getFirst().getOutputs().get(EksClusterOutput.ADDONS);
    assertTrue(addons instanceof EksClusterAddons);
    assertEquals("vpc-cni", ((EksClusterAddons) addons).getAddons().getFirst().getName());
  }

  @Test
  public void testLoad_TransformsNodeGroups() throws Exception {

    String json = """
          [{
            "stepName": "bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterNodeGroupStep",
            "outputs": {
                "nodeGroups": [
                  {"name": "ng-1", "instanceType": "t3.medium"}
                ]
            }
          }]
        """;

    File file = TempFileUtil.writeJsonToTempFile(json);
    List<StepResult<EksClusterOutput>> results = loader.load(file, EksClusterOutput.class);

    Object groups = results.getFirst().getOutputs().get(EksClusterOutput.NODE_GROUPS);
    assertTrue(groups instanceof EksClusterNodeGroups);
    assertEquals("ng-1", ((EksClusterNodeGroups) groups).getNodeGroups().getFirst().getName());
  }

}
