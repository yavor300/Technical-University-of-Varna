$versionFile = "VERSION"
$version = Get-Content $versionFile -Raw
$file = "program-$version.jar"

if (Test-Path $file) {
    Remove-Item $file
}
Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName } > sources.txt
javac -encoding UTF-8 -d bin @(Get-Content sources.txt)
jar cmvf META-INF/MANIFEST.MF $file -C bin/ .
Remove-Item -Path bin -Recurse -Force
Remove-Item -Path sources.txt -Force
