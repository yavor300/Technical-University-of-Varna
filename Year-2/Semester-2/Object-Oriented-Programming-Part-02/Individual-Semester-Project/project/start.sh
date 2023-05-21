#!/bin/bash

artifacts="artifacts"
pattern="program-[0-9]+\.[0-9]+\.[0-9]+\.jar"

if [[ $# -eq 0 ]]; then
  files=($(find "$artifacts" -name "*.jar" -type f -regex ".*/$pattern" | sort -V))
  latestFile=${files[-1]}
else
  version="$1"
  files=($(find "$artifacts" -name "*.jar" -type f -regex ".*/program-$version\.jar"))
  latestFile=${files[0]}
fi

if [[ -z $latestFile ]]; then
  echo "No matching jar file found."
else
  echo "Starting jar file: $latestFile"
  java -jar "$latestFile"
fi
