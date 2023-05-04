# Set the source and destination directories
$src_dir = "E:\Technical-University-of-Varna\Year-2\Semester-2\Object-Oriented-Programming-Part-02\Homework\Homework-06"
$dst_dir = "E:\Technical-University-of-Varna\Year-2\Semester-2\Object-Oriented-Programming-Part-02\Homework\Homework-06-To-Zip"

# Create the destination directory if it doesn't exist
New-Item -ItemType Directory -Force $dst_dir | Out-Null

Get-ChildItem -Path $src_dir -Include *.java -Recurse -File | ForEach-Object {
    $destinationFile = Join-Path $dst_dir $_.Name
    Copy-Item -Path $_.FullName -Destination $destinationFile -Force
}