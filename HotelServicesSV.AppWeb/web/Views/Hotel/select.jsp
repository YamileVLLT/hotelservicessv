<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.entidadesdenegocio.Hotel"%>
<%@page import="hotelservicessv.accesoadatos.HotelDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Hotel> hoteles = HotelDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slHotel" name="idHotel">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Hotel hotel : hoteles) {%>
        <option <%=(id == hotel.getId()) ? "selected" : "" %>  value="<%=hotel.getId()%>"><%= hotel.getNombre()%></option>
    <%}%>
</select>
<label for="idHotel">Hotel</label>