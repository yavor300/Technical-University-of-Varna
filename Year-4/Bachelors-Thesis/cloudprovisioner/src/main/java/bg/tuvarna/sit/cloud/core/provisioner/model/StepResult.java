package bg.tuvarna.sit.cloud.core.provisioner.model;

import bg.tuvarna.sit.cloud.utils.EnumToStringKeySerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StepResult<?> that = (StepResult<?>) o;

    if (!Objects.equals(stepName, that.stepName)) return false;
    if (outputs.size() != that.outputs.size()) return false;

    for (Map.Entry<K, Object> entry : outputs.entrySet()) {
      Object thatValue = that.outputs.get(entry.getKey());
      if (!Objects.equals(entry.getValue(), thatValue)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {

    return Objects.hash(stepName, outputs);
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

    public StepResult<K> build() {
      return instance;
    }
  }
}
