$artifacts = "artifacts/"
$pattern = "program-\d+\.\d+\.\d+\.jar"

$files = Get-ChildItem -Path $artifacts -Filter "*.jar" | Where-Object { $_.Name -match $pattern } | Sort-Object -Property { [version]($_.Name -replace "program-|\.jar") }
$latestFile = $files | Select-Object -Last 1

Write-Host "Starting jar file with name: $latestFile"
java -jar $artifacts/$latestFile
