#! /bin/bash

a=0
until [ ! $a -lt 10 ]
do
  echo $a
  if [ $a -eq 5 ]
    then
      break
  fi
  a=$[$a+1]
done
