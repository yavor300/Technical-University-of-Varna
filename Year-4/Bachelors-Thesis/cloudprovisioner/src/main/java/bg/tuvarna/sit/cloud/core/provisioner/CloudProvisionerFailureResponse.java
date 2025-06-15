package bg.tuvarna.sit.cloud.common;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

  private final String code;
  private final List<String> details;

  public ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.details = new ArrayList<>();
  }
}
