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
}
