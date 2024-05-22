param (
    [Parameter(Mandatory=$true)]
    [string]$imageName,
    [Parameter(Mandatory=$true)]
    [string]$tag
)

# Define Docker Compose file path
$dockerComposeFilePath = "compose.yaml"

# Step 1: Run gradlew clean build
Write-Host "Running './gradlew clean build'..."
Invoke-Expression -Command "./gradlew clean build"
if ($LASTEXITCODE -ne 0) {
    Write-Error "Gradle build failed."
    exit $LASTEXITCODE
}

# Step 2: Build the Docker image
$imageWithTag = "$imageName`:$tag"
Write-Host "Building Docker image $imageWithTag..."
docker build -t $imageWithTag .
if ($LASTEXITCODE -ne 0) {
    Write-Error "Docker build failed."
    exit $LASTEXITCODE
}

# Step 3: Update the Docker Compose file
Write-Host "Updating Docker Compose file..."
try {
    # Load the Docker Compose YAML file
    $composeContent = Get-Content $dockerComposeFilePath -Raw | ConvertFrom-Yaml

    # Update the app image
    $composeContent.services.app.image = $imageWithTag

    # Save the updated Docker Compose YAML file
    $composeContent | ConvertTo-Yaml | Set-Content $dockerComposeFilePath
} catch {
    Write-Error "Failed to update Docker Compose file: $_"
    exit 1
}

# Step 4: Run docker compose up
Write-Host "Starting Docker Compose..."
docker-compose up -d
if ($LASTEXITCODE -ne 0) {
    Write-Error "Docker Compose failed to start."
    exit $LASTEXITCODE
}

Write-Host "Script completed successfully."
