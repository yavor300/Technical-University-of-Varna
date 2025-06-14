package bg.tuvarna.sit.cloud.core.aws.s3.client;

import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AccessControlPolicy;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketPolicyResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketTaggingRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketTaggingResponse;
import software.amazon.awssdk.services.s3.model.GetBucketAclRequest;
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.GetBucketEncryptionRequest;
import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsRequest;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.GetBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.GetBucketPolicyResponse;
import software.amazon.awssdk.services.s3.model.GetBucketTaggingRequest;
import software.amazon.awssdk.services.s3.model.GetBucketTaggingResponse;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.ObjectOwnership;
import software.amazon.awssdk.services.s3.model.OwnershipControlsRule;
import software.amazon.awssdk.services.s3.model.PutBucketAclRequest;
import software.amazon.awssdk.services.s3.model.PutBucketAclResponse;
import software.amazon.awssdk.services.s3.model.PutBucketEncryptionRequest;
import software.amazon.awssdk.services.s3.model.PutBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.PutBucketOwnershipControlsRequest;
import software.amazon.awssdk.services.s3.model.PutBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyResponse;
import software.amazon.awssdk.services.s3.model.PutBucketTaggingRequest;
import software.amazon.awssdk.services.s3.model.PutBucketTaggingResponse;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionConfiguration;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionRule;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;
import software.amazon.awssdk.services.s3.model.VersioningConfiguration;

import java.util.List;

@Slf4j
public class S3SafeClient implements AutoCloseable {

  private final S3Client client;

  public S3SafeClient(S3Client client) {
    this.client = client;
  }

