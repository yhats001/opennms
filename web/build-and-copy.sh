#!/bin/sh
UI_PATH="${OPENNMS_HOME}/jetty-webapps/opennms/assets/ui"
if [ -n "$1" ]; then
  UI_PATH="$1"
fi

yarn build

echo "Copy new UI structure in: $UI_PATH"
cp -R root-config/dist/* "${UI_PATH}/"
cp -R navbar/dist "${UI_PATH}/navbar"
cp -R nodes/dist "${UI_PATH}/nodes"
