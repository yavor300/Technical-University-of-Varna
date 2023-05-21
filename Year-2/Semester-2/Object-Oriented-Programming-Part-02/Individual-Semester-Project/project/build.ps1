$versionFile = "VERSION"
$version = Get-Content $versionFile -Raw
$file_without_version = "program"
$file_with_version = "program-$version"

if (Test-Path $file_without_version-*) {
    Remove-Item $file_without_version-*
}
Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName } > sources.txt
javac -encoding UTF-8 -d bin @(Get-Content sources.txt)
jar cmvf META-INF/MANIFEST.MF "$file_with_version.jar" -C bin/ .
Remove-Item -Path bin -Recurse -Force
Remove-Item -Path sources.txt -Force
