package bg.tuvarna.sit.cloud.core.aws.s3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;

@Getter
public enum S3AclType {

  PRIVATE("private"),
  PUBLIC_READ("public-read"),
  PUBLIC_READ_WRITE("public-read-write"),
  AWS_EXEC_READ("aws-exec-read"),
  AUTHENTICATED_READ("authenticated-read"),
  BUCKET_OWNER_READ("bucket-owner-read"),
  BUCKET_OWNER_FULL_CONTROL("bucket-owner-full-control"),
  LOG_DELIVERY_WRITE("log-delivery-write");

  private final String value;

  S3AclType(String value) {
    this.value = value;
  }

  public BucketCannedACL toSdkAcl() {
    return BucketCannedACL.fromValue(value);
  }

  @JsonCreator
  public static S3AclType fromValue(String value) {
    for (S3AclType acl : values()) {
      if (acl.getValue().equalsIgnoreCase(value)) {
        return acl;
      }
    }
    throw new IllegalArgumentException("Invalid ACL type: " + value);
  }
}
