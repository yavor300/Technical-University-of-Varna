package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3AclType;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAcl;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclGrant;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclGrantee;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAclOwner;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.AccessControlPolicy;
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.ObjectOwnership;
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.OwnershipControls;
import software.amazon.awssdk.services.s3.model.OwnershipControlsRule;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.Type;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3AclStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private StepResult<S3Output> metadata;
  private S3AclStep step;

  @BeforeEach
  void setup() {

    config = new S3BucketConfig();
    metadata = StepResult.<S3Output>builder()
        .stepName(S3PersistentMetadataStep.class.getName())
        .put(S3Output.NAME, "test-bucket")
        .build();
  }

  @Test
  void apply_shouldSkipAcl_whenOwnershipIsBucketOwnerEnforced() {

    Owner owner = Owner.builder()
        .id("owner-id")
        .displayName("owner-name")
        .build();

    Grantee grantee = Grantee.builder()
        .type(Type.CANONICAL_USER)
        .id("grantee-id")
        .build();

    Grant grant = Grant.builder()
        .grantee(grantee)
        .permission(Permission.FULL_CONTROL)
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getOwnershipControls("test-bucket")).thenReturn(
        GetBucketOwnershipControlsResponse.builder()
            .ownershipControls(OwnershipControls.builder()
                .rules(List.of(OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_ENFORCED)
                    .build()))
                .build())
            .build()
    );

    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);
    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());

    // Validate value node contains expected structure
    S3ProvisionedAcl acl = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(acl.getOwner().getId()).isEqualTo("owner-id");
    assertThat(acl.getGrants()).hasSize(1);
    assertThat(acl.getGrants().getFirst().getPermission()).isEqualTo("FULL_CONTROL");
    assertThat(acl.getGrants().getFirst().getGrantee().getIdentifier()).isEqualTo("grantee-id");

    verify(s3).getAcl("test-bucket");
    verify(s3, never()).putAcl(any(), any(), any());
  }

  @Test
  void apply_shouldApplyAccessControlPolicy_whenPolicyIsConfigured() {

    S3BucketConfig.AclConfig acl = new S3BucketConfig.AclConfig();
    S3BucketConfig.AccessControlPolicy policy = new S3BucketConfig.AccessControlPolicy();
    S3BucketConfig.Grantee grantee = new S3BucketConfig.Grantee();
    grantee.setType("CanonicalUser");
    grantee.setId("grantee-id");
    policy.setGrants(List.of(new S3BucketConfig.GrantConfig(grantee, "FULL_CONTROL")));
    policy.setOwner(new S3BucketConfig.Owner("owner-id", "owner-name"));
    acl.setAccessControlPolicy(policy);
    config.setAcl(acl);

    when(s3.getOwnershipControls("test-bucket")).thenReturn(
        GetBucketOwnershipControlsResponse.builder()
            .ownershipControls(OwnershipControls.builder()
                .rules(List.of(OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_PREFERRED)
                    .build()))
                .build())
            .build()
    );

    Owner owner = Owner.builder()
        .id("owner-id")
        .displayName("owner-name")
        .build();

    Grantee granteeResponse = Grantee.builder()
        .type(Type.CANONICAL_USER)
        .id("grantee-id")
        .build();

    Grant grant = Grant.builder()
        .grantee(granteeResponse)
        .permission(Permission.FULL_CONTROL)
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);
    StepResult<S3Output> result = step.apply();

    verify(s3).putAcl(eq("test-bucket"), any(AccessControlPolicy.class), isNull());
    verify(s3).getAcl("test-bucket");
    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
  }

  @Test
  void apply_shouldApplyCannedAcl_whenCannedAclIsConfigured() {

    S3BucketConfig.AclConfig acl = new S3BucketConfig.AclConfig();
    acl.setCanned(S3AclType.PRIVATE);
    config.setAcl(acl);

    when(s3.getOwnershipControls("test-bucket")).thenReturn(
        GetBucketOwnershipControlsResponse.builder()
            .ownershipControls(OwnershipControls.builder()
                .rules(List.of(OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_PREFERRED)
                    .build()))
                .build())
            .build());

    Owner owner = Owner.builder()
        .id("owner-id")
        .displayName("owner-name")
        .build();

    Grantee granteeResponse = Grantee.builder()
        .type(Type.CANONICAL_USER)
        .id("grantee-id")
        .build();

    Grant grant = Grant.builder()
        .grantee(granteeResponse)
        .permission(Permission.FULL_CONTROL)
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();


    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);
    StepResult<S3Output> result = step.apply();

    verify(s3).putAcl("test-bucket", null, S3AclType.PRIVATE.toSdkAcl());
    verify(s3).getAcl("test-bucket");
    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
  }

  @Test
  void apply_shouldApplyBothPolicyAndCannedAcl() {

    S3BucketConfig.AclConfig acl = new S3BucketConfig.AclConfig();

    S3BucketConfig.AccessControlPolicy policy = new S3BucketConfig.AccessControlPolicy();
    S3BucketConfig.Grantee grantee = new S3BucketConfig.Grantee();
    grantee.setType("CanonicalUser");
    grantee.setId("grantee-id");
    policy.setGrants(List.of(new S3BucketConfig.GrantConfig(grantee, "READ")));
    policy.setOwner(new S3BucketConfig.Owner("owner-id", "owner-name"));
    acl.setAccessControlPolicy(policy);

    acl.setCanned(S3AclType.PUBLIC_READ);
    config.setAcl(acl);

    when(s3.getOwnershipControls("test-bucket")).thenReturn(
        GetBucketOwnershipControlsResponse.builder()
            .ownershipControls(OwnershipControls.builder()
                .rules(List.of(OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_PREFERRED)
                    .build()))
                .build())
            .build());

    Owner owner = Owner.builder()
        .id("owner-id")
        .displayName("owner-name")
        .build();

    Grantee granteeResponse = Grantee.builder()
        .type(Type.CANONICAL_USER)
        .id("grantee-id")
        .build();

    Grant grant = Grant.builder()
        .grantee(granteeResponse)
        .permission(Permission.FULL_CONTROL)
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);
    StepResult<S3Output> result = step.apply();

    verify(s3).putAcl(eq("test-bucket"), any(AccessControlPolicy.class), isNull());
    verify(s3).putAcl("test-bucket", null, S3AclType.PUBLIC_READ.toSdkAcl());
    verify(s3).getAcl("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
  }

  @Test
  void generateDesiredState_shouldReturnAccessControlPolicy() {

    S3BucketConfig.Owner owner = new S3BucketConfig.Owner();
    owner.setId("owner-id");
    owner.setDisplayName("owner-name");

    S3BucketConfig.Grantee grantee = new S3BucketConfig.Grantee();
    grantee.setType("CanonicalUser");
    grantee.setId("grantee-id");
    grantee.setUri(null);
    grantee.setEmailAddress(null);

    S3BucketConfig.GrantConfig grant = new S3BucketConfig.GrantConfig();
    grant.setGrantee(grantee);
    grant.setPermission("FULL_CONTROL");

    S3BucketConfig.AccessControlPolicy policy = new S3BucketConfig.AccessControlPolicy();
    policy.setOwner(owner);
    policy.setGrants(List.of(grant));

    S3BucketConfig.AclConfig acl = new S3BucketConfig.AclConfig();
    acl.setAccessControlPolicy(policy);
    acl.setCanned(null);

    config.setAcl(acl);
    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.generateDesiredState();

    S3ProvisionedAcl aclResult = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(aclResult.getOwner().getId()).isEqualTo("owner-id");
    assertThat(aclResult.getGrants()).hasSize(1);
    assertThat(aclResult.getGrants().getFirst().getGrantee().getIdentifier()).isEqualTo("grantee-id");
    assertThat(aclResult.getGrants().getFirst().getPermission()).isEqualTo("FULL_CONTROL");
  }

  @Test
  void generateDesiredState_shouldReturnCannedAclOnly() {

    S3BucketConfig.AclConfig acl = new S3BucketConfig.AclConfig();
    acl.setCanned(S3AclType.BUCKET_OWNER_FULL_CONTROL);
    acl.setAccessControlPolicy(null);

    config.setAcl(acl);
    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.generateDesiredState();

    S3ProvisionedAcl aclResult = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(aclResult.getCannedAcl()).isEqualTo(S3AclType.BUCKET_OWNER_FULL_CONTROL);
    assertThat(aclResult.getOwner()).isNull();
    assertThat(aclResult.getGrants()).isNull();
  }

  @Test
  void generateDesiredState_shouldFallbackToPrivate_whenNoAclConfigured() {

    S3BucketConfig.AclConfig acl = new S3BucketConfig.AclConfig();
    acl.setCanned(null);
    acl.setAccessControlPolicy(null);

    config.setAcl(acl);

    Owner owner = Owner.builder().id("123").displayName("John").build();
    Grantee grantee = Grantee.builder().type(Type.CANONICAL_USER).id("grantee").build();
    Grant grant = Grant.builder().grantee(grantee).permission(Permission.FULL_CONTROL).build();
    GetBucketAclResponse response = GetBucketAclResponse.builder().owner(owner).grants(List.of(grant)).build();

    when(s3.getAcl("test-bucket")).thenReturn(response);

    step = new S3AclStep(s3, config, metadata);
    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
    S3ProvisionedAcl aclResult = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(aclResult.getOwner().getDisplayName()).isEqualTo("John");
  }

  @Test
  void getCurrentState_shouldReturnCurrentAcl() {

    Owner owner = Owner.builder()
        .id("owner-id")
        .displayName("owner-name")
        .build();

    Grantee grantee = Grantee.builder()
        .type(Type.CANONICAL_USER)
        .id("grantee-id")
        .build();

    Grant grant = Grant.builder()
        .grantee(grantee)
        .permission(Permission.FULL_CONTROL)
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
    S3ProvisionedAcl acl = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(acl.getOwner().getId()).isEqualTo("owner-id");
    assertThat(acl.getGrants()).hasSize(1);
    assertThat(acl.getGrants().getFirst().getPermission()).isEqualTo("FULL_CONTROL");

    verify(s3).getAcl("test-bucket");
  }

  @Test
  void getCurrentState_shouldReturnEmptyResult_whenBucketNotFound() {

    AwsServiceException notFoundException = S3Exception.builder()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .message("Not Found")
        .build();

    CloudResourceStepException wrapper = new CloudResourceStepException("wrapped", notFoundException);

    when(s3.getAcl("test-bucket")).thenThrow(wrapper);

    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();

    verify(s3).getAcl("test-bucket");
  }

  @Test
  void destroy_shouldSkipAcl_whenOwnershipIsBucketOwnerEnforced() {

    Owner owner = Owner.builder().id("owner-id").displayName("owner-name").build();
    Grantee grantee = Grantee.builder().type(Type.CANONICAL_USER).id("grantee-id").build();
    Grant grant = Grant.builder().grantee(grantee).permission(Permission.FULL_CONTROL).build();

    GetBucketOwnershipControlsResponse ownershipControls = GetBucketOwnershipControlsResponse.builder()
        .ownershipControls(OwnershipControls.builder()
            .rules(List.of(OwnershipControlsRule.builder()
                .objectOwnership(ObjectOwnership.BUCKET_OWNER_ENFORCED).build()))
            .build())
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getOwnershipControls("test-bucket")).thenReturn(ownershipControls);
    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.destroy(false);

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
    S3ProvisionedAcl acl = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(acl.getOwner().getId()).isEqualTo("owner-id");
    assertThat(acl.getGrants()).hasSize(1);
    assertThat(acl.getGrants().getFirst().getGrantee().getIdentifier()).isEqualTo("grantee-id");

    verify(s3, never()).putAcl(any(), any(), any());
    verify(s3).getOwnershipControls("test-bucket");
    verify(s3).getAcl("test-bucket");
  }

  @Test
  void destroy_shouldApplyPrivateAcl_whenOwnershipIsNotEnforced() {

    Owner owner = Owner.builder().id("owner-id").displayName("owner-name").build();
    Grantee grantee = Grantee.builder().type(Type.CANONICAL_USER).id("grantee-id").build();
    Grant grant = Grant.builder().grantee(grantee).permission(Permission.FULL_CONTROL).build();

    GetBucketOwnershipControlsResponse ownershipControls = GetBucketOwnershipControlsResponse.builder()
        .ownershipControls(OwnershipControls.builder()
            .rules(List.of(OwnershipControlsRule.builder()
                .objectOwnership(ObjectOwnership.OBJECT_WRITER).build()))
            .build())
        .build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getOwnershipControls("test-bucket")).thenReturn(ownershipControls);
    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.destroy(false);

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());
    S3ProvisionedAcl acl = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(acl.getGrants()).hasSize(1);
    assertThat(acl.getGrants().getFirst().getPermission()).isEqualTo("FULL_CONTROL");

    verify(s3).putAcl(eq("test-bucket"), isNull(), eq(S3AclType.PRIVATE.toSdkAcl()));
    verify(s3).getOwnershipControls("test-bucket");
    verify(s3).getAcl("test-bucket");
  }

  @Test
  void revert_shouldApplyCannedAcl_whenCannedAclIsPresent() {

    S3ProvisionedAcl previousAcl = new S3ProvisionedAcl(null, null, S3AclType.PUBLIC_READ);

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.VALUE_NODE, previousAcl)
        .build();

    Owner owner = Owner.builder().id("id").displayName("name").build();
    Grantee grantee = Grantee.builder().type(Type.CANONICAL_USER).id("id").build();
    Grant grant = Grant.builder().grantee(grantee).permission(Permission.READ).build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.revert(previous);

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());

    S3ProvisionedAcl acl = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(acl.getCannedAcl()).isEqualTo(S3AclType.PUBLIC_READ);
    verify(s3).putAcl("test-bucket", null, S3AclType.PUBLIC_READ.toSdkAcl());
    verify(s3).getAcl("test-bucket");
  }

  @Test
  void revert_shouldApplyPolicy_whenCannedAclIsNull() {

    S3ProvisionedAclGrantee granteeDto = new S3ProvisionedAclGrantee("CanonicalUser", "grantee-id", null, null);
    S3ProvisionedAclGrant grantDto = new S3ProvisionedAclGrant(granteeDto, "FULL_CONTROL");
    S3ProvisionedAclOwner ownerDto = new S3ProvisionedAclOwner("owner-id", "owner-name");

    S3ProvisionedAcl previousAcl = new S3ProvisionedAcl(ownerDto, List.of(grantDto), null);

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.VALUE_NODE, previousAcl)
        .build();

    Owner owner = Owner.builder().id("owner-id").displayName("owner-name").build();
    Grantee grantee = Grantee.builder().type(Type.CANONICAL_USER).id("grantee-id").build();
    Grant grant = Grant.builder().grantee(grantee).permission(Permission.FULL_CONTROL).build();

    GetBucketAclResponse aclResponse = GetBucketAclResponse.builder()
        .owner(owner)
        .grants(List.of(grant))
        .build();

    when(s3.getAcl("test-bucket")).thenReturn(aclResponse);

    step = new S3AclStep(s3, config, metadata);

    StepResult<S3Output> result = step.revert(previous);

    assertThat(result.getStepName()).isEqualTo(S3AclStep.class.getName());

    S3ProvisionedAcl acl = (S3ProvisionedAcl) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(acl.getOwner().getId()).isEqualTo("owner-id");
    assertThat(acl.getGrants()).hasSize(1);
    assertThat(acl.getGrants().getFirst().getPermission()).isEqualTo("FULL_CONTROL");

    verify(s3).putAcl(eq("test-bucket"), any(AccessControlPolicy.class), isNull());
    verify(s3).getAcl("test-bucket");
  }


}
