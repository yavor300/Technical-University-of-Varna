package bg.tuvarna.sit.cloud.core.aws.eks.client;

import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddon;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddons;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAuthenticationMode;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterSupportType;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.eks.EksClient;
import software.amazon.awssdk.services.eks.model.Addon;
import software.amazon.awssdk.services.eks.model.AddonStatus;
import software.amazon.awssdk.services.eks.model.BadRequestException;
import software.amazon.awssdk.services.eks.model.ClientException;
import software.amazon.awssdk.services.eks.model.Cluster;
import software.amazon.awssdk.services.eks.model.ClusterStatus;
import software.amazon.awssdk.services.eks.model.CreateAddonRequest;
import software.amazon.awssdk.services.eks.model.CreateAddonResponse;
import software.amazon.awssdk.services.eks.model.CreateClusterRequest;
import software.amazon.awssdk.services.eks.model.CreateClusterResponse;
import software.amazon.awssdk.services.eks.model.DeleteAddonRequest;
import software.amazon.awssdk.services.eks.model.DeleteAddonResponse;
import software.amazon.awssdk.services.eks.model.DeleteClusterRequest;
import software.amazon.awssdk.services.eks.model.DeleteClusterResponse;
import software.amazon.awssdk.services.eks.model.DescribeAddonRequest;
import software.amazon.awssdk.services.eks.model.DescribeAddonResponse;
import software.amazon.awssdk.services.eks.model.DescribeClusterRequest;
import software.amazon.awssdk.services.eks.model.DescribeClusterResponse;
import software.amazon.awssdk.services.eks.model.EksException;
import software.amazon.awssdk.services.eks.model.InvalidParameterException;
import software.amazon.awssdk.services.eks.model.ListAddonsRequest;
import software.amazon.awssdk.services.eks.model.ListAddonsResponse;
import software.amazon.awssdk.services.eks.model.ListTagsForResourceRequest;
import software.amazon.awssdk.services.eks.model.ListTagsForResourceResponse;
import software.amazon.awssdk.services.eks.model.NotFoundException;
import software.amazon.awssdk.services.eks.model.ResourceInUseException;
import software.amazon.awssdk.services.eks.model.ResourceLimitExceededException;
import software.amazon.awssdk.services.eks.model.ResourceNotFoundException;
import software.amazon.awssdk.services.eks.model.ServerException;
import software.amazon.awssdk.services.eks.model.ServiceUnavailableException;
import software.amazon.awssdk.services.eks.model.TagResourceRequest;
import software.amazon.awssdk.services.eks.model.TagResourceResponse;
import software.amazon.awssdk.services.eks.model.UnsupportedAvailabilityZoneException;
import software.amazon.awssdk.services.eks.model.UntagResourceRequest;
import software.amazon.awssdk.services.eks.model.UntagResourceResponse;
import software.amazon.awssdk.services.eks.model.UpdateAddonRequest;
import software.amazon.awssdk.services.eks.model.UpdateAddonResponse;
import software.amazon.awssdk.services.eks.model.UpdateClusterConfigRequest;
import software.amazon.awssdk.services.eks.model.UpdateClusterConfigResponse;
import software.amazon.awssdk.services.eks.model.UpdateClusterVersionRequest;
import software.amazon.awssdk.services.eks.model.UpdateClusterVersionResponse;

