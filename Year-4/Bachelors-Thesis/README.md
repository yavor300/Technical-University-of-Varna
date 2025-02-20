# AWS Provisioning Library

## Overview
The **AWS Provisioning Library** is a Java-based tool that enables developers to provision AWS resources **declaratively** while utilizing the **AWS SDK** for imperative execution. Inspired by Infrastructure as Code (IaC) tools like Terraform, this library provides a seamless way for microservices to request AWS resources by specifying their requirements in a structured format. The library ensures **resource state management** to prevent duplicate creations and maintain consistency across environments.

## Key Features
### 1. Declarative Resource Provisioning
- Developers can declare the required AWS resources using a structured format (e.g., **YAML** or **JSON**), and the library will handle the imperative provisioning.
- Example YAML declaration:
  
  ```yaml
  s3:
    name: my-app-bucket
  rds:
    identifier: my-database
    engine: postgres
    instance_type: db.t3.micro
  ```

### 2. AWS SDK-Based Resource Creation
- Uses **AWS SDK for Java** to create, update, and delete AWS resources.
- Supports a variety of AWS services like **S3, RDS, DynamoDB, SNS, SQS, IAM**, etc.

### 3. State Management
- Keeps track of provisioned resources to prevent duplication and enable idempotency.
- Stores the state in a **local file, database, or AWS Parameter Store/DynamoDB**.
- Provides an API to retrieve the current state of provisioned resources.

### 4. Validation and Conflict Detection
- Before provisioning a resource, the library checks if it **already exists**.
- Ensures that resource changes are applied without unnecessary re-creation.

### 5. Dependency Management
- Handles dependencies between resources, ensuring they are created in the **correct order**.
- Example: An **RDS instance** may require a **security group** to be created first.

### 6. Support for Multiple Environments
- Allows provisioning resources in different environments (**dev, staging, production**) based on configuration.
- Environment-specific overrides are supported.

### 7. Integration with Spring Projects
- The tool will seamlessly integrate with **Spring projects** running with a **web server**.
- Exposes REST APIs to provision resources dynamically within Spring applications.

### 8. LocalStack Integration for Local Development
- Integrates with **LocalStack** to enable local cloud development and testing.
- Developers can simulate AWS cloud services without needing actual AWS infrastructure.

### 9. Shared Cloud Resources Management
- Common cloud resources such as **VPCs** will be managed separately in a dedicated project.
- This library will support referencing and utilizing existing resources created by that separate project.

### 10. Configuration and Extensibility
- Supports configuration via **YAML, JSON, or Java API**.
- Easily extendable to add support for **new AWS services**.

## Implementation Approach
1. **Parsing & Validation**
   - Read the declarative resource configuration (**YAML/JSON**).
   - Validate the provided configuration against supported AWS services.
   
2. **State Management**
   - Check the **existing state** to determine whether a resource needs to be **created, updated, or skipped**.
   - Maintain a **record of created resources** in a structured format.

3. **Resource Provisioning**
   - Use **AWS SDK** to provision the resources imperatively.
   - Handle **exceptions, retries, and error reporting**.

4. **Logging & Monitoring**
   - Provide **logs and monitoring hooks** to track provisioning status.
   - Integrate with **AWS CloudWatch** for observability.

## Example Usage
### Java API Example
```java
AwsProvisioner provisioner = new AwsProvisioner();
provisioner.createS3Bucket("my-app-bucket");
provisioner.createRdsInstance("my-database", "postgres", "db.t3.micro");
```

### Declarative Configuration Example (YAML)
```yaml
aws_resources:
  s3:
    - name: my-app-bucket
  rds:
    - identifier: my-database
      engine: postgres
      instance_type: db.t3.micro
```
### Annotation-Based Configuration
- For example, developers can annotate **POJO (DTO) classes** to declare that a document should be stored in a specific **S3 bucket**.
- Example annotation usage:
  
  ```java
  @S3Storage(bucket = "my-app-bucket", key = "documents/${id}.json")
  public class Document {
      private String id;
      private String content;
      
      // Getters and setters
  }
  ```

## Next Steps
- Define a **structured format** for declarative configuration.
- Implement **core provisioning logic** for initial AWS services.
- Develop **state management mechanism**.
- Provide a **CLI or REST API** for interacting with the library.
- Implement **LocalStack** integration for local development and testing.
- Develop functionality to use **shared cloud resources** from an external project.

## Potential Enhancements
- Implement **rollback functionality** in case of provisioning failures.
---
This document serves as an initial proposal for the **AWS Provisioning Library**.
