package bg.tuvarna.sit;

import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleDispatcher;
import bg.tuvarna.sit.cloud.credentials.AwsBasicCredentialsAuthenticationManager;
import bg.tuvarna.sit.cloud.credentials.BaseAuthenticationManager;
import bg.tuvarna.sit.cloud.config.InitializerInjectionModule;
import bg.tuvarna.sit.cloud.exception.InitializationException;
import bg.tuvarna.sit.cloud.utils.EnvVar;

import com.google.inject.Guice;
import com.google.inject.Injector;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;
import software.amazon.awssdk.services.sts.model.StsException;

import java.net.URI;
import java.util.Arrays;

@Slf4j
public class Main {

  public static void main(String[] services) {

    System.out.println("""
        
        _________ .__                   ._____________                    .__       .__                           \s
        \\_   ___ \\|  |   ____  __ __  __| _/\\______   \\_______  _______  _|__| _____|__| ____   ____   ___________\s
        /    \\  \\/|  |  /  _ \\|  |  \\/ __ |  |     ___/\\_  __ \\/  _ \\  \\/ /  |/  ___/  |/  _ \\ /    \\_/ __ \\_  __ \\
        \\     \\___|  |_(  <_> )  |  / /_/ |  |    |     |  | \\(  <_> )   /|  |\\___ \\|  (  <_> )   |  \\  ___/|  | \\/
         \\______  /____/\\____/|____/\\____ |  |____|     |__|   \\____/ \\_/ |__/____  >__|\\____/|___|  /\\___  >__|  \s
                \\/                       \\/                                       \\/               \\/     \\/      \s
        
        :: Cloud Provisioner ::                                                               (1.0.0)             \s
        """);

    // TODO [Idea] META-INF folder
    // TODO [Enhancement] Currently we are AWS-specific for the cloud provider
    if (services.length < 1) {
      String message = """
          Missing required arguments. Usage:
           - java -jar cloudprovisioner.jar [services...]
           - ./gradlew provisionCloudResources --args='[services...]'
          Example: s3 eks""";
      log.error(message);
      throw new InitializationException(message);
    }

    Injector initializer = Guice.createInjector(new InitializerInjectionModule());

    // TODO [Enhancement] Add new manager for AwsSessionCredentials
    BaseAuthenticationManager<AwsBasicCredentials> authenticationManager =
        initializer.getInstance(AwsBasicCredentialsAuthenticationManager.class);

    AwsBasicCredentials credentials = authenticationManager.getCredentials();

    try (StsClient stsClient = StsClient.builder()
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .endpointOverride(
            EnvVar.ENDPOINT_URL.getValueOrDefault(null) != null ? URI.create(EnvVar.ENDPOINT_URL.getValue()) : null)
        .region(Region.AWS_GLOBAL)
        .build()) {
      GetCallerIdentityResponse identity = stsClient.getCallerIdentity();
      log.info("Authenticated AWS identity: Account = {}, ARN = {}, UserId = {}",
          identity.account(), identity.arn(), identity.userId());
    } catch (StsException e) {
      log.error("Failed to verify AWS credentials using STS: {}", e.getMessage(), e);
      throw new InitializationException("Unable to validate credentials with STS");
    }

    CloudBundleDispatcher dispatcher = initializer.getInstance(CloudBundleDispatcher.class);

    Arrays.stream(services).forEach(service -> dispatcher.dispatch(service, credentials));
  }

  // TODO [Maybe] Not needed for now. Maybe use with 'export' job ?
  //  private static void cleanupStateFiles(String bucket) {
  //    String[] prefixes = {"current-", "desired-"};
  //    for (String prefix : prefixes) {
  //      File file = new File(".cloudprovisioner/" + prefix + bucket + ".json");
  //      deleteFileIfExists(file, "Deleted state file: {}", "Failed to execute state file: {}");
  //    }
  //  }
}
