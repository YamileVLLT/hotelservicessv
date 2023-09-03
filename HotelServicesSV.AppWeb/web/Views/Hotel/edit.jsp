<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Hotel"%>
<% Hotel hotel = (Hotel) request.getAttribute("hotel");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Hotel</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Hotel</h5>
            <form action="Hotel" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=Hotel.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=hotel.getNombre()%>" required class="validate" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtImagen" type="text" name="imagen" value="<%=hotel.getImagen()%>" required class="validate" maxlength="100">
                        <label for="txtImagen">Imagen</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtDireccion" type="text" name="direccion" value="<%=hotel.getDireccion()%>" required class="validate" maxlength="15">
                        <label for="txtDireccion">Dirección</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtDescripcion" type="text" name="descripcion" value="<%=hotel.getDescripcion()%>" required class="validate" maxlength="15">
                        <label for="txtDescripcion">Descripción</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtTelefono" type="text" name="telefono" value="<%=hotel.getTelefono()%>" required class="validate" maxlength="15">
                        <label for="txtTelefono">Telefono</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtDepartamento" type="text" name="departamento" value="<%=hotel.getDepartamento()%>" required class="validate" maxlength="15">
                        <label for="txtDepartamento">Departamento</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtEntrada" type="text" name="entrada" value="<%=hotel.getEntrada()%>" required class="validate" maxlength="15">
                        <label for="txtEntrada">Entrada</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtHorario" type="text" name="horario" value="<%=hotel.getDescripcion()%>" required class="validate" maxlength="15">
                        <label for="txtHorario">Horario</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Hotel" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>