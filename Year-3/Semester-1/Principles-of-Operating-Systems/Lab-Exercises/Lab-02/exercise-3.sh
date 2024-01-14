#! /bin/bash

echo "Enter first file name..."
read a
echo "Enter second file name..."
read b
test -f $a -a -f $b -a $a -nt $b && echo "Both exists and the first file is newer" || echo "Not all files exist or the first file is not newer"
