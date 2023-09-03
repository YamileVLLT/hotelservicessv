<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Servicio"%>
<%@page import="hotelservicessv.entidadesdenegocio.Hotel"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Servicio> servicios = (ArrayList<Servicio>) request.getAttribute("servicios");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (servicios == null) {
        servicios = new ArrayList();
    } else if (servicios.size() > numReg) {
        double divNumPage = (double) servicios.size() / (double) numReg;
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
        <title>Lista de Servicios</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Servicios</h5>
            <form action="Servicio" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtIdHotel" type="text" name="idhotel">
                        <label for="txtIdHotel">IdHotel</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtServicios" type="text" name="servicios">
                        <label for="txtServicios">Servicios</label>
                    </div>                     
                    <div class="input-field col l4 s12">
                        <input  id="txtEstado" type="text" name="estado">
                        <label for="txtEstado">Estado</label>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Hotel/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Servicio?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                    <th>IdHotel</th>  
                                    <th>Servicios</th> 
                                    <th>Estado</th>  
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Servicio servicio : servicios) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=servicio.getIdhotel()%></td>  
                                    <td><%=servicio.getServicios()%></td>
                                    <td><%=servicio.getEstado()%></td>  
                                    <td><%=servicio.getHotel().getNombre()%></td>  
                                    <td>
                                        <div style="display:flex">
                                             <a href="Servicio?accion=edit&id=<%=servicio.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="Servicio?accion=details&id=<%=servicio.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="Servicio?accion=delete&id=<%=servicio.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                            <i class="material-icons">delete</i>
                                        </a>    
                                        </div>                                                                    
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
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