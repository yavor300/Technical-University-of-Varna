#!/bin/bash

versionFile="VERSION"
version=$(cat "$versionFile")
jarName="program"
jarNameWithVersion="$jarName-$version"
artifacts="artifacts/"

if [[ ! -d "$artifacts" ]]; then
  mkdir -p "$artifacts"
fi

find . -type f -name "*.java" > classes.txt
javac -encoding UTF-8 -d bin @"classes.txt"
jar cmvf META-INF/MANIFEST.MF "$jarNameWithVersion.jar" -C bin/ .
cp "$jarNameWithVersion.jar" "$artifacts"
rm "$jarNameWithVersion.jar"
rm -rf bin
rm classes.txt
