/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Logic.Activo;
import Logic.Dependencia;
import Logic.Funcionario;
import accesoADatos.ServicioActivo;
import accesoADatos.ServicioBien;
import accesoADatos.ServicioDependencia;
import accesoADatos.ServicioFuncionario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class ModeloTransferencia extends Observable {

    private ArrayList<Activo> listaActivos;
    private Activo activo;
    public final String[] tiposEstadoTransferencia = {"Recibida", "Aceptada", "Rechazada"};
    public ServicioActivo servicioActivo;
    public ServicioDependencia servicioDependencia;
    private ServicioFuncionario servicioFuncionario;
    private ServicioBien servicioBien;
    private Funcionario funcionario;
    private Funcionario responsable;
    private Dependencia origen;
    private Dependencia destino;

    public ModeloTransferencia(Funcionario funcionario) {
        this.funcionario = funcionario;
        listaActivos = new ArrayList();
        responsable = null;
        origen = null;
        destino = null;
    }
    
    public void setServicioActivo(ServicioActivo servicioActivo){
        this.servicioActivo = servicioActivo;
    }
    
    public void setServicioFuncionario(ServicioFuncionario servicioFuncionario) {
        this.servicioFuncionario = servicioFuncionario;
    }
    
    public void setServicioBien(ServicioDependencia servicioDependencia) {
        this.servicioDependencia = servicioDependencia;
    }
    
    public void setServicioDependencia(ServicioBien servicioBien) {
        this.servicioBien = servicioBien;
    }

    public void eliminarActivo(Activo activo) throws Exception {
        try {
            listaActivos.remove(activo);
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    public void buscarActivo(String codigo) throws Exception {
        try {
            if (codigo.equals("")) {
                throw (new Exception("Serial invalido"));
            }
            Iterator<Activo> ite = listaActivos.iterator();
            while (ite.hasNext()) {
                Activo d = ite.next();
                if(d.getCodigoActivo() == Integer.valueOf(codigo)){
                  activo = d;
                  break;
                }
            }
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarResponsable(String id) throws Exception {
        try {
            if (id.equals("")) {
                throw (new Exception("ID invalido"));
            }
            Funcionario aux = servicioFuncionario.consultarFuncionario_Nombre_ID(id);
            responsable = aux;
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarOrigen(String numero) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("ID invalido"));
            }
            origen = servicioDependencia.buscarDependencia(Integer.valueOf(numero));
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarDestino(String numero) throws Exception {
        try {
            if (numero.equals("")) {
                throw (new Exception("ID invalido"));
            }
            destino = servicioDependencia.buscarDependencia(Integer.valueOf(numero));
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    private ArrayList<Object> getListaActivo() {
        ArrayList<Object> list = new ArrayList();
        if (activo != null) {
            Object[] fila = {activo.getCodigoActivo(), activo.getBien().getDescripcion(), activo.getFuncionario(), activo.getUbicacion()};
            list.add(fila);
        } else {
            Iterator<Activo> ite = listaActivos.iterator();
            while (ite.hasNext()) {
                Activo activo = ite.next();
                Object[] fila = {activo.getCodigoActivo(), activo.getBien().getDescripcion(), activo.getFuncionario(), activo.getUbicacion()};
                list.add(fila);
            }
        }
        return list;
    }

    public ArrayList<Activo> getListaActivos() {
        return listaActivos;
    }

    public void setListaActivos(ArrayList<Activo> listaActivos) {
        this.listaActivos = listaActivos;
        this.setChanged();
        this.notifyObservers();
    }

    public Activo getActivo() {
        return activo;
    }

    public Funcionario getResponsable() {
        return responsable;
    }

    public void setResponsable(Funcionario responsable) {
        this.responsable = responsable;
    }

    public Dependencia getOrigen() {
        return origen;
    }

    public void setOrigen(Dependencia origen) {
        this.origen = origen;
    }

    public Dependencia getDestino() {
        return destino;
    }

    public void setDestino(Dependencia destino) {
        this.destino = destino;
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers(getListaActivo());
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public void limpiar() {
        activo = null;
        responsable = null;
        origen = null;
        destino = null;
        listaActivos.removeAll(listaActivos);
        this.setChanged();
        this.notifyObservers();
    }
}
