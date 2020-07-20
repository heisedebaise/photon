#!/bin/bash

mvn clean install versions:use-latest-releases
find . -name 'pom.xml.*'
#find . -name 'pom.xml.*' -exec rm -rf {} \;