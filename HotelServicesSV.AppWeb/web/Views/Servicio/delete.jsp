<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.servici"%>
<% Servicio servicio = (Servicio) request.getAttribute("servicio");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Servicio</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Servicio</h5>
            <form action="Empresa" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=empresa.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtIdHotel" type="text" value="<%=servicio.getNombre()%>" disabled>
                        <label for="txtIdHotel">IdHotel</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtServicios" type="text" value="<%=servicio.getServicios()%>" disabled>
                        <label for="txtServicios">Servicios</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input id="txtEstado" type="text" value="<%=servicio.getEstado().getNombre()%>" disabled>
                        <label for="txtEstado">Estado</label>
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Servicio" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>