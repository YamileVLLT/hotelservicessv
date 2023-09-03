<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Hotel"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Hotel> hoteles = (ArrayList<Hotel>) request.getAttribute("hoteles");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (hoteles == null) {
        hoteles = new ArrayList();
    } else if (hoteles.size() > numReg) {
        double divNumPage = (double) hoteles.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Lista de Hoteles</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <p class="custom-font"><h5>Buscar Hoteles</h5></p>
            <form action="Hotel" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l6 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>
                    <div class="input-field col l6 s12">
                        <input  id="txtDepartamento" type="text" name="departamento">
                        <label for="txtDepartamento">Departamento</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Hotel?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Imagen</th> 
                                    <th>Dirección</th> 
                                    <th>Descripción</th> 
                                    <th>Telefono</th>
                                    <th>Departamento</th> 
                                    <th>Entrada</th>
                                    <th>Horario</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Hotel hotel : hoteles) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">
                                    <td><%=hotel.getNombre()%></td> 
                                    <td><%=hotel.getImagen()%></td>
                                    <td><%=hotel.getDireccion()%></td>
                                    <td><%=hotel.getDescripcion()%></td>
                                    <td><%=hotel.getTelefono()%></td>
                                    <td><%=hotel.getDepartamento()%></td>
                                    <td><%=hotel.getEntrada()%></td>
                                    <td><%=hotel.getHorario()%></td>
                                    <td>
                                        <div style="display:flex">
                                            <a href="Hotel?accion=edit&id=<%=hotel.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                                <i class="material-icons">edit</i>
                                            </a>
                                            <a href="Hotel?accion=details&id=<%=hotel.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                                <i class="material-icons">description</i>
                                            </a>
                                            <a href="Hotel?accion=delete&id=<%=hotel.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                                <i class="material-icons">delete</i>
                                            </a>     
                                        </div>
                                    </td>                                   
                                </tr>
                                <% } %>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>