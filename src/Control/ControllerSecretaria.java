/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ModeloJefe;
import Modelo.ModeloSecretaria;
import Modelo.ModeloSolicitud;
import Modelo.ModeloTransferencia;
import Vista.VistaJefe;
import Vista.VistaSecretaria;
import Vista.VistaSolicitud;
import Vista.VistaTransferencia;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioFuncionario;
import accesoADatos.ServicioSolicitud;
import accesoADatos.ServicioTransferencia;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
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
public class ControllerSecretaria extends AbstractController{

    private ModeloSecretaria modelo;
    private VistaSecretaria vista;
    private VistaSolicitud vistaSolicitud;
    private ModeloSolicitud modeloSolicitud;

    public ControllerSecretaria(ModeloSecretaria modelo, VistaSecretaria vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setModelo(modelo);
        vista.setControlador(this);
        modelo.setServicioSolicitud(ServicioSolicitud.getServicioSolicitud());
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
        vistaSolicitud.setControlador(this);
        vistaSolicitud.addWindowListener(this);
        modeloSolicitud = null;
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
                            modeloSolicitud = new ModeloSolicitud(modelo.getFuncionario());
                            modeloSolicitud.setServicioFuncionario(ServicioFuncionario.getServicioFuncionario());
                            vistaSolicitud.setModelo(modeloSolicitud);
                            vistaSolicitud.cargarDatos(modelo.getSolicitud());
                            vistaSolicitud.setVisible(true);
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
                    String aux = "";
                    if (vistaSolicitud.jcbEstado.getModel().getSelectedItem().toString().equals("Rechazada")) {
                        aux = vistaSolicitud.preguntarDetalle("Al estar la solicitud en estado Rechazada debe ingresar un detalle del rechazo.");
                    }
                    if (aux != null) {
                        modelo.cambiarEstadoSolicitud(vistaSolicitud.jcbEstado.getModel().getSelectedItem().toString(), aux);
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.mostrarMensaje("Se cambio el estado de la solicitud");
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose();
                    }
                }
                break;
                case "buscar": {
                    if (modelo.getSolicitud() == null) {
                        modelo.buscarSolicitud(vista.jtIdBuscar.getText());
                        vista.mostrarMensaje("Se encontro la solicitud");
                    } else {
                        modelo.buscarBien(vistaSolicitud.jtfIdBuscar.getText());
                        vistaSolicitud.mostrarMensaje("Se encontro el bien");
                    }
                }
                break;
                case "limpiar": {
                    vistaSolicitud.limpiarTodosEspacios();

                }
                break;
                case "cancelar": {
                    vistaSolicitud.mostrarMensaje("No se realizo ningún cambio");
                    vistaSolicitud.setVisible(false);
                    vistaSolicitud.limpiarTodosEspacios();
                    vistaSolicitud.dispose();
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
