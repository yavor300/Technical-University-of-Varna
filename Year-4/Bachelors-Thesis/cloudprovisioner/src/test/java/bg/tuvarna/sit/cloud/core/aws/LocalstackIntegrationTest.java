package bg.tuvarna.sit.cloud.core.aws;

import org.junit.jupiter.api.*;

import java.io.*;

public class LocalstackIntegrationTest {

  private static Process localstackProcess;

  @BeforeAll
  public static void startLocalstack() throws IOException, InterruptedException {

    ProcessBuilder pb = new ProcessBuilder("localstack", "start", "-d");
    pb.inheritIO();
    localstackProcess = pb.start();

    Thread.sleep(5000);
  }

  @AfterAll
  public static void stopLocalstack() throws IOException, InterruptedException {
    // Stop LocalStack
    new ProcessBuilder("localstack", "stop").start().waitFor();
  }

  @Test
  public void testProvisioning() {
    // Call your main method
    bg.tuvarna.sit.Main.main(new String[]{"s3"});
  }
}
