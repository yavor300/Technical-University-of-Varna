param (
  [string]$version = ""
)

$artifacts = "artifacts/"
$pattern = "program-\d+\.\d+\.\d+\.jar"

if ([string]::IsNullOrEmpty($version)) {
  $files = Get-ChildItem -Path $artifacts -Filter "*.jar" | Where-Object { $_.Name -match $pattern } | Sort-Object -Property { [version]($_.Name -replace "program-|\.jar") }
  $latestFile = $files | Select-Object -Last 1
}
else {
  $files = Get-ChildItem -Path $artifacts -Filter "*.jar" | Where-Object { $_.Name -eq "program-$version.jar" }
  $latestFile = $files | Select-Object -First 1
}

if (-not "$artifacts/$latestFile") {
  Write-Host "No matching jar file found."
}
else {
  Write-Host "Starting jar file with name: $latestFile"
  java -jar $artifacts/$latestFile
}
