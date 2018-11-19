/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ModeloAdministrador;
import Modelo.ModeloSolicitud;
import Modelo.ModeloTransferencia;
import Vista.VistaAdministrador;
import Vista.VistaBien;
import Vista.VistaSolicitud;
import Vista.VistaTransferencia;
import accesoADatos.GlobalException;
import accesoADatos.NoDataException;
import accesoADatos.ServicioActivo;
import accesoADatos.ServicioBien;
import accesoADatos.ServicioDependencia;
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
public class ControllerAdministrador extends AbstractController implements ItemListener {

    private ModeloAdministrador modelo;
    private VistaAdministrador vista;
    private VistaSolicitud vistaSolicitud;
    private VistaTransferencia vistaTrasferencia;
    private ModeloSolicitud modeloSolicitud;
    private ModeloTransferencia modeloTrasferencia;
    private VistaBien vistaBien;

    public ControllerAdministrador(ModeloAdministrador modelo, VistaAdministrador vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.setServicioSolicitud(ServicioSolicitud.getServicioSolicitud());
        modelo.setServicioTransferencia(ServicioTransferencia.getServicioTransferencia());
        modelo.setServicioFuncionario(ServicioFuncionario.getServicioFuncionario());
        vista.setModelo(modelo);
        vista.setControlador(this);
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
        modeloTrasferencia.setServicioActivo(ServicioActivo.getServicioActivo());
        modeloTrasferencia.setServicioBien(ServicioBien.getServicioBien());
        modeloTrasferencia.setServicioDependencia(ServicioDependencia.getServicioDependencia());
        modeloTrasferencia.setServicioFuncionario(ServicioFuncionario.getServicioFuncionario());
        vistaTrasferencia.setModelo(modeloTrasferencia);
        vistaSolicitud.addWindowListener(this);
        vistaTrasferencia.addWindowListener(this);
        vistaBien = new VistaBien();
        vistaBien.setControlador(this);
    }

