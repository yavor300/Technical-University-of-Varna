package bg.tuvarna.sit.cloud.credentials.model;

import bg.tuvarna.sit.cloud.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse {

  private final String code;
  private final String message;

  public ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public String toJson() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return "{\"code\":\"UNKNOWN\", \"message\":\"Failed to serialize error response\"}";
    }
  }
}
