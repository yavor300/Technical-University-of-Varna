package bg.tuvarna.sit.cloud.core.aws.s3.client;

import bg.tuvarna.sit.cloud.exception.BucketAclProvisioningException;
import bg.tuvarna.sit.cloud.exception.BucketCreationException;
import bg.tuvarna.sit.cloud.exception.BucketEncryptionProvisioningException;
import bg.tuvarna.sit.cloud.exception.BucketOwnershipProvisioningException;
import bg.tuvarna.sit.cloud.exception.BucketOwnershipVerificationException;
import bg.tuvarna.sit.cloud.exception.BucketPolicyProvisioningException;
import bg.tuvarna.sit.cloud.exception.BucketTaggingProvisioningException;
import bg.tuvarna.sit.cloud.exception.BucketVerificationException;
import bg.tuvarna.sit.cloud.exception.BucketVersioningProvisioningException;
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

  public CreateBucketResponse create(String bucketName) {

    try {
      CreateBucketResponse response = client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
      log.info("Successfully created bucket '{}'", bucketName);
      return response;

    } catch (BucketAlreadyExistsException e) {
      log.error(
          "Bucket name '{}' is not available. Bucket names must be globally unique. " + "Choose a different name and " +
              "try again.",
          bucketName, e);
      throw new BucketCreationException(bucketName, e);

    } catch (BucketAlreadyOwnedByYouException e) {
      log.warn(
          "Bucket '{}' already exists and is owned by you. " + "In all regions except us-east-1, this is an error. In" +
              " us-east-1, it resets ACLs.",
          bucketName, e);
      throw new BucketCreationException(bucketName, e);

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while creating bucket '{}'. " + "Possible causes: network issues, invalid credentials, " +
              "or local misconfiguration.",
          bucketName, e);
      throw new BucketCreationException(bucketName, e);

    } catch (S3Exception e) {
      log.error("S3 service error while creating bucket '{}'. AWS error code: '{}', message: '{}'. ", bucketName,
          e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketCreationException(bucketName, e);
    }
  }

  public HeadBucketResponse head(String bucketName, boolean silent) throws BucketVerificationException {

    try {
      HeadBucketResponse response = client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
      log.info("Successfully verified existence of bucket '{}'", bucketName);
      return response;

    } catch (NoSuchBucketException e) {
      if (!silent) {
        log.error("Bucket '{}' does not exist. This may indicate it was deleted or never created.", bucketName, e);
      }
      throw new BucketVerificationException(bucketName, e);

    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error while verifying bucket '{}'. " + "Possible causes: network issues, invalid " +
                "credentials, " +
                "or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketVerificationException(bucketName, e);

    } catch (S3Exception e) {
      if (!silent) {
        log.error("S3 service error while verifying bucket '{}'. AWS error code: '{}', message: '{}'. ", bucketName,
            e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketVerificationException(bucketName, e);
    }
  }

  public PutBucketAclResponse putAcl(String bucketName, AccessControlPolicy policy, BucketCannedACL acl)
      throws BucketAclProvisioningException {

    try {
      PutBucketAclResponse response = client.putBucketAcl(
          PutBucketAclRequest.builder().bucket(bucketName).accessControlPolicy(policy).acl(acl).build());
      log.info("Successfully applied acl configuration for bucket '{}'", bucketName);
      return response;

    } catch (S3Exception e) {
      log.error("S3 service error while applying ACL to bucket '{}'. AWS error code: '{}', message: '{}'. ", bucketName,
          e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketAclProvisioningException(bucketName, e);

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while applying ACL to bucket '{}'. " + "Possible causes: network issues, invalid " +
              "credentials, or local misconfiguration.",
          bucketName, e);
      throw new BucketAclProvisioningException(bucketName, e);
    }
  }

  public GetBucketAclResponse getAcl(String bucketName, boolean silent) throws BucketAclProvisioningException {

    try {
      GetBucketAclResponse response = client.getBucketAcl(GetBucketAclRequest.builder().bucket(bucketName).build());
      log.info("Successfully fetched acl configuration for bucket '{}'", bucketName);
      return response;

    } catch (S3Exception e) {
      if (!silent) {
        log.error("S3 service error fetching ACL for bucket '{}'. AWS error code: '{}', message: '{}'.", bucketName,
            e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketAclProvisioningException(bucketName, e);
    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error fetching ACL for bucket '{}'. " + "Possible causes: network issues, invalid " +
                "credentials," +
                " or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketAclProvisioningException(bucketName, e);
    }
  }

  public PutBucketEncryptionResponse putEncryption(String bucketName, ServerSideEncryption sseAlgorithm,
                                                   String kmsKeyId) {

    ServerSideEncryptionByDefault.Builder defaultEncryption = ServerSideEncryptionByDefault.builder()
        .sseAlgorithm(sseAlgorithm);

    if (sseAlgorithm == ServerSideEncryption.AWS_KMS && kmsKeyId != null) {
      defaultEncryption.kmsMasterKeyID(kmsKeyId);
    }

    ServerSideEncryptionRule rule = ServerSideEncryptionRule.builder()
        .applyServerSideEncryptionByDefault(defaultEncryption.build()).build();

    PutBucketEncryptionRequest request = PutBucketEncryptionRequest.builder().bucket(bucketName)
        .serverSideEncryptionConfiguration(ServerSideEncryptionConfiguration.builder().rules(rule).build()).build();

    try {
      PutBucketEncryptionResponse resposne = client.putBucketEncryption(request);
      log.info("Successfully applied server-side encryption '{}' to bucket '{}'", sseAlgorithm.toString(), bucketName);
      return resposne;

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while applying encryption to bucket '{}'. Possible causes: network issues, invalid " +
              "credentials, or local misconfiguration.",
          bucketName, e);
      throw new BucketEncryptionProvisioningException(bucketName, e);

    } catch (S3Exception e) {
      log.error("S3 service error while applying encryption to bucket '{}'. AWS error code: '{}', message: '{}'. ",
          bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketEncryptionProvisioningException(bucketName, e);
    }
  }

  public GetBucketEncryptionResponse getEncryption(String bucketName, boolean silent)
      throws BucketEncryptionProvisioningException {

    try {
      GetBucketEncryptionResponse response = client.getBucketEncryption(
          GetBucketEncryptionRequest.builder().bucket(bucketName).build());
      log.info("Successfully verified server-side encryption for bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error while verifying encryption for bucket {}. Possible causes: network issues, invalid " +
                "credentials, or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketEncryptionProvisioningException(bucketName, e);
    } catch (S3Exception e) {
      if (!silent) {
        log.error("S3 service error while verifying encryption to bucket '{}'. AWS error code: '{}', message: '{}'. ",
            bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketEncryptionProvisioningException(bucketName, e);
    }
  }

  public PutBucketOwnershipControlsResponse putOwnershipControls(String name, ObjectOwnership ownership) {

    PutBucketOwnershipControlsRequest request = PutBucketOwnershipControlsRequest.builder().bucket(name)
        .ownershipControls(software.amazon.awssdk.services.s3.model.OwnershipControls.builder()
            .rules(OwnershipControlsRule.builder().objectOwnership(ownership).build()).build()).build();

    try {
      PutBucketOwnershipControlsResponse response = client.putBucketOwnershipControls(request);
      log.info("Set ownership controls '{}' for bucket '{}'", ownership.toString(), name);
      return response;

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while setting ownership controls for bucket {}. Possible causes: network issues, " +
              "invalid credentials, or local misconfiguration.",
          name, e);
      throw new BucketOwnershipProvisioningException(name, e);

    } catch (S3Exception e) {
      log.error(
          "S3 service error while setting ownership controls for bucket '{}'. AWS error code: '{}', message: '{}'. ",
          name, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketOwnershipProvisioningException(name, e);
    }
  }

  public GetBucketOwnershipControlsResponse getOwnershipControls(String bucketName, boolean silent)
      throws BucketOwnershipProvisioningException {

    try {
      GetBucketOwnershipControlsResponse response = client.getBucketOwnershipControls(
          GetBucketOwnershipControlsRequest.builder().bucket(bucketName).build());
      log.info("Fetched ownership controls for bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error while verifying ownership controls for bucket {}. Possible causes: network issues, " +
                "invalid credentials, or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketOwnershipVerificationException(bucketName, "Client-side error during verification", e);

    } catch (S3Exception e) {
      if (!silent) {
        log.error(
            "S3 service error while verifying ownership controls for bucket '{}'. AWS error code: '{}', message: '{}'" +
                ". ",
            bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketOwnershipVerificationException(bucketName, "Service-side error during verification", e);
    }
  }

  public PutBucketTaggingResponse putTags(String bucketName, List<Tag> tags) {

    try {
      PutBucketTaggingResponse response = client.putBucketTagging(
          PutBucketTaggingRequest.builder().bucket(bucketName).tagging(Tagging.builder().tagSet(tags).build()).build());
      log.info("Applied tags to bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while setting tags for bucket {}. Possible causes: network issues, invalid credentials, " +
              "or local misconfiguration.",
          bucketName, e);
      throw new BucketTaggingProvisioningException(bucketName, e);

    } catch (S3Exception e) {
      log.error("S3 service error while applying tags to bucket '{}'. AWS error code: '{}', message: '{}'. ",
          bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketTaggingProvisioningException(bucketName, e);
    }
  }

  public GetBucketTaggingResponse getTags(String bucketName, boolean silent) throws BucketTaggingProvisioningException {

    try {
      GetBucketTaggingResponse response = client.getBucketTagging(
          GetBucketTaggingRequest.builder().bucket(bucketName).build());
      log.info("Fetched tags for bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error while fetching tags for bucket {}. Possible causes: network issues, invalid " +
                "credentials," +
                " or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketTaggingProvisioningException(bucketName, e);

    } catch (S3Exception e) {
      if (!silent) {
        log.error("S3 service error while fetching tags for bucket '{}'. AWS error code: '{}', message: '{}'. ",
            bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketTaggingProvisioningException(bucketName, e);
    }
  }

  public PutBucketVersioningResponse putVersioning(String bucketName, BucketVersioningStatus status) {

    try {
      PutBucketVersioningResponse response = client.putBucketVersioning(
          PutBucketVersioningRequest.builder()
              .bucket(bucketName)
              .versioningConfiguration(VersioningConfiguration.builder()
                  .status(status)
                  .build())
              .build());
      log.info("Set versioning '{}' for bucket '{}'", status, bucketName);
      return response;

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while setting versioning for bucket {}. Possible causes: network issues, invalid " +
              "credentials, or local misconfiguration.",
          bucketName, e);
      throw new BucketVersioningProvisioningException(bucketName, e);

    } catch (S3Exception e) {

      log.error("S3 service error while fetching tags for bucket '{}'. AWS error code: '{}', message: '{}'. ",
          bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketVersioningProvisioningException(bucketName, e);
    }
  }

  public GetBucketVersioningResponse getVersioning(String bucketName, boolean silent)
      throws BucketVersioningProvisioningException {

    try {
      GetBucketVersioningResponse response = client.getBucketVersioning(
          GetBucketVersioningRequest.builder().bucket(bucketName).build());
      log.info("Retrieved versioning status for bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error while retrieving versioning for bucket {}. Possible causes: network issues, invalid " +
                "credentials, or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketVersioningProvisioningException(bucketName, e);

    } catch (S3Exception e) {
      if (!silent) {
        log.error("S3 service error while retrieving versioning for bucket '{}'. AWS error code: '{}', message: '{}'. ",
            bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketVersioningProvisioningException(bucketName, e);
    }
  }

  public PutBucketPolicyResponse putPolicy(String bucketName, String policy) {

    try {
      PutBucketPolicyResponse response = client.putBucketPolicy(PutBucketPolicyRequest.builder()
          .bucket(bucketName)
          .policy(policy)
          .build());
      log.info("Applied policy to bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      log.error(
          "Client-side error while attaching policy to bucket {}. Possible causes: network issues, invalid " +
              "credentials, or local misconfiguration.",
          bucketName, e);
      throw new BucketPolicyProvisioningException(bucketName, e);

    } catch (S3Exception e) {
      log.error("S3 service error while attaching policy to bucket '{}'. AWS error code: '{}', message: '{}'. ",
          bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      throw new BucketPolicyProvisioningException(bucketName, e);
    }
  }

  public GetBucketPolicyResponse getPolicy(String bucketName, boolean silent) {

    try {
      GetBucketPolicyResponse response = client.getBucketPolicy(GetBucketPolicyRequest.builder()
          .bucket(bucketName)
          .build());
      log.info("Retrieved policy for bucket '{}'", bucketName);
      return response;

    } catch (SdkClientException e) {
      if (!silent) {
        log.error(
            "Client-side error while fetching policy for bucket {}. Possible causes: network issues, invalid " +
                "credentials, or local misconfiguration.",
            bucketName, e);
      }
      throw new BucketPolicyProvisioningException(bucketName, e);

    } catch (S3Exception e) {
      if (!silent) {
        log.error("S3 service error while fetching policy for bucket '{}'. AWS error code: '{}', message: '{}'. ",
            bucketName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);
      }
      throw new BucketPolicyProvisioningException(bucketName, e);
    }
  }

  @Override
  public void close() {
    client.close();
  }
}
