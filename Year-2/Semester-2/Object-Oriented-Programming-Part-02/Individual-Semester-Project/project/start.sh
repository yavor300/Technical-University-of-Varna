#!/bin/bash

artifacts="artifacts/"
pattern="program-[0-9]+\.[0-9]+\.[0-9]+\.jar"

files=($(ls -1 $artifacts | grep -E $pattern | sort -V))
latestFile=${files[-1]}

echo "Starting jar file with name: $latestFile"
java -jar "$artifacts/$latestFile"