    @Override
    public void mouseClicked(MouseEvent ae) {
        String numero;
        try {
            if (ae.getSource().getClass() == JTable.class) {
                JTable tabla = (JTable) ae.getSource();
                numero = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
                tabla.changeSelection(tabla.getSelectedRow(), 0, false, true);
                if(modeloSolicitud != null){
                    modeloSolicitud.buscarBien(numero);
                }else{
                    modelo.buscarSolicitud(numero);
                }
                if (ae.getClickCount() == 1) {
                    switch (ae.getButton()) {
                        case MouseEvent.BUTTON2: {//Click derecho
                            //instrucciones("eliminar");
                        }
                        break;
                        case MouseEvent.BUTTON1: {//Click izquierdo
                            if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                                if(modeloSolicitud != null){
                                    vistaBien.cargarDatos(modeloSolicitud.getBien());
                                    vistaBien.setVisible(true);
                                } else {
                                    modeloSolicitud = new ModeloSolicitud(modelo.getFuncionario());
                                    modeloSolicitud.setRegistrador(modelo.funcionarioAsociado());
                                    modeloSolicitud.setServicioFuncionario(ServicioFuncionario.getServicioFuncionario());
                                    vistaSolicitud.setModelo(modeloSolicitud);
                                    vistaSolicitud.cargarDatos(modelo.getSolicitud());
                                    vistaSolicitud.setVisible(true);
                                }
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
                case "insertar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        if(modeloSolicitud == null){
                            modeloSolicitud = new ModeloSolicitud(modelo.getFuncionario());
                            vistaSolicitud.setModelo(modeloSolicitud);
                            vistaSolicitud.setVisible(true);
                        }else{
                            vistaBien.setVisible(true);
                        }
                        
                    } else if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[1])) {
                        if(modelo.getTransferencia() == null){
                            vistaTrasferencia.setVisible(true);
                        } else{
                            
                        }
                    }
                }
                break;
                case "agregar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        modelo.crearSolicitud(vistaSolicitud.jtfNumero.getText(),
                                vistaSolicitud.jtfFecha.getText(),
                                vistaSolicitud.jcbTipo.getModel().getSelectedItem().toString(),
                                vistaSolicitud.jcbEstado.getModel().getSelectedItem().toString()
                        );
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.mostrarMensaje("Se guardo la solicitud");
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose();
                    } else if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[1])) {
                        modelo.crearTransferencia(vistaTrasferencia.jtfNumero.getText(),
                                modeloTrasferencia.getOrigen(),
                                modeloTrasferencia.getDestino(),
                                vistaTrasferencia.jtfUbicacion.getText(),
                                modeloTrasferencia.getResponsable()
                        );
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.mostrarMensaje("Se guardo la solicitud");
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose(); vistaTrasferencia.dispose();
                    }
                }
                break;
                case "agregarbien": {
                    try{
                    modeloSolicitud.agregarBien(vistaBien.jtfSerial.getText(),
                            vistaBien.jtfDescripcion.getText(),
                            vistaBien.jtfMarca.getText(),
                            vistaBien.jtfModelo.getText(),
                            vistaBien.jtfPrecio.getText(),
                            vistaBien.jtfCantidadUnidades.getModel().getValue().toString()
                    );
                    vistaBien.setVisible(false);
                    vistaBien.mostrarMensaje("Se guardo la solicitud");
                    vistaBien.limpiarTodosEspacios();
                    vistaBien.dispose();
                    }catch(Exception ex){
                        vistaBien.mostrarMensaje(ex.getMessage());
                    }
                }
                break;
                case "modificar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        modelo.modificarSolicitud(vistaSolicitud.jtfNumero.getText(),
                                vistaSolicitud.jtfFecha.getText(),
                                vistaSolicitud.jcbTipo.getModel().getSelectedItem().toString(),
                                vistaSolicitud.jcbEstado.getModel().getSelectedItem().toString()
                        );
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.mostrarMensaje("Se guardo la solicitud");
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose();
                    } else if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[1])) {
                        modelo.modificarTransferencia(vistaTrasferencia.jtfNumero.getText(),
                                modeloTrasferencia.getOrigen(),
                                modeloTrasferencia.getDestino(),
                                vistaTrasferencia.jtfUbicacion.getText(),
                                modeloTrasferencia.getResponsable()
                        );
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.mostrarMensaje("Se guardo la solicitud");
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose(); vistaTrasferencia.dispose();
                    }
                }
                break;
                case "modificarbien": {
                    modeloSolicitud.modificarBien(vistaBien.jtfSerial.getText(),
                                vistaBien.jtfDescripcion.getText(),
                                vistaBien.jtfMarca.getText(),
                                vistaBien.jtfModelo.getText(),
                                vistaBien.jtfPrecio.getText(),
                                vistaBien.jtfCantidadUnidades.getModel().getValue().toString()
                        );
                        vistaBien.setVisible(false);
                        vistaBien.mostrarMensaje("Se guardo la solicitud");
                        vistaBien.limpiarTodosEspacios();
                        vistaBien.dispose();
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
                case "buscarbien": {
                    try{
                    modeloSolicitud.buscarBien(vistaSolicitud.jtfIdBuscar.getText());
                    }catch(Exception ex){
                        vistaSolicitud.mostrarMensaje(ex.getMessage());
                    }
                }
                break;
                case "buscarresponsable": {
                    try{
                    modeloTrasferencia.buscarResponsable(vistaTrasferencia.jtfFuncionario.getText());
                    vistaTrasferencia.mostrarMensaje("¡Responsable encontrado!");
                    }catch(Exception ex){
                        vistaTrasferencia.mostrarMensaje(ex.getMessage());
                    }
                }
                break;
                case "buscarorigen": {
                    try{
                    modeloTrasferencia.buscarOrigen(vistaTrasferencia.jtfOrigen.getText());
                    vistaTrasferencia.mostrarMensaje("¡Dependencia de origen encontrada!");
                    }catch(Exception ex){
                        vistaTrasferencia.mostrarMensaje(ex.getMessage());
                    }
                }
                break;
                case "buscardestino": {
                    try{
                    modeloTrasferencia.buscarDestino(vistaTrasferencia.jtfDestino.getText());
                    vistaTrasferencia.mostrarMensaje("¡Dependencia de destino encontrada!");
                    }catch(Exception ex){
                        vistaTrasferencia.mostrarMensaje(ex.getMessage());
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
                case "cancelarbien": {
                    vistaBien.mostrarMensaje("No se realizó ningún cambio");
                    vistaBien.setVisible(false);
                    vistaBien.limpiarTodosEspacios();
                    vistaBien.dispose();

                }
                break;
                case "cancelar": {
                    if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[0])) {
                        vistaSolicitud.mostrarMensaje("No se realizó ningún cambio");
                        vistaSolicitud.setVisible(false);
                        vistaSolicitud.limpiarTodosEspacios();
                        vistaSolicitud.dispose();
                        modeloSolicitud = null;
                        modelo.limpiar();
                    } else if (modelo.getTipo().equalsIgnoreCase(modelo.tiposSolicitud[1])) {
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
        vista.toFront();
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
