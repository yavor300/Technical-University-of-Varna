#! /bin/bash

for file in $1/*
do
  if [ -x $file ]
  then echo $file
  fi
done
