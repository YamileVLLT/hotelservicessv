package hotelservicessv.accesoadatos;

import java.util.*;
import java.sql.*;
import hotelservicessv.entidadesdenegocio.*;

public class ServicioDAL {
   static String obtenerCampos() {
        return "s.Id, s.IdHotel, s.Servicios, s.Estado";
    }
    
    private static String obtenerSelect(Servicio pServicio) {
        String sql;
        sql = "SELECT ";
        if (pServicio.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pServicio.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Servicio s");
        return sql;
    }
    
    private static String agregarOrderBy(Servicio pServicio) {
        String sql = " ORDER BY e.Id DESC";
        if (pServicio.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pServicio.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Servicio pServicio) throws Exception {
        int result;
        String sql;
        
        try ( Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO Servicio(Id,IdHotel,Servicios,Estado,) VALUES(?,?,?,?,?)";
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pServicio.getId());
                ps.setInt(2, pServicio.getIdhotel());
                ps.setString(3, pServicio.getServicios());
                ps.setString(4, pServicio.getEstado());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        
        return result;
    }
    
    public static int modificar(Servicio pServicio) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Servicio SET Id=?, IdHotel=?, Servicios=?, Estado=? WHERE Id=?";
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pServicio.getId());
                ps.setInt(2, pServicio.getIdhotel());
                ps.setString(3, pServicio.getServicios());
                ps.setString(4, pServicio.getEstado());
                ps.setInt(6, pServicio.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        
        return result;
    }
    
    public static int eliminar(Servicio pServicio) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Servicio WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pServicio.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    static int asignarDatosResultSet(Servicio pServicio, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pServicio.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pServicio.setIdhotel(pResultSet.getInt(pIndex)); 
        pIndex++;
        pServicio.setServicios(pResultSet.getString(pIndex)); 
        pIndex++;
        pServicio.setEstado(pResultSet.getString(pIndex));  
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Servicio> pServicios) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                 Servicio servicio = new Servicio();
                asignarDatosResultSet(servicio, resultSet, 0);
                pServicios.add(servicio);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirHotel(PreparedStatement pPS, ArrayList<Servicio> pServicios) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Hotel> hotelMap = new HashMap(); 
            while (resultSet.next()) {
                Servicio Servicio = new Servicio();
                int index = asignarDatosResultSet(Servicio, resultSet, 0);
                if (hotelMap.containsKey(Servicio.getIdhotel()) == false) {
                    Hotel hotel = new Hotel();
                    HotelDAL.asignarDatosResultSet(hotel, resultSet, index);
                    hotelMap.put(hotel.getId(), hotel); 
                    Servicio.setHotel(hotel); 
                } else {
                    Servicio.setHotel(hotelMap.get(Servicio.getIdhotel())); 
                }
                pServicios.add(Servicio); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Servicio obtenerPorId(Servicio pServicio) throws Exception {
        Servicio servicio = new Servicio();
        ArrayList<Servicio> servicios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pServicio);
            sql += " WHERE e.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pServicio.getId());
                obtenerDatos(ps, servicios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (servicios.size() > 0) {
            servicio = servicios.get(0);
        }
        return servicio;
    }
    
    public static ArrayList<Servicio> obtenerTodos() throws Exception {
        ArrayList<Servicio> Servicios = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Servicio()); 
            sql += agregarOrderBy(new Servicio());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, Servicios);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return Servicios;
    }
    
    static void querySelect(Servicio pServicio, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pServicio.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pServicio.getId());
            }
        }

        if (pServicio.getId()> 0) {
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pServicio.getId());
            }
        }
        
        if (pServicio.getIdhotel()> 0) {
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pServicio.getId());
            }
        }

        if (pServicio.getServicios()!= null && pServicio.getServicios().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" s.Servicios LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pServicio.getServicios()+ "%");
            }
        }

        if (pServicio.getEstado()!= null && pServicio.getEstado().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" s.Estado=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pServicio.getEstado());
            }
        }
    }
    
    public static ArrayList<Servicio> buscar(Servicio pServicio) throws Exception {
        ArrayList<Servicio> Servicios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pServicio);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pServicio, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pServicio);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pServicio, utilQuery);
                obtenerDatos(ps, Servicios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return Servicios;
    }
    
    public static ArrayList<Servicio> buscarIncluirHotel(Servicio pServicio) throws Exception {
        ArrayList<Servicio> Servicios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pServicio.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pServicio.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += ServicioDAL.obtenerCampos();
            sql += " FROM Servicio e";
            sql += " JOIN Hotel h on (e.IdHotel=h.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pServicio, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pServicio);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pServicio, utilQuery);
                obtenerDatosIncluirHotel(ps, Servicios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return Servicios;
    } 
}
