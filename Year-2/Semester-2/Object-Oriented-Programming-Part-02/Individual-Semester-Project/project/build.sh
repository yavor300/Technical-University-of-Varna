#!/bin/bash

versionFile="VERSION"
version=$(cat "$versionFile")
fileWithoutVersion="program"
fileWithVersion="$fileWithoutVersion-$version"

if [ -e "$fileWithoutVersion-"* ]; then
    rm "$fileWithoutVersion-"*
fi

find . -type f -name "*.java" > sources.txt
javac -encoding UTF-8 -d bin "@sources.txt"
jar cmvf META-INF/MANIFEST.MF "$fileWithVersion.jar" -C bin/ .
rm -rf bin
rm sources.txt
