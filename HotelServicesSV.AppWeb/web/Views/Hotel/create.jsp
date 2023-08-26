<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Hotel</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Hotel</h5>
            <form action="Hotel" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtImagen" type="text" name="imagen" required class="validate" maxlength="30">
                        <label for="txtImagen">Imagen</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtDireccion" type="text" name="direccion" required class="validate" maxlength="30">
                        <label for="txtDireccion">Dirección</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtDescripcion" type="text" name="descripcion" required class="validate" maxlength="30">
                        <label for="txtDescripcion">Descripción</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtTelefono" type="text" name="telefono" required class="validate" maxlength="30">
                        <label for="txtTelefono">Telefono</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtDepartamento" type="text" name="departamento" required class="validate" maxlength="30">
                        <label for="txtDepartamento">Departamento</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtEntrada" type="text" name="entrada" required class="validate" maxlength="30">
                        <label for="txtEntrada">Entrada</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtHorario" type="text" name="horario" required class="validate" maxlength="30">
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