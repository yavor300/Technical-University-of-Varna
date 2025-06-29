package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.credentials.AuthenticationConfig;
import bg.tuvarna.sit.cloud.credentials.model.VaultAwsCredentialsData;
import bg.tuvarna.sit.cloud.credentials.model.VaultDataWrapper;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VaultAwsCredentialsProviderTest {

  @Mock
  private VaultClient vaultClient;

  @Mock
  private ObjectMapper yamlMapper;

  @Mock
  private AuthenticationConfig.VaultConfig config;

  @InjectMocks
  private VaultAwsCredentialsProvider provider;

  @Test
  void fetchCredentials_shouldReturnValidCredentials() throws Exception {

    VaultAwsCredentialsData data = new VaultAwsCredentialsData();
    data.setAccessKeyId("AKIA_TEST");
    data.setSecretAccessKey("SECRET_TEST");

    VaultDataWrapper innerData = new VaultResponse().getData();
    innerData.setData(data);

    VaultResponse response = new VaultResponse();
    response.setData(innerData);

    when(vaultClient.getSecrets(config)).thenReturn(response);
    when(yamlMapper.writeValueAsString(any())).thenReturn("yaml-representation");

    AwsBasicCredentials credentials = provider.config(config).fetchCredentials();

    assertThat(credentials.accessKeyId()).isEqualTo("AKIA_TEST");
    assertThat(credentials.secretAccessKey()).isEqualTo("SECRET_TEST");

    verify(vaultClient).getSecrets(config);
    verify(yamlMapper).writeValueAsString(response);
  }

}