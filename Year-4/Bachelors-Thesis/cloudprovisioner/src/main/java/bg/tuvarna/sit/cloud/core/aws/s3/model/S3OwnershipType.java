package bg.tuvarna.sit.cloud.core.aws.s3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import software.amazon.awssdk.services.s3.model.ObjectOwnership;

@Getter
public enum S3OwnershipType {

  BUCKET_OWNER_PREFERRED("BucketOwnerPreferred"),
  OBJECT_WRITER("ObjectWriter"),
  BUCKET_OWNER_ENFORCED("BucketOwnerEnforced");

  private final String value;

  S3OwnershipType(String value) {
    this.value = value;
  }

  public ObjectOwnership toSdkType() {
    return ObjectOwnership.fromValue(value);
  }

  @JsonCreator
  public static S3OwnershipType fromValue(String value) {
    for (S3OwnershipType type : values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid ownership control value: " + value);
  }
}
