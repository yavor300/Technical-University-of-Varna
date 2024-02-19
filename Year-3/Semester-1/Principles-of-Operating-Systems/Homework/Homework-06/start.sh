#! /bin/bash

./init 
./producer &
./toupper &
./consumer &
