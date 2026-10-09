# Cloud Provisioner

Cloud Provisioner is a Java and Gradle based infrastructure provisioning project developed for the bachelor's thesis. It provisions AWS resources from YAML configuration files, compares the desired state with the live and previously stored state, applies only the required changes, and persists the result locally for future reconciliation.

The repository contains the provisioning library/plugin itself and a small consumer project that demonstrates how the plugin is used from another Gradle build.

## Project Structure

```text
.
|-- cloudprovisioner/       # Java library, CLI entry point, and Gradle plugin
|-- cloudconsumer/          # Example project using the Gradle plugin
|-- vault/                  # Local Vault notes/files used during development
|-- *.drawio, *.png         # Architecture and UML diagrams
`-- Documentation.pdf       # Thesis documentation export
```

## Current Capabilities

- Gradle plugin: `bg.tuvarna.sit.cloudprovisioner`
- CLI entry point: `bg.tuvarna.sit.Main`
- Supported resource bundles:
  - `s3` for Amazon S3 buckets
  - `eks` for Amazon EKS clusters
- YAML based resource configuration
- AWS SDK for Java v2 integration
- Basic AWS credentials loaded from Vault or environment variables
- Optional endpoint overrides for LocalStack/local development
- State persistence under `.cloudprovisioner/<profile>/<service>/`
- Drift detection by comparing stored state, live cloud state, and desired configuration
- Reconciliation, destroy, and rollback/revert flow
- Step based provisioning with ordered, isolated resource operations
- Parallel provisioning using each config list's fixed thread pool settings
- Unit tests for configuration, credentials, provisioning steps, state loading, and state comparison

## Modules

### `cloudprovisioner`

The main implementation. It builds a shaded artifact and publishes both the library and Gradle plugin.

Important packages:

- `bg.tuvarna.sit.cloud.gradle` - Gradle plugin registration
- `bg.tuvarna.sit.cloud.core.provisioner` - common provisioning abstractions
- `bg.tuvarna.sit.cloud.core.aws.s3` - S3 bundle, steps, state, and clients
- `bg.tuvarna.sit.cloud.core.aws.eks` - EKS bundle, steps, state, and clients
- `bg.tuvarna.sit.cloud.credentials` - authentication managers and providers
- `bg.tuvarna.sit.cloud.utils` - configuration, logging, environment, and state writing utilities

### `cloudconsumer`

An example Gradle project that applies the plugin:

```gradle
plugins {
    id 'java'
    id 'bg.tuvarna.sit.cloudprovisioner' version '1.0.0.B'
}
```

The plugin adds the `provisionCloudResources` task and wires the `cloudprovisioner` dependency into the consumer's runtime classpath.

## Configuration Layout

The provisioner selects configuration files based on `AWS_PROFILE`.

```text
src/main/resources/cloud/<AWS_PROFILE>/
|-- authentication.yml
|-- s3.yml
`-- eks.yml
```

For example, if `AWS_PROFILE=localstack`, S3 configuration is loaded from:

```text
src/main/resources/cloud/localstack/s3.yml
```

The current sample consumer includes:

```text
cloudconsumer/src/main/resources/cloud/localstack/authentication.yml
cloudconsumer/src/main/resources/cloud/localstack/s3.yml
```

## Authentication

`authentication.yml` supports a list of credential providers. The current sample uses Vault first and static environment credentials second:

```yaml
providers:
  - vault:
      scheme: http
      host: localhost
      port: 8200
      secretPath: /v1/application/data/cloudprovisioner/000000000000
      token: ${VAULT_TOKEN}
  - staticCredentials:
      accessKeyId: ${AWS_ACCESS_KEY_ID}
      secretAccessKey: ${AWS_SECRET_ACCESS_KEY}
```

Useful environment variables:

