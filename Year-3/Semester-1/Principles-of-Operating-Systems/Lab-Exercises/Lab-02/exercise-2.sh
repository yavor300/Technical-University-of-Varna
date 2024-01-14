#! /bin/bash
test -d $1 -a -r $1 && echo "Dir" || echo "Not dir"
