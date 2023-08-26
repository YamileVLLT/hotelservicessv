<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Servicio"%>
<% Servicio servicio = (Servicio) request.getAttribute("servicio");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Servicio</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Servicio</h5>
            <form action="Empresa" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=empresa.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtIdHotel" type="text" name="idhotel" value="<%=servicio.getIdHotel()%>" required class="validate" maxlength="50">
                        <label for="txtIdHotel">IdHotel</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtServicios" type="text" name="servicios" value="<%=servicio.getServicios()%>" required class="validate" maxlength="30">
                        <label for="txtServicios">Servicios</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtEstado" type="text" name="estado" value="<%=servicio.getEstado()%>" required  class="validate" maxlength="25">
                        <label for="txtEstado">Estado</label>
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Hotel/select.jsp">                           
                            <jsp:param name="id" value="<%=servicio.getIdHotel() %>" />  
                        </jsp:include>  
                        <span id="slHotel_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Servicio" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;                
                var slHotel = document.getElementById("slHotel");
                var slHotel_error = document.getElementById("slHotel_error");
                if (slHotel.value == 0) {
                    slHotel_error.innerHTML = "El Hotel es obligatorio";
                    result = false;
                } else {
                    slHotel_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>