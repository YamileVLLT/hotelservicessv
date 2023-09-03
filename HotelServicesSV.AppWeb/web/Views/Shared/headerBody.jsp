<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hotelservicessv.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<nav>
    <div class="nav-wrapper blue">
            <a href="Home" class="brand-logo">HotelServices.sv</a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>       
        <ul class="right hide-on-med-and-down">  
            <% if (SessionUser.isAuth(request)) {  %>
            <li><a href="Home">Inicio</a></li>
            <li><a href="Hotel">Hoteles</a></li>
                <li><a href="Servicio">Servicios</a></li>
            <li><a href="Administrador">Administradores</a></li>
            <li><a href="Rol">Roles</a></li>
            <li><a href="Administrador?accion=cambiarpass">Cambiar password</a></li>
            <li><a href="Administrador?accion=login">Cerrar sesión</a></li>
            <%}%>
        </ul>
    </div>
</nav>

<ul class="sidenav" id="mobile-demo">
     <% if (SessionUser.isAuth(request)) {  %>
     <li><a href="Home">Inicio</a></li>
     <li><a href="Hotel">Hoteles</a></li>
     <li><a href="Servicios">Servicios</a></li>
     <li><a href="Administrador">Administrador</a></li>
     <li><a href="Rol">Roles</a></li>
     <li><a href="Administrador?accion=cambiarpass">Cambiar password</a></li>
     <li><a href="Administrador?accion=login">Cerrar sesión</a></li>
     <%}%>
</ul>