/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Logic.Bien;
import Logic.Funcionario;
import accesoADatos.ServicioFuncionario;
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
    public Funcionario funcionario;

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
            if (cantidad.equals("")) {
                throw (new Exception("Cantidad invalida"));
            }
            listaBienes.add(new Bien(serial, descripcion, modelo, marca, Integer.valueOf(precio), Integer.valueOf(cantidad)));
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    public void eliminarBien(Bien bien) throws Exception {
        try {
            listaBienes.remove(bien);
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
                    listaBienes.set(ite.previousIndex(), new Bien(serial, descripcion, modelo, marca, Integer.valueOf(precio), Integer.valueOf(cantidad)));
                    break;
                }
            }
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
                bien = d;
                break;
            }
            this.notifyObservers();
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }
    
    public void buscarFuncionario(String id) throws Exception {
        try {
            if (id.equals("")) {
                throw (new Exception("ID invalido"));
            }
            Funcionario aux = servicioFuncionario.consultarFuncionario_Nombre_ID(id);
            if (!aux.getPuesto().equals("Registrador")) {
                throw (new Exception("El funcionario debe ser un registrador"));
            }
            funcionario = aux;
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

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
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
        listaBienes.removeAll(listaBienes);
        this.setChanged();
        this.notifyObservers();
    }
}
