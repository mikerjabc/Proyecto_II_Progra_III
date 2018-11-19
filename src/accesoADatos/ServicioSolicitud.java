/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoADatos;

import Logic.Bien;
import Logic.Funcionario;
import Logic.Solicitud;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Fernando
 */
public class ServicioSolicitud extends Servicio {

    private static final String INSERTARSOLICITUD = "{call insertarSolicitud(?,?,?,?,?)}";
    private static final String ELIMINARSOLICITUD = "{call eliminarSolicitud(?)}";
    private static final String MODIFICARSOLICITUD = "{call modificarSolicitud(?,?,?,?)}";
    private static final String LISTARSOLICITUD = "{?=call listarSolicitud}";
    private static final String CONSULTARSOLICITUD = "{?=call buscarSolicitud(?)}";
    private static final String CONSULTARFUNCIONARIOSOLICITUD = "{?=call buscarfuncionarioasociado(?)}";
    private static final String ASIGNARSOLICITUD = "{call asignarsolicitudafuncionario(?,?)}";

    private static ServicioSolicitud servicioSolicitud = new ServicioSolicitud();

    public void insertarSolicitud(Solicitud laSolicitud, Funcionario elFuncionario) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(INSERTARSOLICITUD);
            pstmt.setInt(1, laSolicitud.getNumeroSolicitud());
            pstmt.setString(2, laSolicitud.getFecha());
            pstmt.setString(3, laSolicitud.getTipo());
            pstmt.setString(4, laSolicitud.getEstado());
            pstmt.setString(5, elFuncionario.getId());
            boolean resultado = pstmt.execute();
            
            Iterator<Bien> ite = laSolicitud.getListaBienes().iterator();
            while(ite.hasNext()){
                ServicioBien.getServicioBien().insertarBien(ite.next(), laSolicitud.getNumeroSolicitud());
            }
            
            
            if (resultado == true) {
                throw new NoDataException("No se realizo la insercion");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public void eliminarSolicitud(int elNumero) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conexion.prepareCall(ELIMINARSOLICITUD);
            pstmt.setInt(1, elNumero);
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

    }

    public void modificarSolicitud(Solicitud laSolicitud) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(MODIFICARSOLICITUD);
            pstmt.setInt(1, laSolicitud.getNumeroSolicitud());
            pstmt.setString(2, laSolicitud.getFecha());
            pstmt.setString(3, laSolicitud.getTipo());
            pstmt.setString(4, laSolicitud.getEstado());
            boolean resultado = pstmt.execute();
            
            
            Iterator<Bien> ite = laSolicitud.getListaBienes().iterator();
            while(ite.hasNext()){
                ServicioBien.getServicioBien().modificarBien(ite.next());
            }

            if (resultado == true) {
                throw new NoDataException("No se realizo la modificacion");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("-------------Llave no existe");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public void asignarSolicitud(Solicitud laSolicitud, Funcionario elRegistrador) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(ASIGNARSOLICITUD);
            pstmt.setInt(1, laSolicitud.getNumeroSolicitud());
            pstmt.setString(2, elRegistrador.getId());

            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la modificacion");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("-------------Llave no existe");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public Funcionario buscarFuncionarioAsignadoSolicitud(int numero) throws GlobalException, NoDataException, SQLException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        Funcionario elFuncionario = null;

        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(CONSULTARFUNCIONARIOSOLICITUD);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, numero);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                elFuncionario = ServicioFuncionario.getServicioFuncionario().consultarFuncionario(rs.getString("registrador"));
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        return elFuncionario;
    }

    public Funcionario buscarFuncionarioAdministrador(int numero) throws GlobalException, NoDataException, SQLException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        Funcionario elFuncionario = null;

        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(CONSULTARFUNCIONARIOSOLICITUD);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, numero);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                elFuncionario = ServicioFuncionario.getServicioFuncionario().consultarFuncionario(rs.getString("administrador"));
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        return elFuncionario;
    }

    public ArrayList<Solicitud> listarSolicitudes() throws GlobalException, NoDataException, SQLException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        Solicitud laSolicitud = null;
        ArrayList<Solicitud> coleccion = new ArrayList();

        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTARSOLICITUD);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {
                laSolicitud = new Solicitud(
                        rs.getInt("numerosolicitud"),
                        rs.getString("fecha"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                );
                laSolicitud.setListaBienes(ServicioBien.getServicioBien().buscarBienPorSolicitud(rs.getInt("numerosolicitud")));
                laSolicitud.cantidadymonto();
                coleccion.add(laSolicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null) {
            throw new NoDataException("No hay datos");
        }
        return coleccion;
    }

    public Solicitud buscarSolicitud(int numero) throws GlobalException, NoDataException, SQLException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        Solicitud laSolicitud = null;

        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(CONSULTARSOLICITUD);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, numero);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                laSolicitud = new Solicitud(
                        rs.getInt("numerosolicitud"),
                        rs.getString("fecha"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                );
                laSolicitud.setListaBienes(ServicioBien.getServicioBien().buscarBienPorSolicitud(rs.getInt("numerosolicitud")));
                laSolicitud.cantidadymonto();
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (laSolicitud == null) {
            throw new NoDataException("No existe una solicitud con este número");
        }
        return laSolicitud;
    }

    public static ServicioSolicitud getServicioSolicitud() {
        return servicioSolicitud;
    }
}
