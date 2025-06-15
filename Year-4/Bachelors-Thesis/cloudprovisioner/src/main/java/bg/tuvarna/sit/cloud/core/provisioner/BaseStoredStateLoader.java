package bg.tuvarna.sit.cloud.core.provisioner;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class BaseStoredStateLoader<K extends Enum<K>> {

  private final ObjectMapper mapper;

  protected BaseStoredStateLoader(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public List<StepResult<K>> load(File file, Class<K> keyEnumClass) throws IOException {

    JavaType resultType = mapper.getTypeFactory()
        .constructParametricType(StepResult.class, keyEnumClass);

    JavaType listType = mapper.getTypeFactory()
        .constructCollectionType(List.class, resultType);

    List<StepResult<K>> results = mapper.readValue(file, listType);

    for (StepResult<K> result : results) {
      if (result.getOutputs() != null && !result.getOutputs().isEmpty()) {
        result.setVoid(false);
      }
      transform(result);
    }

    return results;
  }

  protected abstract void transform(StepResult<K> result);
}
