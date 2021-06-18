#!/bin/sh
UI_PATH="${OPENNMS_HOME}/jetty-webapps/opennms/assets/ui"
if [ -n "$1" ]; then
  UI_PATH="$1"
fi

echo "Copy new UI structure in: $UI_PATH"
mkdir -p ${UI_PATH}/navbar ${UI_PATH}/nodes
cp -Rv root-config/dist/* "${UI_PATH}/"
cp -Rv navbar/dist/* "${UI_PATH}/navbar"
cp -Rv nodes/dist/* "${UI_PATH}/nodes"
