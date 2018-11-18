/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Logic.Bien;
import Logic.Funcionario;
import Logic.Solicitud;
import Vista.VistaAdministrador;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioSolicitud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MikerJABC
 */
public class ModeloAdministrador extends Observable {

    /**
     * @return the elFuncionario
     */
    public Funcionario getElFuncionario() {
        return elFuncionario;
    }

    /**
     * @param elFuncionario the elFuncionario to set
     */
    public void setElFuncionario(Funcionario elFuncionario) {
        this.elFuncionario = elFuncionario;
    }
    
    private Funcionario elFuncionario;
   
    
    ArrayList<Bien> bienes  = new ArrayList<>();
    VistaAdministrador vistaAdmistrador;
        int total;
    public void updateTable(Bien bien  ){
        bienes.add(bien);
        this.vistaAdmistrador.dtm.addRow(new Object [] {bien.getDescripcion(),bien.getModelo(),bien.getSerial(),bien.getPrecio(),bien.getCantidad()  });
        
        if (this.vistaAdmistrador.getCampoMontoTotal().getText().equalsIgnoreCase(""))
            total = 0;
        else 
        total = Integer.parseInt(this.vistaAdmistrador.getCampoMontoTotal().getText());
        
        total = (int) (total + bien.getCantidad()*bien.getPrecio());
        this.vistaAdmistrador.getCampoMontoTotal().setText(""+total);
    }
 
    public void setVista(VistaAdministrador view){
        this.vistaAdmistrador = view;
    }

    
    public ArrayList<Bien> getBienes() {
        return bienes;    
    }
 
    public void setNumeroNuevoSolicitud(ArrayList<Solicitud> misSolicitudes) {
         int numeroSolicitudes = misSolicitudes.size()+1;  
         vistaAdmistrador.getCampoNumeroComprobante().setText(  ""+numeroSolicitudes );
     }
    
    public Solicitud getSolicitud(){
        Solicitud nuevaSolicitud = new Solicitud( 
                Integer.parseInt(this.vistaAdmistrador.getCampoNumeroComprobante().getText()) ,
                this.vistaAdmistrador.getCampoFecha().getText(), 
                this.vistaAdmistrador.getCampoTipoAdqui().getSelectedItem().toString(), 
                "por Revisar"
        );
        nuevaSolicitud.setListaBienes(bienes);
        return nuevaSolicitud;
    }

    public void limpiar() {
 
        this.vistaAdmistrador.getCampoFecha().setText("");
        this.vistaAdmistrador.getCampoMontoTotal().setText("");
        this.vistaAdmistrador.dtm.setRowCount(0);
        notifyObservers();
        setChanged();
        bienes.clear();
     }    

    public void setTabla() throws GlobalException {
        ArrayList<Solicitud> sol = new ArrayList<>();
        ServicioSolicitud accServicioSolicitud = new ServicioSolicitud();
        try {
            sol = accServicioSolicitud.listarSolicitudes();
        } catch (NoDataException ex) {
            Logger.getLogger(ModeloAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModeloAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < sol.size(); i++) {
            try {
                if (accServicioSolicitud.buscarFuncionarioAsignadoSolicitud(sol.get(i).getNumeroSolicitud()) != elFuncionario){
                     sol.remove(i);
                } 
            } catch (NoDataException ex) {
                Logger.getLogger(ModeloAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ModeloAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
        for (int i = 0; i < sol.size(); i++) {
            this.vistaAdmistrador.dtm.addRow(new Object [] {sol.get(i).getNumeroSolicitud(),sol.get(i).getFecha(),sol.get(i).getTipo(),sol.get(i).getEstado() });
        }
     }
    
    
}
