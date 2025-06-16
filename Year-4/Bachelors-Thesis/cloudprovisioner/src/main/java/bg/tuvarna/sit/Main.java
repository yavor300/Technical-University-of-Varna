package bg.tuvarna.sit;

import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleDispatcher;
import bg.tuvarna.sit.cloud.credentials.AwsBasicCredentialsProvider;
import bg.tuvarna.sit.cloud.credentials.BaseCredentialsProvider;
import bg.tuvarna.sit.cloud.config.InitializerInjectionModule;
import bg.tuvarna.sit.cloud.exception.InitializationException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

// TODO [Idea] Check logger source class names to defer base/implementation in logs
@Slf4j
public class Main {

  public static void main(String[] args) {

    // TODO [Idea] META-INF folder
    // TODO [Enhancement] Currently we are AWS-specific for the cloud provider
    if (args.length < 1) {
      String message = """
          Missing required arguments. Usage:
           - java -jar cloudprovisioner.jar [services...]
           - ./gradlew provisionCloudResources --args='[services...]'
          Example: s3 cloudfront""";
      log.error(message);
      throw new InitializationException(message);
    }

    Injector initializer = Guice.createInjector(new InitializerInjectionModule());

    // TODO [Enhancement] Determine what provider to use e.g not only basic credentials
    BaseCredentialsProvider<AwsBasicCredentials> credentialsProvider =
        initializer.getInstance(AwsBasicCredentialsProvider.class);

    CloudBundleDispatcher dispatcher = initializer.getInstance(CloudBundleDispatcher.class);

    // TODO [Implementation] Support multiple service separated by ',' in the args
    String service = args[0];
    AwsBasicCredentials credentials = credentialsProvider.getCredentials().credentials();
    dispatcher.dispatch(service, credentials);
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
