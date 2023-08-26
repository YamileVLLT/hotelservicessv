<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Hotel"%>
<% Hotel contacto = (Hotel) request.getAttribute("hotel");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Hotel</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Hotel</h5>          
            <form action="Hotel" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=contacto.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input disabled  id="txtNombre" type="text" value="<%=hotel.getNombre()%>">
                        <label for="txtNombre">Nombre</label>
                    </div>       
                    <div class="input-field col l4 s12">
                        <input disabled  id="txtImagen" type="text" value="<%=hotel.getImagen()%>">
                        <label for="txtImagen">Imagen</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input disabled  id="txtDireccion" type="text" value="<%=hotel.getDireccion()%>">
                        <label for="txtDireccion">Dirección</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input disabled  id="txtDescripcion" type="text" value="<%=hotel.getDescripcion()%>">
                        <label for="txtDescripcion">Descripción</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input disabled  id="txtTelefono" type="text" value="<%=hotel.getTelefono()%>">
                        <label for="txtTelefono">Telefono</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input disabled  id="txtDepartamento" type="text" value="<%=hotel.getDepartamento()%>">
                        <label for="txtDepartamento">Departamento</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input disabled  id="txtEntrada" type="text" value="<%=hotel.getEntrada()%>">
                        <label for="txtEntrada">Entrada</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input disabled  id="txtHorario" type="text" value="<%=hotel.getHrario()%>">
                        <label for="txtHorario">Horario</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Hotel" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>