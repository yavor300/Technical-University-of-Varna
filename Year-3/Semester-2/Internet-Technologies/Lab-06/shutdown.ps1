# Define Docker Compose file path
$dockerComposeFilePath = "compose.yaml"

# Ensure the Docker Compose file exists
if (-Not (Test-Path $dockerComposeFilePath)) {
    Write-Error "Docker Compose file not found at path: $dockerComposeFilePath"
    exit 1
}

# Run docker compose down to stop and remove containers, networks, etc.
Write-Host "Stopping Docker Compose services..."
docker-compose -f $dockerComposeFilePath down
if ($LASTEXITCODE -ne 0) {
    Write-Error "Failed to stop Docker Compose services."
    exit $LASTEXITCODE
} else {
    Write-Host "Docker Compose services stopped successfully."
}
