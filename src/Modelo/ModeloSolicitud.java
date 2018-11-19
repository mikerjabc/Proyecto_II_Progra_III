/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Logic.Bien;
import Logic.Funcionario;
import accesoADatos.ServicioFuncionario;
import accesoADatos.ServicioSolicitud;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Observable;

/**
 *
 * @author MikerJABC
 */
public class ModeloSolicitud extends Observable {

    private ServicioFuncionario servicioFuncionario;
    private ArrayList<Bien> listaBienes;
    private Bien bien;
    public final String[] tiposBien = {"Compra", "Donación", "Producción institucional"};
    public final String[] tiposEstadoSolicitud = {"Recibida", "Por verificar", "Rechazada", "Espera de rotulación", "Procesada"};
    private Funcionario funcionario;
    private Funcionario registrador;
    private int cantidad;
    private float monto;

    public ModeloSolicitud(Funcionario funcionario) {
        listaBienes = new ArrayList();
        bien = null;
        this.funcionario = funcionario;
    }
    
    public void setServicioFuncionario(ServicioFuncionario servicioFuncionario) {
        this.servicioFuncionario = servicioFuncionario;
    }
    
    public void agregarBien(String serial, String descripcion, String modelo, String marca, String precio, String cantidad) throws Exception {
        try {
            if (serial.equals("")) {
                throw (new Exception("Serial invalido"));
            }
            if (descripcion.equals("")) {
                throw (new Exception("Descripción invalida"));
            }
            if (modelo.equals("")) {
                throw (new Exception("Modelo invalido"));
            }
            if (marca.equals("")) {
                throw (new Exception("Marca invalida"));
            }
            if (precio.equals("")) {
                throw (new Exception("Precio invalido"));
            }
            bien = new Bien(serial, descripcion, modelo, marca, Float.valueOf(precio), Integer.valueOf(cantidad));
            listaBienes.add(bien);
            bien = null;
            this.cantidadymonto();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    public void eliminarBien() throws Exception {
        try {
            if (bien == null) {
                throw (new Exception("Serial invalido"));
            }
            listaBienes.remove(bien);
            bien = null;
            this.cantidadymonto();
            this.setChanged();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    public void modificarBien(String serial, String descripcion, String modelo, String marca, String precio, String cantidad) throws Exception {
        try {
            if (serial.equals("")) {
                throw (new Exception("Serial invalido"));
            }
            if (descripcion.equals("")) {
                throw (new Exception("Descripción invalida"));
            }
            if (modelo.equals("")) {
                throw (new Exception("Modelo invalido"));
            }
            if (marca.equals("")) {
                throw (new Exception("Marca invalida"));
            }
            if (precio.equals("")) {
                throw (new Exception("Precio invalido"));
            }
            if (cantidad.equals("")) {
                throw (new Exception("Cantidad invalida"));
            }
            ListIterator<Bien> ite = listaBienes.listIterator();
            while (ite.hasNext()) {
                Bien d = ite.next();
                if (bien.getSerial().equals(d.getSerial())) {
                    listaBienes.set(ite.previousIndex(), new Bien(serial, descripcion, modelo, marca, Float.valueOf(precio), Integer.valueOf(cantidad)));
                    break;
                }
            }
            this.cantidadymonto();
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }

    }

    public void buscarBien(String serial) throws Exception {
        try {
            if (serial.equals("")) {
                throw (new Exception("Serial invalido"));
            }
            Iterator<Bien> ite = listaBienes.iterator();
            while (ite.hasNext()) {
                Bien d = ite.next();
                if (d.getSerial().equals(serial)) {
                    bien = d;
                     break;
                }
            }
            if(bien == null){
                throw (new Exception("Solicitud no encontrada"));
            }
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarRegistrador(String id) throws Exception {
        try {
            if (id.equals("")) {
                throw (new Exception("ID invalido"));
            }
            Funcionario aux = servicioFuncionario.consultarFuncionario_Nombre_ID(id);
            if (!aux.getPuesto().equals("Registrador")) {
                throw (new Exception("El funcionario debe ser un registrador"));
            }
            registrador = aux;
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    private ArrayList<Object> getListaBien() {
        ArrayList<Object> list = new ArrayList();
        if (bien != null) {
            Object[] fila = {bien.getSerial(), bien.getDescripcion(), bien.getMarca(), bien.getModelo(), bien.getPrecio(), bien.getCantidad()};
            list.add(fila);
        } else {
            Iterator<Bien> ite = listaBienes.iterator();
            while (ite.hasNext()) {
                Bien bien = ite.next();
                Object[] fila = {bien.getSerial(), bien.getDescripcion(), bien.getMarca(), bien.getModelo(), bien.getPrecio(), bien.getCantidad()};
                list.add(fila);
            }
        }
        return list;
    }

    public ArrayList<Bien> getListaBienes() {
        return listaBienes;
    }

    public void setListaBienes(ArrayList<Bien> listaBienes) {
        this.listaBienes = listaBienes;
        this.notifyObservers();
    }

    public Bien getBien() {
        return bien;
    }

    public Funcionario getRegistrador() {
        return registrador;
    }

    public void setRegistrador(Funcionario registrador) {
        this.registrador = registrador;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getMonto() {
        return monto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers(getListaBien());
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public void limpiar() {
        bien = null;
        registrador = null;
        listaBienes.removeAll(listaBienes);
        this.setChanged();
        this.notifyObservers();
    }

    public int getNumeroSolicitud() {
        int aux = 0;
        try{
           aux = ServicioSolicitud.getServicioSolicitud().listarSolicitudes().size();
        }catch(Exception ex){
            
        }
        return aux + 1;
    }
    
    public void cantidadymonto() {
        cantidad = 0;
        monto = 0;
        Iterator<Bien> it = listaBienes.iterator();
        while (it.hasNext()) {
            Bien item = it.next();
            cantidad += item.getCantidad();
            monto += (item.getPrecio() * item.getCantidad());
        }
    }

    public void setBien() {
        bien = null;
    }
}
