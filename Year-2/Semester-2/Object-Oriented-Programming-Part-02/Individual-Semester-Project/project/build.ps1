Remove-Item -Path program.jar -Force
Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName } > sources.txt
javac -encoding UTF-8 -d bin @(Get-Content sources.txt)
jar cmvf META-INF/MANIFEST.MF program.jar -C bin/ .
Remove-Item -Path bin -Recurse -Force
Remove-Item -Path sources.txt -Force
