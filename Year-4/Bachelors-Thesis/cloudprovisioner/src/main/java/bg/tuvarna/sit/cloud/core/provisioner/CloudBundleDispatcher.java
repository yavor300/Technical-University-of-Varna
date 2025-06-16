package bg.tuvarna.sit.cloud.core.provisioner;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.util.Set;

@SuppressWarnings("rawtypes")
public class CloudBundleDispatcher {

  private final Set<CloudBundleRunner> bundles;

  @Inject
  public CloudBundleDispatcher(@Named("bundles") Set<CloudBundleRunner> bundles) {
    this.bundles = bundles;
  }

  public void dispatch(String resourceType, AwsBasicCredentials credentials) {

    bundles.stream()
        .filter(p -> p.getType().toString().equalsIgnoreCase(resourceType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No provisioner found for type: " + resourceType))
        .run(credentials);
  }
}
