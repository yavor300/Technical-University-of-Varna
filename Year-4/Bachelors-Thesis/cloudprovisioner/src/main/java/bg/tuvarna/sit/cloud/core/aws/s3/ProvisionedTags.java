package bg.tuvarna.sit.cloud.core.aws.s3;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class ProvisionedTags {

  private final Map<String, String> tags;

  @JsonAnyGetter
  public Map<String, String> getTags() {
    return tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProvisionedTags that)) return false;
    return Objects.equals(this.tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tags);
  }
}
