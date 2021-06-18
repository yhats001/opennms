#!/usr/bin/env bash

# Exit script if a statement returns a non-true return value.
set -o errexit

# Use the error status of the first failure, rather than that of the last item in a pipeline.
set -o pipefail

cd "$(dirname "$0")"

# shellcheck disable=SC1091
source ../set-build-environment.sh

# Build the UI and copy it into the target directory
UI_PATH="${PWD}/target/ui"
mkdir -p "$UI_PATH"
pushd ../../web
./copy.sh "${UI_PATH}"
popd

# Now copy other files we need to override as well
cp ../../opennms-webapp/src/main/webapp/WEB-INF/templates/navbar.ftl target/

# Build and save
docker build . -t horizon
docker image save horizon -o images/horizon.oci
