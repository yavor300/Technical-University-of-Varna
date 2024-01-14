#!/bin/bash

# 1. Create a directory and 2. change into it
mkdir demoDir && cd demoDir

# 3. Create files
echo "Hello World" > file1.txt
echo "Hello Universe" > file2.txt

# 4. List files
ls

# 5. Copy file1 to file1copy
cp file1.txt file1copy.txt

# 6. Move file1copy to renamedFile
mv file1copy.txt renamedFile.txt

# 7. Display content of files
cat file1.txt
more file2.txt

# 8. Display differences between files
diff file1.txt file2.txt

# 9. Search for "Hello" in file1
grep "Hello" file1.txt

# 10. Using pipes - count number of files in the directory
ls | wc -l

# 11. Count words, lines, characters in file1
wc file1.txt

# 12. Demonstrating operators
echo "Adding to file" >> file1.txt
cat file1.txt

# Running a background process
echo "This is a background process" &
