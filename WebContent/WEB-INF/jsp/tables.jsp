<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp" />
        <h1>Hello World!</h1>
        <c:if test="${not empty tables}">
        <c:forEach items="${tables}" var="table" >
            <div class="table">
                
                <a href="http://localhost:8080/jakehewitt/secured/Table?tableName=${table.tableName}" >
                <c:out value="${table.tableName}" />
                </a>
                
            </div>
        </c:forEach>
        </c:if>
    </body>
</html>
