package bg.tuvarna.sit.cloud.core;

import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleRunner;
import bg.tuvarna.sit.cloud.utils.NamedInjections;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.util.Set;

@Singleton
public class CloudBundleDispatcher {

  private final Set<CloudBundleRunner<?>> bundles;

  @Inject
  public CloudBundleDispatcher(@Named(NamedInjections.BUNDLES) Set<CloudBundleRunner<?>> bundles) {
    this.bundles = bundles;
  }

  // TODO [Enhancement] Pass credentials from the constructor as they are the same?
  public void dispatch(String resourceType, AwsBasicCredentials credentials) {

    bundles.stream()
        .filter(p -> p.getType().toString().equalsIgnoreCase(resourceType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No provisioner found for type: " + resourceType))
        .run(credentials);
  }

}
