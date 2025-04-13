package bg.tuvarna.sit;

import bg.tuvarna.sit.cloud.core.aws.s3.S3AclStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3EncryptionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3PolicyStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisioningContext;
import bg.tuvarna.sit.cloud.core.aws.s3.S3StepExecutor;
import bg.tuvarna.sit.cloud.core.aws.s3.S3TaggingStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3VersioningStep;
import bg.tuvarna.sit.cloud.config.AuthenticationConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisioningResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketProvisioner;
import bg.tuvarna.sit.cloud.common.ErrorResponse;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultClient;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultAwsCredentialsProvider;
import bg.tuvarna.sit.cloud.common.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
public class Main {

  private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static void main(String[] args) {

    if (args.length < 2) {
      log.error("Invalid arguments. Usage: java -jar cloudprovisioner.jar <auth-config-path.yml> <s3-config-path.yml>");
      System.exit(1);
    }

    String authConfigPath = args[0];
    String s3ConfigPath = args[1];

    log.info("Loading authentication config from: {}", authConfigPath);
    AuthenticationConfig authConfig;
    try {
      authConfig = loadConfig(authConfigPath, AuthenticationConfig.class);
      log.debug("Authentication config loaded");
    } catch (IOException e) {
      logError(ErrorCode.AUTH_CONFIG_LOAD_ERROR, e);
      return;
    }

    log.info("Loading S3 config from: {}", s3ConfigPath);
    S3BucketConfig s3Config;
    try {
      s3Config = loadConfig(s3ConfigPath, S3BucketConfig.class);
      log.debug("S3 config loaded: bucketName={}", s3Config.getName());
    } catch (IOException e) {
      logError(ErrorCode.S3_CONFIG_LOAD_ERROR, e);
      return;
    }

    log.info("Authenticating using Vault credentials...");
    AwsBasicCredentials credentials = authenticate(authConfig);
    if (credentials == null) {
      // TODO Think for a fallback mechanism
      System.exit(1);
    }

    log.info("Starting provisioning process...");
    CloudProvisioningResponse cloudProvisioningResponse = provisionS3(s3Config, credentials);
    if (cloudProvisioningResponse == null) {
      System.exit(1);
    }

    try {
      String json = MAPPER.writeValueAsString(cloudProvisioningResponse);
      log.info("Provisioning completed", StructuredArguments.keyValue("provisioning", json));
    } catch (JsonProcessingException e) {
      logError(ErrorCode.SERIALIZATION_ERROR, e);
    }
  }

  private static <T> T loadConfig(String path, Class<T> clazz) throws IOException {
    File file = new File(path);
    if (!file.exists()) {
      throw new IOException("Config file not found: " + path);
    }
    return YAML_MAPPER.readValue(file, clazz);
  }

  private static AwsBasicCredentials authenticate(AuthenticationConfig config) {
    VaultAwsCredentialsProvider vaultAwsCredentialsProvider = new VaultAwsCredentialsProvider(new VaultClient(config.getVault(), MAPPER), MAPPER);
    try {
      return vaultAwsCredentialsProvider.fetchCredentials().toAwsBasicCredentials();
    } catch (IOException e) {
      logError(ErrorCode.VAULT_AUTH_ERROR, e);
      return null;
    }
  }

  private static CloudProvisioningResponse provisionS3(S3BucketConfig config, AwsBasicCredentials awsBasicCredentials) {

    CloudResourceProvisioner<S3BucketConfig> provisioner =
        new S3BucketProvisioner(new S3ProvisioningContext(awsBasicCredentials, URI.create("https://s3.localhost.localstack.cloud:4566"),
            Region.of(config.getRegion())), new S3StepExecutor(List.of(new S3PolicyStep(), new S3VersioningStep(),
            new S3TaggingStep(), new S3EncryptionStep(), new S3AclStep())));
    try {
      CloudProvisioningResponse provisioned = provisioner.provision(config);
      log.info("S3 provisioning completed successfully");
      return provisioned;
    } catch (Exception e) {
      logError(ErrorCode.S3_PROVISION_ERROR, e);
      return null;
    }
  }

  private static void logError(ErrorCode code, Exception e) {

    log.error("{} - {}", code.getCode(), code.getMessage(), e);
    ErrorResponse error = new ErrorResponse(code);
    log.error("Error Response: \n{}", error.toJson());
  }
}
