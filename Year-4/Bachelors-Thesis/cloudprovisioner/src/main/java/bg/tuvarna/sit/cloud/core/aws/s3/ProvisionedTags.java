package bg.tuvarna.sit.cloud.core.aws.s3;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ProvisionedTags {

  private final Map<String, String> tags;

  @JsonAnyGetter
  public Map<String, String> getTags() {
    return tags;
  }
}