  public CreateBucketResponse create(String bucketName) throws CloudResourceStepException {

    try {
      return client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());

    } catch (BucketAlreadyExistsException e) {

      String message = ("Bucket name '%s' is not available. Bucket names must be globally unique. Choose a different " +
          "name and try again.").formatted(bucketName);
      log.debug(message, bucketName, e);

      CloudResourceStepException exception = new CloudResourceStepException(
          "S3 service error while creating bucket '%s'".formatted(bucketName), e);
      exception.getMessageDetails().add(message);
      throw exception;

    } catch (BucketAlreadyOwnedByYouException e) {

      String message = ("Bucket '%s' already exists and is owned by you. In all regions except us-east-1, this is an " +
          "error. In us-east-1, it resets ACLs.").formatted(bucketName);
      log.debug(message, bucketName, e);

      CloudResourceStepException exception = new CloudResourceStepException(
          "S3 service error while creating bucket '%s'".formatted(bucketName), e);
      exception.getMessageDetails().add(message);
      throw exception;

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "S3 service error while creating bucket '%s'".formatted(bucketName),
          e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "S3 service error while creating bucket '%s'".formatted(bucketName), e);
    }
  }

  public HeadBucketResponse head(String bucketName) throws CloudResourceStepException {

    try {
      return client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());

    } catch (NoSuchBucketException e) {

      String message = "Bucket '%s' does not exist. This may indicate it was deleted or never created."
          .formatted(bucketName);

      log.debug(message, bucketName, e);

      CloudResourceStepException exception = new CloudResourceStepException(
          "S3 service error while verifying existence of bucket '%s'".formatted(bucketName), e);
      exception.getMessageDetails().add(message);

      throw exception;

    } catch (SdkClientException e) {

      throw handleSdkClientException(bucketName,
          "S3 service error while verifying existence of bucket '%s'".formatted(bucketName), e);
    } catch (S3Exception e) {

      throw handleS3Exception(bucketName,
          "S3 service error while verifying existence of bucket '%s'".formatted(bucketName), e);
    }
  }

  public DeleteBucketResponse delete(String bucketName) throws CloudResourceStepException {

    try {
      return client.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName,
          "S3 service error while deleting bucket '%s'".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "S3 service error while deleting bucket '%s'".formatted(bucketName), e);
    }
  }

  public PutBucketAclResponse putAcl(String bucketName, AccessControlPolicy policy, BucketCannedACL acl)
      throws CloudResourceStepException {

    try {
      return client.putBucketAcl(
          PutBucketAclRequest.builder().bucket(bucketName).accessControlPolicy(policy).acl(acl).build());

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to apply ACL to bucket '%s'.".formatted(bucketName), e);

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to apply ACL to bucket '%s'.".formatted(bucketName), e);
    }
  }

  public GetBucketAclResponse getAcl(String bucketName) throws CloudResourceStepException {

    try {
      return client.getBucketAcl(GetBucketAclRequest.builder().bucket(bucketName).build());

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to fetch acl for bucket '%s'.".formatted(bucketName), e);

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to fetch acl for bucket '%s'.".formatted(bucketName), e);
    }
  }

  public PutBucketEncryptionResponse putEncryption(String bucketName, ServerSideEncryption sseAlgorithm,
                                                   String kmsKeyId, boolean bucketKeyEnabled) throws CloudResourceStepException {

    ServerSideEncryptionByDefault.Builder defaultEncryption = ServerSideEncryptionByDefault.builder()
        .sseAlgorithm(sseAlgorithm);

    if (sseAlgorithm == ServerSideEncryption.AWS_KMS && kmsKeyId != null) {
      defaultEncryption.kmsMasterKeyID(kmsKeyId);
    }

    ServerSideEncryptionRule rule = ServerSideEncryptionRule.builder()
        .applyServerSideEncryptionByDefault(defaultEncryption.build())
        .bucketKeyEnabled(bucketKeyEnabled)
        .build();

    PutBucketEncryptionRequest request = PutBucketEncryptionRequest.builder().bucket(bucketName)
        .serverSideEncryptionConfiguration(ServerSideEncryptionConfiguration.builder().rules(rule).build()).build();

    try {
      return client.putBucketEncryption(request);

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to add encryption to bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to add encryption to bucket '%s'.".formatted(bucketName), e);
    }
  }

  public GetBucketEncryptionResponse getEncryption(String bucketName) throws CloudResourceStepException {

    try {
      return client.getBucketEncryption(
          GetBucketEncryptionRequest.builder().bucket(bucketName).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to fetch encryption for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to fetch encryption for bucket '%s'.".formatted(bucketName), e);
    }
  }

  public PutBucketOwnershipControlsResponse putOwnershipControls(String name, ObjectOwnership ownership)
      throws CloudResourceStepException {

    PutBucketOwnershipControlsRequest request = PutBucketOwnershipControlsRequest.builder().bucket(name)
        .ownershipControls(software.amazon.awssdk.services.s3.model.OwnershipControls.builder()
            .rules(OwnershipControlsRule.builder().objectOwnership(ownership).build()).build()).build();

    try {
      return client.putBucketOwnershipControls(request);

    } catch (SdkClientException e) {
      throw handleSdkClientException(name, "Failed to set ownership controls for bucket '%s'.".formatted(name), e);

    } catch (S3Exception e) {
      throw handleS3Exception(name, "Failed to set ownership controls for bucket '%s'.".formatted(name), e);
    }
  }

  public GetBucketOwnershipControlsResponse getOwnershipControls(String bucketName) throws CloudResourceStepException {

    try {
      return client.getBucketOwnershipControls(
          GetBucketOwnershipControlsRequest.builder().bucket(bucketName).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to fetch ownership controls for bucket '%s'."
          .formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to fetch ownership controls for bucket '%s'."
          .formatted(bucketName), e);
    }
  }

  public PutBucketTaggingResponse putTags(String bucketName, List<Tag> tags) throws CloudResourceStepException {

    try {
      return client.putBucketTagging(
          PutBucketTaggingRequest.builder().bucket(bucketName).tagging(Tagging.builder().tagSet(tags).build()).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to set tags for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to set tags for bucket '%s'.".formatted(bucketName), e);
    }
  }

  public DeleteBucketTaggingResponse deleteTags(String bucketName) throws CloudResourceStepException {

    try {
      return client.deleteBucketTagging(DeleteBucketTaggingRequest.builder().bucket(bucketName).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to delete tags for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to delete tags for bucket '%s'.".formatted(bucketName), e);
    }
  }


  public GetBucketTaggingResponse getTags(String bucketName) throws CloudResourceStepException {

    try {
      return client.getBucketTagging(GetBucketTaggingRequest.builder()
          .bucket(bucketName).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to get tags for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to get tags for bucket '%s'.".formatted(bucketName), e);
    }
  }

  public PutBucketVersioningResponse putVersioning(String bucketName, BucketVersioningStatus status)
      throws CloudResourceStepException {

    try {
      return client.putBucketVersioning(PutBucketVersioningRequest.builder()
              .bucket(bucketName)
              .versioningConfiguration(VersioningConfiguration.builder()
                  .status(status)
                  .build())
              .build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to set versioning on bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to set versioning on bucket '%s'.".formatted(bucketName), e);
    }
  }

  public GetBucketVersioningResponse getVersioning(String bucketName)
      throws CloudResourceStepException {

    try {
      return client.getBucketVersioning(GetBucketVersioningRequest.builder().bucket(bucketName).build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to get versioning for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to get versioning for bucket '%s'.".formatted(bucketName), e);
    }
  }

  public PutBucketPolicyResponse putPolicy(String bucketName, String policy) throws CloudResourceStepException {

    try {
      return client.putBucketPolicy(PutBucketPolicyRequest.builder()
          .bucket(bucketName)
          .policy(policy)
          .build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to attach policy to bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to attach policy to bucket '%s'.".formatted(bucketName), e);
    }
  }

  public GetBucketPolicyResponse getPolicy(String bucketName) {

    try {
      return client.getBucketPolicy(GetBucketPolicyRequest.builder()
          .bucket(bucketName)
          .build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to get policy for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to get policy for bucket '%s'.".formatted(bucketName), e);
    }
  }

  public DeleteBucketPolicyResponse deletePolicy(String bucketName) throws CloudResourceStepException {

    try {
      return client.deleteBucketPolicy(DeleteBucketPolicyRequest.builder()
              .bucket(bucketName)
              .build());

    } catch (SdkClientException e) {
      throw handleSdkClientException(bucketName, "Failed to delete policy for bucket '%s'.".formatted(bucketName), e);

    } catch (S3Exception e) {
      throw handleS3Exception(bucketName, "Failed to delete policy for bucket '%s'.".formatted(bucketName), e);
    }
  }

  @Override
  public void close() {
    client.close();
  }

  private CloudResourceStepException handleS3Exception(String bucket, String message, S3Exception e) {

    log.debug("S3 service error for bucket '{}'. AWS error code: '{}', message: '{}'.", bucket,
        e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);

    CloudResourceStepException exception = new CloudResourceStepException(message, e);
    exception.getMessageDetails().add("Reason: " + e.getMessage());

    return exception;
  }

  private RetryableCloudResourceStepException handleSdkClientException(String bucket, String message,
                                                                       SdkClientException e)
      throws RetryableCloudResourceStepException {
    log.debug(
        "Client-side error for bucket '{}'. Possible causes: network issues, invalid credentials, or local " +
            "misconfiguration.", bucket, e);

    RetryableCloudResourceStepException exception = new RetryableCloudResourceStepException(message, e);
    exception.getMessageDetails().add("Reason: " + e.getMessage());
    Throwable cause = e.getCause();
    if (cause != null) {
      exception.getMessageDetails().add(cause.getMessage());
    }

    return exception;
  }

}
