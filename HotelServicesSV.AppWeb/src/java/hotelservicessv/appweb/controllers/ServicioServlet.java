package hotelservicessv.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import hotelservicessv.accesoadatos.HotelDAL;
import hotelservicessv.accesoadatos.ServicioDAL;
import hotelservicessv.appweb.utils.*;
import hotelservicessv.entidadesdenegocio.Hotel;
import hotelservicessv.entidadesdenegocio.Servicio;

@WebServlet(name = "ServicioServlet", urlPatterns = {"/ServicioServlet"})
public class ServicioServlet extends HttpServlet {
    private Servicio obtenerServicio(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Servicio servicio = new Servicio();
        if (accion.equals("create") == false) {
            servicio.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        servicio.setId(Integer.parseInt(Utilidad.getParameter(request, "IdHotel", "")));
        servicio.setIdHotel(Utilidad.getParameter(request, "rubro", ""));
        servicio.setServicios(Utilidad.getParameter(request, "Servicios", ""));
        servicio.setEstado(Utilidad.getParameter(request, "Estado", ""));
        
        if (accion.equals("index")) {
            servicio.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            servicio.setTop_aux(servicio.getTop_aux() == 0 ? Integer.MAX_VALUE : servicio.getTop_aux());
        }
        
        return servicio;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Servicio servicio = new Servicio();
            servicio.setTop_aux(10);
            ArrayList<Servicio> servicios = ServicioDAL.buscarIncluirHotel(servicio);
            request.setAttribute("empresas", servicios);
            request.setAttribute("top_aux", servicio.getTop_aux());
            request.getRequestDispatcher("Views/Servicio/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Servicio servicio = obtenerServicio(request);
            ArrayList<Servicio> servicios;
            servicios = ServicioDAL.buscarIncluirHotel(servicio);
            request.setAttribute("servicio", servicios);
            request.setAttribute("top_aux", servicio.getTop_aux());
            request.getRequestDispatcher("Views/Empresa/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Servicio/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Servicio servicio = obtenerServicio(request);
            int result = ServicioDAL.crear(servicio);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro guardar el nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Servicio servicio = obtenerServicio(request);
            Servicio servicio_result = ServicioDAL.obtenerPorId(servicio);
            if (servicio_result.getId() > 0) {
                Hotel hotel = new Hotel();
                hotel.setId(servicio_result.getIdhotel());
                servicio_result.setHotel(HotelDAL.obtenerPorId(hotel));
                request.setAttribute("servicio", servicio_result);
            } else {
                Utilidad.enviarError("El Id:" + servicio_result.getId() + " no existe en la tabla de Servicios", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Servicio/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Servicio servicio = obtenerServicio(request);
            int result = ServicioDAL.modificar(servicio);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Servicio/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Servicio/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Servicio servicio = obtenerServicio(request);
            int result = ServicioDAL.eliminar(servicio);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
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