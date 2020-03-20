#!/bin/bash

version=0.1
git checkout -b $version
git add bin
git add **/src
git add **/pom.xml
git add LICENSE
git add pom.xml
git add README.md
git commit -m $version
git push origin $version
git checkout master
