package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class StepResult {

  private String stepName;
  private Map<String, Object> outputs = new LinkedHashMap<>();

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final StepResult instance = new StepResult();

    public Builder stepName(String stepName) {
      instance.setStepName(stepName);
      return this;
    }

    public Builder put(String key, Object value) {
      instance.getOutputs().put(key, value);
      return this;
    }

    public Builder putAll(Map<String, Object> map) {
      instance.getOutputs().putAll(map);
      return this;
    }

    public StepResult build() {
      return instance;
    }
  }
}
