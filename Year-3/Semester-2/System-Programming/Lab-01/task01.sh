#!/bin/bash

mkdir root
mkdir root/left
mkdir root/left/l1
mkdir root/left/l2
mkdir root/right
mkdir root/right/r1
mkdir root/right/r2

echo "primeren root" > root/tekst.txt
echo "primeren left" > root/left/tekst.txt
echo "primeren left l1" > root/left/l1/tekst.txt
echo "primeren left l2" > root/left/l2/tekst.txt
echo "primeren right" > root/right/tekst.txt
echo "primeren right r1" > root/right/r1/tekst.txt
echo "primeren right r2" > root/right/r2/tekst.txt

grep -r "primeren" root/ | wc -l

rm -rf root/
