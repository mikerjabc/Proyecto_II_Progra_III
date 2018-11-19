package Modelo;


import Logic.Activo;
import Logic.Bien;
import Logic.Funcionario;
import Logic.Solicitud;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioActivo;
import accesoADatos.ServicioFuncionario;
import accesoADatos.ServicioSolicitud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloRegistrador extends Observable {
    private ServicioSolicitud servicioSolicitud;
    private ServicioFuncionario servicioFuncionario;
    private ServicioActivo servicioActivo;
    public final String[] tiposSolicitud = {"Incorporación","Catálogo"};
    public final Object[] VARIABLESTABLA = {"Número","Fecha","Tipo","Estado","Cantidad de Bienes","Monto Total","Código","Bien","Descripción","Funcionario","Ubicación"};
    private String tipo;
    private Solicitud solicitud;
    private Activo activo;
    private Funcionario funcionario;

    public ModeloRegistrador(Funcionario funcionario) {
        tipo = tiposSolicitud[0];
        this.funcionario = funcionario;
    }
    
    public void setServicioSolicitud(ServicioSolicitud servicioDependencia) {
        this.servicioSolicitud = servicioDependencia;
    }
    
    public void setServicioFuncionario(ServicioFuncionario servicioFuncionario) {
        this.servicioFuncionario = servicioFuncionario;
    }
    public void setServicioActivo(ServicioActivo servicioActivo) {
        this.servicioActivo = servicioActivo;
    }
    
    public void agregarActivo(int codigo, Bien bien, String descripcion, Funcionario funcionario, String ubicacion) throws Exception {
        try {
            if (codigo == 0) {
                throw (new Exception("Código invalido"));
            }
            if (bien == null) {
                throw (new Exception("Bien invalido"));
            }
            if (descripcion.equals("")) {
                throw (new Exception("Descripción invalida"));
            }
            if (funcionario == null) {
                throw (new Exception("Funcionario invalido"));
            }
            if (ubicacion.equals("")) {
                throw (new Exception("Ubicación invalida"));
            }
            Activo activo = new Activo(codigo, bien, descripcion, funcionario, ubicacion);
            servicioActivo.insertarActivo(activo, servicioFuncionario.consultarDependenciaPorFuncionario(funcionario.getId()).getCodigo());
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void modificarActivo(int codigo, Bien bien, String descripcion, Funcionario funcionario, String ubicacion) throws Exception {
        try {
            if (codigo == 0) {
                throw (new Exception("Código invalido"));
            }
            if (bien == null) {
                throw (new Exception("Bien invalido"));
            }
            if (descripcion.equals("")) {
                throw (new Exception("Descripción invalida"));
            }
            if (funcionario == null) {
                throw (new Exception("Funcionario invalido"));
            }
            if (ubicacion.equals("")) {
                throw (new Exception("Ubicación invalida"));
            }
            Activo activo = new Activo(codigo, bien, descripcion, funcionario, ubicacion);
            servicioActivo.modificarActivo(activo);
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void eliminarActivo(String codigo) throws Exception {
        try {
            servicioActivo.eliminarBien(codigo);
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarSolicitud_Activo(String numero) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("Número invalido"));
            }
            if (tipo.equalsIgnoreCase(tiposSolicitud[0])) {
                solicitud = servicioSolicitud.buscarSolicitud(Integer.valueOf(numero));
                if(!solicitud.getEstado().equals("Por verificar") || !(servicioSolicitud.buscarFuncionarioAsignadoSolicitud(solicitud.getNumeroSolicitud()) == null)){
                    activo = null;
                    solicitud = null;
                    notifyObservers();
                    throw (new Exception("No existe la solicitud"));
                }
                
            }else{
                activo = servicioActivo.buscarActivo(Integer.valueOf(numero));
                solicitud = null;
            }
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void procesarSolicitud() throws Exception {
        try {
            if (solicitud == null) {
                throw (new Exception("Solicitud invalida"));
            }
            int cant = 0;
            Iterator<Bien> ite = solicitud.getListaBienes().iterator();
                while (ite.hasNext()) {
                    if(servicioActivo.buscarActivoPorBien(ite.next().getSerial()) != null){
                        cant++;
                    }
                }
            if (solicitud.getListaBienes().size() != cant) {
                throw (new Exception("No todos los bienes han sido asignados"));
            }
            solicitud.setEstado("Procesada");
            servicioSolicitud.modificarSolicitud(solicitud);
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void cambiarTipo(String tipo) throws Exception {
        try {
            if (tipo.equals("")) {
                throw (new Exception("Debe ingresar un codigo"));
            }
            this.tipo = tipo;
            solicitud = null;
            activo = null;
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public ArrayList<Object> getListaSolicitudes() {
        ArrayList<Object> list = new ArrayList();
        try {
            if (tipo.equals(tiposSolicitud[0]) && solicitud == null) {
                Iterator<Solicitud> ite = servicioSolicitud.listarSolicitudes().iterator();
                while (ite.hasNext()) {
                    Solicitud solicitud = ite.next();
                    if (solicitud.getEstado().equalsIgnoreCase("por verificar") && servicioSolicitud.buscarFuncionarioAsignadoSolicitud(solicitud.getNumeroSolicitud()) == null) {
                        Object[] fila = {solicitud.getNumeroSolicitud(),
                            solicitud.getFecha(),
                            solicitud.getTipo(),
                            solicitud.getEstado(),
                            solicitud.getCantiadadBienes(),
                            solicitud.getMontoTotal()
                        };
                        list.add(fila);
                    }
                }
            } else if (tipo.equals(tiposSolicitud[1]) && activo == null) {
                Iterator<Activo> ite = servicioActivo.listarActivo().iterator();
                while (ite.hasNext()) {
                    Activo activo = ite.next();
                    Object[] fila = {activo.getCodigoActivo(),
                        activo.getBien().getDescripcion(),
                        activo.getDescripcionActivo(),
                        activo.getFuncionario().getNombre(),
                        activo.getUbicacion()};
                    list.add(fila);
                }
            } else if (solicitud != null) {
                Object[] fila = {solicitud.getNumeroSolicitud(),
                    solicitud.getFecha(),
                    solicitud.getTipo(),
                    solicitud.getEstado(),
                    solicitud.getCantiadadBienes(),
                    solicitud.getMontoTotal()
                };
                list.add(fila);
            } else if (activo != null) {
                Object[] fila = {activo.getCodigoActivo(),
                    activo.getBien().getDescripcion(),
                    activo.getDescripcionActivo(),
                    activo.getFuncionario().getNombre(),
                    activo.getUbicacion()};
                list.add(fila);
            }
        } catch (GlobalException | NoDataException ex) {
        } catch (SQLException ex) {
            Logger.getLogger(ModeloRecurHumanos.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return list;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Activo getActivo() {
        return activo;
    }
    
    public int numeroConsecutivoParaActivo() throws Exception {
        int aux;
        try{
           aux = servicioActivo.listarActivo().size();
        }catch(Exception ex){
            throw(new Exception(ex.getMessage()));
        }
        return aux + 1;
    }
    
    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers(getListaSolicitudes());
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setActivo(Activo activo) {
        this.activo = activo;
    }
    
    public void limpiar() {
        solicitud = null;
        activo = null;
        this.setChanged();
        this.notifyObservers();
    }
}
