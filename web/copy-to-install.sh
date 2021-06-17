#!/bin/sh
UI_PATH="${OPENNMS_HOME}/jetty-webapps/opennms/assets/ui"
echo "Rebuilding new UI structure in: $UI_PATH"
rm -rf $UI_PATH/*
cp -R root-config/dist/* "${UI_PATH}/"
cp -R navbar/dist "${UI_PATH}/navbar"
cp -R nodes/dist "${UI_PATH}/nodes"
