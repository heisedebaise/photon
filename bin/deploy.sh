#!/bin/bash

sed -i 's/mysql:3306/localhost:3306/' photon-web/src/main/resources/photon.config
source bin/install.sh
sed -i 's/localhost:3306/mysql:3306/' photon-web/src/main/resources/photon.config

podman stop -t 1 tomcat
rm -rf ~/tomcat/webapps/ROOT
cp -r photon-web/target/photon-web-1.0 ~/tomcat/webapps/ROOT
podman restart tomcat
tail -f ~/tomcat/logs/catalina.out
