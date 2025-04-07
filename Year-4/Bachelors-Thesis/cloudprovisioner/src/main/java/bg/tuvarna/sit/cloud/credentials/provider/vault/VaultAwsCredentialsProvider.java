package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.credentials.aws.AwsCredentials;
import bg.tuvarna.sit.cloud.credentials.provider.CloudCredentialsProvider;
import bg.tuvarna.sit.cloud.credentials.model.VaultAwsCredentialsData;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;

import java.io.IOException;

@Slf4j
public class VaultAwsCredentialsProvider implements CloudCredentialsProvider<AwsCredentials> {

  private final VaultClient vaultClient;
  private final ObjectMapper mapper;

  public VaultAwsCredentialsProvider(VaultClient vaultClient, ObjectMapper mapper) {
    this.vaultClient = vaultClient;
    this.mapper = mapper;
  }

  @Override
  public AwsCredentials fetchCredentials() throws IOException {

    VaultResponse vaultResponse = vaultClient.getVaultSecrets();

    try {

      VaultResponse masked = (VaultResponse) vaultResponse.clone();
      VaultAwsCredentialsData credentialsData = masked.getData().getData();
      credentialsData.setAccessKeyId("****");
      credentialsData.setSecretAccessKey("****");

      String maskedJson = mapper.writeValueAsString(masked);
      log.info("AWS credentials successfully fetched from Vault",
          StructuredArguments.keyValue("details", maskedJson));

    } catch (CloneNotSupportedException e) {
      log.warn("Unable to serialize masked Vault response for logging. Masking skipped.",
          StructuredArguments.keyValue("details", e.getMessage()));
    }

    VaultAwsCredentialsData data = vaultResponse.getData().getData();
    return new AwsCredentials(data.getAccessKeyId(), data.getSecretAccessKey());
  }
}
