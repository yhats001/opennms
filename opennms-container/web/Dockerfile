FROM opennms/horizon:bleeding
COPY ./target/ui /opt/opennms/jetty-webapps/opennms/assets/ui/
COPY ./target/navbar.ftl /opt/opennms/jetty-webapps/opennms/WEB-INF/templates/navbar.ftl
COPY ./target/import-map.json.jsp /opt/opennms/jetty-webapps/opennms/import-map.json.jsp
