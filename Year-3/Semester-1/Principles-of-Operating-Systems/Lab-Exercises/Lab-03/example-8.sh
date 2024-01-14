#! /bin/bash

NUMS="1 2 3 4 5 6 7"
for NUM in $NUMS
do
  q=$[ $NUM % 2 ]
  if [ $q -eq 0 ]
    then
      echo "Number $NUM is an even number!"
      continue
  fi
  echo "Number $NUM"
done
