<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Servicio"%>
<% Servicio servicio = (Servicio) request.getAttribute("servicio");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de la Servicio</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de la Servicio</h5>
             <div class="row">
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtIdHotel" type="text" value="<%=servicio.getIdHotel()%>" disabled>
                        <label for="txtIdHotel">IdHotel</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtServicios" type="text" value="<%=servicio.getServicios()%>" disabled>
                        <label for="txtServicios">Servicios</label>
                    </div>       
                    <div class="input-field col l4 s12">
                        <input id="txtEstado"text" value="<%=servicio.getEstado().getNombre()%>" disabled>
                        <label for="txtEstado">Estado</label>
                    </div> 
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Servicio?accion=edit&id=<%=servicio.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Servicio" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
