#!/bin/bash

git add bin
git add pom.xml
git add *.md
git add LICENSE

for file in *
do
    if [[ $file == photon-* ]]; then
        git add $file/pom.xml
        git add $file/src
    fi
done

git commit -m dev
git push