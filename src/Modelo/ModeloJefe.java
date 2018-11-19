
package Modelo;

import Logic.Bien;
import Logic.Funcionario;
import Logic.Solicitud;
import Logic.Transferencia;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioFuncionario;
import accesoADatos.ServicioSolicitud;
import accesoADatos.ServicioTransferencia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloJefe extends Observable {
    private ServicioSolicitud servicioSolicitud;
    private ServicioTransferencia servicioTransferencia;
    private ServicioFuncionario servicioFuncionario;
    public final String[] tiposSolicitud = {"Incorporación","Traslado"};
    public final String[] tiposBienes = {"Compra","Donación","Producción institucional"};
    public final Object[] VARIABLESTABLA = {"Número","Fecha","Tipo","Estado","Cantidad de Bienes","Monto Total","Origen","Destino","Ubicación","Funcionario","Autorización"};
    private String tipo;
    private Solicitud solicitud;
    private Transferencia transferencia;
    private Funcionario funcionario;

    public ModeloJefe(Funcionario funcionario) {
        this.funcionario = funcionario;
        tipo = tiposSolicitud[0];
    }
    
    public void setServicioSolicitud(ServicioSolicitud servicioDependencia) {
        this.servicioSolicitud = servicioDependencia;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void setServicioTransferencia(ServicioTransferencia servicioTransferencia) {
        this.servicioTransferencia = servicioTransferencia;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void setServicioFuncionario(ServicioFuncionario servicioFuncionario) {
        this.servicioFuncionario = servicioFuncionario;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void buscarSolicitud(String numero) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("numero invalido"));
            }
            if(tipo.equalsIgnoreCase(tiposSolicitud[0])){
                solicitud = servicioSolicitud.buscarSolicitud(Integer.valueOf(numero));
                transferencia = null;
                if(!solicitud.getEstado().equals("Por verificar")){
                    solicitud = null;
                    throw (new Exception("No se encontro la solicitud"));
                }
            }else if(tipo.equalsIgnoreCase(tiposSolicitud[1])){
                transferencia = servicioTransferencia.buscarTransferencia(Integer.valueOf(numero));
                solicitud = null;
                if(!transferencia.getAutorizacion().equals("Recibida")){
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
    
    public void AsignarRegistradorASolicitud(String numero, Funcionario registrador) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("numero invalido"));
            }
            if (registrador == null) {
                throw (new Exception("Registrador invalido"));
            }
            servicioSolicitud.asignarSolicitud(solicitud, registrador);
            solicitud = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void AutorizarTransferencia(String estado, String detalle) throws Exception {
        try {
            if (transferencia == null) {
                throw (new Exception("Transferencia invalida invalido"));
            }
            if (!detalle.equals("")) {
                String aux = estado + '(' + detalle + ')';
                transferencia.setAutorizacion(aux);
            } else {
                transferencia.setAutorizacion(estado);
            }
            servicioTransferencia.modificarTransferencia(transferencia);
            transferencia = null;
            this.setChanged();
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
            this.setChanged();
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
                        if (solicitud.getEstado().equalsIgnoreCase("por verificar") && servicioSolicitud.buscarFuncionarioAsignadoSolicitud(solicitud.getNumeroSolicitud()) == null) {
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
                        if (transferencia.getAutorizacion().equalsIgnoreCase("Recibida")) {
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
        super.notifyObservers(getListaSolicitudes());
    }

    public void limpiar() {
        solicitud = null;
        transferencia = null;
        this.setChanged();
        this.notifyObservers();
    }
}
