// TODO [Maybe] Need to wait to provision cluster and then change
//package bg.tuvarna.sit.cloud.core.aws.eks.step;
//
//import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
//import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
//import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
//import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAuthenticationMode;
//import bg.tuvarna.sit.cloud.core.aws.eks.step.base.EksClusterProvisionStep;
//import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
//import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
//import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
//import jakarta.inject.Inject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.hc.core5.http.HttpStatus;
//import software.amazon.awssdk.services.eks.model.AuthenticationMode;
//import software.amazon.awssdk.services.eks.model.EksException;
//
//@Slf4j
//@ProvisionOrder(3)
//public class EksClusterAuthenticationModeStep extends EksClusterProvisionStep {
//
//  private final StepResult<EksClusterOutput> metadata;
//
//  @Inject
//  protected EksClusterAuthenticationModeStep(EksSafeClient eks, EksClusterConfig config,
//                                             StepResult<EksClusterOutput> metadata) {
//    super(eks, config);
//    this.metadata = metadata;
//  }
//
//  @Override
//  public StepResult<EksClusterOutput> apply() {
//
//    String step = this.getClass().getName();
//    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
//
//    if (clusterName == null || clusterName.isEmpty()) {
//      log.warn("Missing cluster name in metadata — skipping authentication mode update.");
//      return StepResult.<EksClusterOutput>builder().stepName(step).build();
//    }
//
//    EksClusterAuthenticationMode authenticationMode = config.getAuthenticationMode();
//    eks.updateAuthenticationMode(clusterName, authenticationMode);
//    log.info("Authentication mode for EKS cluster '{}' set to '{}'", clusterName, authenticationMode);
//
//    return StepResult.<EksClusterOutput>builder()
//        .stepName(step)
//        .put(EksClusterOutput.AUTHENTICATION_MODE, authenticationMode.toString())
//        .build();
//  }
//
//
//  @Override
//  public StepResult<EksClusterOutput> generateDesiredState() {
//
//    String step = this.getClass().getName();
//
//    if (config.isToDelete()) {
//
//      return StepResult.<EksClusterOutput>builder()
//          .stepName(step)
//          .build();
//    }
//
//    EksClusterAuthenticationMode desiredMode = config.getAuthenticationMode();
//
//    return StepResult.<EksClusterOutput>builder()
//        .stepName(step)
//        .put(EksClusterOutput.AUTHENTICATION_MODE, desiredMode.toString())
//        .build();
//  }
//
//  @Override
//  public StepResult<EksClusterOutput> getCurrentState() {
//
//    String step = this.getClass().getName();
//    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
//
//    if (clusterName == null || clusterName.isEmpty()) {
//      log.warn("Missing cluster name in metadata — skipping authentication mode fetch.");
//      return StepResult.<EksClusterOutput>builder().stepName(step).build();
//    }
//
//    try {
//
//      AuthenticationMode mode = eks.getAuthenticationMode(clusterName);
//      log.info("Fetched authentication mode '{}' for EKS cluster '{}'", mode.toString(), clusterName);
//
//      return StepResult.<EksClusterOutput>builder()
//          .stepName(step)
//          .put(EksClusterOutput.AUTHENTICATION_MODE, mode.toString())
//          .build();
//
//    } catch (CloudResourceStepException e) {
//      if (e.getCause() instanceof EksException eksException) {
//        if (eksException.statusCode() == HttpStatus.SC_NOT_FOUND) {
//          log.info("EKS cluster '{}' not found when retrieving authentication mode.", clusterName);
//          return StepResult.<EksClusterOutput>builder().stepName(step).build();
//        }
//      }
//      throw e;
//    }
//  }
//
//  @Override
//  public StepResult<EksClusterOutput> destroy(boolean enforcePreventDestroy) {
//
//    String step = this.getClass().getName();
//    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
//
//    if (clusterName == null || clusterName.isEmpty()) {
//      log.warn("Missing cluster name in metadata. Skipping authentication mode reset.");
//      return StepResult.<EksClusterOutput>builder().stepName(step).build();
//    }
//
//    eks.updateAuthenticationMode(clusterName, EksClusterAuthenticationMode.API);
//    log.info("Reset authentication mode for EKS cluster '{}' to default (API)", clusterName);
//
//    return StepResult.<EksClusterOutput>builder()
//        .stepName(step)
//        .put(EksClusterOutput.AUTHENTICATION_MODE, EksClusterAuthenticationMode.API.toString())
//        .build();
//  }
//
//  @Override
//  public StepResult<EksClusterOutput> revert(StepResult<EksClusterOutput> previous) {
//
//    String step = this.getClass().getName();
//    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
//
//    if (clusterName == null || clusterName.isEmpty()) {
//      log.warn("Missing cluster name in metadata — skipping authentication mode revert.");
//      return StepResult.<EksClusterOutput>builder().stepName(step).build();
//    }
//
//    String modeValue = (String) previous.getOutputs().get(EksClusterOutput.AUTHENTICATION_MODE);
//
//    EksClusterAuthenticationMode revertMode = (modeValue == null || modeValue.isBlank())
//        ? EksClusterAuthenticationMode.API
//        : EksClusterAuthenticationMode.valueOf(modeValue);
//
//    eks.updateAuthenticationMode(clusterName, revertMode);
//    log.info("Reverted authentication mode for EKS cluster '{}' to '{}'", clusterName, revertMode);
//
//    return StepResult.<EksClusterOutput>builder()
//        .stepName(step)
//        .put(EksClusterOutput.AUTHENTICATION_MODE, revertMode.toString())
//        .build();
//  }
//
//}
