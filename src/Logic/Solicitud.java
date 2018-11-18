/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Observable;

public class Solicitud extends Observable {

    private int numeroSolicitud;
    private String fecha;
    private String tipo;
    private String estado;
    private int cantidadBienes;
    public float montoTotal;
    private ArrayList<Bien> listaBienes;

    public Solicitud(int numeroSolicitud, String fecha, String tipo, String estado) {
        this.numeroSolicitud = numeroSolicitud;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantiadadBienes() {
        return cantidadBienes;
    }

    public void setCantiadadBienes(int cantiadadBienes) {
        this.cantidadBienes = cantiadadBienes;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public ArrayList<Bien> getListaBienes() {
        return listaBienes;
    }

    public void setListaBienes(ArrayList<Bien> listaBienes) {
        this.listaBienes = listaBienes;
    }

//    public int cantlistaBienes(ArrayList<Bien> listaBienes) {
//        int cantidad = 0;
//        ArrayList<Bien> list = listaBienes;
//
//        for (int i = 0; i < list.size(); i++) {
//            cantidad = cantidad + list.get(i).getCantidad();
//        }
//        return cantidad;
//    }
//    
//     public float montoTotalBienes(ArrayList<Bien> listaBienes) {
//        float monto = 0;
//        ArrayList<Bien> list = listaBienes;
//
//        for (int i = 0; i < list.size(); i++) {
//            monto = monto + (list.get(i).getPrecio()*list.get(i).getCantidad());
//
//        }
//        return monto;
//    }
//    public void cantidadymonto(ArrayList<Bien> listaBienes) {
//        int cantidad = 0;
//        float monto = 0;
//        ArrayList<Bien> list = listaBienes;
//
//        for (int i = 0; i < list.size(); i++) {
//            
//            cantidad = cantidad + list.get(i).getCantidad();
//            monto = monto + (list.get(i).getPrecio() * list.get(i).getCantidad());
//            cantidad++;
//        }
//        this.setCantiadadBienes(cantidad);
//        this.setMontoTotal(monto);
//    }

//     public void cantidadymonto(ArrayList<Bien> listaBienes) {
//      
//        ArrayList<Bien> list = listaBienes;
//cantidadBienes=7;
//        for (int i = 0; i < list.size(); i++) {
//            
//           cantidadBienes= cantidadBienes + list.get(i).getCantidad();
//            montoTotal = montoTotal + (list.get(i).getPrecio() * list.get(i).getCantidad());
////            cantidad++;
//
//        }
////        this.setCantiadadBienes(cantidad);
////        this.setMontoTotal(monto);
//    }
    
    public void cantidadymonto(ArrayList<Bien> listaBienes) {
    Iterator<Bien> it = listaBienes.iterator();
// mientras al iterador queda proximo juego
while(it.hasNext()){
    Bien item=it.next();
    
    cantidadBienes= cantidadBienes + item.getCantidad();
            montoTotal = montoTotal + (item.getPrecio() * item.getCantidad());
}}
    
    
    
}
