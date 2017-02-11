<%@ page import="models.FBConnection" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <jsp:include page="/WEB-INF/jsp/includes/head.jsp"></jsp:include>
    <body>
        <a id="fbLoginButton" href="<%=new FBConnection().getFBAuthUrl()%>">
            <img src="http://localhost:8080/jakehewitt/images/facebookLoginButton.png" />
        </a>
        
        
    </body>
</html>
