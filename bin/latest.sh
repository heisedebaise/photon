#!/bin/bash

mvn clean install versions:use-latest-releases
find . -name 'pom.xml.*'