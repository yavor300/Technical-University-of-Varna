$versionFile = "VERSION"
$version = Get-Content $versionFile -Raw
$jarName = "program"
$jarNameWithVersion = "$jarName-$version"
$artifacts = "artifacts/"

if (-not (Test-Path -Path $artifacts)) {
    New-Item -ItemType Directory -Path $artifacts -Force
}

Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName } > classes.txt
javac -encoding UTF-8 -d bin @(Get-Content classes.txt)
jar cmvf META-INF/MANIFEST.MF "$jarNameWithVersion.jar" -C bin/ .
Copy-Item -Path "$jarNameWithVersion.jar" -Destination $artifacts
Remove-Item "$jarNameWithVersion.jar"


Remove-Item -Path bin -Recurse -Force
Remove-Item -Path classes.txt -Force
