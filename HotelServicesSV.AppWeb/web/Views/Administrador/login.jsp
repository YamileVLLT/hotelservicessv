<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Login</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5></h5>
            <form action="Administrador?accion=login" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="col s6">
                        <img src="/HotelServicesSV.AppWeb/wwwroot/images/login.jpg" alt=""/>
                    </div>
                    <div class="col s6">
                        <div class="input-field col l5 s12">                                             
                            <i class="material-icons prefix">account_circle</i>
                            <input  id="txtLogin" type="text" name="login" required class="validate" maxlength="25">  
                            <label for="txtLogin">Login</label>
                        </div>     
                        <div class="input-field col l5 s12">                                             
                            <i class="material-icons prefix">enhanced_encryption</i>
                            <input  id="txtPassword" type="password" name="password" required class="validate" minlength="5" maxlength="32">  
                            <label for="txtPassword">Password</label>
                        </div>
                        <div class="col l12 s12">
                            <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">send</i>Login</button>                                               
                        </div>
                    </div>
                    
                </div>

                <% if (request.getAttribute("error") != null) { %>
                <div class="row">
                    <div class="col l12 s12">
                        <span style="color:red"><%= request.getAttribute("error") %></span>                                              
                    </div>
                </div>
                <%}%>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
