package hotelservicessv.accesoadatos;

import java.util.*;
import java.sql.*;
import hotelservicessv.entidadesdenegocio.*;

public class HotelDAL {
  static String obtenerCampos() {
        return "h.Id, h.Nombre,h.Imagen,h.Direccion, h.Descripcion, h.Telefono, h.Departamento, h.Entrada, h.Horario";
    }
    
    private static String obtenerSelect(Hotel pHotel) {
        String sql;
        sql = "SELECT ";
        if (pHotel.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pHotel.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Contacto c");
        return sql;
    }
    
    private static String agregarOrderBy(Hotel pHotel) {
        String sql = " ORDER BY c.Id DESC";
        if (pHotel.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pHotel.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Hotel pHotel) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Contacto(Nombre, Email, Telefono, Celular) VALUES(?, ?, ?, ?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pHotel.getNombre());
                ps.setString(2, pHotel.getImagen());
                ps.setString(3, pHotel.getDireccion());
                ps.setString(4, pHotel.getDescripcion());
                ps.setString(5, pHotel.getTelefono());
                ps.setString(6, pHotel.getDepartamento());
                ps.setString(7, pHotel.getEntrada());
                ps.setString(8, pHotel.getHorario());
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
    
    public static int modificar(Hotel pHotel) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Contacto SET Nombre=?, Email = ?, Telefono = ?, Celular = ? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pHotel.getNombre());
                ps.setString(2, pHotel.getImagen());
                ps.setString(3, pHotel.getDireccion());
                ps.setString(4, pHotel.getDescripcion());
                ps.setString(5, pHotel.getTelefono());
                ps.setString(6, pHotel.getDepartamento());
                ps.setString(7, pHotel.getEntrada());
                ps.setString(8, pHotel.getHorario());
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
    
    public static int eliminar(Hotel pHotel) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Hotel WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pHotel.getId());
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
    
    static int asignarDatosResultSet(Hotel pHotel, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pHotel.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pHotel.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setImagen(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setDireccion(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setDescripcion(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setTelefono(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setDepartamento(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setEntrada(pResultSet.getString(pIndex));
        pIndex++;
        pHotel.setHorario(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Hotel> pHotel) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Hotel Hotel = new Hotel(); 
                asignarDatosResultSet(Hotel, resultSet, 0);
                pHotel.add(Hotel);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Hotel obtenerPorId(Hotel pHotel) throws Exception {
        Hotel Hotel = new Hotel();
        ArrayList<Hotel> hoteles = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pHotel);
            sql += " WHERE c.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pHotel.getId());
                obtenerDatos(ps, hoteles);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (hoteles.size() > 0) {
            Hotel = hoteles.get(0);
        }
        return Hotel;
    }
    
    public static ArrayList<Hotel> obtenerTodos() throws Exception {
        ArrayList<Hotel> Hotel = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Hotel());
            sql += agregarOrderBy(new Hotel());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, Hotel);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return Hotel;
    }
    
    static void querySelect(Hotel pHotel, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pHotel.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" c.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pHotel.getId()); 
            }
        }

        if (pHotel.getNombre() != null && pHotel.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" c.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getNombre() + "%"); 
            }
        }
        
        if (pHotel.getImagen()!= null && pHotel.getImagen().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.Imagen LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getImagen()+ "%"); 
            }
        }
        
        if (pHotel.getDireccion()!= null && pHotel.getDireccion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.Direccion LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getDireccion()+ "%"); 
            }
        }
        
        if (pHotel.getDescripcion()!= null && pHotel.getDescripcion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.Descripcion LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getDescripcion()+ "%"); 
            }
        }
        
        if (pHotel.getTelefono()!= null && pHotel.getTelefono().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.Telefono LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getTelefono()+ "%"); 
            }
        }
        
        if (pHotel.getDepartamento()!= null && pHotel.getDepartamento().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.Departamento LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getDepartamento()+ "%"); 
            }
        }
        
        if (pHotel.getEntrada()!= null && pHotel.getEntrada().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.Entrada LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getEntrada()+ "%"); 
            }
        }
        
        if (pHotel.getHorario()!= null && pHotel.getHorario().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" h.getHorario LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHotel.getHorario()+ "%"); 
            }
        }
    }
    
    public static ArrayList<Hotel> buscar(Hotel pHotel) throws Exception {
        ArrayList<Hotel> Hotel = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pHotel);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pHotel, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pHotel);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pHotel, utilQuery);
                obtenerDatos(ps, Hotel);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return Hotel;
    }
}
