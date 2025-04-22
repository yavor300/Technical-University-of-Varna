package bg.tuvarna.sit.cloud.core.provisioner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class StepResultStateWriter<K extends Enum<K>> {

  private final File output;

  public StepResultStateWriter(String path) {
    this.output = new File(path);
  }

  public void write(List<StepResult<K>> results) {
    ObjectWriter mapper = new ObjectMapper().writerWithDefaultPrettyPrinter();
    try {
      mapper.writeValue(output, results);
      log.info("State written to '{}'", output.getAbsolutePath());
    } catch (IOException e) {
      log.error("Failed to write state to '{}'", output.getAbsolutePath(), e);
      throw new RuntimeException("Failed to write step result state", e);
    }
  }
}
