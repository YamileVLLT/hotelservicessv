package hotelservicessv.entidadesdenegocio;

import java.util.ArrayList;

public class Hotel {
   private int id;
   private String nombre;
   private String imagen;
   private String direccion;
   private String descripcion;
   private String telefono;
   private String departamento;
   private String entrada;
   private String horario;
   private int top_aux;
   private ArrayList<Servicio> servicios;

    public Hotel() {
    }

    public Hotel(int id, String nombre, String imagen, String direccion, String descripcion, String telefono, String departamento, String entrada, String horario, int top_aux, ArrayList<Servicio> servicios) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.departamento = departamento;
        this.entrada = entrada;
        this.horario = horario;
        this.top_aux = top_aux;
        this.servicios = servicios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }
   
   
}
