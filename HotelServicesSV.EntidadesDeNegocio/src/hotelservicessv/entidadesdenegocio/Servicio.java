
package hotelservicessv.entidadesdenegocio;


public class Servicio {
   private int id;
   private int idhotel;
   private String servicios;
   private String estado;
   private int top_aux;
   private Hotel hotel;

    public Servicio() {
    }

    public Servicio(int id, int idhotel, String servicios, String estado, int top_aux, Hotel hotel) {
        this.id = id;
        this.idhotel = idhotel;
        this.servicios = servicios;
        this.estado = estado;
        this.top_aux = top_aux;
        this.hotel = hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdhotel() {
        return idhotel;
    }

    public void setIdhotel(int idhotel) {
        this.idhotel = idhotel;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
   
   
    
}
