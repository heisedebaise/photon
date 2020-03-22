#!/bin/bash

git add bin
git add LICENSE
git add pom.xml
git add README.md

for name in photon-*
do
  git add $name/src
  git add $name/pom.xml
done

git commit -m dev
git push