| Variable | Purpose |
| --- | --- |
| `AWS_PROFILE` | Selects the configuration directory and state namespace |
| `AWS_ACCESS_KEY_ID` | Static AWS access key fallback |
| `AWS_SECRET_ACCESS_KEY` | Static AWS secret key fallback |
| `VAULT_TOKEN` | Token for the Vault credential provider |
| `ENDPOINT_URL` | Global endpoint override, used by STS/EKS flows |
| `S3_ENDPOINT_URL` | S3-specific endpoint override |
| `LOG_FORMAT` | Enables structured JSON logging when configured accordingly |

## S3 Configuration

S3 resources are defined in `s3.yml` under `buckets`.

```yaml
buckets:
  - id: 94f5b153-c2cd-4b11-831f-54ee554e7c7e
    name: bucket-two
    region: us-east-1
    preventDestroy: true
    tags:
      environment: dev
      team: storage
    versioning: Enabled
    encryption:
      type: aws:kms
      kmsKeyId: arn:aws:kms:us-east-1:123456789013:key/your-key-id
    ownershipControls: BucketOwnerEnforced
    policy: |
      {
        "Version": "2012-10-17",
        "Statement": []
      }
```

Implemented S3 steps include bucket creation, tagging, versioning, encryption, ownership controls, ACLs, policies, and persistent metadata.

## EKS Configuration

EKS resources are loaded from `eks.yml` under `clusters`.

Supported cluster fields include:

- `id`
- `name`
- `region`
- `version`
- `roleArn`
- `subnets`
- `authenticationMode`
- `supportType`
- `enableZonalShift`
- `ownedEncryptionKMSKeyArn`
- `addons`
- `nodeGroups`
- common fields such as `preventDestroy`, `toDelete`, `enableReconciliation`, `tags`, and `retry`

Implemented EKS steps include cluster creation, authentication mode, addons, node groups, tagging, and persistent metadata.

## State Management

After provisioning, state is written to:

```text
.cloudprovisioner/<AWS_PROFILE>/<service>/state-<resource-name>#<resource-id>.json
```

On each run, the provisioner:

1. Loads the previously persisted state.
2. Reads the live state from AWS.
3. Detects drift between persisted and live state.
4. Calculates the desired state from YAML.
5. Applies only the required provisioning or destroy steps.
6. Persists the merged state back to disk.

If a resource id disappears from the YAML configuration, the runner marks the stored resource for deletion. `preventDestroy` protects resources from accidental destruction unless explicitly disabled.

## Running Locally

Publish the plugin/library to Maven Local:

```bash
cd cloudprovisioner
./gradlew publishToMavenLocal
```

Run the sample consumer against the `localstack` profile:

```bash
cd ../cloudconsumer
AWS_PROFILE=localstack \
AWS_ACCESS_KEY_ID=test \
AWS_SECRET_ACCESS_KEY=test \
S3_ENDPOINT_URL=http://localhost:4566 \
ENDPOINT_URL=http://localhost:4566 \
./gradlew provisionCloudResources --args='s3'
```

Provision multiple supported bundles:

```bash
./gradlew provisionCloudResources --args='s3 eks'
```

On Windows PowerShell, set environment variables before running Gradle:

```powershell
$env:AWS_PROFILE = "localstack"
$env:AWS_ACCESS_KEY_ID = "test"
$env:AWS_SECRET_ACCESS_KEY = "test"
$env:S3_ENDPOINT_URL = "http://localhost:4566"
$env:ENDPOINT_URL = "http://localhost:4566"
./gradlew provisionCloudResources --args="s3"
```

## Build and Test

From `cloudprovisioner`:

```bash
./gradlew clean build
./gradlew test
```

From `cloudconsumer`:

```bash
./gradlew build
```

## Notes and Limitations

- The implementation is currently AWS-specific.
- The active bundles are S3 and EKS. Other AWS services from the original proposal are not implemented in the current codebase.
- EKS support targets real AWS-style APIs; LocalStack support is mainly useful for S3/local endpoint testing.
- Configuration validation is still an area for improvement.
- The sample `cloudconsumer` project currently contains an S3 localstack configuration but no checked-in `eks.yml`.
