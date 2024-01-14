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
