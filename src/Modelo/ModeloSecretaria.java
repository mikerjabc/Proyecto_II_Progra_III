/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Logic.Bien;
import Logic.Funcionario;
import Logic.Solicitud;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioSolicitud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MikerJABC
 */
public class ModeloSecretaria extends Observable {

    private ServicioSolicitud servicioSolicitud;
    private Solicitud solicitud;
    private Funcionario funcionario;

    public ModeloSecretaria(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    public void setServicioSolicitud(ServicioSolicitud servicioDependencia) {
        this.servicioSolicitud = servicioDependencia;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void cambiarEstadoSolicitud(String estado, String detalle) throws Exception {
        try {
            if (solicitud == null) {
                throw (new Exception("Solicitud invalida"));
            }
            if(!detalle.equals("")){
            String aux = estado + '(' + detalle + ')';
            solicitud.setEstado(aux);
            }else{
                solicitud.setEstado(estado);
            }
            servicioSolicitud.modificarSolicitud(solicitud);
            solicitud = null;
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarSolicitud(String numero) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("Número invalido"));
            }
            solicitud = servicioSolicitud.buscarSolicitud(Integer.valueOf(numero));
            if (!solicitud.getEstado().equals("Recibida")) {
                solicitud = null;
                throw (new Exception("Solicitud no encontrada"));
            }
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public Bien buscarBien(String serial) throws Exception {
        Bien aux = null;
        try {
            if (solicitud == null) {
                throw (new Exception("Solicitud invalida"));
            }
            if (serial.equals("")) {
                throw (new Exception("ID invalido"));
            }
            if(solicitud != null){
                Iterator<Bien> ite = solicitud.getListaBienes().iterator();
                while (ite.hasNext()) {
                    Bien d = ite.next();
                    if (d.getSerial().equalsIgnoreCase(serial)) {
                        aux = d;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
        return aux;
    }
    
    public ArrayList<Object> getListaSolicitudes() {
        ArrayList<Object> list = new ArrayList();
        try {
            if (solicitud == null) {
                Iterator<Solicitud> ite = servicioSolicitud.listarSolicitudes().iterator();
                while (ite.hasNext()) {
                    Solicitud solicitud = ite.next();
                    if (solicitud.getEstado().equalsIgnoreCase("Recibida")) {
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
                if (solicitud.getEstado().equalsIgnoreCase("Recibida")) {
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
        } catch (GlobalException | NoDataException ex) {
        } catch (SQLException ex) {
            Logger.getLogger(ModeloRecurHumanos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    @Override
    public void notifyObservers() {
        super.notifyObservers(getListaSolicitudes());
    }

    public void limpiar() {
        solicitud = null;
        this.setChanged();
        this.notifyObservers();
    }
}