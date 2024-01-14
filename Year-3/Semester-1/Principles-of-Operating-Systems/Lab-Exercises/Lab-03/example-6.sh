#! /bin/bash


i=1
var=0
while [ $i -lt 5 ]
do
  var=$[ var + 1 ]
  echo $var
  i=$[i+1]
done
