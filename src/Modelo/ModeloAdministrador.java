package Modelo;

import Logic.Activo;
import Logic.Bien;
import Logic.Dependencia;
import Logic.Funcionario;
import Logic.Solicitud;
import Logic.Transferencia;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioSolicitud;
import accesoADatos.ServicioTransferencia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloAdministrador extends Observable {
    private ServicioSolicitud servicioSolicitud;
    private ServicioTransferencia servicioTransferencia;
    public final String[] tiposSolicitud = {"Incorporación","Traslado"};
    public final String[] tiposBienes = {"Compra","Donación","Producción institucional"};
    public final Object[] VARIABLESTABLA = {"Número","Fecha","Tipo","Estado","Cantidad de Bienes","Monto Total","Origen","Destino","Ubicación","Funcionario","Autorización"};
    private String tipo;
    private Solicitud solicitud;
    private Transferencia transferencia;
    private Funcionario funcionario;

    public ModeloAdministrador(Funcionario funcionario) {
        this.funcionario = funcionario;
        tipo = tiposSolicitud[0];
    }
    
    public void setServicioSolicitud(ServicioSolicitud servicioSolicitud) {
        this.servicioSolicitud = servicioSolicitud;
    }
    
    public void setServicioTransferencia(ServicioTransferencia servicioTransferencia) {
        this.servicioTransferencia = servicioTransferencia;
    }
    
    public void buscarSolicitud(String numero) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("numero invalido"));
            }
            if(tipo.equalsIgnoreCase(tiposSolicitud[0])){
                solicitud = servicioSolicitud.buscarSolicitud(Integer.valueOf(numero));
                transferencia = null;
                if(!servicioSolicitud.buscarFuncionarioAdministrador(Integer.valueOf(numero)).getId().equals(funcionario.getId())){
                    solicitud = null;
                    throw (new Exception("No se encontro la solicitud"));
                }
            }else if(tipo.equalsIgnoreCase(tiposSolicitud[1])){
                transferencia = servicioTransferencia.buscarTransferencia(Integer.valueOf(numero));
                
                solicitud = null;
                if(!servicioTransferencia.buscarFuncionarioAdministrador(Integer.valueOf(numero)).getId().equals(funcionario.getId())){
                    transferencia = null;
                    throw (new Exception("No se encontro la transferencia"));
                }
            }
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void crearSolicitud(String numero, String fecha, String tipo, String estado, ArrayList<Bien> lista) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("Número invalido"));
            }
            if (fecha.equals("")) {
                throw (new Exception("Fecha invalida"));
            }
            if (tipo.equals("")) {
                throw (new Exception("Tipo invalido"));
            }
            if (estado.equals("")) {
                throw (new Exception("Estado invalido"));
            }
            if (funcionario == null) {
                throw (new Exception("Administrador invalido"));
            }
            if (lista == null) {
                throw (new Exception("Lista de bienes invalida"));
            }
            solicitud = new Solicitud(Integer.valueOf(numero),fecha,tipo,estado);
            solicitud.setListaBienes(lista);
            servicioSolicitud.insertarSolicitud(solicitud, funcionario);
            solicitud = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void modificarSolicitud(String numero, String fecha, String tipo, String estado, ArrayList<Bien> lista) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("Número invalido"));
            }
            if (fecha.equals("")) {
                throw (new Exception("Fecha invalida"));
            }
            if (tipo.equals("")) {
                throw (new Exception("Tipo invalido"));
            }
            if (estado.equals("")) {
                throw (new Exception("Estado invalido"));
            }
            if (lista == null) {
                throw (new Exception("Lista de bienes invalida"));
            }
            if (!solicitud.getEstado().equals("Recibida")
                    || !(servicioSolicitud.buscarFuncionarioAsignadoSolicitud(solicitud.getNumeroSolicitud()) == null)
                    || !servicioSolicitud.buscarFuncionarioAdministrador(Integer.valueOf(numero)).getId().equals(funcionario.getId())) {
                solicitud = null;
                throw (new Exception("Esta solicitud no puede ser modificada"));
            }
            solicitud = new Solicitud(Integer.valueOf(numero),fecha,tipo,estado);
            solicitud.setListaBienes(lista);
            servicioSolicitud.modificarSolicitud(solicitud);
            solicitud = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void eliminarSolicitud(boolean condicion) throws Exception {
        try {
            if (solicitud == null) {
                throw (new Exception("Número invalido"));
            }
            if (condicion || !solicitud.getEstado().equals("Recibida")
                    || !(servicioSolicitud.buscarFuncionarioAsignadoSolicitud(solicitud.getNumeroSolicitud()) == null)
                    || !servicioSolicitud.buscarFuncionarioAdministrador(solicitud.getNumeroSolicitud()).getId().equals(funcionario.getId())) {
                throw (new Exception("Eliminación cancelada"));
            }
            servicioSolicitud.eliminarSolicitud(solicitud.getNumeroSolicitud());
            solicitud = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void crearTransferencia(String numero, Dependencia origen, Dependencia destino, String ubicacion, Funcionario funcionario, ArrayList<Activo> lista) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("Número invalido"));
            }
            if (origen == null) {
                throw (new Exception("Dependencia de origen invalida"));
            }
            if (destino == null || origen.getCodigo() == destino.getCodigo()) {
                throw (new Exception("Dependencia de destino invalida"));
            }
            if (ubicacion.equals("")) {
                throw (new Exception("Ubicación invalida"));
            }
            if (funcionario == null) {
                throw (new Exception("Funcionario invalido"));
            }
            if (lista == null) {
                throw (new Exception("Lista de activos invalida"));
            }
            transferencia = new Transferencia(Integer.valueOf(numero),origen,destino,ubicacion,funcionario);
            servicioTransferencia.insertarTransferencia(transferencia, this.funcionario);
            transferencia = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void modificarTransferencia(String numero, Dependencia origen, Dependencia destino, String ubicacion, Funcionario funcionario, ArrayList<Activo> lista) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("Número invalido"));
            }
            if (origen == null) {
                throw (new Exception("Dependencia de origen invalida"));
            }
            if (destino == null || origen.getCodigo() == destino.getCodigo()) {
                throw (new Exception("Dependencia de destino invalida"));
            }
            if (ubicacion.equals("")) {
                throw (new Exception("Ubicación invalida"));
            }
            if (funcionario == null) {
                throw (new Exception("Funcionario invalido"));
            }
            if (lista == null) {
                throw (new Exception("Lista de activos invalida"));
            }
            if (!transferencia.getAutorizacion().equals("Recibida")
                    || !servicioTransferencia.buscarFuncionarioAdministrador(Integer.valueOf(numero)).getId().equals(funcionario.getNombre())) {
                solicitud = null;
                throw (new Exception("Esta solicitud no puede ser modificada"));
            }
            transferencia = new Transferencia(Integer.valueOf(numero),origen,destino,ubicacion,funcionario);
            servicioTransferencia.modificarTransferencia(transferencia);
            transferencia = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void eliminarTransferencia(boolean condicion) throws Exception {
        try {
            if (transferencia == null) {
                throw (new Exception("Número invalido"));
            }
            if (condicion || !transferencia.getAutorizacion().equals("Recibida")
                    || !servicioTransferencia.buscarFuncionarioAdministrador(transferencia.getNumero()).getId().equals(funcionario.getNombre())) {
                throw (new Exception("Eliminación cancelada"));
            }
            servicioTransferencia.eliminarTransferencia(transferencia.getNumero());
            transferencia = null;
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
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public ArrayList<Object> getListaSolicitudes() {
        ArrayList<Object> list = new ArrayList();
        try {
            if (tipo.equals(tiposSolicitud[0])) {
                if (solicitud == null) {
                    Iterator<Solicitud> ite = servicioSolicitud.listarSolicitudes().iterator();
                    while (ite.hasNext()) {
                        Solicitud solicitud = ite.next();
                        if (servicioSolicitud.buscarFuncionarioAdministrador(solicitud.getNumeroSolicitud()).getId().equals(funcionario.getId())){
                            Object[] fila = new Object[6];
                            fila[0] = solicitud.getNumeroSolicitud();
                            fila[1] = solicitud.getFecha();
                            fila[2] = solicitud.getTipo();
                            fila[3] = solicitud.getEstado();
                            fila[4] = solicitud.getCantiadadBienes();
                            fila[5] = solicitud.getMontoTotal();
                            list.add(fila);
                        }
                    }
                } else {
                    Object[] fila = new Object[6];
                    fila[0] = solicitud.getNumeroSolicitud();
                    fila[1] = solicitud.getFecha();
                    fila[2] = solicitud.getTipo();
                    fila[3] = solicitud.getEstado();
                    fila[4] = solicitud.getCantiadadBienes();
                    fila[5] = solicitud.getMontoTotal();
                    list.add(fila);
                }
            } else if (tipo.equals(tiposSolicitud[1])) {
                if (transferencia == null) {
                    Iterator<Transferencia> ite = servicioTransferencia.listarTransferencia().iterator();
                    while (ite.hasNext()) {
                        Transferencia transferencia = ite.next();
                        if (servicioTransferencia.buscarFuncionarioAdministrador(transferencia.getNumero()).getId().equals(funcionario.getId())) {
                            Object[] fila = new Object[6];
                            fila[0] = transferencia.getNumero();
                            fila[1] = transferencia.getOrigen().getNombre();
                            fila[2] = transferencia.getDestino().getNombre();
                            fila[3] = transferencia.getUbicacion();
                            fila[4] = transferencia.getFuncionario().getNombre();
                            fila[5] = transferencia.getAutorizacion();
                            list.add(fila);
                        }
                    }
                } else {
                    Object[] fila = new Object[6];
                    fila[0] = transferencia.getNumero();
                    fila[1] = transferencia.getOrigen().getNombre();
                    fila[2] = transferencia.getDestino().getNombre();
                    fila[3] = transferencia.getUbicacion();
                    fila[4] = transferencia.getFuncionario().getNombre();
                    fila[5] = transferencia.getAutorizacion();
                    list.add(fila);
                }
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

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        this.notifyObservers();
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
        this.notifyObservers();
    }
    
    public Funcionario funcionarioAsociado() throws Exception {
        Funcionario aux = null;
        try{
            aux = servicioSolicitud.buscarFuncionarioAsignadoSolicitud(solicitud.getNumeroSolicitud());
        }catch(Exception ex){
            throw(new Exception(ex.getMessage()));
        }
        return aux;
    }
    
    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers(getListaSolicitudes());
    }

    public void limpiar() {
        solicitud = null;
        transferencia = null;
        this.setChanged();
        this.notifyObservers();
    }
}