package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.StepResultStateWriteException;
import bg.tuvarna.sit.cloud.utils.NamedInjections;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Singleton
public class StepResultStateWriter<K extends Enum<K>> {

  private final ObjectWriter objectWriter;

  @Inject
  public StepResultStateWriter(@Named(NamedInjections.DEFAULT_PRETTY_PRINTER) ObjectWriter objectWriter) {
    this.objectWriter = objectWriter;
  }

  public void write(File output, List<StepResult<K>> results) throws StepResultStateWriteException {
    try {
      objectWriter.writeValue(output, results);
      log.info("State written to '{}'", output.getAbsolutePath());
    } catch (IOException e) {
      String message = "Failed to write state to '%s'".formatted(output.getAbsolutePath());
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, output.getAbsolutePath(), e);
      throw new StepResultStateWriteException(message, e);
    }
  }
}
