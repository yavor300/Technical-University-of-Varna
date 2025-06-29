package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvisionedLabels {

  private Map<String, String> labels = new HashMap<>();

  @JsonAnyGetter
  public Map<String, String> getLabels() {
    return labels;
  }

  @JsonAnySetter
  public void addTag(String key, String value) {
    this.labels.put(key, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o instanceof ProvisionedLabels other) {
      return Objects.equals(this.labels, other.labels);
    }

    if (o instanceof Map<?, ?> otherMap) {
      Map<String, String> casted = (Map<String, String>) otherMap;
      return Objects.equals(this.labels, casted);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(labels);
  }

  @Override
  public String toString() {
    return labels.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining(", ", "{", "}"));
  }
}
