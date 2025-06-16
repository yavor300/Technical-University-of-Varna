package bg.tuvarna.sit.cloud.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.JavaExec;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudProvisionerPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {

    String cloudprovisionerVersion = readPluginVersion();

    project.getDependencies().add("implementation",
        "bg.tuvarna.sit:cloudprovisioner:" + cloudprovisionerVersion);

    project.getTasks().register("provisionCloudResources", JavaExec.class, task -> {
      task.setGroup("cloud");
      task.setDescription("Run the cloudprovisioner to provision cloud infrastructure resources");
      task.getMainClass().set("bg.tuvarna.sit.Main");
      // Use only runtimeClasspath, which will point to the plugin’s bundled classes if needed
      task.setClasspath(project.getConfigurations().getByName("runtimeClasspath"));
      task.setWorkingDir(project.getProjectDir());
    });
  }

  private String readPluginVersion() {

    try (InputStream in = getClass().getClassLoader().getResourceAsStream("plugin-version.properties")) {
      Properties props = new Properties();
      props.load(in);
      return props.getProperty("version");
    } catch (IOException e) {
      throw new RuntimeException("Could not read plugin version", e);
    }
  }

}
