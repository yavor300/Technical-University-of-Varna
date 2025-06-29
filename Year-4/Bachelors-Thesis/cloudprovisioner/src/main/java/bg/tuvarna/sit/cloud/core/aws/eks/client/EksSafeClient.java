package bg.tuvarna.sit.cloud.core.aws.eks.client;

import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddon;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddons;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAmiType;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroup;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroups;
import bg.tuvarna.sit.cloud.core.aws.eks.model.ProvisionedLabels;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.exception.CloudExceptionHandler;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.services.eks.EksClient;
import software.amazon.awssdk.services.eks.model.AMITypes;
import software.amazon.awssdk.services.eks.model.AuthenticationMode;
import software.amazon.awssdk.services.eks.model.BadRequestException;
import software.amazon.awssdk.services.eks.model.ClientException;
import software.amazon.awssdk.services.eks.model.CreateAccessConfigRequest;
import software.amazon.awssdk.services.eks.model.CreateAddonRequest;
import software.amazon.awssdk.services.eks.model.CreateClusterRequest;
import software.amazon.awssdk.services.eks.model.CreateClusterResponse;
import software.amazon.awssdk.services.eks.model.CreateNodegroupRequest;
import software.amazon.awssdk.services.eks.model.DeleteAddonRequest;
import software.amazon.awssdk.services.eks.model.DeleteClusterRequest;
import software.amazon.awssdk.services.eks.model.DeleteClusterResponse;
import software.amazon.awssdk.services.eks.model.DeleteNodegroupRequest;
import software.amazon.awssdk.services.eks.model.DescribeAddonRequest;
import software.amazon.awssdk.services.eks.model.DescribeAddonResponse;
import software.amazon.awssdk.services.eks.model.DescribeClusterRequest;
import software.amazon.awssdk.services.eks.model.DescribeClusterResponse;
import software.amazon.awssdk.services.eks.model.DescribeNodegroupRequest;
import software.amazon.awssdk.services.eks.model.DescribeNodegroupResponse;
import software.amazon.awssdk.services.eks.model.EksException;
import software.amazon.awssdk.services.eks.model.EncryptionConfig;
import software.amazon.awssdk.services.eks.model.InvalidParameterException;
import software.amazon.awssdk.services.eks.model.InvalidRequestException;
import software.amazon.awssdk.services.eks.model.ListAddonsRequest;
import software.amazon.awssdk.services.eks.model.ListAddonsResponse;
import software.amazon.awssdk.services.eks.model.ListNodegroupsRequest;
import software.amazon.awssdk.services.eks.model.ListNodegroupsResponse;
import software.amazon.awssdk.services.eks.model.ListTagsForResourceRequest;
import software.amazon.awssdk.services.eks.model.ListTagsForResourceResponse;
import software.amazon.awssdk.services.eks.model.Nodegroup;
import software.amazon.awssdk.services.eks.model.NodegroupScalingConfig;
import software.amazon.awssdk.services.eks.model.NotFoundException;
import software.amazon.awssdk.services.eks.model.Provider;
import software.amazon.awssdk.services.eks.model.ResourceInUseException;
import software.amazon.awssdk.services.eks.model.ResourceLimitExceededException;
import software.amazon.awssdk.services.eks.model.ResourceNotFoundException;
import software.amazon.awssdk.services.eks.model.ServerException;
import software.amazon.awssdk.services.eks.model.ServiceUnavailableException;
import software.amazon.awssdk.services.eks.model.SupportType;
import software.amazon.awssdk.services.eks.model.TagResourceRequest;
import software.amazon.awssdk.services.eks.model.UnsupportedAvailabilityZoneException;
import software.amazon.awssdk.services.eks.model.UntagResourceRequest;
import software.amazon.awssdk.services.eks.model.UpdateAccessConfigRequest;
import software.amazon.awssdk.services.eks.model.UpdateAddonRequest;
import software.amazon.awssdk.services.eks.model.UpdateClusterConfigRequest;
import software.amazon.awssdk.services.eks.model.UpdateClusterVersionRequest;
import software.amazon.awssdk.services.eks.model.UpdateLabelsPayload;
import software.amazon.awssdk.services.eks.model.UpdateNodegroupConfigRequest;
import software.amazon.awssdk.services.eks.model.UpdateNodegroupVersionRequest;
import software.amazon.awssdk.services.eks.model.UpgradePolicyRequest;
import software.amazon.awssdk.services.eks.model.VpcConfigRequest;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.eks.model.ZonalShiftConfigRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class EksSafeClient implements AutoCloseable {

  static final String BASE_EXCEPTION_MESSAGE_ON_CREATE = "EKS service error while creating cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_UPDATE = "EKS service error while updating cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_DESCRIBE = "EKS service error while describing cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_DELETE = "EKS service error while deleting cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE = "EKS service error while tagging cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET
      = "EKS service error while retrieving tags for cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE
      = "EKS service error while deleting tags for cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE = "EKS service error while applying addon '%s' in cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE = "EKS service error while updating addon '%s' in cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET = "EKS service error while fetching addons from cluster '%s'";
  static final String BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE = "EKS service error while deleting addon '%s' from cluster '%s'";


  private final EksClient client;

  public EksSafeClient(EksClient client) {
    this.client = client;
  }

  @Override
  public void close() {
    client.close();
  }

  public CreateClusterResponse create(EksClusterConfig config) throws CloudResourceStepException {

    try {

      VpcConfigRequest vpcConfig = VpcConfigRequest.builder()
          .subnetIds(config.getSubnets())
          .build();

      CreateAccessConfigRequest accessConfig = CreateAccessConfigRequest.builder()
          .bootstrapClusterCreatorAdminPermissions(config.isBootstrapClusterCreatorAdminPermissions())
          .authenticationMode(AuthenticationMode.fromValue(config.getAuthenticationMode().getValue()))
          .build();

      UpgradePolicyRequest upgradePolicy = UpgradePolicyRequest.builder()
          .supportType(SupportType.fromValue(config.getSupportType().getValue()))
          .build();

      ZonalShiftConfigRequest zonalShift = ZonalShiftConfigRequest.builder()
          .enabled(config.isEnableZonalShift())
          .build();

      CreateClusterRequest.Builder requestBuilder = CreateClusterRequest.builder()
          .name(config.getName())
          .version(config.getVersion())
          .roleArn(config.getRoleArn())
          .bootstrapSelfManagedAddons(config.isBootstrapSelfManagedAddons())
          .resourcesVpcConfig(vpcConfig)
          .accessConfig(accessConfig)
          .upgradePolicy(upgradePolicy)
          .zonalShiftConfig(zonalShift);

      if (config.getOwnedEncryptionKMSKeyArn() != null && !config.getOwnedEncryptionKMSKeyArn().isEmpty()) {
        requestBuilder.encryptionConfig(
            EncryptionConfig.builder()
                .provider(Provider.builder()
                    .keyArn(config.getOwnedEncryptionKMSKeyArn())
                    .build())
                .build());
      }

      return client.createCluster(requestBuilder.build());

    } catch (ResourceInUseException | ResourceLimitExceededException | InvalidParameterException | ClientException |
             ServerException | ServiceUnavailableException | UnsupportedAvailabilityZoneException e) {

      String message = BASE_EXCEPTION_MESSAGE_ON_CREATE.formatted(config.getName());
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(config.getName(),
          BASE_EXCEPTION_MESSAGE_ON_CREATE.formatted(config.getName()), e);

    } catch (EksException e) {
      throw handleEksException(config.getName(),
          BASE_EXCEPTION_MESSAGE_ON_CREATE.formatted(config.getName()), e);
    }
  }

  public void updateCluster(String clusterName, EksClusterConfig config) throws CloudResourceStepException {

    try {

      UpdateClusterConfigRequest.Builder requestBuilder = UpdateClusterConfigRequest.builder()
          .name(clusterName)
          .accessConfig(UpdateAccessConfigRequest.builder()
              .authenticationMode(AuthenticationMode.fromValue(config.getAuthenticationMode().getValue()))
              .build())
          .zonalShiftConfig(ZonalShiftConfigRequest.builder()
              .enabled(config.isEnableZonalShift())
              .build())
          .resourcesVpcConfig(VpcConfigRequest.builder()
              .subnetIds(config.getSubnets()).build())
          .upgradePolicy(UpgradePolicyRequest.builder()
              .supportType(SupportType.fromValue(config.getSupportType().getValue()))
              .build());

      client.updateClusterConfig(requestBuilder.build());
      log.info("Updated access, zonal shift and vpc config for EKS cluster '{}'", clusterName);
      waitUntilActive(clusterName, Duration.ofMinutes(10));

      UpdateClusterVersionRequest.Builder versionBuilder = UpdateClusterVersionRequest.builder()
          .name(clusterName)
          .version(config.getVersion());

      client.updateClusterVersion(versionBuilder.build());
      log.info("Updated version for EKS cluster '{}' to '{}'", clusterName, config.getVersion());

    } catch (ResourceInUseException | ResourceLimitExceededException | InvalidParameterException | ClientException |
             ServerException | ResourceNotFoundException | ServiceUnavailableException |
             InvalidRequestException | UnsupportedAvailabilityZoneException e) {

      String message = BASE_EXCEPTION_MESSAGE_ON_UPDATE.formatted(config.getName());
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_UPDATE.formatted(clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_UPDATE.formatted(clusterName), e);
    }
  }

  public void waitUntilActive(String clusterName, Duration timeout) throws CloudResourceStepException {

    Instant start = Instant.now();
    // TODO [Enhancement] Move to config
    Duration pollInterval = Duration.ofSeconds(10);

    log.info("Waiting for EKS cluster '{}' to reach status ACTIVE...", clusterName);

    while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {

      DescribeClusterResponse response = describe(clusterName);

      String status = response.cluster().statusAsString();
      log.info("Cluster '{}' status: {}", clusterName, status);

      if ("ACTIVE".equalsIgnoreCase(status)) {
        log.info("EKS cluster '{}' is now ACTIVE.", clusterName);
        return;
      }

      try {
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        throw new CloudResourceStepException("Polling interrupted while waiting for cluster to become ACTIVE.", ie);
      }
    }

    throw new CloudResourceStepException(
        "Timed out waiting for EKS cluster '%s' to become ACTIVE.".formatted(clusterName));
  }

  public DescribeClusterResponse describe(String clusterName) throws CloudResourceStepException {

    try {
      return client.describeCluster(DescribeClusterRequest.builder().name(clusterName).build());

    } catch (ResourceNotFoundException | ClientException | ServerException | ServiceUnavailableException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_DESCRIBE.formatted(clusterName);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_DESCRIBE.formatted(clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_DESCRIBE.formatted(clusterName), e);
    }
  }

  public DeleteClusterResponse delete(String clusterName) throws CloudResourceStepException {

    try {
      return client.deleteCluster(DeleteClusterRequest.builder().name(clusterName).build());

    } catch (ResourceNotFoundException | InvalidParameterException | ClientException
             | ServerException | ServiceUnavailableException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_DELETE.formatted(clusterName);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_DELETE.formatted(clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_DELETE.formatted(clusterName), e);
    }
  }

  public void waitUntilDeleted(String clusterName, Duration timeout) throws CloudResourceStepException {

    Instant start = Instant.now();
    Duration pollInterval = Duration.ofSeconds(10);

    log.info("Waiting for EKS cluster '{}' to be deleted...", clusterName);

    while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {

      try {
        DescribeClusterResponse response = client.describeCluster(
            DescribeClusterRequest.builder().name(clusterName).build());
        String status = response.cluster().statusAsString();
        log.info("Cluster '{}' status: {}", clusterName, status);

      } catch (ResourceNotFoundException e) {
        log.info("EKS cluster '{}' has been deleted.", clusterName);
        return;
      } catch (Exception e) {
        log.warn("Error while checking cluster status for '{}'", clusterName, e);
      }

      try {
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        throw new CloudResourceStepException("Polling interrupted while waiting for cluster deletion.", ie);
      }
    }

    throw new CloudResourceStepException(
        "Timed out waiting for EKS cluster '%s' to be deleted.".formatted(clusterName));
  }

  public void putTags(String clusterArn, Map<String, String> tags) throws CloudResourceStepException {

    try {
      client.tagResource(TagResourceRequest.builder()
          .resourceArn(clusterArn)
          .tags(tags)
          .build());

      log.info("Successfully tagged EKS cluster '{}'", clusterArn);

    } catch (BadRequestException | NotFoundException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE.formatted(clusterArn);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterArn,
          BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE.formatted(clusterArn), e);

    } catch (EksException e) {
      throw handleEksException(clusterArn,
          BASE_EXCEPTION_MESSAGE_ON_TAGGING_CREATE.formatted(clusterArn), e);
    }
  }

  public Map<String, String> getTags(String clusterArn) throws CloudResourceStepException {

    try {
      ListTagsForResourceResponse response = client.listTagsForResource(
          ListTagsForResourceRequest.builder()
              .resourceArn(clusterArn)
              .build());

      return response.tags();

    } catch (BadRequestException | NotFoundException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET.formatted(clusterArn);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterArn,
          BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET.formatted(clusterArn), e);

    } catch (EksException e) {
      throw handleEksException(clusterArn,
          BASE_EXCEPTION_MESSAGE_ON_TAGGING_GET.formatted(clusterArn), e);
    }
  }

  public void deleteTags(String clusterArn) throws CloudResourceStepException {

    try {

      Map<String, String> existingTags = getTags(clusterArn);

      if (existingTags.isEmpty()) {
        log.info("No tags found on EKS cluster '{}', nothing to delete.", clusterArn);
        return;
      }

      List<String> tagKeys = new ArrayList<>(existingTags.keySet());

      client.untagResource(UntagResourceRequest.builder()
          .resourceArn(clusterArn)
          .tagKeys(tagKeys)
          .build());

      log.info("Successfully deleted all tags from EKS cluster '{}'", clusterArn);

    } catch (BadRequestException | NotFoundException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE.formatted(clusterArn);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterArn,
          BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE.formatted(clusterArn), e);

    } catch (EksException e) {
      throw handleEksException(clusterArn,
          BASE_EXCEPTION_MESSAGE_ON_TAGGING_DELETE.formatted(clusterArn), e);
    }
  }

  public void createAddon(String clusterName, String addonName, String addonVersion) throws CloudResourceStepException {

    try {
      CreateAddonRequest.Builder builder = CreateAddonRequest.builder()
          .clusterName(clusterName)
          .addonName(addonName);

      if (addonVersion != null && !addonVersion.isBlank()) {
        builder.addonVersion(addonVersion);
      }

      client.createAddon(builder.build());
      log.info("Successfully created add-on '{}' on cluster '{}'", addonName, clusterName);

    } catch (ResourceInUseException e) {
      log.info("Add-on '{}' is already installed on cluster '{}'. Skipping.", addonName, clusterName);

    } catch (InvalidParameterException | InvalidRequestException | ResourceNotFoundException |
             ClientException | ServerException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE.formatted(addonName, clusterName);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    }
    catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE.formatted(addonName, clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDON_CREATE.formatted(addonName, clusterName), e);
    }
  }

  public void updateAddon(String clusterName, String addonName, String addonVersion) throws CloudResourceStepException {

    try {
      UpdateAddonRequest.Builder builder = UpdateAddonRequest.builder()
          .clusterName(clusterName)
          .addonName(addonName);

      if (addonVersion != null && !addonVersion.isBlank()) {
        builder.addonVersion(addonVersion);
      }

      client.updateAddon(builder.build());
      log.info("Successfully updated add-on '{}' on cluster '{}' to version '{}'",
          addonName, clusterName, addonVersion != null ? addonVersion : "(latest)");

    } catch (ResourceNotFoundException e) {
      log.warn("Addon '{}' not found in cluster '{}'. Cannot update.", addonName, clusterName);

    } catch (InvalidParameterException | InvalidRequestException | ResourceInUseException |
             ClientException | ServerException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE.formatted(addonName, clusterName);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE.formatted(addonName, clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDON_UPDATE.formatted(addonName, clusterName), e);
    }
  }

  public EksClusterAddons getAddons(String clusterName) throws CloudResourceStepException {

    try {
      ListAddonsResponse response = client.listAddons(ListAddonsRequest.builder()
          .clusterName(clusterName)
          .build());

      List<EksClusterAddon> addons = new ArrayList<>();

      for (String addonName : response.addons()) {
        DescribeAddonResponse addonDetails = client.describeAddon(
            DescribeAddonRequest.builder()
                .clusterName(clusterName)
                .addonName(addonName)
                .build());

        String version = addonDetails.addon().addonVersion();
        addons.add(new EksClusterAddon(addonName, version));
      }

      log.info("Retrieved {} addon(s) from cluster '{}'", addons.size(), clusterName);
      return new EksClusterAddons(addons);

    } catch (InvalidParameterException | InvalidRequestException | ResourceNotFoundException |
             ClientException | ServerException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET.formatted(clusterName);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET.formatted(clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDONS_GET.formatted(clusterName), e);
    }
  }

  public void waitUntilAddonsActive(String clusterName, EksClusterAddons addons, Duration timeout)
      throws CloudResourceStepException {

    Instant start = Instant.now();
    Duration pollInterval = Duration.ofSeconds(10);

    log.info("Waiting for addons on cluster '{}' to become ACTIVE...", clusterName);

    Set<String> pending = addons.getAddons()
        .stream()
        .map(EksClusterAddon::getName)
        .collect(Collectors.toSet());

    while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
      Iterator<String> iterator = pending.iterator();

      while (iterator.hasNext()) {
        String addonName = iterator.next();

        try {
          DescribeAddonResponse response = client.describeAddon(DescribeAddonRequest.builder()
              .clusterName(clusterName)
              .addonName(addonName)
              .build());

          String status = response.addon().statusAsString();
          log.info("Addon '{}' status: {}", addonName, status);

          if ("ACTIVE".equalsIgnoreCase(status)) {
            iterator.remove();
          }

        } catch (ResourceNotFoundException e) {
          log.warn("Addon '{}' not found in cluster '{}'", addonName, clusterName);
        } catch (EksException e) {
          log.warn("Failed to check status for addon '{}'", addonName, e);
        }
      }

      if (pending.isEmpty()) {
        log.info("All addons in cluster '{}' are ACTIVE.", clusterName);
        return;
      }

      try {
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new CloudResourceStepException("Polling interrupted while waiting for addons to become ACTIVE.", e);
      }
    }

    throw new CloudResourceStepException(
        "Timed out waiting for addons to become ACTIVE in cluster '%s'. Pending: %s"
            .formatted(clusterName, pending));
  }

  public void removeAddon(String clusterName, String addonName) throws CloudResourceStepException {

    try {
      client.deleteAddon(DeleteAddonRequest.builder()
          .clusterName(clusterName)
          .addonName(addonName)
          .build());

      log.info("Successfully removed add-on '{}' from cluster '{}'", addonName, clusterName);

    } catch (ResourceNotFoundException e) {
      log.warn("Add-on '{}' not found on cluster '{}'. It might have already been removed.", addonName, clusterName);

    } catch (InvalidParameterException | InvalidRequestException |
             ClientException | ServerException e) {
      String message = BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE.formatted(addonName, clusterName);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
      throw CloudExceptionHandler.wrapToCloudResourceStepException(message, e.getMessage(), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE.formatted(addonName, clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          BASE_EXCEPTION_MESSAGE_ON_ADDONS_DELETE.formatted(addonName, clusterName), e);
    }
  }

  public void waitUntilAddonsDeleted(String clusterName, EksClusterAddons eksClusterAddons, Duration timeout)
      throws CloudResourceStepException {

    Instant start = Instant.now();
    Duration pollInterval = Duration.ofSeconds(10);

    log.info("Waiting for addons on cluster '{}' to be deleted...", clusterName);

    Set<String> pending = eksClusterAddons.getAddons()
        .stream()
        .map(EksClusterAddon::getName)
        .collect(Collectors.toSet());

    while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
      Iterator<String> iterator = pending.iterator();

      while (iterator.hasNext()) {
        String addonName = iterator.next();

        try {
          client.describeAddon(DescribeAddonRequest.builder()
              .clusterName(clusterName)
              .addonName(addonName)
              .build());

          log.info("Addon '{}' still exists on cluster '{}'", addonName, clusterName);

        } catch (ResourceNotFoundException e) {
          log.info("Addon '{}' successfully deleted from cluster '{}'", addonName, clusterName);
          iterator.remove();
        } catch (EksException e) {
          log.warn("Failed to check deletion status for addon '{}'", addonName, e);
        }
      }

      if (pending.isEmpty()) {
        log.info("All addons successfully deleted from cluster '{}'", clusterName);
        return;
      }

      try {
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new CloudResourceStepException("Polling interrupted while waiting for addons to be deleted.", e);
      }
    }

    throw new CloudResourceStepException(
        "Timed out waiting for addons to be deleted in cluster '%s'. Still present: %s"
            .formatted(clusterName, pending));
  }

  public void createNodeGroup(String clusterName, EksClusterConfig.EksNodeGroupConfig ngConfig)
      throws CloudResourceStepException {

    try {
      CreateNodegroupRequest request = CreateNodegroupRequest.builder()
          .clusterName(clusterName)
          .nodegroupName(ngConfig.getName())
          .nodeRole(ngConfig.getNodeRoleArn())
          .subnets(ngConfig.getSubnets())
          .amiType(AMITypes.fromValue(ngConfig.getAmiType().getValue()))
          .releaseVersion(ngConfig.getReleaseVersion())
          .diskSize(ngConfig.getDiskSize())
          .instanceTypes(ngConfig.getInstanceType())
          .scalingConfig(NodegroupScalingConfig.builder()
              .minSize(ngConfig.getMinSize())
              .maxSize(ngConfig.getMaxSize())
              .desiredSize(ngConfig.getDesiredSize())
              .build())
          .labels(ngConfig.getLabels())
          .tags(ngConfig.getTags())
          .build();

      client.createNodegroup(request);
      log.info("Successfully created node group '{}' in cluster '{}'", ngConfig.getName(), clusterName);

    } catch (ResourceInUseException e) {
      log.info("Node group '{}' already exists in cluster '{}'. Skipping.", ngConfig.getName(), clusterName);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          "EKS error while creating node group '%s' in cluster '%s'".formatted(ngConfig.getName(), clusterName), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          "SDK client error while creating node group '%s' in cluster '%s'".formatted(ngConfig.getName(), clusterName),
          e);
    }
  }

  public void updateNodeGroup(String clusterName, EksClusterNodeGroup ngUpdate) throws CloudResourceStepException {

    try {

      UpdateNodegroupConfigRequest.Builder requestBuilder = UpdateNodegroupConfigRequest.builder()
          .clusterName(clusterName)
          .nodegroupName(ngUpdate.getName());

      if (!ngUpdate.getLabels().getLabels().isEmpty()) {
        requestBuilder.labels(UpdateLabelsPayload.builder()
            .addOrUpdateLabels(ngUpdate.getLabels().getLabels())
            .build());
      }

      requestBuilder.scalingConfig(NodegroupScalingConfig.builder()
          .desiredSize(ngUpdate.getDesiredSize())
          .minSize(ngUpdate.getMinSize())
          .maxSize(ngUpdate.getMaxSize())
          .build());

      client.updateNodegroupConfig(requestBuilder.build());
      log.info("Successfully updated node group '{}' in cluster '{}'", ngUpdate.getName(), clusterName);
      waitUntilNodeGroupsActive(clusterName, List.of(ngUpdate), Duration.ofMinutes(5));

      // Update version if specified
      if (ngUpdate.getReleaseVersion() != null) {

        UpdateNodegroupVersionRequest.Builder versionBuilder = UpdateNodegroupVersionRequest.builder()
            .clusterName(clusterName)
            .nodegroupName(ngUpdate.getName());

        if (ngUpdate.getReleaseVersion() != null) {
          versionBuilder.releaseVersion(ngUpdate.getReleaseVersion());
        }
        client.updateNodegroupVersion(versionBuilder.build());
        log.info("Successfully triggered version update for node group '{}' in cluster '{}'", ngUpdate.getName(),
            clusterName);
        waitUntilNodeGroupsActive(clusterName, List.of(ngUpdate), Duration.ofMinutes(5));
      }

    } catch (ResourceNotFoundException e) {
      log.warn("Node group '{}' not found in cluster '{}'. Cannot update.", ngUpdate.getName(), clusterName);
    } catch (EksException e) {
      throw handleEksException(clusterName,
          "EKS error while updating node group '%s' in cluster '%s'".formatted(ngUpdate.getName(), clusterName), e);
    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          "SDK client error while updating node group '%s' in cluster '%s'".formatted(ngUpdate.getName(), clusterName),
          e);
    }
  }


  public void waitUntilNodeGroupsActive(String clusterName, List<EksClusterNodeGroup> nodeGroupNames, Duration timeout)
      throws CloudResourceStepException {

    Instant start = Instant.now();
    Duration pollInterval = Duration.ofSeconds(10);

    log.info("Waiting for node groups on cluster '{}' to become ACTIVE...", clusterName);

    Set<String> pending = nodeGroupNames
        .stream()
        .map(EksClusterNodeGroup::getName)
        .collect(Collectors.toSet());

    while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
      Iterator<String> iterator = pending.iterator();

      while (iterator.hasNext()) {
        String nodeGroupName = iterator.next();

        try {
          DescribeNodegroupResponse response = client.describeNodegroup(
              DescribeNodegroupRequest.builder()
                  .clusterName(clusterName)
                  .nodegroupName(nodeGroupName)
                  .build());

          String status = response.nodegroup().statusAsString();
          log.info("Node group '{}' status: {}", nodeGroupName, status);

          if ("ACTIVE".equalsIgnoreCase(status)) {
            iterator.remove();
          }

        } catch (ResourceNotFoundException e) {
          log.warn("Node group '{}' not found in cluster '{}'", nodeGroupName, clusterName);
        } catch (Exception e) {
          log.warn("Failed to check status for node group '{}'", nodeGroupName, e);
        }
      }

      if (pending.isEmpty()) {
        log.info("All node groups in cluster '{}' are ACTIVE.", clusterName);
        return;
      }

      try {
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new CloudResourceStepException("Polling interrupted while waiting for node groups to become ACTIVE.", e);
      }
    }

    throw new CloudResourceStepException(
        "Timed out waiting for node groups to become ACTIVE in cluster '%s'. Pending: %s"
            .formatted(clusterName, pending));
  }


  public EksClusterNodeGroups getNodeGroups(String clusterName) throws CloudResourceStepException {

    try {
      ListNodegroupsResponse listResponse = client.listNodegroups(ListNodegroupsRequest.builder()
          .clusterName(clusterName)
          .build());

      List<EksClusterNodeGroup> result = new ArrayList<>();

      for (String nodeGroupName : listResponse.nodegroups()) {

        DescribeNodegroupResponse response = client.describeNodegroup(DescribeNodegroupRequest.builder()
            .clusterName(clusterName)
            .nodegroupName(nodeGroupName)
            .build());

        Nodegroup ng = response.nodegroup();

        result.add(new EksClusterNodeGroup(
            ng.nodegroupName(),
            ng.instanceTypes() != null && !ng.instanceTypes().isEmpty() ? ng.instanceTypes().getFirst() : null,
            ng.scalingConfig().desiredSize(),
            ng.scalingConfig().minSize(),
            ng.scalingConfig().maxSize(),
            ng.nodeRole(),
            new SubnetIdSet(ng.subnets()),
            EksClusterAmiType.fromValue(ng.amiTypeAsString()),
            ng.releaseVersion(),
            ng.diskSize(),
            new ProvisionedLabels(ng.labels()),
            new ProvisionedTags(ng.tags())
        ));
      }

      log.info("Retrieved {} node group(s) from cluster '{}'", result.size(), clusterName);
      return new EksClusterNodeGroups(result);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          "SDK client error while fetching node groups for EKS cluster '%s'".formatted(clusterName), e);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          "EKS error while fetching node groups for cluster '%s'".formatted(clusterName), e);
    }
  }

  public void deleteNodeGroup(String clusterName, String nodeGroupName) throws CloudResourceStepException {

    try {
      DeleteNodegroupRequest request = DeleteNodegroupRequest.builder()
          .clusterName(clusterName)
          .nodegroupName(nodeGroupName)
          .build();

      client.deleteNodegroup(request);
      log.info("Successfully initiated deletion of node group '{}' from cluster '{}'", nodeGroupName, clusterName);

    } catch (ResourceNotFoundException e) {
      log.warn("Node group '{}' not found in cluster '{}'. Skipping deletion.", nodeGroupName, clusterName);

    } catch (EksException e) {
      throw handleEksException(clusterName,
          "EKS error while deleting node group '%s' from cluster '%s'".formatted(nodeGroupName, clusterName), e);

    } catch (SdkClientException e) {
      throw CloudExceptionHandler.handleSdkClientException(clusterName,
          "SDK client error while deleting node group '%s' from cluster '%s'".formatted(nodeGroupName, clusterName), e);
    }
  }

  public void waitUntilNodeGroupsDeleted(String clusterName, EksClusterNodeGroups nodeGroups, Duration timeout)
      throws CloudResourceStepException {

    Instant start = Instant.now();
    Duration pollInterval = Duration.ofSeconds(10);

    log.info("Waiting for node groups on cluster '{}' to be deleted...", clusterName);

    Set<String> pending = nodeGroups.getNodeGroups().stream()
        .map(EksClusterNodeGroup::getName)
        .collect(Collectors.toSet());

    while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
      Iterator<String> iterator = pending.iterator();

      while (iterator.hasNext()) {
        String nodeGroupName = iterator.next();

        try {
          client.describeNodegroup(
              DescribeNodegroupRequest.builder()
                  .clusterName(clusterName)
                  .nodegroupName(nodeGroupName)
                  .build());

          // If no exception, the node group still exists
          log.info("Node group '{}' still exists in cluster '{}'", nodeGroupName, clusterName);

        } catch (ResourceNotFoundException e) {
          log.info("Node group '{}' successfully deleted from cluster '{}'", nodeGroupName, clusterName);
          iterator.remove();

        } catch (Exception e) {
          log.warn("Failed to check deletion status for node group '{}'", nodeGroupName, e);
        }
      }

      if (pending.isEmpty()) {
        log.info("All node groups successfully deleted from cluster '{}'", clusterName);
        return;
      }

      try {
        Thread.sleep(pollInterval.toMillis());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new CloudResourceStepException("Polling interrupted while waiting for node groups to be deleted.", e);
      }
    }

    throw new CloudResourceStepException(
        "Timed out waiting for node groups to be deleted in cluster '%s'. Still present: %s"
            .formatted(clusterName, pending));
  }

  private CloudResourceStepException handleEksException(String cluster, String message, EksException e) {

    log.debug(
        Slf4jLoggingUtil.DEBUG_PREFIX + "EKS service error for cluster '{}'. AWS error code: '{}', message: '{}'.",
        cluster,
        e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage(), e);

    CloudResourceStepException exception = new CloudResourceStepException(message, e);
    exception.getMessageDetails().add(e.getMessage());

    return exception;
  }

}
