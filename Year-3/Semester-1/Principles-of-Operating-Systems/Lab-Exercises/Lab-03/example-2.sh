#! /bin/bash

if [ -f $1 ]
then
  echo "The $1 is a file"
elif [ -d $1 ]
then
  echo "The $1 is a directory"
else
  echo "Unknown $1"
fi
pos1213@LinuxOS:~/Lab-03$ cat example-2.sh
#! /bin/bash

echo "Enter choice (1, 2):"
read ch
case $ch in
  1) echo "Choice is 1";;
  2) echo "Choice is 2";;
  *) echo "Unknown";;
esac
