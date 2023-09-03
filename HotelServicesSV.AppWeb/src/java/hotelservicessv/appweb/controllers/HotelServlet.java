package hotelservicessv.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import hotelservicessv.accesoadatos.HotelDAL;
import hotelservicessv.entidadesdenegocio.Hotel;
import hotelservicessv.appweb.utils.*;

@WebServlet(name = "HotelServlet", urlPatterns = {"/Hotel"})
public class HotelServlet extends HttpServlet {
private Hotel obtenerHotel(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Hotel hotel = new Hotel();
        if (accion.equals("create") == false) {
            hotel.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }

        hotel.setNombre(Utilidad.getParameter(request, "nombre", ""));
        hotel.setImagen(Utilidad.getParameter(request, "Imagen", ""));
        hotel.setDireccion(Utilidad.getParameter(request, "Direccion", ""));
        hotel.setDescripcion(Utilidad.getParameter(request, "Descripcion", ""));
        hotel.setTelefono(Utilidad.getParameter(request, "telefono", ""));
        hotel.setDepartamento(Utilidad.getParameter(request, "Departamento", ""));
        hotel.setEntrada(Utilidad.getParameter(request, "Entrada", ""));
        hotel.setHorario(Utilidad.getParameter(request, "Horario", ""));
        
        if (accion.equals("index")) {
            hotel.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            hotel.setTop_aux(hotel.getTop_aux() == 0 ? Integer.MAX_VALUE : hotel.getTop_aux());
        }
        
        return hotel;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Hotel hotel = new Hotel();
            hotel.setTop_aux(10);
            ArrayList<Hotel> hoteles = HotelDAL.buscar(hotel);
            request.setAttribute("hoteles", hoteles);
            request.setAttribute("top_aux", hotel.getTop_aux());             
            request.getRequestDispatcher("Views/Hotel/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Hotel hotel = obtenerHotel(request);
            ArrayList<Hotel> hoteles = HotelDAL.buscar(hotel);
            request.setAttribute("hoteles", hoteles);
            request.setAttribute("top_aux", hotel.getTop_aux());
            request.getRequestDispatcher("Views/Hotel/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Hotel/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Hotel hotel = obtenerHotel(request);
            int result = HotelDAL.crear(hotel);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Hotel hotel = obtenerHotel(request);
            Hotel hotel_result = HotelDAL.obtenerPorId(hotel);
            if (hotel_result.getId() > 0) {
                request.setAttribute("hotel", hotel_result);
            } else {
                Utilidad.enviarError("El Id:" + hotel.getId() + " no existe en la tabla de Hotel", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Hotel/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Hotel hotel = obtenerHotel(request);
            int result = HotelDAL.modificar(hotel);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Hotel/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Hotel/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Hotel hotel = obtenerHotel(request);
            int result = HotelDAL.eliminar(hotel);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logró eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
// </editor-fold>
}
    
