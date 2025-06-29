package bg.tuvarna.sit.cloud.utils;

import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerFailureResponse;
import bg.tuvarna.sit.cloud.core.provisioner.model.ErrorCode;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import org.slf4j.Logger;

import java.util.concurrent.ExecutionException;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Singleton
public class Slf4jLoggingUtil {

  public static final String DEBUG_PREFIX = "[cloud-debugger] - ";

  private final ObjectMapper yaml;

  @Inject
  public Slf4jLoggingUtil(@Named(NamedInjections.YAML_MAPPER) ObjectMapper yaml) {
    this.yaml = yaml;
  }

  public static boolean isJsonLoggingEnabled() {
    return "json".equalsIgnoreCase(EnvVar.LOG_FORMAT.getValue());
  }

  public void logError(Logger log, ErrorCode code, Throwable e) {

    CloudProvisionerFailureResponse error = new CloudProvisionerFailureResponse(code);

    error.getDetails().add(e.getMessage());
    if (e.getCause() instanceof CloudResourceStepException stepException) {
      error.getDetails().addAll(stepException.getMessageDetails());
    }

    if (e.getCause() instanceof CloudProvisioningTerminationException termination) {
      if (termination.getCause() instanceof CloudResourceStepException stepException) {
        error.getDetails().addAll(stepException.getMessageDetails());
      }
    }

    if (e.getCause() instanceof ExecutionException executionException) {
      if (executionException.getCause() instanceof CloudResourceStepException stepException) {
        error.getDetails().addAll(stepException.getMessageDetails());
      }
    }

    if (e.getCause() instanceof JsonMappingException jsonException) {
      error.getDetails().add(jsonException.getMessage());
    }

    log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", code.getMessage(), e);
    if (isJsonLoggingEnabled()) {
      log.error(code.getMessage(), keyValue("error", error));
    } else {
      try {
        log.error("{}\n{}", code.getMessage(), yaml.writeValueAsString(error));
      } catch (JsonProcessingException ex) {
        log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "Failed to serialize error details to YAML format.", ex);
      }
    }
  }
}
