#! /bin/bash

for i in $1/*
do
  if [ -d $i ]
  then
    echo "Subdirectory found: $i"
    ls -a $i
  elif [ -f $i ]
    then
      echo "File found: $i"
      cat $i
  fi
done
