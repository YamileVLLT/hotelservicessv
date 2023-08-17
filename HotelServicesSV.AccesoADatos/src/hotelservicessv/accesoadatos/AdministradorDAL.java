
package hotelservicessv.accesoadatos;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import hotelservicessv.entidadesdenegocio.*;

public class AdministradorDAL {
    public static String encriptarMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }
    }
    
    static String obtenerCampos() {
        return "u.Id, u.IdRol, u.Nombre, u.Apellido, u.Login, u.Estatus, u.FechaRegistro";
    }
    
    private static String obtenerSelect(Administrador pAdministrador) {
        String sql;
        sql = "SELECT ";
        if (pAdministrador.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pAdministrador.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Usuario u");
        return sql;
    }
    
    private static String agregarOrderBy(Administrador pAdministrador) {
        String sql = " ORDER BY u.Id DESC";
        if (pAdministrador.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pAdministrador.getTop_aux() + " ";
        }
        return sql;
    }
    
    private static boolean existeLogin(Administrador pAdministrador) throws Exception {
        boolean existe = false;
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdministrador);
            sql += " WHERE u.Id<>? AND u.Login=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAdministrador.getId());
                ps.setString(2, pAdministrador.getLogin());
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (administradores.size() > 0) {
            Administrador administrador = administradores.get(0);
            if (administrador.getId() > 0 && administrador.getLogin().equals(pAdministrador.getLogin())) {
                existe = true;
            }
        }
        return existe;
    }
    
    public static int crear(Administrador pAdministrador) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pAdministrador);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Usuario(IdRol,Nombre,Apellido,Login,Pass,Estatus,FechaRegistro) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pAdministrador.getIdRol());
                    ps.setString(2, pAdministrador.getNombre());
                    ps.setString(3, pAdministrador.getApellido()); 
                    ps.setString(4, pAdministrador.getLogin());
                    ps.setString(5, encriptarMD5(pAdministrador.getPassword())); 
                    ps.setByte(6, pAdministrador.getEstatus());
                    ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
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
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }
    
    public static int modificar(Administrador pAdministrador) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pAdministrador);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {                
                sql = "UPDATE Usuario SET IdRol=?, Nombre=?, Apellido=?, Login=?, Estatus=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pAdministrador.getIdRol());
                    ps.setString(2, pAdministrador.getNombre());  
                    ps.setString(3, pAdministrador.getApellido());
                    ps.setString(4, pAdministrador.getLogin());
                    ps.setByte(5, pAdministrador.getEstatus());
                    ps.setInt(6, pAdministrador.getId());
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
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }
    
    public static int eliminar(Administrador pAdministrador) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Usuario WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAdministrador.getId());
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
    
    static int asignarDatosResultSet(Administrador pAdministrador, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pAdministrador.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pAdministrador.setIdRol(pResultSet.getInt(pIndex)); 
        pIndex++;
        pAdministrador.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pAdministrador.setApellido(pResultSet.getString(pIndex)); 
        pIndex++;
        pAdministrador.setLogin(pResultSet.getString(pIndex)); 
        pIndex++;
        pAdministrador.setEstatus(pResultSet.getByte(pIndex)); 
        pIndex++;
        pAdministrador.setFechaRegistro(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Administrador> pUsuarios) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Administrador usuario = new Administrador();
                asignarDatosResultSet(usuario, resultSet, 0);
                pUsuarios.add(usuario);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Administrador> pAdministradores) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap(); 
            while (resultSet.next()) {
                Administrador administrador = new Administrador();
                int index = asignarDatosResultSet(administrador, resultSet, 0);
                if (rolMap.containsKey(administrador.getIdRol()) == false) {
                    Rol rol = new Rol();
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    administrador.setRol(rol); 
                } else {
                    administrador.setRol(rolMap.get(administrador.getIdRol())); 
                }
                pAdministradores.add(administrador); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Administrador obtenerPorId(Administrador pAdministrador) throws Exception {
        Administrador administrador = new Administrador();
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdministrador);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAdministrador.getId());
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (administradores.size() > 0) {
            administrador = administradores.get(0);
        }
        return administrador;
    }
    
    public static ArrayList<Administrador> obtenerTodos() throws Exception {
        ArrayList<Administrador> administradores;
        administradores = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Administrador()); 
            sql += agregarOrderBy(new Administrador());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return administradores;
    }
    
    static void querySelect(Administrador pAdministrador, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pAdministrador.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" u.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pAdministrador.getId());
            }
        }

        if (pAdministrador.getIdRol() > 0) {
            pUtilQuery.AgregarNumWhere(" u.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pAdministrador.getIdRol());
            }
        }
        
        if (pAdministrador.getNombre() != null && pAdministrador.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAdministrador.getNombre() + "%");
            }
        }

        if (pAdministrador.getApellido() != null && pAdministrador.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAdministrador.getApellido() + "%");
            }
        }

        if (pAdministrador.getLogin() != null && pAdministrador.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pAdministrador.getLogin());
            }
        }

        if (pAdministrador.getEstatus() > 0) {
            pUtilQuery.AgregarNumWhere(" u.Estatus=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pAdministrador.getEstatus());
            }
        }
    }
    
    public static ArrayList<Administrador> buscar(Administrador pAdministrador) throws Exception {
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdministrador);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pAdministrador, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pAdministrador);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pAdministrador, utilQuery);
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return administradores;
    }
    
    public static Administrador login(Administrador pAdministrador) throws Exception {
        Administrador administrador = new Administrador();
        ArrayList<Administrador> administradores = new ArrayList();
        String password = encriptarMD5(pAdministrador.getPassword());
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdministrador);
            sql += " WHERE u.Login=? AND u.Password=? AND u.Estatus=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pAdministrador.getLogin());
                ps.setString(2, password);
                ps.setByte(3, Administrador.EstatusAdministrador.ACTIVO);
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        if (administradores.size() > 0) {
            administrador = administradores.get(0);
        }
        return administrador;
    }
    
    public static int cambiarPassword(Administrador pAdministrador, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        Administrador administradorAnt = new Administrador();
        administradorAnt.setLogin(pAdministrador.getLogin());
        administradorAnt.setPassword(pPasswordAnt);
        Administrador administradorAut = login(administradorAnt);

        if (administradorAut.getId() > 0 && administradorAut.getLogin().equals(pAdministrador.getLogin())) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "UPDATE Usuario SET Password=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setString(1, encriptarMD5(pAdministrador.getPassword())); 
                    ps.setInt(2, pAdministrador.getId());
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
        } else {
            result = 0;
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result;
    }
    
    public static ArrayList<Administrador> buscarIncluirRol(Administrador pAdministrador) throws Exception {
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pAdministrador.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pAdministrador.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += RolDAL.obtenerCampos();
            sql += " FROM Usuario u";
            sql += " JOIN Rol r on (u.IdRol=r.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pAdministrador, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pAdministrador);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pAdministrador, utilQuery);
                obtenerDatosIncluirRol(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return administradores;
    }
}

