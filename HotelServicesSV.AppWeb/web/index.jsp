<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<% if (SessionUser.isAuth(request) == false) {
         response.sendRedirect("Administrador?accion=login");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Principal Bienvenidos!</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container"> 
            <div class="row">
                <div class="centered">
                    <h1>Bienvenidos!</h1>
                    <img src="/HotelServicesSV.AppWeb/wwwroot/images/login.jpg" alt=""/>
                    <span></span> 
                </div>
            </div>            
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
