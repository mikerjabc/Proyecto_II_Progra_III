/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.AbstractController;
import Logic.Solicitud;
import Modelo.ModeloSolicitud;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class VistaSolicitud extends javax.swing.JFrame implements Observer {

    private DefaultTableModel model;

    public VistaSolicitud() {
        initComponents();
        initLocal();
    }

    private void initLocal() {
        model = (DefaultTableModel) jtBienes.getModel();
        setTitle("Solicitud");
        setLocationRelativeTo(null);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.LEFT);
        //Ajustar el texto a la izquierta
        jtBienes.getColumnModel().getColumn(0).setCellRenderer(tcr);//Serial
        jtBienes.getColumnModel().getColumn(1).setCellRenderer(tcr);//Descripcion
        jtBienes.getColumnModel().getColumn(2).setCellRenderer(tcr);//Marca
        jtBienes.getColumnModel().getColumn(3).setCellRenderer(tcr);//Modelo
        jtBienes.getColumnModel().getColumn(4).setCellRenderer(tcr);//Precio
        jtBienes.getColumnModel().getColumn(5).setCellRenderer(tcr);//Cantidad
        //Ajustar tamaño
        jtBienes.getColumnModel().getColumn(0).setPreferredWidth(100);//Numero
        jtBienes.getColumnModel().getColumn(1).setPreferredWidth(200);//Descripcion
        jtBienes.getColumnModel().getColumn(2).setPreferredWidth(100);//Marca
        jtBienes.getColumnModel().getColumn(3).setPreferredWidth(200);//Modelo
        jtBienes.getColumnModel().getColumn(4).setPreferredWidth(150);//Precio
        jtBienes.getColumnModel().getColumn(5).setPreferredWidth(150);//Cantidad
        this.setResizable(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtfNumero = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtfCantidadBienes = new javax.swing.JTextField();
        jtfMontoTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jcbTipo = new javax.swing.JComboBox<>();
        jcbEstado = new javax.swing.JComboBox<>();
        jtfRegistrador = new javax.swing.JTextField();
        jtfFecha = new javax.swing.JTextField();
        jbBuscarRegistrador = new javax.swing.JButton();
        jbLimpiar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbAgregar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jtfIdBuscar = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtBienes = new javax.swing.JTable();
        jbAgregarBien = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 2, 26)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Solicitud");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Número:");

        jtfNumero.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtfNumero.setName("nombre"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Tipo:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Estado:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Fecha:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Cantidad de Bienes:");
        jLabel4.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Monto Total:");
        jLabel5.setToolTipText("");

        jtfCantidadBienes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtfCantidadBienes.setName("precio"); // NOI18N

        jtfMontoTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtfMontoTotal.setName("precio"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Registrador");
        jLabel9.setToolTipText("");

        jcbTipo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jcbEstado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jtfRegistrador.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtfRegistrador.setName("precio"); // NOI18N

        jtfFecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtfFecha.setName("precio"); // NOI18N

        jbBuscarRegistrador.setText("Buscar");
        jbBuscarRegistrador.setName("buscarRegistrador"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNumero, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfFecha)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfMontoTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfCantidadBienes, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbEstado, 0, 577, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtfRegistrador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbBuscarRegistrador)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtfCantidadBienes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtfMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfRegistrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarRegistrador))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jbLimpiar.setText("Limpiar");
        jbLimpiar.setName("limpiar"); // NOI18N

        jbCancelar.setText("Cancelar");
        jbCancelar.setName("cancelar"); // NOI18N

        jbAgregar.setText("Agregar");
        jbAgregar.setName("agregar"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Bienes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jtfIdBuscar.setName("idBuscar"); // NOI18N

        jbBuscar.setText("Buscar");
        jbBuscar.setName("buscar"); // NOI18N

        jLabel7.setText("ID:");

        jtBienes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial", "Descripción", "Marca", "Modelo", "Precio", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jtBienes);

        jbAgregarBien.setText("Agregar");
        jbAgregarBien.setName("insertar"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfIdBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbAgregarBien)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfIdBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar)
                    .addComponent(jLabel7)
                    .addComponent(jbAgregarBien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAgregar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbLimpiar)
                    .addComponent(jbAgregar)
                    .addComponent(jbCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaSolicitud().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JButton jbAgregar;
    public javax.swing.JButton jbAgregarBien;
    public javax.swing.JButton jbBuscar;
    public javax.swing.JButton jbBuscarRegistrador;
    public javax.swing.JButton jbCancelar;
    public javax.swing.JButton jbLimpiar;
    public javax.swing.JComboBox<String> jcbEstado;
    public javax.swing.JComboBox<String> jcbTipo;
    public javax.swing.JTable jtBienes;
    public javax.swing.JTextField jtfCantidadBienes;
    public javax.swing.JTextField jtfFecha;
    public javax.swing.JTextField jtfIdBuscar;
    public javax.swing.JTextField jtfMontoTotal;
    public javax.swing.JTextField jtfNumero;
    public javax.swing.JTextField jtfRegistrador;
    // End of variables declaration//GEN-END:variables
    
    private ModeloSolicitud modelo;
    private AbstractController controlador;
    
    public void setModelo(ModeloSolicitud modelo){
        this.modelo = modelo;
        modelo.addObserver(this);
        ajustatVistaParaFuncionario();
    }
    
    public void setControlador(AbstractController controlador){
        this.controlador = controlador;
        jbAgregar.addActionListener(controlador);
        jbAgregarBien.addActionListener(controlador);
        jbCancelar.addActionListener(controlador);
        jbBuscar.addActionListener(controlador);
        jbLimpiar.addActionListener(controlador);
        jtfCantidadBienes.addKeyListener(controlador);
        jtfNumero.addKeyListener(controlador);
        jtfIdBuscar.addKeyListener(controlador);
        jtfMontoTotal.addKeyListener(controlador);
        jtfFecha.addKeyListener(controlador);
        jtBienes.addMouseListener(controlador);
        jbBuscarRegistrador.addActionListener(controlador);
    }
    
    public void ajustatVistaParaFuncionario() {
        jtfCantidadBienes.setEditable(false);
        jtfNumero.setEditable(false);
        jtfMontoTotal.setEditable(false);
        jcbTipo.removeAllItems();
        jcbEstado.removeAllItems();
        
        switch (modelo.getFuncionario().getPuesto().toLowerCase()) {
            case "administrador": {
                jcbTipo.addItem(modelo.tiposBien[0]);
                jcbTipo.addItem(modelo.tiposBien[1]);
                jcbTipo.addItem(modelo.tiposBien[2]);
                jcbEstado.addItem(modelo.tiposEstadoSolicitud[0]);
                jcbEstado.setEnabled(false);
                jtfNumero.setText(String.valueOf(modelo.getNumeroSolicitud()));
                jbBuscarRegistrador.setEnabled(false);
                jtfRegistrador.setEnabled(false);
            }
            break;
            case "secretaria": {
                jcbEstado.addItem(modelo.tiposEstadoSolicitud[1]);
                jcbEstado.addItem(modelo.tiposEstadoSolicitud[2]);
                jcbTipo.setEnabled(false);
                
                jtfRegistrador.setEditable(false);
                jbBuscarRegistrador.setEnabled(false);
                jbAgregarBien.setEnabled(false);
                jbLimpiar.setEnabled(false);
                jtBienes.setEnabled(false);
                jcbTipo.setEnabled(false);
                jtfFecha.setEditable(false);
            }
            break;
            case "jefe": {
                jtBienes.setEnabled(false);
                jbAgregarBien.setEnabled(false);
                jcbEstado.setEditable(false);
                jbLimpiar.setEnabled(false);
                jcbTipo.setEnabled(false);
                jcbEstado.setEnabled(false);
                jtfFecha.setEditable(false);
            }
            break;
            case "registrador": {
                jcbEstado.addItem(modelo.tiposEstadoSolicitud[3]);
                jcbEstado.addItem(modelo.tiposEstadoSolicitud[4]);
                jbBuscarRegistrador.setEnabled(false);
                jbAgregarBien.setEnabled(false);
                jbLimpiar.setEnabled(false);
                jtfRegistrador.setEditable(false);
                jcbTipo.setEnabled(false);
                jtfFecha.setEditable(false);
            }
            break;
        }
    }
    
    public void limpiarTodosEspacios() {
        jtfCantidadBienes.setText("");
        jtfNumero.setText("");
        jtfIdBuscar.setText("");
        jcbEstado.getModel().setSelectedItem(modelo.tiposEstadoSolicitud[0]);
        jcbTipo.getModel().setSelectedItem(modelo.tiposBien[0]);
        modelo.limpiar();
        jtfMontoTotal.setText("");
        jtfFecha.setText("");
    }
    
    public void cargarDatos(Solicitud solicitud) {
        jtfNumero.setText(String.valueOf(solicitud.getNumeroSolicitud()));
        jtfFecha.setText(solicitud.getFecha());
        jcbEstado.getModel().setSelectedItem(solicitud.getEstado());
        jcbTipo.getModel().setSelectedItem(solicitud.getTipo());
        modelo.setMonto(solicitud.getMontoTotal());
        modelo.setCantidad(solicitud.getCantiadadBienes());
        modelo.setListaBienes(solicitud.getListaBienes());
        if(modelo.getRegistrador() != null){
            jtfRegistrador.setText(modelo.getRegistrador().getNombre());
        }
    }
    
    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public String preguntarDetalle(String pregunta) {
        String aux;
        aux = JOptionPane.showInputDialog(pregunta);
        return aux;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        if (o1 != null) {
            if (o1.getClass() == ArrayList.class) {
                ArrayList aux = (ArrayList) o1;
                int filas = model.getRowCount();
                for (int i = 0; i < filas; i++) {
                    model.removeRow(0);
                }
                Iterator<Object[]> ite = aux.iterator();
                while (ite.hasNext()) {
                    model.addRow(ite.next());
                }
                this.jtfMontoTotal.setText(String.valueOf(modelo.getMonto()));
                this.jtfCantidadBienes.setText(String.valueOf(modelo.getCantidad()));
                if(modelo.getRegistrador()!= null){
                    jtfRegistrador.setText(modelo.getRegistrador().getNombre());
                }
            }
        }
    }
}
