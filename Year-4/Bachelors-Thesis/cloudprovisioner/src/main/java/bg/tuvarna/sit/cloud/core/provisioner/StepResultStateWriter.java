package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.StepResultStateWriteException;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class StepResultStateWriter<K extends Enum<K>> {

  private final ObjectWriter objectWriter;

  @Inject
  public StepResultStateWriter(@Named("defaultPrettyPrinter") ObjectWriter objectWriter) {
    this.objectWriter = objectWriter;
  }

  public void write(File output, List<StepResult<K>> results) {
    try {
      objectWriter.writeValue(output, results);
      log.info("State written to '{}'", output.getAbsolutePath());
    } catch (IOException e) {
      String message = "Failed to write state to '%s'".formatted(output.getAbsolutePath());
      // TODO [Enhancement] Add prefix to the log for provisioning lib on debug
      log.debug(message, output.getAbsolutePath(), e);
      throw new StepResultStateWriteException(message, e);
    }
  }
}
