#!/bin/bash -e

### Build the UI and copy it into the target directory
UI_PATH="${PWD}/target/ui"
mkdir -p "$UI_PATH"
pushd ../../web
./build-and-copy.sh "${UI_PATH}"
popd

### Now copy other files we need to override as well
cp ../../opennms-webapp/src/main/webapp/WEB-INF/templates/navbar.ftl target/

docker build . -t opennms/horizon:features-vue3
