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
    
    public void setServicioDependencia(ServicioDependencia servicioDependencia) {
        this.servicioDependencia = servicioDependencia;
    }
    
    public void setServicioBien(ServicioBien servicioBien) {
        this.servicioBien = servicioBien;
    }

    public void eliminarActivo(boolean condicion) throws Exception {
        try {
            if (activo == null) {
                throw (new Exception("Código invalido"));
            }
            if (condicion) {
                throw (new Exception("Eliminación cancelada"));
            }
            listaActivos.remove(activo);
            servicioActivo.eliminarActivo(activo.getCodigoActivo());
            activo = null;
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
     public void insertarActivo(String codigo) throws Exception {
        try {
            if (codigo.equals("")) {
                throw (new Exception("Código invalido"));
            }
            activo = servicioActivo.buscarActivo(Integer.valueOf(codigo));
            if (activo == null) {
                throw (new Exception("Código no encontrado"));
            }
            listaActivos.add(activo);
            activo = null;
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    public void buscarActivo(String codigo) throws Exception {
        try {
            if (codigo.equals("")) {
                throw (new Exception("Código invalido"));
            }
            Iterator<Activo> ite = listaActivos.iterator();
            while (ite.hasNext()) {
                Activo d = ite.next();
                if(d.getCodigoActivo() == Integer.valueOf(codigo)){
                  activo = d;
                  break;
                }
            }
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
            Object[] fila = {activo.getCodigoActivo(), activo.getBien().getDescripcion(), activo.getFuncionario().getNombre(), activo.getUbicacion()};
            list.add(fila);
        } else {
            Iterator<Activo> ite = listaActivos.iterator();
            while (ite.hasNext()) {
                Activo activo = ite.next();
                Object[] fila = {activo.getCodigoActivo(), activo.getBien().getDescripcion(), activo.getFuncionario().getNombre(), activo.getUbicacion()};
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
        this.notifyObservers();
    }

    public Dependencia getOrigen() {
        return origen;
    }

    public void setOrigen(Dependencia origen) {
        this.origen = origen;
        this.notifyObservers();
    }

    public Dependencia getDestino() {
        return destino;
    }

    public void setDestino(Dependencia destino) {
        this.destino = destino;
        this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
        this.setChanged();
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