import java.net.ConnectException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EksSafeClientTest {

  private static final String SDK_CLIENT_EXCEPTION_MESSAGE = SdkClientException.class.getSimpleName();
  private static final String CONNECTION_EXCEPTION_MESSAGE = ConnectException.class.getSimpleName();
  private static final String UNSUPPORTED_AZ_MESSAGE = UnsupportedAvailabilityZoneException.class.getSimpleName();
  private static final String SERVICE_UNAVAILABLE_EXCEPTION = ServiceUnavailableException.class.getSimpleName();
  private static final String INVALID_PARAMETER_EXCEPTION_MESSAGE = InvalidParameterException.class.getSimpleName();
  private static final String RESOURCE_LIMIT_EXCEEDED_MESSAGE = ResourceLimitExceededException.class.getSimpleName();
  private static final String RESOURCE_IN_USE_MESSAGE = ResourceInUseException.class.getSimpleName();
  private static final String AWS_ERROR_DETAILS_MESSAGE = "EKS error";
  private static final String EKS_EXCEPTION_MESSAGE = "Generic EKS error";
  private static final String CLIENT_EXCEPTION_MESSAGE = ClientException.class.getSimpleName();
  private static final String SERVER_EXCEPTION_MESSAGE = ServerException.class.getSimpleName();
  private static final String RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE = ResourceNotFoundException.class.getSimpleName();
  private static final String BAD_REQUEST_EXCEPTION_MESSAGE = BadRequestException.class.getSimpleName();


  @Mock
  private EksClient eksClient;

  @Captor
  private ArgumentCaptor<CreateClusterRequest> createRequestCapture;

  @Captor
  private ArgumentCaptor<UpdateClusterConfigRequest> updateConfigRequestCapture;

  @Captor
  private ArgumentCaptor<UpdateClusterVersionRequest> updateVersionRequestCapture;

  @Captor
  private ArgumentCaptor<TagResourceRequest> tagResourceRequestCapture;

  @Captor
  private ArgumentCaptor<ListTagsForResourceRequest> listTagsForResourceRequestCapture;

  @Captor
  private ArgumentCaptor<CreateAddonRequest> createAddonRequestCapture;

  @Captor
  private ArgumentCaptor<UpdateAddonRequest> updateAddonRequestCapture;

  @Captor
  private ArgumentCaptor<DeleteAddonRequest> deleteAddonRequestArgumentCaptor;

  private EksClusterConfig config;

  private EksSafeClient safeClient;

  @BeforeEach
  public void setUp() {

    safeClient = new EksSafeClient(eksClient);
    config = createBasicConfig();
  }

  @Test
  public void testCloseClient() {

    safeClient.close();
    verify(eksClient, times(1)).close();
  }

  @Test
  public void testCreateCluster_Success() {

    when(eksClient.createCluster(any(CreateClusterRequest.class))).thenReturn(CreateClusterResponse.builder().build());

    CreateClusterResponse response = safeClient.create(config);

    assertNotNull(response);
    verify(eksClient, times(1)).createCluster(createRequestCapture.capture());
    assertSuccessfulCreateRequest(config, createRequestCapture.getValue());
  }

  @Test
  public void testCreateCluster_Success_WhenOwnedEncryptionKMSKeyArn_IsNull() {

    config.setOwnedEncryptionKMSKeyArn(null);

    when(eksClient.createCluster(any(CreateClusterRequest.class))).thenReturn(CreateClusterResponse.builder().build());

    CreateClusterResponse response = safeClient.create(config);

    assertNotNull(response);
    verify(eksClient, times(1)).createCluster(createRequestCapture.capture());
    assertSuccessfulCreateRequest(config, createRequestCapture.getValue());
  }

  @Test
  public void testCreateCluster_Success_WhenOwnedEncryptionKMSKeyArn_IsEmptyString() {

    config.setOwnedEncryptionKMSKeyArn("");

    when(eksClient.createCluster(any(CreateClusterRequest.class))).thenReturn(CreateClusterResponse.builder().build());

    CreateClusterResponse response = safeClient.create(config);

    assertNotNull(response);
    verify(eksClient, times(1)).createCluster(createRequestCapture.capture());
    assertSuccessfulCreateRequest(config, createRequestCapture.getValue());
  }

  @Test
  public void testCreateCluster_ThrowsResourceInUse() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(ResourceInUseException.builder().message(RESOURCE_IN_USE_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(), RESOURCE_IN_USE_MESSAGE,
        ResourceInUseException.class);
  }

  @Test
  public void testCreateCluster_ThrowsResourceLimitExceeded() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(ResourceLimitExceededException.builder()
            .message(RESOURCE_LIMIT_EXCEEDED_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(), RESOURCE_LIMIT_EXCEEDED_MESSAGE,
        ResourceLimitExceededException.class);
  }

  @Test
  public void testCreateCluster_ThrowsInvalidParameter() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(
            InvalidParameterException.builder().message(INVALID_PARAMETER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(), INVALID_PARAMETER_EXCEPTION_MESSAGE,
        InvalidParameterException.class);
  }

  @Test
  public void testCreateCluster_ThrowsClientException() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(ClientException.builder().message(CLIENT_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(), CLIENT_EXCEPTION_MESSAGE,
        ClientException.class);
  }

  @Test
  public void testCreateCluster_ThrowsServerException() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(ServerException.builder().message(SERVER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(), SERVER_EXCEPTION_MESSAGE,
        ServerException.class);
  }

  @Test
  public void testCreateCluster_ThrowsServiceUnavailableException() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(ServiceUnavailableException.builder().message(SERVICE_UNAVAILABLE_EXCEPTION).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(), SERVICE_UNAVAILABLE_EXCEPTION,
        ServiceUnavailableException.class);
  }

  @Test
  public void testCreateCluster_ThrowsUnsupportedAvailabilityZoneException() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(UnsupportedAvailabilityZoneException.builder().message(UNSUPPORTED_AZ_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE, ex, config.getName(),
        UNSUPPORTED_AZ_MESSAGE, UnsupportedAvailabilityZoneException.class);
  }

  @Test
  public void testCreateCluster_ThrowsSdkClientException_WithCause() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE,
            new ConnectException(CONNECTION_EXCEPTION_MESSAGE)));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.create(config));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE.formatted(config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertEquals(CONNECTION_EXCEPTION_MESSAGE, ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertInstanceOf(ConnectException.class, ex.getCause().getCause());
  }

  @Test
  public void testCreateCluster_ThrowsSdkClientException_WithoutCause() {

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.create(config));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE.formatted(config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testCreateCluster_ThrowsGenericEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.createCluster(any(CreateClusterRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.create(config));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_CREATE.formatted(config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testUpdateCluster_Success() {

    doReturn(UpdateClusterConfigResponse.builder().build()).when(eksClient)
        .updateClusterConfig(any(UpdateClusterConfigRequest.class));

    doReturn(UpdateClusterVersionResponse.builder().build()).when(eksClient)
        .updateClusterVersion(any(UpdateClusterVersionRequest.class));

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doNothing().when(spyClient).waitUntilActive(eq(config.getName()), any());

    assertDoesNotThrow(() -> spyClient.updateCluster(config.getName(), config));
    verify(spyClient, times(1)).waitUntilActive(eq(config.getName()), any());

    verify(eksClient).updateClusterConfig(updateConfigRequestCapture.capture());
    verify(eksClient).updateClusterVersion(updateVersionRequestCapture.capture());

    UpdateClusterConfigRequest configRequest = updateConfigRequestCapture.getValue();
    UpdateClusterVersionRequest versionRequest = updateVersionRequestCapture.getValue();

    assertEquals(config.getName(), configRequest.name());
    assertEquals(config.getAuthenticationMode().getValue(), configRequest.accessConfig().authenticationMode().toString());
    assertEquals(config.isEnableZonalShift(), configRequest.zonalShiftConfig().enabled());
    assertEquals(config.getSubnets(), configRequest.resourcesVpcConfig().subnetIds());
    assertEquals(config.getSupportType().getValue(), configRequest.upgradePolicy().supportType().toString());
    assertEquals(config.getName(), versionRequest.name());
    assertEquals(config.getVersion(), versionRequest.version());
  }

  @Test
  public void testUpdateCluster_ThrowsResourceInUseException() {

    when(eksClient.updateClusterConfig(any(UpdateClusterConfigRequest.class)))
        .thenThrow(ResourceLimitExceededException.builder()
            .message(RESOURCE_LIMIT_EXCEEDED_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.updateCluster(config.getName(), config));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_UPDATE, ex, config.getName(), RESOURCE_LIMIT_EXCEEDED_MESSAGE,
        ResourceLimitExceededException.class);
  }

  @Test
  public void testUpdateCluster_ThrowsSdkClientException_WithoutCause() {

    doThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE))
        .when(eksClient).updateClusterConfig(any(UpdateClusterConfigRequest.class));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.updateCluster(config.getName(), config));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_UPDATE.formatted(config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testUpdateCluster_ThrowsEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doNothing().when(spyClient).waitUntilActive(eq(config.getName()), any());

    doReturn(UpdateClusterConfigResponse.builder().build()).when(eksClient)
        .updateClusterConfig(any(UpdateClusterConfigRequest.class));

    when(eksClient.updateClusterVersion(any(UpdateClusterVersionRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class,
        () -> spyClient.updateCluster(config.getName(), config));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_UPDATE.formatted(config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testWaitUntilActive_Success() {

    String clusterName = config.getName();
    Duration timeout = Duration.ofSeconds(30);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    Cluster pendingCluster = Cluster.builder().status(ClusterStatus.CREATING).build();
    Cluster activeCluster = Cluster.builder().status(ClusterStatus.ACTIVE).build();

    DescribeClusterResponse creatingResponse = DescribeClusterResponse.builder().cluster(pendingCluster).build();
    DescribeClusterResponse activeResponse = DescribeClusterResponse.builder().cluster(activeCluster).build();

    when(spyClient.describe(clusterName)).thenReturn(creatingResponse).thenReturn(activeResponse);

    assertDoesNotThrow(() -> spyClient.waitUntilActive(clusterName, timeout));

    verify(spyClient, atLeast(2)).describe(clusterName);
  }

  @Test
  public void testWaitUntilActive_TimesOut() {

    String clusterName = config.getName();
    Duration timeout = Duration.ofSeconds(5);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    Cluster pendingCluster = Cluster.builder().status(ClusterStatus.CREATING).build();
    DescribeClusterResponse creatingResponse = DescribeClusterResponse.builder().cluster(pendingCluster).build();

    when(spyClient.describe(clusterName)).thenReturn(creatingResponse);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.waitUntilActive(clusterName, timeout)
    );

    assertTrue(ex.getMessage().contains("Timed out waiting"));
  }

  @Test
  public void testDescribeCluster_Success() {

    Cluster cluster = Cluster.builder().name(config.getName()).status(ClusterStatus.ACTIVE).build();
    DescribeClusterResponse expectedResponse = DescribeClusterResponse.builder().cluster(cluster).build();

    when(eksClient.describeCluster(any(DescribeClusterRequest.class))).thenReturn(expectedResponse);

    DescribeClusterResponse actualResponse = safeClient.describe(config.getName());

    assertNotNull(actualResponse);
    assertEquals(config.getName(), actualResponse.cluster().name());
  }

  @Test
  public void testDescribeCluster_ThrowsClientException() {

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenThrow(ClientException.builder().message(CLIENT_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.describe(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DESCRIBE, ex, config.getName(), CLIENT_EXCEPTION_MESSAGE,
        ClientException.class);
  }

  @Test
  public void testDescribeCluster_ThrowsServerException() {

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenThrow(ServerException.builder().message(SERVER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.describe(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DESCRIBE, ex, config.getName(), SERVER_EXCEPTION_MESSAGE,
        ServerException.class);
  }

  @Test
  public void testDescribeCluster_ThrowsServiceUnavailableException() {

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenThrow(ServiceUnavailableException.builder().message(SERVICE_UNAVAILABLE_EXCEPTION).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.describe(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DESCRIBE, ex, config.getName(), SERVICE_UNAVAILABLE_EXCEPTION,
        ServiceUnavailableException.class);
  }

  @Test
  public void testDescribeCluster_ThrowsSdkClientException() {

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.describe(config.getName()));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DESCRIBE.formatted(config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testDescribeCluster_ThrowsGenericEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.describe(config.getName()));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DESCRIBE.formatted(config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testDeleteCluster_Success() {

    String clusterName = config.getName();
    DeleteClusterResponse expectedResponse = DeleteClusterResponse.builder().build();

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class))).thenReturn(expectedResponse);

    DeleteClusterResponse actualResponse = safeClient.delete(clusterName);

    assertNotNull(actualResponse);
    verify(eksClient).deleteCluster(any(DeleteClusterRequest.class));
  }

  @Test
  public void testDeleteCluster_ThrowsResourceNotFoundException() {

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(ResourceNotFoundException.builder().message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.delete(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE, ex, config.getName(), RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE,
        ResourceNotFoundException.class);
  }

  @Test
  public void testDeleteCluster_ThrowsInvalidParameterException() {

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(
            InvalidParameterException.builder().message(INVALID_PARAMETER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.delete(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE, ex, config.getName(), INVALID_PARAMETER_EXCEPTION_MESSAGE,
        InvalidParameterException.class);
  }

  @Test
  public void testDeleteCluster_ThrowsClientException() {

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(ClientException.builder().message(CLIENT_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.delete(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE, ex, config.getName(), CLIENT_EXCEPTION_MESSAGE,
        ClientException.class); }

  @Test
  public void testDeleteCluster_ThrowsServerException() {

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(ServerException.builder().message(SERVER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.delete(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE, ex, config.getName(), SERVER_EXCEPTION_MESSAGE,
        ServerException.class);
  }

  @Test
  public void testDeleteCluster_ThrowsServiceUnavailableException() {

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(ServiceUnavailableException.builder().message(SERVICE_UNAVAILABLE_EXCEPTION).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.delete(config.getName()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE, ex, config.getName(), SERVICE_UNAVAILABLE_EXCEPTION,
        ServiceUnavailableException.class);
  }

  @Test
  public void testDeleteCluster_ThrowsSdkClientException() {

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.delete(config.getName()));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE.formatted(config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testDeleteCluster_ThrowsGenericEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.deleteCluster(any(DeleteClusterRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.delete(config.getName()));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_DELETE.formatted(config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testWaitUntilDeleted_Success() {

    String clusterName = config.getName();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    Cluster deletingCluster = Cluster.builder().status(ClusterStatus.DELETING).build();
    DescribeClusterResponse deletingResponse = DescribeClusterResponse.builder().cluster(deletingCluster).build();

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenReturn(deletingResponse)
        .thenThrow(ResourceNotFoundException.builder().message("Cluster not found").build());

    assertDoesNotThrow(() -> spyClient.waitUntilDeleted(clusterName, Duration.ofSeconds(30)));
    verify(eksClient, atLeastOnce()).describeCluster(any(DescribeClusterRequest.class));
  }

  @Test
  public void testWaitUntilDeleted_TimesOut() {

    String clusterName = config.getName();
    Cluster deletingCluster = Cluster.builder().status(ClusterStatus.DELETING).build();
    DescribeClusterResponse deletingResponse = DescribeClusterResponse.builder().cluster(deletingCluster).build();

    when(eksClient.describeCluster(any(DescribeClusterRequest.class))).thenReturn(deletingResponse);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.waitUntilDeleted(clusterName, Duration.ofSeconds(5))
    );

    assertTrue(ex.getMessage().contains("Timed out waiting"));
  }

  @Test
  public void testWaitUntilDeleted_HandlesUnexpectedException() {

    String clusterName = config.getName();

    when(eksClient.describeCluster(any(DescribeClusterRequest.class)))
        .thenThrow(new RuntimeException("unexpected"));

    EksSafeClient spyClient = Mockito.spy(safeClient);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.waitUntilDeleted(clusterName, Duration.ofSeconds(5))
    );

    assertTrue(ex.getMessage().contains("Timed out waiting"));
  }

  @Test
  public void testPutTags_Success() {

    String clusterArn = config.buildArn();

    doReturn(TagResourceResponse.builder().build())
        .when(eksClient).tagResource(any(TagResourceRequest.class));

    assertDoesNotThrow(() -> safeClient.putTags(clusterArn, config.getTags()));

    verify(eksClient).tagResource(tagResourceRequestCapture.capture());
    TagResourceRequest request = tagResourceRequestCapture.getValue();
    assertEquals(request.tags(), config.getTags());
  }

  @Test
  public void testPutTags_ThrowsBadRequestException() {

    when(eksClient.tagResource(any(TagResourceRequest.class)))
        .thenThrow(BadRequestException.builder()
            .message(BAD_REQUEST_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.putTags(config.buildArn(), config.getTags()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE, ex, config.buildArn(), BAD_REQUEST_EXCEPTION_MESSAGE,
        BadRequestException.class);
  }

  @Test
  public void testPutTags_ThrowsNotFoundException() {

    when(eksClient.tagResource(any(TagResourceRequest.class)))
        .thenThrow(NotFoundException.builder()
            .message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.putTags(config.buildArn(), config.getTags()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE, ex, config.buildArn(), RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE,
        NotFoundException.class);
  }

  @Test
  public void testPutTags_ThrowsSdkClientException() {

    when(eksClient.tagResource(any(TagResourceRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.putTags(config.buildArn(), config.getTags()));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE.formatted(config.buildArn()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testPutTags_ThrowsEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.tagResource(any(TagResourceRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.putTags(config.buildArn(), config.getTags()));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE.formatted(config.buildArn()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testGetTags_Success() {

    String clusterArn = config.buildArn();

    doReturn(ListTagsForResourceResponse.builder().build())
        .when(eksClient).listTagsForResource(any(ListTagsForResourceRequest.class));

    assertDoesNotThrow(() -> safeClient.getTags(clusterArn));

    verify(eksClient).listTagsForResource(listTagsForResourceRequestCapture.capture());
    ListTagsForResourceRequest request = listTagsForResourceRequestCapture.getValue();
    assertEquals(request.resourceArn(), config.buildArn());
  }

  @Test
  public void testGetTags_ThrowsBadRequestException() {

    when(eksClient.listTagsForResource(any(ListTagsForResourceRequest.class)))
        .thenThrow(BadRequestException.builder()
            .message(BAD_REQUEST_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.getTags(config.buildArn()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET, ex, config.buildArn(), BAD_REQUEST_EXCEPTION_MESSAGE,
        BadRequestException.class);
  }

  @Test
  public void testGetTags_ThrowsNotFoundException() {

    when(eksClient.listTagsForResource(any(ListTagsForResourceRequest.class)))
        .thenThrow(NotFoundException.builder()
            .message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.getTags(config.buildArn()));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET, ex, config.buildArn(), RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE,
        NotFoundException.class);
  }

  @Test
  public void testGetTags_ThrowsSdkClientException() {

    when(eksClient.listTagsForResource(any(ListTagsForResourceRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> safeClient.getTags(config.buildArn()));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET.formatted(config.buildArn()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testGetTags_ThrowsEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.listTagsForResource(any(ListTagsForResourceRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> safeClient.getTags(config.buildArn()));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET.formatted(config.buildArn()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testDeleteTags_Success() {

    String clusterArn = config.buildArn();
    Map<String, String> existingTags = config.getTags();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doReturn(existingTags).when(spyClient).getTags(clusterArn);

    doReturn(UntagResourceResponse.builder().build()).when(eksClient)
        .untagResource(any(UntagResourceRequest.class));

    assertDoesNotThrow(() -> spyClient.deleteTags(clusterArn));

    ArgumentCaptor<UntagResourceRequest> captor = ArgumentCaptor.forClass(UntagResourceRequest.class);
    verify(eksClient).untagResource(captor.capture());

    UntagResourceRequest request = captor.getValue();
    assertEquals(clusterArn, request.resourceArn());
    assertEquals(List.copyOf(existingTags.keySet()), request.tagKeys());
  }

  @Test
  public void testDeleteTags_NoTagsPresent() {

    String clusterArn = config.buildArn();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doReturn(Map.of()).when(spyClient).getTags(clusterArn);

    assertDoesNotThrow(() -> spyClient.deleteTags(clusterArn));

    verify(eksClient, never()).untagResource(any(UntagResourceRequest.class));
  }

  @Test
  public void testDeleteTags_ThrowsBadRequest() {

    String clusterArn = config.buildArn();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doReturn(config.getTags()).when(spyClient).getTags(clusterArn);

    when(eksClient.untagResource(any(UntagResourceRequest.class)))
        .thenThrow(BadRequestException.builder().message(BAD_REQUEST_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.deleteTags(clusterArn)
    );
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE, ex, config.buildArn(), BAD_REQUEST_EXCEPTION_MESSAGE,
        BadRequestException.class);
  }

  @Test
  public void testDeleteTags_ThrowsNotFound() {

    String clusterArn = config.buildArn();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doReturn(config.getTags()).when(spyClient).getTags(clusterArn);

    when(eksClient.untagResource(any(UntagResourceRequest.class)))
        .thenThrow(NotFoundException.builder().message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class,
        () -> spyClient.deleteTags(clusterArn));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE, ex, config.buildArn(), RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE,
        NotFoundException.class);
  }

  @Test
  public void testDeleteTags_ThrowsSdkClientException() {

    String clusterArn = config.buildArn();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doReturn(config.getTags()).when(spyClient).getTags(clusterArn);

    when(eksClient.untagResource(any(UntagResourceRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class, () -> spyClient.deleteTags(config.buildArn()));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE.formatted(config.buildArn()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testDeleteTags_ThrowsEksException() {

    String clusterArn = config.buildArn();

    EksSafeClient spyClient = Mockito.spy(safeClient);
    doReturn(config.getTags()).when(spyClient).getTags(clusterArn);

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.untagResource(any(UntagResourceRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class, () -> spyClient.deleteTags(config.buildArn()));
    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE.formatted(config.buildArn()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testCreateAddon_Success_WithVersion() {

    String cluster = config.getName();
    String addon = "vpc-cni";
    String version = "v1.12.0";

    doReturn(CreateAddonResponse.builder().build()).when(eksClient).createAddon(any(CreateAddonRequest.class));

    assertDoesNotThrow(() -> safeClient.createAddon(cluster, addon, version));

    verify(eksClient).createAddon(createAddonRequestCapture.capture());
    CreateAddonRequest createAddonRequest = createAddonRequestCapture.getValue();
    assertEquals(config.getName(), createAddonRequest.clusterName());
    assertEquals(addon, createAddonRequest.addonName());
    assertEquals(version, createAddonRequest.addonVersion());
  }

  @Test
  public void testCreateAddon_Success_NoVersion() {

    String cluster = config.getName();
    String addon = "vpc-cni";

    doReturn(CreateAddonResponse.builder().build()).when(eksClient).createAddon(any(CreateAddonRequest.class));

    assertDoesNotThrow(() -> safeClient.createAddon(cluster, addon, null));

    verify(eksClient).createAddon(createAddonRequestCapture.capture());
    CreateAddonRequest createAddonRequest = createAddonRequestCapture.getValue();
    assertEquals(config.getName(), createAddonRequest.clusterName());
    assertEquals(addon, createAddonRequest.addonName());
    assertNull(createAddonRequest.addonVersion());
  }

  @Test
  public void testCreateAddon_ResourceInUse_IsTolerated() {

    when(eksClient.createAddon(any(CreateAddonRequest.class)))
        .thenThrow(ResourceInUseException.builder().message(RESOURCE_IN_USE_MESSAGE).build());

    assertDoesNotThrow(() -> safeClient.createAddon(config.getName(), "vpc-cni", "v1.12.0"));
  }

  @Test
  public void testCreateAddon_ThrowsInvalidParameterException() {

    when(eksClient.createAddon(any(CreateAddonRequest.class)))
        .thenThrow(InvalidParameterException.builder().message(INVALID_PARAMETER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> safeClient.createAddon(config.getName(), "vpc-cni", "v1.12.0")
    );

    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE, ex, "vpc-cni", config.getName(), INVALID_PARAMETER_EXCEPTION_MESSAGE,
        InvalidParameterException.class);
  }

  @Test
  public void testCreateAddon_ThrowsSdkClientException() {

    when(eksClient.createAddon(any(CreateAddonRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class,
            () -> safeClient.createAddon(config.getName(), "vpc-cni", "v1.12.0"));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE.formatted("vpc-cni", config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testCreateAddon_ThrowsEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.createAddon(any(CreateAddonRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> safeClient.createAddon(config.getName(), "vpc-cni", "v1.12.0")
    );

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE.formatted("vpc-cni", config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testUpdateAddon_Success_WithVersion() {

    String cluster = config.getName();
    String addon = "vpc-cni";
    String version = "v1.12.0";

    doReturn(UpdateAddonResponse.builder().build()).when(eksClient)
        .updateAddon(any(UpdateAddonRequest.class));

    assertDoesNotThrow(() -> safeClient.updateAddon(cluster, addon, version));

    verify(eksClient).updateAddon(updateAddonRequestCapture.capture());
    UpdateAddonRequest updateRequest = updateAddonRequestCapture.getValue();
    assertEquals(cluster, updateRequest.clusterName());
    assertEquals(addon, updateRequest.addonName());
    assertEquals(version, updateRequest.addonVersion());
  }

  @Test
  public void testUpdateAddon_Success_NoVersion() {

    doReturn(UpdateAddonResponse.builder().build()).when(eksClient)
        .updateAddon(any(UpdateAddonRequest.class));

    assertDoesNotThrow(() -> safeClient.updateAddon(config.getName(), "vpc-cni", null));

    verify(eksClient).updateAddon(updateAddonRequestCapture.capture());
    UpdateAddonRequest updateRequest = updateAddonRequestCapture.getValue();
    assertEquals(config.getName(), updateRequest.clusterName());
    assertEquals("vpc-cni", updateRequest.addonName());
    assertNull(updateRequest.addonVersion());
  }

  @Test
  public void testUpdateAddon_ResourceNotFound_IsTolerated() {

    when(eksClient.updateAddon(any(UpdateAddonRequest.class)))
        .thenThrow(ResourceNotFoundException.builder().message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    assertDoesNotThrow(() -> safeClient.updateAddon(config.getName(), "vpc-cni", "v1.12.0"));
  }

  @Test
  public void testUpdateAddon_ThrowsInvalidParameterException() {

    when(eksClient.updateAddon(any(UpdateAddonRequest.class)))
        .thenThrow(InvalidParameterException.builder().message(INVALID_PARAMETER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> safeClient.updateAddon(config.getName(), "vpc-cni", "v1.12.0")
    );

    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE, ex, "vpc-cni", config.getName(), INVALID_PARAMETER_EXCEPTION_MESSAGE,
        InvalidParameterException.class);
  }

  @Test
  public void testUpdateAddon_ThrowsSdkClientException() {

    when(eksClient.updateAddon(any(UpdateAddonRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class,
            () -> safeClient.updateAddon(config.getName(), "vpc-cni", "v1.12.0"));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE.formatted("vpc-cni", config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testUpdateAddon_ThrowsEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.updateAddon(any(UpdateAddonRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> safeClient.updateAddon(config.getName(), "vpc-cni", "v1.12.0")
    );

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE.formatted("vpc-cni", config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testGetAddons_Success() {

    String clusterName = config.getName();

    ListAddonsResponse listResponse = ListAddonsResponse.builder()
        .addons(List.of("vpc-cni"))
        .build();

    when(eksClient.listAddons(any(ListAddonsRequest.class))).thenReturn(listResponse);

    DescribeAddonResponse cniDetails = DescribeAddonResponse.builder()
        .addon(Addon.builder().addonVersion("v1.12.0").build())
        .build();

    when(eksClient.describeAddon(any(DescribeAddonRequest.class))).thenReturn(cniDetails);

    EksClusterAddons result = safeClient.getAddons(clusterName);

    assertNotNull(result);
    assertEquals(1, result.getAddons().size());

    assertEquals("vpc-cni", result.getAddons().getFirst().getName());
    assertEquals("v1.12.0", result.getAddons().getFirst().getVersion());
  }

  @Test
  public void testGetAddons_EmptyList() {

    String clusterName = config.getName();

    when(eksClient.listAddons(any(ListAddonsRequest.class))).thenReturn(ListAddonsResponse.builder().addons(List.of()).build());

    EksClusterAddons result = safeClient.getAddons(clusterName);

    assertNotNull(result);
    assertTrue(result.getAddons().isEmpty());
  }

  @Test
  public void testGetAddons_ThrowsInvalidParameter() {

    when(eksClient.listAddons(any(ListAddonsRequest.class)))
        .thenThrow(InvalidParameterException.builder().message(INVALID_PARAMETER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class,
        () -> safeClient.getAddons(config.getName()));

    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET, ex, config.getName(), INVALID_PARAMETER_EXCEPTION_MESSAGE,
        InvalidParameterException.class);
  }

  @Test
  public void testGetAddons_ThrowsSdkClientException() {

    when(eksClient.listAddons(any(ListAddonsRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class,
            () -> safeClient.getAddons(config.getName()));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET.formatted(config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testGetAddons_ThrowsGenericEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.listAddons(any(ListAddonsRequest.class))).thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> safeClient.getAddons(config.getName())
    );

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET.formatted(config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testWaitUntilAddonsActive_Success() {

    String clusterName = "test-cluster";
    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("vpc-cni", "v1"),
        new EksClusterAddon("coredns", "v1")
    ));

    DescribeAddonResponse creating = DescribeAddonResponse.builder()
        .addon(Addon.builder().status(AddonStatus.CREATING).build()).build();

    DescribeAddonResponse active = DescribeAddonResponse.builder()
        .addon(Addon.builder().status(AddonStatus.ACTIVE).build()).build();

    EksSafeClient spyClient = Mockito.spy(safeClient);

    when(eksClient.describeAddon(any(DescribeAddonRequest.class)))
        .thenAnswer(invocation -> {
          DescribeAddonRequest req = invocation.getArgument(0);
          if ("vpc-cni".equals(req.addonName())) {
            return active;
          } else if ("coredns".equals(req.addonName())) {
            return creating;
          }
          return active;
        })
        .thenReturn(active);

    assertDoesNotThrow(() ->
        spyClient.waitUntilAddonsActive(clusterName, addons, Duration.ofSeconds(30)));

    verify(eksClient, atLeastOnce()).describeAddon(any(DescribeAddonRequest.class));
  }

  @Test
  public void testWaitUntilAddonsActive_TimesOut() {

    String clusterName = config.getName();

    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("vpc-cni", "v1")
    ));

    DescribeAddonResponse creating = DescribeAddonResponse.builder()
        .addon(Addon.builder().status(AddonStatus.CREATING).build()).build();

    when(eksClient.describeAddon(any(DescribeAddonRequest.class))).thenReturn(creating);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.waitUntilAddonsActive(clusterName, addons, Duration.ofSeconds(5))
    );

    assertTrue(ex.getMessage().contains("Timed out"));
  }

  @Test
  public void testWaitUntilAddonsActive_HandlesResourceNotFound() {

    String clusterName = config.getName();
    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("nonexistent-addon", "v1")
    ));

    when(eksClient.describeAddon(any(DescribeAddonRequest.class)))
        .thenThrow(ResourceNotFoundException.builder().message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    EksSafeClient spyClient = Mockito.spy(safeClient);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.waitUntilAddonsActive(clusterName, addons, Duration.ofSeconds(5))
    );

    assertTrue(ex.getMessage().contains("Timed out"));
  }

  @Test
  public void testWaitUntilAddonsActive_HandlesEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    String clusterName = config.getName();
    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("unstable-addon", "v1")
    ));

    when(eksClient.describeAddon(any(DescribeAddonRequest.class)))
        .thenThrow(serviceException);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    assertThrows(CloudResourceStepException.class,
        () -> spyClient.waitUntilAddonsActive(clusterName, addons, Duration.ofSeconds(5)));
  }

  @Test
  public void testRemoveAddon_Success() {

    String cluster = config.getName();
    String addon = "vpc-cni";

    doReturn(DeleteAddonResponse.builder().build())
        .when(eksClient).deleteAddon(any(DeleteAddonRequest.class));

    assertDoesNotThrow(() -> safeClient.removeAddon(cluster, addon));

    verify(eksClient).deleteAddon(deleteAddonRequestArgumentCaptor.capture());
    DeleteAddonRequest addonRequest = deleteAddonRequestArgumentCaptor.getValue();
    assertEquals(cluster, addonRequest.clusterName());
    assertEquals(addon, addonRequest.addonName());
  }

  @Test
  public void testRemoveAddon_AddonNotFound_IsTolerated() {

    when(eksClient.deleteAddon(any(DeleteAddonRequest.class)))
        .thenThrow(ResourceNotFoundException.builder().message(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE).build());

    assertDoesNotThrow(() -> safeClient.removeAddon(config.getName(), "missing-addon"));
  }

  @Test
  public void testRemoveAddon_ThrowsInvalidParameterException() {

    when(eksClient.deleteAddon(any(DeleteAddonRequest.class)))
        .thenThrow(InvalidParameterException.builder().message(INVALID_PARAMETER_EXCEPTION_MESSAGE).build());

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class,
        () -> safeClient.removeAddon(config.getName(), "vpc-cni"));
    assertBaseExceptionHandling(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE, ex, "vpc-cni", config.getName(), INVALID_PARAMETER_EXCEPTION_MESSAGE,
        InvalidParameterException.class);
  }

  @Test
  public void testRemoveAddon_ThrowsSdkClientException() {

    when(eksClient.deleteAddon(any(DeleteAddonRequest.class)))
        .thenThrow(SdkClientException.create(SDK_CLIENT_EXCEPTION_MESSAGE));

    RetryableCloudResourceStepException ex =
        assertThrows(RetryableCloudResourceStepException.class,
            () -> safeClient.removeAddon(config.getName(), "vpc-cni"));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE.formatted("vpc-cni", config.getName()), ex.getMessage());
    assertEquals(SDK_CLIENT_EXCEPTION_MESSAGE, ex.getMessageDetails().getFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> ex.getMessageDetails().get(1));
    assertInstanceOf(SdkClientException.class, ex.getCause());
    assertNull(ex.getCause().getCause());
  }

  @Test
  public void testRemoveAddon_ThrowsEksException() {

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.deleteAddon(any(DeleteAddonRequest.class)))
        .thenThrow(serviceException);

    CloudResourceStepException ex = assertThrows(CloudResourceStepException.class,
        () -> safeClient.removeAddon(config.getName(), "vpc-cni"));

    assertEquals(EksSafeClient.BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE.formatted("vpc-cni", config.getName()), ex.getMessage());
    assertEquals(serviceException.getMessage(), ex.getMessageDetails().getFirst());
    assertInstanceOf(EksException.class, ex.getCause());
  }

  @Test
  public void testWaitUntilAddonsDeleted_Success() {

    String cluster = config.getName();
    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("vpc-cni", "v1"),
        new EksClusterAddon("core-dns", "v1")
    ));

    EksSafeClient spyClient = Mockito.spy(safeClient);

    AtomicInteger vpcCniCallCount = new AtomicInteger();

    when(eksClient.describeAddon(any(DescribeAddonRequest.class)))
        .thenAnswer(invocation -> {
          DescribeAddonRequest req = invocation.getArgument(0);
          String addon = req.addonName();

          if ("vpc-cni".equals(addon)) {
            if (vpcCniCallCount.getAndIncrement() == 0) {
              return DescribeAddonResponse.builder()
                  .addon(Addon.builder().status(AddonStatus.DELETING).build())
                  .build();
            } else {
              throw ResourceNotFoundException.builder().message("vpc-cni deleted").build();
            }
          }

          if ("core-dns".equals(addon)) {
            throw ResourceNotFoundException.builder().message("core-dns deleted").build();
          }

          return DescribeAddonResponse.builder()
              .addon(Addon.builder().status(AddonStatus.ACTIVE).build())
              .build();
        });

    assertDoesNotThrow(() -> spyClient.waitUntilAddonsDeleted(cluster, addons, Duration.ofSeconds(30)));
  }

  @Test
  public void testWaitUntilAddonsDeleted_TimesOut() {

    String cluster = config.getName();
    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("vpc-cni", "v1")
    ));

    DescribeAddonResponse stillPresent = DescribeAddonResponse.builder()
        .addon(Addon.builder().status(AddonStatus.DELETING).build()).build();

    when(eksClient.describeAddon(any(DescribeAddonRequest.class))).thenReturn(stillPresent);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    CloudResourceStepException ex = assertThrows(
        CloudResourceStepException.class,
        () -> spyClient.waitUntilAddonsDeleted(cluster, addons, Duration.ofSeconds(5))
    );

    assertTrue(ex.getMessage().contains("Timed out waiting for addons to be deleted"));
  }

  @Test
  public void testWaitUntilAddonsDeleted_HandlesEksException() {

    String cluster = config.getName();
    EksClusterAddons addons = new EksClusterAddons(List.of(
        new EksClusterAddon("bad-addon", "v1")
    ));

    AwsServiceException serviceException = EksException.builder()
        .awsErrorDetails(AwsErrorDetails.builder()
            .errorMessage(AWS_ERROR_DETAILS_MESSAGE).build())
        .message(EKS_EXCEPTION_MESSAGE).build();

    when(eksClient.describeAddon(any(DescribeAddonRequest.class)))
        .thenThrow(serviceException);

    EksSafeClient spyClient = Mockito.spy(safeClient);

    assertThrows(CloudResourceStepException.class,
        () -> spyClient.waitUntilAddonsDeleted(cluster, addons, Duration.ofSeconds(5)));
  }


  private EksClusterConfig createBasicConfig() {

    Map<String, String> tags = new HashMap<>();
    tags.put("environment", "development");

    EksClusterConfig config = new EksClusterConfig();
    config.setName("test-cluster");
    config.setVersion("1.29");
    config.setRoleArn("arn:aws:iam::123456789012:role/EKSClusterRole");
    config.setSubnets(List.of("subnet-111", "subnet-222"));
    config.setAuthenticationMode(EksClusterAuthenticationMode.API);
    config.setSupportType(EksClusterSupportType.STANDARD);
    config.setTags(tags);
    config.setBootstrapClusterCreatorAdminPermissions(true);
    config.setBootstrapSelfManagedAddons(true);
    config.setEnableZonalShift(true);
    config.setOwnedEncryptionKMSKeyArn("arn:aws:kms:eu-west-1:123456789012:key/mrk-1234abcd12ab34cd56ef1234567890ab");

    return config;
  }

  private void assertSuccessfulCreateRequest(EksClusterConfig config, CreateClusterRequest request) {

    assertEquals(config.getName(), request.name());
    assertEquals(config.getVersion(), request.version());
    assertEquals(config.getRoleArn(), request.roleArn());
    assertEquals(config.getSubnets(), request.resourcesVpcConfig().subnetIds());
    assertEquals(config.isEnableZonalShift(), request.zonalShiftConfig().enabled());
    assertEquals(config.getAuthenticationMode().getValue(), request.accessConfig().authenticationMode().toString());
    assertEquals(config.isBootstrapClusterCreatorAdminPermissions(),
        request.accessConfig().bootstrapClusterCreatorAdminPermissions());
    assertEquals(config.isBootstrapSelfManagedAddons(), request.bootstrapSelfManagedAddons());
    assertEquals(config.getSupportType().getValue(), request.upgradePolicy().supportType().toString());

    if (config.getOwnedEncryptionKMSKeyArn() != null && !config.getOwnedEncryptionKMSKeyArn().isEmpty()) {
      assertEquals(config.getOwnedEncryptionKMSKeyArn(), request.encryptionConfig().getFirst().provider().keyArn());
    } else {
      assertTrue(request.encryptionConfig().isEmpty());
    }
  }

  private void assertBaseExceptionHandling(String baseMessage, CloudResourceStepException ex, String clusterName,
                                           String detailsMessage, Class<? extends EksException> cause) {

    assertEquals(baseMessage.formatted(clusterName), ex.getMessage());
    assertEquals(detailsMessage, ex.getMessageDetails().getFirst());
    assertInstanceOf(cause, ex.getCause());
  }

  private void assertBaseExceptionHandling(String baseMessage, CloudResourceStepException ex, String clusterName, String additionalFormat,
                                           String detailsMessage, Class<? extends EksException> cause) {

    assertEquals(baseMessage.formatted(clusterName, additionalFormat), ex.getMessage());
    assertEquals(detailsMessage, ex.getMessageDetails().getFirst());
    assertInstanceOf(cause, ex.getCause());
  }

}
