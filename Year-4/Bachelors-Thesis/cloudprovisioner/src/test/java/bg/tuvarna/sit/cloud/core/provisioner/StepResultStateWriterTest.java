package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.utils.StepResultStateWriter;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.StepResultStateWriteException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StepResultStateWriterTest {

  @Mock
  private ObjectWriter objectWriter;

  private StepResultStateWriter<S3Output> writer;

  @BeforeEach
  void setUp() {
    writer = new StepResultStateWriter<>(objectWriter);
  }

  @Test
  void write_shouldSerializeResultsToFile() throws Exception {
    File outputFile = mock(File.class);
    when(outputFile.getAbsolutePath()).thenReturn("/tmp/state.json");

    StepResult<S3Output> result = StepResult.<S3Output>builder()
        .stepName("step-1")
        .put(S3Output.NAME, "my-bucket")
        .build();

    List<StepResult<S3Output>> results = List.of(result);

    writer.write(outputFile, results);

    verify(objectWriter).writeValue(outputFile, results);
  }

  @Test
  void write_shouldThrowException_whenSerializationFails() throws Exception {
    File outputFile = mock(File.class);
    when(outputFile.getAbsolutePath()).thenReturn("/tmp/fail.json");

    StepResult<S3Output> result = StepResult.<S3Output>builder()
        .stepName("step-1")
        .put(S3Output.NAME, "my-bucket")
        .build();

    List<StepResult<S3Output>> results = List.of(result);

    IOException ioException = new IOException("Disk error");
    doThrow(ioException).when(objectWriter).writeValue(outputFile, results);

    assertThatThrownBy(() -> writer.write(outputFile, results))
        .isInstanceOf(StepResultStateWriteException.class)
        .hasMessageContaining("Failed to write state to '/tmp/fail.json'")
        .hasCause(ioException);
  }
}
