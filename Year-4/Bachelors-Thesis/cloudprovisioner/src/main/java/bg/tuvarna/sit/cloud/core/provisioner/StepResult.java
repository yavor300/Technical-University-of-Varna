package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.utils.EnumToStringKeySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class StepResult<K extends Enum<K>> {

  private String stepName;

  @JsonSerialize(keyUsing = EnumToStringKeySerializer.class)
  private Map<K, Object> outputs = new LinkedHashMap<>();

  @JsonIgnore
  private boolean isVoid = true;

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
      instance.setVoid(false);
      return this;
    }

    public Builder<K> putAll(Map<K, Object> map) {
      if (map != null && !map.isEmpty()) {
        instance.getOutputs().putAll(map);
        instance.setVoid(false);
      }
      return this;
    }

    public StepResult<K> build() {
      return instance;
    }
  }
}
