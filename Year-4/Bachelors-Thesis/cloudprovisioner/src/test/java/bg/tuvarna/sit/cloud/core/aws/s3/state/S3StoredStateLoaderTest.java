package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3ProvisionedAcl;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.utils.TempFileUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static org.gradle.internal.impldep.org.junit.Assert.assertNull;
import static org.gradle.internal.impldep.org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class S3StoredStateLoaderTest {

  private ObjectMapper standardMapper;
  private ObjectMapper jsonMapper;
  private S3StoredStateLoader loader;

  @BeforeEach
  void setUp() {
    standardMapper = new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    jsonMapper = new ObjectMapper();
    loader = new S3StoredStateLoader(standardMapper, jsonMapper);
  }

  @Test
  public void testLoad_TransformsTags() throws Exception {
    String json = """
          [{
            "stepName": "bg.tuvarna.sit.cloud.core.aws.s3.step.S3TaggingStep",
            "outputs" : {
              "value" : {
                "environment" : "dev",
                "team" : "storage"
              }
            }
          }]
        """;

    File file = TempFileUtil.writeJsonToTempFile(json);
    List<StepResult<S3Output>> results = loader.load(file, S3Output.class);

    assertEquals(1, results.size());
    Object tags = results.getFirst().getOutputs().get(S3Output.VALUE_NODE);
    assertTrue(tags instanceof ProvisionedTags);
    assertEquals("dev", ((ProvisionedTags) tags).getTags().get("environment"));
  }

  @Test
  public void testLoad_TransformsAcl() throws Exception {

    String json = """
          [{
             "stepName" : "bg.tuvarna.sit.cloud.core.aws.s3.step.S3AclStep",
             "outputs" : {
               "value" : {
                 "owner" : {
                   "id" : "75aa57f09aa0c8caeab4f8c24e99d10f8e7faeebf76c078efc7c6caea54ba06a",
                   "displayName" : "webfile"
                 },
                 "grants" : [ {
                   "grantee" : {
                     "type" : "CanonicalUser",
                     "identifier" : "75aa57f09aa0c8caeab4f8c24e99d10f8e7faeebf76c078efc7c6caea54ba06a",
                     "uri" : null,
                     "emailAddress" : null
                   },
                   "permission" : "FULL_CONTROL"
                 } ],
                 "cannedAcl" : null
               }
             }
           }]
        """;

    File file = TempFileUtil.writeJsonToTempFile(json);
    List<StepResult<S3Output>> results = loader.load(file, S3Output.class);

    assertEquals(1, results.size());
    Object acl = results.getFirst().getOutputs().get(S3Output.VALUE_NODE);
    assertTrue(acl instanceof S3ProvisionedAcl);
    assertNull(((S3ProvisionedAcl) acl).getCannedAcl());
  }

}
