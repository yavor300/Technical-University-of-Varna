package bg.tuvarna.sit.cloud.credentials.provider;

import bg.tuvarna.sit.cloud.credentials.model.VaultAwsCredentialsData;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.io.IOException;

@Slf4j
public class VaultCredentialsProvider implements CloudCredentialsProvider {

  private final VaultClient vaultClient;
  private final ObjectMapper mapper;

  public VaultCredentialsProvider(VaultClient vaultClient, ObjectMapper mapper) {
    this.vaultClient = vaultClient;
    this.mapper = mapper;
  }

  @Override
  public AwsBasicCredentials fetchAwsCredentials() throws IOException {

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
    return AwsBasicCredentials.create(data.getAccessKeyId(), data.getSecretAccessKey());
  }
}
