package bg.tuvarna.sit.cloud.core.aws.eks.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAmiType;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroup;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroups;
import bg.tuvarna.sit.cloud.core.aws.eks.model.ProvisionedLabels;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.eks.model.EksException;

@ExtendWith(MockitoExtension.class)
class EksClusterNodeGroupStepTest {

  @Mock
  private EksSafeClient eks;

  @Mock
  private EksClusterConfig config;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterNodeGroupStep step;

  @BeforeEach
  void setup() {

    metadata = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterPersistentMetadataStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .build();
  }

  private void initStep() {
    step = new EksClusterNodeGroupStep(eks, config, metadata);
  }

  @Test
  void testApply_CreatesNewNodeGroup() {

    EksClusterConfig.EksNodeGroupConfig ng = new EksClusterConfig.EksNodeGroupConfig();
    ng.setName("ng1");
    ng.setInstanceType("t3.medium");
    ng.setMinSize(1);
    ng.setMaxSize(3);
    ng.setDesiredSize(2);
    ng.setNodeRoleArn("arn:aws:iam::1234567890:role/node-role");
    ng.setSubnets(List.of("subnet-1", "subnet-2"));

    when(config.getNodeGroups()).thenReturn(List.of(ng));
    when(eks.getNodeGroups("test-cluster")).thenReturn(new EksClusterNodeGroups());

    initStep();

    StepResult<EksClusterOutput> result = step.apply();

    verify(eks).createNodeGroup(eq("test-cluster"), eq(ng));
    verify(eks).waitUntilNodeGroupsActive(eq("test-cluster"), anyList(), eq(Duration.ofMinutes(10)));
    verify(eks, times(2)).getNodeGroups("test-cluster");

    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().containsKey(EksClusterOutput.NODE_GROUPS));
  }

  @Test
  void testApply_shouldUpdateNodeGroup_whenConfigDiffers() {

    String clusterName = "test-cluster";

    StepResult<EksClusterOutput> metadata = StepResult.<EksClusterOutput>builder()
        .stepName("meta")
        .put(EksClusterOutput.NAME, clusterName)
        .build();

    EksClusterConfig.EksNodeGroupConfig newConfig = new EksClusterConfig.EksNodeGroupConfig();
    newConfig.setName("ng-1");
    newConfig.setInstanceType("t3.medium");
    newConfig.setDesiredSize(2);
    newConfig.setMinSize(1);
    newConfig.setMaxSize(3);
    newConfig.setNodeRoleArn("arn:aws:iam::123456:role/role");
    newConfig.setSubnets(List.of("subnet-1"));
    newConfig.setAmiType(EksClusterAmiType.AL2_X86_64);
    newConfig.setReleaseVersion("v1");
    newConfig.setDiskSize(20);
    newConfig.setLabels(Map.of("env", "dev"));
    newConfig.setTags(Map.of("team", "devops"));

    when(config.getNodeGroups()).thenReturn(List.of(newConfig));

    EksClusterNodeGroup existing = new EksClusterNodeGroup(
        "ng-1", "t3.small", 1, 1, 3, "arn:aws:iam::123456:role/role",
        new SubnetIdSet(List.of("subnet-1")), EksClusterAmiType.AL2_X86_64, "v1", 20,
        new ProvisionedLabels(Map.of()), new ProvisionedTags(Map.of())
    );

    when(eks.getNodeGroups(clusterName)).thenReturn(new EksClusterNodeGroups(List.of(existing)));
    doNothing().when(eks).updateNodeGroup(eq(clusterName), any());
    doNothing().when(eks).waitUntilNodeGroupsActive(eq(clusterName), any(), any());
    when(eks.getNodeGroups(clusterName)).thenReturn(new EksClusterNodeGroups(List.of(existing)));

    EksClusterNodeGroupStep step = new EksClusterNodeGroupStep(eks, config, metadata);
    StepResult<EksClusterOutput> result = step.apply();

    assertNotNull(result);
    verify(eks, times(1)).updateNodeGroup(eq(clusterName), any());
  }

  @Test
  void testApply_shouldSkipUpdate_whenConfigMatches() {

    String clusterName = "test-cluster";

    StepResult<EksClusterOutput> metadata = StepResult.<EksClusterOutput>builder()
        .stepName("meta")
        .put(EksClusterOutput.NAME, clusterName)
        .build();

    EksClusterConfig.EksNodeGroupConfig matchingConfig = new EksClusterConfig.EksNodeGroupConfig();
    matchingConfig.setName("ng-1");
    matchingConfig.setInstanceType("t3.medium");
    matchingConfig.setDesiredSize(2);
    matchingConfig.setMinSize(1);
    matchingConfig.setMaxSize(3);
    matchingConfig.setNodeRoleArn("arn:aws:iam::123456:role/role");
    matchingConfig.setSubnets(List.of("subnet-1"));
    matchingConfig.setAmiType(EksClusterAmiType.AL2_X86_64);
    matchingConfig.setReleaseVersion("v1");
    matchingConfig.setDiskSize(20);
    matchingConfig.setLabels(Map.of("env", "dev"));
    matchingConfig.setTags(Map.of("team", "devops"));

    EksClusterNodeGroup existing = new EksClusterNodeGroup(
        "ng-1", "t3.medium", 2, 1, 3, "arn:aws:iam::123456:role/role",
        new SubnetIdSet(List.of("subnet-1")), EksClusterAmiType.AL2_X86_64, "v1", 20,
        new ProvisionedLabels(Map.of("env", "dev")),
        new ProvisionedTags(Map.of("team", "devops"))
    );

    when(config.getNodeGroups()).thenReturn(List.of(matchingConfig));
    when(eks.getNodeGroups(clusterName)).thenReturn(new EksClusterNodeGroups(List.of(existing)));
    doNothing().when(eks).waitUntilNodeGroupsActive(eq(clusterName), any(), any());
    when(eks.getNodeGroups(clusterName)).thenReturn(new EksClusterNodeGroups(List.of(existing)));

    EksClusterNodeGroupStep step = new EksClusterNodeGroupStep(eks, config, metadata);
    StepResult<EksClusterOutput> result = step.apply();

    assertNotNull(result);
    verify(eks, never()).updateNodeGroup(any(), any());
    verify(eks, never()).createNodeGroup(any(), any());
  }

  @Test
  void testGenerateDesiredState_shouldReturnDesiredState_whenNodeGroupsPresent() {

    EksClusterConfig.EksNodeGroupConfig configGroup = new EksClusterConfig.EksNodeGroupConfig();
    configGroup.setName("ng-1");
    configGroup.setInstanceType("t3.medium");
    configGroup.setDesiredSize(2);
    configGroup.setMinSize(1);
    configGroup.setMaxSize(3);
    configGroup.setNodeRoleArn("arn:aws:iam::123456:role/role");
    configGroup.setSubnets(List.of("subnet-1", "subnet-2"));
    configGroup.setAmiType(EksClusterAmiType.AL2_X86_64);
    configGroup.setReleaseVersion("1.27");
    configGroup.setDiskSize(20);
    configGroup.setLabels(Map.of("env", "test"));
    configGroup.setTags(Map.of("team", "devops"));

    when(config.getNodeGroups()).thenReturn(List.of(configGroup));

    initStep();
    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertNotNull(result);
    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().containsKey(EksClusterOutput.NODE_GROUPS));

    EksClusterNodeGroups nodeGroups = (EksClusterNodeGroups) result.getOutputs().get(EksClusterOutput.NODE_GROUPS);
    assertEquals(1, nodeGroups.getNodeGroups().size());

    EksClusterNodeGroup generated = nodeGroups.getNodeGroups().getFirst();
    assertEquals("ng-1", generated.getName());
    assertEquals("t3.medium", generated.getInstanceType());
    assertEquals(2, generated.getDesiredSize());
    assertEquals(1, generated.getMinSize());
    assertEquals(3, generated.getMaxSize());
    assertEquals("arn:aws:iam::123456:role/role", generated.getNodeRoleArn());
    assertEquals(List.of("subnet-1", "subnet-2"), generated.getSubnetIdSet().getSubnetIds());
    assertEquals(EksClusterAmiType.AL2_X86_64, generated.getAmiType());
    assertEquals("1.27", generated.getReleaseVersion());
    assertEquals(20, generated.getDiskSize());
    assertEquals(Map.of("env", "test"), generated.getLabels().getLabels());
    assertEquals(Map.of("team", "devops"), generated.getTags().getTags());
  }

  @Test
  void testGetCurrentState_shouldReturnNodeGroups_whenClusterExists() {

    EksClusterNodeGroup group = new EksClusterNodeGroup();
    group.setName("ng-1");
    EksClusterNodeGroups nodeGroups = new EksClusterNodeGroups(List.of(group));

    when(eks.getNodeGroups("test-cluster")).thenReturn(nodeGroups);

    initStep();
    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().containsKey(EksClusterOutput.NODE_GROUPS));

    EksClusterNodeGroups actual = (EksClusterNodeGroups) result.getOutputs().get(EksClusterOutput.NODE_GROUPS);
    assertEquals(1, actual.getNodeGroups().size());
    assertEquals("ng-1", actual.getNodeGroups().getFirst().getName());
  }

  @Test
  void testGetCurrentState_shouldReturnEmpty_whenClusterNotFound() {

    AwsServiceException eksException = EksException.builder()
        .message("Cluster not found")
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .build();

    CloudResourceStepException wrapped = new CloudResourceStepException("wrapped", eksException);

    when(eks.getNodeGroups("test-cluster")).thenThrow(wrapped);

    initStep();
    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testGetCurrentState_shouldThrow_whenUnexpectedError() {

    CloudResourceStepException err = new CloudResourceStepException("some error");
    when(eks.getNodeGroups("test-cluster")).thenThrow(err);

    initStep();
    assertThrows(CloudResourceStepException.class, () -> step.getCurrentState());
  }

  @Test
  void testDestroy_shouldDeleteNodeGroupsIfPresent() {

    initStep();

    EksClusterNodeGroup ng1 = new EksClusterNodeGroup();
    ng1.setName("ng-1");

    EksClusterNodeGroup ng2 = new EksClusterNodeGroup();
    ng2.setName("ng-2");

    EksClusterNodeGroups groups = new EksClusterNodeGroups(List.of(ng1, ng2));

    when(eks.getNodeGroups("test-cluster")).thenReturn(groups);

    StepResult<EksClusterOutput> result = step.destroy(false);

    verify(eks).deleteNodeGroup("test-cluster", "ng-1");
    verify(eks).deleteNodeGroup("test-cluster", "ng-2");
    verify(eks).waitUntilNodeGroupsDeleted("test-cluster", groups, Duration.ofMinutes(10));

    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testDestroy_shouldDoNothingIfNoNodeGroups() {

    initStep();

    EksClusterNodeGroups emptyGroups = new EksClusterNodeGroups(List.of());

    when(eks.getNodeGroups("test-cluster")).thenReturn(emptyGroups);

    StepResult<EksClusterOutput> result = step.destroy(false);

    verify(eks, never()).deleteNodeGroup(any(), any());
    verify(eks, never()).waitUntilNodeGroupsDeleted(any(), any(), any());

    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testDestroy_shouldHandleNullNodeGroups() {

    initStep();

    when(eks.getNodeGroups("test-cluster")).thenReturn(null);

    StepResult<EksClusterOutput> result = step.destroy(false);

    verify(eks, never()).deleteNodeGroup(any(), any());
    verify(eks, never()).waitUntilNodeGroupsDeleted(any(), any(), any());

    assertEquals(EksClusterNodeGroupStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testRevert_shouldDeleteUnexpectedNodeGroups() {

    initStep();

    EksClusterNodeGroup currentNg = new EksClusterNodeGroup();
    currentNg.setName("current-only");

    EksClusterNodeGroups current = new EksClusterNodeGroups(List.of(currentNg));
    EksClusterNodeGroups previous = new EksClusterNodeGroups(List.of());

    when(eks.getNodeGroups("test-cluster")).thenReturn(current);

    StepResult<EksClusterOutput> previousState = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterNodeGroupStep.class.getName())
        .put(EksClusterOutput.NODE_GROUPS, previous)
        .build();

    step.revert(previousState);

    verify(eks).deleteNodeGroup("test-cluster", "current-only");
  }

  @Test
  void testRevert_shouldUpdateMismatchedNodeGroups() {
    initStep();

    EksClusterNodeGroup current = new EksClusterNodeGroup();
    current.setName("ng-1");
    current.setInstanceType("t3.small");

    EksClusterNodeGroup previous = new EksClusterNodeGroup();
    previous.setName("ng-1");
    previous.setInstanceType("t3.large");

    EksClusterNodeGroups currentGroups = new EksClusterNodeGroups(List.of(current));
    EksClusterNodeGroups previousGroups = new EksClusterNodeGroups(List.of(previous));

    when(eks.getNodeGroups("test-cluster")).thenReturn(currentGroups).thenReturn(previousGroups);

    StepResult<EksClusterOutput> previousState = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterNodeGroupStep.class.getName())
        .put(EksClusterOutput.NODE_GROUPS, previousGroups)
        .build();

    step.revert(previousState);

    verify(eks).updateNodeGroup("test-cluster", previous);
  }

  @Test
  void testRevert_shouldCreateMissingNodeGroups() {
    initStep();

    EksClusterNodeGroup desired = new EksClusterNodeGroup();
    desired.setName("desired-only");
    desired.setInstanceType("t3.medium");
    desired.setDesiredSize(2);
    desired.setMinSize(1);
    desired.setMaxSize(3);
    desired.setNodeRoleArn("arn:aws:iam::123:role/EKSNodeRole");
    desired.setSubnetIdSet(new SubnetIdSet(List.of("subnet-1")));
    desired.setLabels(new ProvisionedLabels());
    desired.setTags(new ProvisionedTags());

    EksClusterNodeGroups previous = new EksClusterNodeGroups(List.of(desired));
    EksClusterNodeGroups current = new EksClusterNodeGroups(List.of());

    when(eks.getNodeGroups("test-cluster")).thenReturn(current).thenReturn(previous); // final get after revert

    StepResult<EksClusterOutput> previousState = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterNodeGroupStep.class.getName())
        .put(EksClusterOutput.NODE_GROUPS, previous)
        .build();

    step.revert(previousState);

    verify(eks).createNodeGroup(eq("test-cluster"), any(EksClusterConfig.EksNodeGroupConfig.class));
  }

}
