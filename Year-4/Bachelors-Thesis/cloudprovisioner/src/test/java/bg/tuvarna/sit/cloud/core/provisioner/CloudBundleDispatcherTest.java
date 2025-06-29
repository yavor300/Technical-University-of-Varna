package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CloudBundleDispatcherTest {

  @Mock
  private CloudBundleRunner<?> s3Runner;

  @Mock
  private CloudBundleRunner<?> eksRunner;

  private CloudBundleDispatcher dispatcher;

  private final AwsBasicCredentials credentials =
      AwsBasicCredentials.create("accessKey", "secretKey");

  @BeforeEach
  void setup() {

    dispatcher = new CloudBundleDispatcher(Set.of(s3Runner, eksRunner));
  }

  @Test
  void dispatch_shouldCallMatchingRunner() {

    when(eksRunner.getType()).thenReturn(CloudResourceType.EKS);
    when(s3Runner.getType()).thenReturn(CloudResourceType.S3);

    dispatcher.dispatch("S3", credentials);

    verify(s3Runner).run(credentials);
    verify(eksRunner, never()).run(any());
  }

  @Test
  void dispatch_shouldBeCaseInsensitive() {

    when(s3Runner.getType()).thenReturn(CloudResourceType.S3);
    when(eksRunner.getType()).thenReturn(CloudResourceType.EKS);
    dispatcher.dispatch("eKs", credentials);

    verify(eksRunner).run(credentials);
    verify(s3Runner, never()).run(any());
  }

  @Test
  void dispatch_shouldThrowException_whenNoRunnerFound() {

    dispatcher = new CloudBundleDispatcher(Collections.emptySet());

    String unknownType = "Lambda";

    assertThatThrownBy(() -> dispatcher.dispatch(unknownType, credentials))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No provisioner found for type: " + unknownType);

    verifyNoInteractions(s3Runner, eksRunner);
  }
}
