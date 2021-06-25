FROM opennms/horizon:bleeding
COPY --chown=10001:0 ./target/ui /opt/opennms/jetty-webapps/opennms/assets/ui/
COPY --chown=10001:0 ./target/navbar.ftl /opt/opennms/jetty-webapps/opennms/WEB-INF/templates/navbar.ftl
COPY --chown=10001:0 ./target/import-map.json.jsp /opt/opennms/jetty-webapps/opennms/import-map.json.jsp
