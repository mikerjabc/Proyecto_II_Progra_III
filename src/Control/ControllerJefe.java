/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ModeloJefe;
import Modelo.ModeloSolicitud;
import Modelo.ModeloTransferencia;
import Vista.VistaJefe;
import Vista.VistaSolicitud;
import Vista.VistaTransferencia;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioFuncionario;
import accesoADatos.ServicioSolicitud;
import accesoADatos.ServicioTransferencia;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTable;

/**
 *
 * @author Fernando
 */
public class ControllerJefe extends AbstractController implements ItemListener {

    private ModeloJefe modelo;
    private VistaJefe vista;
    private VistaSolicitud vistaSolicitud;
    private VistaTransferencia vistaTrasferencia;
    private ModeloSolicitud modeloSolicitud;
    private ModeloTransferencia modeloTrasferencia;

    public ControllerJefe(ModeloJefe modelo, VistaJefe vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setModelo(modelo);
        vista.setControlador(this);
        modelo.setServicioSolicitud(ServicioSolicitud.getServicioSolicitud());
        modelo.setServicioTransferencia(ServicioTransferencia.getServicioTransferencia());
        modelo.setServicioFuncionario(ServicioFuncionario.getServicioFuncionario());
        ajustarVista();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource().getClass() == JButton.class) {
                JButton button = (JButton) ae.getSource();
                instrucciones(button.getName());
            }
            if (ae.getSource().getClass() == JMenuItem.class) {
                JMenuItem button = (JMenuItem) ae.getSource();
                instrucciones(button.getName());
            }
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    public void ajustarVista() {
        vistaSolicitud = new VistaSolicitud();
        vistaTrasferencia = new VistaTransferencia();
        vistaTrasferencia.setControlador(this);
        vistaSolicitud.setControlador(this);
        modeloSolicitud = null;
        //modeloSolicitud = new ModeloSolicitud();
        modeloTrasferencia = new ModeloTransferencia(modelo.getFuncionario());
        vistaTrasferencia.setModelo(modeloTrasferencia);
        vistaSolicitud.addWindowListener(this);
        vistaTrasferencia.addWindowListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent ae) {
        String numero;
        try {
            if (ae.getSource().getClass() == JTable.class) {
                JTable tabla = (JTable) ae.getSource();
                numero = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
                tabla.changeSelection(tabla.getSelectedRow(), 0, false, true);
                modelo.buscarSolicitud(numero);
                if (ae.getClickCount() == 1) {
                    switch (ae.getButton()) {
                        case MouseEvent.BUTTON2: {//Click derecho
                            //instrucciones("eliminar");
                        }
                        break;
                        case MouseEvent.BUTTON1: {//Click izquierdo
                            if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                                modeloSolicitud = new ModeloSolicitud(modelo.getFuncionario());
                                modeloSolicitud.setRegistrador(modelo.funcionarioAsociado());
                                modeloSolicitud.setServicioFuncionario(ServicioFuncionario.getServicioFuncionario());
                                vistaSolicitud.setModelo(modeloSolicitud);
                                vistaSolicitud.cargarDatos(modelo.getSolicitud());
                                vistaSolicitud.setVisible(true);
                            } else {
                                vistaTrasferencia.cargarDatos(modelo.getTransferencia());
                                vistaTrasferencia.setVisible(true);
                            }
                        }
                        break;
                    }
                }
            }
        } catch (GlobalException | NoDataException ex) {
            vista.mostrarMensaje(ex.getMessage());
        } catch (Exception ex1) {
            vista.mostrarMensaje(ex1.getMessage());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mostrarVista() {
        vista.setVisible(true);
    }

    @Override
    public void ocultarVista() {
        vista.setVisible(false);
    }

    public void instrucciones(String x) throws Exception {
        try {
            switch (x.toLowerCase()) {
                case "agregar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        modelo.AsignarRegistradorASolicitud(vistaSolicitud.jtfNumero.getText(), modeloSolicitud.getRegistrador());
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.mostrarMensaje("Se asigno un registrador a la solicitud");
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose();
                    } else if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[1])) {
                        String aux = "";
                        if (vistaTrasferencia.jcbEstado.getModel().getSelectedItem().toString().equals("Rechazada")) {
                            aux = vistaSolicitud.preguntarDetalle("Al estar la transferencia en estado Rechazada debe ingresar un detalle del rechazo.");
                        }
                        if (aux != null || vistaTrasferencia.jcbEstado.getModel().getSelectedItem().toString().equals("Recibida")) {
                            modelo.AutorizarTransferencia(vistaTrasferencia.jcbEstado.getModel().getSelectedItem().toString(),aux);
                            vistaTrasferencia.mostrarMensaje("Se guardo el cambio en el estado de la trasferencia");
                            vistaTrasferencia.setVisible(false);
                            vistaTrasferencia.limpiarTodosEspacios();
                            vistaTrasferencia.dispose();
                        }
                    }
                }
                break;
                case "buscar": {
                    modeloTrasferencia.limpiar();
                    if(modeloSolicitud != null){
                        modeloSolicitud.limpiar();
                    }
                    modelo.limpiar();
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        if(modelo.getSolicitud() == null){
                            modelo.buscarSolicitud(vista.jtIdBuscar.getText());
                            vista.mostrarMensaje("Se encontro la solicitud");
                        }else{
                            modeloSolicitud.buscarBien(vistaSolicitud.jtfIdBuscar.getText());
                            vistaSolicitud.mostrarMensaje("Se encontro el bien");
                        }
                    } else {
                        if(modelo.getTransferencia()== null){
                            modelo.buscarSolicitud(vista.jtIdBuscar.getText());
                            vista.mostrarMensaje("Se encontro la transferencia");
                        }else{
                            modeloTrasferencia.buscarActivo(vistaTrasferencia.jtfCodigoBuscar.getText());
                            vistaTrasferencia.mostrarMensaje("Se encontro el activo");
                        }
                    }
                }
                break;
                case "buscarregistrador": {
                    try{
                    modeloSolicitud.buscarRegistrador(vistaSolicitud.jtfRegistrador.getText());
                    vistaSolicitud.mostrarMensaje("¡Registrador encontrado!");
                    }catch(Exception ex){
                        vistaSolicitud.mostrarMensaje(ex.getMessage());
                    }
                }
                break;
                case "limpiar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        vistaSolicitud.limpiarTodosEspacios();
                    } else {
                        vistaTrasferencia.limpiarTodosEspacios();
                    }
                }
                break;
                case "cancelar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        vistaSolicitud.mostrarMensaje("No se realizo ningun cambio");
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose();
                        modelo.limpiar();
                        
                    } else {
                        vistaSolicitud.mostrarMensaje("No se realizo ningun cambio");
                        vistaTrasferencia.setVisible(false);
                        vistaTrasferencia.limpiarTodosEspacios();
                        vistaTrasferencia.dispose();
                        modelo.limpiar();
                    }
                }
                break;
                case "cambiar": {
                    vista.dispose();
                }
                break;
                case "salir": {
                    System.exit(0);
                }
                break;
                default: {
                }
                break;
            }
        } catch (Exception ex) {
            throw (new Exception(ex.getMessage()));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        try {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                modelo.cambiarTipo(ie.getItem().toString());
                vista.cambiarValoresTabla(modelo.getTipo());
            }
        } catch (Exception exception) {
            vista.mostrarMensaje(exception.getMessage());
        }
    }
    
    @Override
    public void cerrarVista() {
        vista.dispose();
    }
    
    @Override
    public void windowOpened(WindowEvent we) {
        vista.setEnabled(false);
    }

    @Override
    public void windowClosing(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
        vista.setEnabled(true);
    }

    @Override
    public void windowIconified(WindowEvent we) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        
    }

    @Override
    public void windowActivated(WindowEvent we) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        
    }
}
