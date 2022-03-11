/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista;

import controlador.Actividad.Servicio.ActividadServicio;
import controlador.Actividad.Servicio.SActividadServicio;
import controlador.exception.AdyacenciaExepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lista.controlador.Lista;

import vista.ModeloTabla.ModeloTablaSubActividad;

/**
 *
 * @author Javier
 */
public class Frm_RegActividad extends javax.swing.JDialog {

    private SActividadServicio sas = new SActividadServicio();
    private ActividadServicio as = new ActividadServicio();
    private ModeloTablaSubActividad mt = new ModeloTablaSubActividad();

    /**
     * Creates new form Frm_RegActividad
     */
    public Frm_RegActividad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarComboActividades();
        cargarCombos();
        cargarTabla();
    }

    private void cargarTabla() {
        if (sas.getGrafo() != null) {
            mt.setGrafoD(sas.getGrafo());
            jtCronograma.setModel(mt);
            mt.fireTableStructureChanged();
            jtCronograma.updateUI();
            System.out.println(mt.getColumnCount() + "---");
        } else {
            DefaultTableModel modelo = new DefaultTableModel(1, 1);
            jtCronograma.setModel(modelo);
            jtCronograma.updateUI();
            System.out.println(modelo.getColumnCount());
        }
    }

    private void cargarComboActividades() {
        cbxActividades.removeAllItems();
        cbxActividades.updateUI();
        if (as.getGrafo() != null) {
            for (int i = 0; i < as.getGrafo().numVertices(); i++) {
                String label = as.getGrafo().obtenerEtiqueta(i + 1).toString();
                cbxActividades.addItem(label);
                cbxActividades.updateUI();
            }
        }
    }

    private void cargarCombos() {
        cbxSAInicial.removeAllItems();
        cbxSAFinal.removeAllItems();
        cbxSAInicial.updateUI();
        cbxSAFinal.updateUI();
        if (sas.getGrafo() != null) {
            for (int i = 0; i < sas.getGrafo().numVertices(); i++) {
                String label = sas.getGrafo().obtenerEtiqueta(i + 1).toString();
                cbxSAInicial.addItem(label);
                cbxSAFinal.addItem(label);
                cbxSAInicial.updateUI();
                cbxSAFinal.updateUI();

            }
        }
    }

    private void limpiarDatosAct() {
        NombreA.setText("");
        PresupuestoA.setText("");
        jdFechaIniA.setDate(null);
        jdFechaFinA.setDate(null);
        as.fijarActividad(null);
        cargarTabla();
        cargarComboActividades();
    }

    private void limpiarDatosSAct() {
        NombreSA.setText("");
        PresupuestoSA.setText("");
        jdFechaIniSA.setDate(null);
        jdFechaFinSA.setDate(null);
        Encargado.setText("");
        sas.fijarSActividad(null);
        cargarTabla();
        cargarCombos();
    }

    public void generarCaminos() throws AdyacenciaExepcion {
        if (cbxSAInicial.getSelectedItem() == cbxSAFinal.getSelectedItem()) {
            JOptionPane.showMessageDialog(null, "No se puede agregar un nuevo valor", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Integer p1 = cbxSAInicial.getSelectedIndex() + 1;
            Integer p2 = cbxSAFinal.getSelectedIndex() + 1;
            
            long[][] mAdy = new long[sas.getGrafo().numVertices()][sas.getGrafo().numVertices()];
            Lista caminos = sas.getGrafo().algoritmoFloyd(mAdy, p1, p2);

            
            String aux = "";
            System.out.println(caminos.tamanio());
            for (int i = 0; i < caminos.tamanio(); i++) {
                aux = aux.concat(String.valueOf(sas.getGrafo().obtenerEtiqueta(Integer.parseInt(caminos.consultarDatoPosicion(i).toString()))));
                System.out.println(sas.getGrafo().obtenerEtiqueta(Integer.parseInt(caminos.consultarDatoPosicion(i).toString())));
                if (i+1 < caminos.tamanio()) {
                    aux = aux.concat(" --> ");
                }
            }
            jTextArea1.setText(aux);
        }

    }

    private void agregarAdycencias() {
        if (sas.getGrafo() != null) {
            if (cbxSAFinal.getSelectedIndex() == cbxSAInicial.getSelectedIndex()) {
                JOptionPane.showMessageDialog(null, "No se puede agregar un mismo valor", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                Integer p1 = cbxSAInicial.getSelectedIndex() + 1;
                Integer p2 = cbxSAFinal.getSelectedIndex() + 1;


                Long diaIni = (sas.getGrafo().obtenerEtiqueta(p1).getFechainicio().getTime() + sas.getGrafo().obtenerEtiqueta(p2).getFechainicio().getTime())/2;
                Long diaFin = (sas.getGrafo().obtenerEtiqueta(p1).getFechaFinalizacion().getTime() + sas.getGrafo().obtenerEtiqueta(p2).getFechaFinalizacion().getTime())/2;
                System.out.println(diaFin-diaIni);
                Long dias = Math.abs(diaFin - diaIni);
                

                sas.getGrafo().insertarArista(p1, p2, Double.parseDouble(dias.toString()));
                //ps.getGrafo().obtenerCodigo(p1);
                if (sas.actualizarGrafo(sas.getGrafoObjeto())) {
                    JOptionPane.showMessageDialog(null, "Gracias Actualizar grafo", "OK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede agregar un mismo valor", "Error", JOptionPane.ERROR_MESSAGE);
                }
                limpiarDatosSAct();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Boolean validarDatosAct() {
        return (NombreA.getText().trim().length() > 0 && PresupuestoA.getText().trim().length() > 0 && jdFechaIniA.getDate() != null && jdFechaFinA.getDate() != null);
    }

    private Boolean validarDatosSubAct() {
        return (NombreSA.getText().trim().length() > 0 && PresupuestoSA.getText().trim().length() > 0 && Encargado.getText().trim().length() > 0 && jdFechaIniSA.getDate() != null && jdFechaFinSA.getDate() != null);
    }

    private void agregarAct() {
        if (validarDatosAct()) {
            try {
                as.getActividad().setNombre(NombreA.getText().trim());
                as.getActividad().setPresupuesto(Double.parseDouble(PresupuestoA.getText().trim()));
                as.getActividad().setFechainicio(jdFechaIniA.getDate());
                as.getActividad().setFechaFinalizacion(jdFechaFinA.getDate());
                if (as.guardar()) {
                    JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "OK", JOptionPane.INFORMATION_MESSAGE);
                    limpiarDatosAct();
                } else {
                    JOptionPane.showMessageDialog(null, "No se Pudo Guardar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarSubAct() {
        if (validarDatosSubAct()) {
            try {
                sas.getSActividad().setNombre(NombreSA.getText().trim());
                sas.getSActividad().setPresupuesto(Double.parseDouble(PresupuestoSA.getText().trim()));
                sas.getSActividad().setFechainicio(jdFechaIniSA.getDate());
                sas.getSActividad().setFechaFinalizacion(jdFechaFinSA.getDate());
                sas.getSActividad().setEncargado(Encargado.getText().trim());
                if (sas.guardar()) {
                    JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "OK", JOptionPane.INFORMATION_MESSAGE);
                    limpiarDatosSAct();
                } else {
                    JOptionPane.showMessageDialog(null, "No se Pudo Guardar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        NombreA = new javax.swing.JTextField();
        jdFechaIniA = new com.toedter.calendar.JDateChooser();
        jdFechaFinA = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        PresupuestoA = new javax.swing.JTextField();
        btnGuardarAct = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        NombreSA = new javax.swing.JTextField();
        jdFechaIniSA = new com.toedter.calendar.JDateChooser();
        jdFechaFinSA = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        PresupuestoSA = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        Encargado = new javax.swing.JTextField();
        btnGuardarSubA = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbxActividades = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCronograma = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        cbxSAInicial = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cbxSAFinal = new javax.swing.JComboBox<>();
        btnAdyacencia = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnMejorRuta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Registro de SubActividades");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RegistroA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Fecha Inicio:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Fecha Fin:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Presupuesto:");

        btnGuardarAct.setText("Guardar");
        btnGuardarAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)
                        .addGap(38, 38, 38)
                        .addComponent(NombreA))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdFechaIniA, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jdFechaFinA, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(PresupuestoA, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarAct, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel2))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(NombreA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(PresupuestoA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnGuardarAct)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdFechaIniA, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel4)
                    .addComponent(jdFechaFinA, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Registro de Actividades");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RegistroSA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Nombre:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Fecha Inicio:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Fecha Fin:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Presupuesto:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Encargado");

        btnGuardarSubA.setText("Guardar");
        btnGuardarSubA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSubAActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText(" Actividad:");

        cbxActividades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel7))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12)))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(NombreSA, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PresupuestoSA, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jdFechaFinSA, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnGuardarSubA, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdFechaIniSA, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(Encargado))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbxActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(jLabel7))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NombreSA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addComponent(jdFechaFinSA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarSubA, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdFechaIniSA, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PresupuestoSA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(Encargado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        jtCronograma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtCronograma);

        jLabel13.setText("S.Actividad Inicial:");

        cbxSAInicial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("S.Actividad Final:");

        cbxSAFinal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAdyacencia.setText("Asignar");
        btnAdyacencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdyacenciaActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        btnMejorRuta.setText("Mejor Ruta");
        btnMejorRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMejorRutaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnMejorRuta)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxSAInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxSAFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAdyacencia, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(241, 241, 241))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(235, 235, 235))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbxSAInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cbxSAFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdyacencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMejorRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 670, 690);

        setSize(new java.awt.Dimension(684, 715));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActActionPerformed
        // TODO add your handling code here:
        agregarAct();
    }//GEN-LAST:event_btnGuardarActActionPerformed

    private void btnGuardarSubAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSubAActionPerformed
        // TODO add your handling code here:
        agregarSubAct();
    }//GEN-LAST:event_btnGuardarSubAActionPerformed

    private void btnAdyacenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdyacenciaActionPerformed
        agregarAdycencias();
    }//GEN-LAST:event_btnAdyacenciaActionPerformed

    private void btnMejorRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMejorRutaActionPerformed
        try {
            // TODO add your handling code here:
            generarCaminos();
        } catch (AdyacenciaExepcion ex) {
            Logger.getLogger(Frm_RegActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMejorRutaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_RegActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_RegActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_RegActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_RegActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Frm_RegActividad dialog = new Frm_RegActividad(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Encargado;
    private javax.swing.JTextField NombreA;
    private javax.swing.JTextField NombreSA;
    private javax.swing.JTextField PresupuestoA;
    private javax.swing.JTextField PresupuestoSA;
    private javax.swing.JButton btnAdyacencia;
    private javax.swing.JButton btnGuardarAct;
    private javax.swing.JButton btnGuardarSubA;
    private javax.swing.JButton btnMejorRuta;
    private javax.swing.JComboBox<String> cbxActividades;
    private javax.swing.JComboBox<String> cbxSAFinal;
    private javax.swing.JComboBox<String> cbxSAInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private com.toedter.calendar.JDateChooser jdFechaFinA;
    private com.toedter.calendar.JDateChooser jdFechaFinSA;
    private com.toedter.calendar.JDateChooser jdFechaIniA;
    private com.toedter.calendar.JDateChooser jdFechaIniSA;
    private javax.swing.JTable jtCronograma;
    // End of variables declaration//GEN-END:variables
}
