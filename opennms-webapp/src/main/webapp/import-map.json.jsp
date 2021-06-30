<%--
/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2021-2021 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2021 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

--%>
<%@page language="java"
        contentType="application/json; charset=UTF-8"
        pageEncoding="UTF-8"
        session="true"
        import="
            java.io.FileReader,
            java.io.IOException,
            java.util.Arrays,
            java.util.LinkedHashMap,
            java.util.List,
            java.util.Map,
            com.google.gson.Gson,
            com.google.gson.stream.JsonReader,
            org.opennms.web.api.Util
        "%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  final String baseHref = Util.calculateUrlBase(request);
  final String prefix = request.getSession().getServletContext().getRealPath("assets");
  final List<String> importMapFiles = Arrays.asList("/ui/navbar/import-map.json", "/ui/main/import-map.json");
  final Map<String, String> imports = new LinkedHashMap<>();
  for (String importMapFile : importMapFiles) {
      Gson gson = new Gson();
      try (FileReader fileReader = new FileReader(prefix + importMapFile);
           JsonReader reader = new JsonReader(fileReader)) {
           Map<String,Map<String,String>> jsonMap = gson.fromJson(reader, Map.class);
           Map<String,String> importMap = jsonMap.get("imports");
           // Add to map and prefix with baseHref
           for (String importKey : importMap.keySet()) {
               imports.put(importKey, baseHref + importMap.get(importKey));
           }
      } catch (IOException e) {
           throw new RuntimeException(e);
      }
  }
  pageContext.setAttribute("imports", imports);
%>
{
    "imports": {
        "@opennms/root-config": "<%= baseHref %>assets/ui/opennms-root-config.js",
     <c:forEach var="entry" items="${imports}" varStatus="status">
        "${entry.key}": "${entry.value}"<c:if test="${not status.last}">,</c:if>
     </c:forEach>
    }
}
