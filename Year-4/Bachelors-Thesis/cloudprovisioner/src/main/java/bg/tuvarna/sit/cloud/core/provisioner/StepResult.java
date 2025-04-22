package bg.tuvarna.sit.cloud.core.provisioner;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class StepResult<K extends Enum<K>> {

  private String stepName;

  @JsonSerialize(keyUsing = ToStringSerializer.class)
  private Map<K, Object> outputs = new LinkedHashMap<>();

  public static <K extends Enum<K>> Builder<K> builder() {
    return new Builder<>();
  }

  public static class Builder<K extends Enum<K>> {
    private final StepResult<K> instance = new StepResult<>();

    public Builder<K> stepName(String stepName) {
      instance.setStepName(stepName);
      return this;
    }

    public Builder<K> put(K key, Object value) {
      instance.getOutputs().put(key, value);
      return this;
    }

    public Builder<K> putAll(Map<K, Object> map) {
      instance.getOutputs().putAll(map);
      return this;
    }

    public StepResult<K> build() {
      return instance;
    }
  }
}
