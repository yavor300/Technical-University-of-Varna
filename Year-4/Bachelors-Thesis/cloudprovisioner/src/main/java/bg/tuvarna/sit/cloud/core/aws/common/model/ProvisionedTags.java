package bg.tuvarna.sit.cloud.core.aws.common.model;

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
public class ProvisionedTags {

  private Map<String, String> tags = new HashMap<>();

  @JsonAnyGetter
  public Map<String, String> getTags() {
    return tags;
  }

  @JsonAnySetter
  public void addTag(String key, String value) {
    this.tags.put(key, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o instanceof ProvisionedTags other) {
      return Objects.equals(this.tags, other.tags);
    }

    if (o instanceof Map<?, ?> otherMap) {
      Map<String, String> casted = (Map<String, String>) otherMap;
      return Objects.equals(this.tags, casted);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(tags);
  }

  @Override
  public String toString() {
    return tags.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining(", ", "{", "}"));
  }
}
