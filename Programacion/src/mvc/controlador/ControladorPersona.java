/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controlador;

import mvc.Modelo.ModeloPersona;
import mvc.Modelo.Persona;
import mvc.Vista.VistaPersonas;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class ControladorPersona {

    private ModeloPersona modelo;
    private VistaPersonas vista;

    public ControladorPersona(ModeloPersona modelo, VistaPersonas vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        cargaPersonas();
    }

    public void iniciaControl() {
        vista.getBtnConsultar().addActionListener(o -> buscar());
        vista.getBtnCrear().addActionListener(l -> abrirDialogo("Crear"));
        vista.getBtnEditar().addActionListener(l -> abrirDialogo("Editar"));
        vista.getBtnRemover().addActionListener(l -> abrirDialogo("Eliminar"));
        vista.getBtnLimpiar().addActionListener(l -> limpiarbusqueda());
        vista.getBtnAceptar().addActionListener(l -> crearEditarPersona());
    }

    private void limpiarbusqueda() {
        vista.getTxtBuscar().setText("");
        cargaPersonas();
    }

    private void buscar() {
        List<Persona> listper = modelo.listarPersonas();
        String idBuscado = vista.getTxtBuscar().getText();

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cedula");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");

        for (Persona p : listper) {
            if (p.getId_persona().equals(idBuscado)) {
                Object[] fila = {
                    p.getId_persona(),
                    p.getNombre(),
                    p.getApellido(),};
                modeloTabla.addRow(fila);
            }
        }

        vista.getTblPersonas().setModel(modeloTabla);
    }

    private void crearEditarPersona() {
        if (vista.getDialog1().getTitle().contentEquals("Crear")) {

            ModeloPersona p = new ModeloPersona();

            if (vista.getTxtcedula().equals("") || vista.getTxtnombre().equals("") || vista.getTxtapellido().equals("")) {

                JOptionPane.showMessageDialog(null, "POR FAVOR LLENE LOS DATOS");

            } else {
                String cedula = vista.getTxtcedula().getText();
                String nombre = vista.getTxtnombre().getText();
                String apellido = vista.getTxtapellido().getText();

                if (cedulaExiste(cedula)) {
                    JOptionPane.showMessageDialog(null, "La cédula ya existe. Por favor, ingrese una cédula diferente.");
                } else if (!validarCedula(cedula)) {
                    JOptionPane.showMessageDialog(null, "La cédula debe tener 10 dígitos.");
                } else if (!validarNombre(nombre)) {
                    JOptionPane.showMessageDialog(null, "El nombre debe ser valido");
                } else if (!validarApellido(apellido)) {
                    JOptionPane.showMessageDialog(null, "El apellido debe ser valido");
                } else {
                    p.setId_persona(cedula);
                    p.setNombres(nombre);
                    p.setApellido(apellido);

                    if (p.grabarPersona()) {
                        limpiar();
                        JOptionPane.showMessageDialog(vista, "DATOS CREADOS");
                        vista.getDialog1().setVisible(false);
                        cargaPersonas();
                    } else {
                        JOptionPane.showMessageDialog(vista, "ERROR AL GRABAR DATOS");
                    }
                }
            }
        } else if (vista.getDialog1().getTitle().contentEquals("Editar")) {

            ModeloPersona p = new ModeloPersona();

            if (vista.getTxtcedula().equals("") || vista.getTxtnombre().equals("") || vista.getTxtapellido().equals("")) {
                JOptionPane.showMessageDialog(null, "POR FAVOR LLENE LOS DATOS");
            } else {
                String cedula = vista.getTxtcedula().getText();
                String nombre = vista.getTxtnombre().getText();
                String apellido = vista.getTxtapellido().getText();

                if (!validarCedula(cedula)) {
                    JOptionPane.showMessageDialog(null, "El campo de cédula debe tener 10 dígitos.");
                } else if (!validarNombre(nombre)) {
                    JOptionPane.showMessageDialog(null, "El nombre debe ser valido");
                } else if (!validarApellido(apellido)) {
                    JOptionPane.showMessageDialog(null, "El apellido debe ser valido");
                } else {
                    p.setId_persona(cedula);
                    p.setNombres(nombre);
                    p.setApellido(apellido);

                    if (p.ModificarPersona()) {
                        limpiar();
                        JOptionPane.showMessageDialog(vista, "DATOS CREADOS");
                        vista.getDialog1().setVisible(false);
                        cargaPersonas();
                    } else {
                        JOptionPane.showMessageDialog(vista, "ERROR AL GRABAR DATOS");
                    }
                }
            }

        } else if (vista.getDialog1().getTitle().contentEquals("Eliminar")) {
            ModeloPersona p = new ModeloPersona();
            p.setId_persona(vista.getTxtcedula().getText());
            if (p.EliminarPersona()) {

                limpiar();
                JOptionPane.showMessageDialog(vista, "DATOS ELIMINADOS");

                vista.getDialog1().setVisible(false);
                cargaPersonas();

            } else {
                JOptionPane.showMessageDialog(vista, "ERROR AL GRABAR DATOS");
            }

        }
    }

    private void limpiar() {
        vista.getTxtcedula().setText("");
        vista.getTxtnombre().setText("");
        vista.getTxtapellido().setText("");
        vista.getTxtcedula().setEnabled(true);

    }

    private void abrirDialogo(String ce) {

        vista.getDialog1().setLocationRelativeTo(null);
        vista.getDialog1().setSize(750, 600);
        vista.getDialog1().setTitle(ce);
        vista.getDialog1().setVisible(true);
        if (vista.getDialog1().getTitle().contentEquals("Crear")) {

        } else if (vista.getDialog1().getTitle().contentEquals("Editar")) {

            LlenarDatos();

        } else if (vista.getDialog1().getTitle().contentEquals("Eliminar")) {
            LlenarDatos();
        }
    }

    private void cargaPersonas() {
        DefaultTableModel mJtable;
        mJtable = (DefaultTableModel) vista.getTblPersonas().getModel();
        mJtable.setNumRows(0);
        List<Persona> listaP = modelo.listarPersonas();
        listaP.stream().forEach(p -> {
            String[] rowData = {p.getId_persona(), p.getNombre(), p.getApellido()};
            mJtable.addRow(rowData);
        }
        );
    }

    public void LlenarDatos() {

        List<Persona> listper = modelo.listarPersonas();
        int selectedRow = vista.getTblPersonas().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Para que los datos se llenen, debe seleccionar un elemento de la tabla");
        } else {
            String selectedId = vista.getTblPersonas().getValueAt(selectedRow, 0).toString();
            Optional<Persona> matchingPersona = listper.stream()
                    .filter(p -> selectedId.equals(p.getId_persona()))
                    .findFirst();

            if (matchingPersona.isPresent()) {
                Persona p = matchingPersona.get();
                vista.getTxtcedula().setText(p.getId_persona());
                vista.getTxtcedula().setEnabled(false);
                vista.getTxtnombre().setText(p.getNombre());
                vista.getTxtapellido().setText(p.getApellido());

            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento válido de la tabla.");
            }
        }
    }

    private boolean cedulaExiste(String cedula) {
        List<Persona> listaPersonas = modelo.listarPersonas();

        for (Persona persona : listaPersonas) {
            if (persona.getId_persona().equals(cedula)) {
                return true;
            }
        }

        return false;
    }

    public boolean validarNombre(String nombre) {

        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}");
    }

    public boolean validarApellido(String apellido) {

        return apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}");
    }

    public boolean validarCedula(String cedula) {

        return cedula.matches("\\d{10}");
    }

}
