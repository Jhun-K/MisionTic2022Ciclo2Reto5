package ui;

import model.Estudiante;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;
import controlador.Controlador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Vista {

    private Controlador controlador;

    public Vista() {
        controlador = new Controlador();
    }

    public void mostrarMenu() {
        boolean terminarCiclo = false;
        
        String mensajeMenu = "INSTITUTO LA FLORESTA\n" +
            "Seleccione tarea a realizar:\n" 
            + "1. Ingresar estudiante\n"
            + "2. Buscar estudiante\n"
            + "3. Modificar estudiante \n"
            + "4. Eliminar Estudiante\n"
            + "5. Ver directorio de estudiantes\n"
            + "6. Salir\n"
            + "Opción:\n";

        while (!terminarCiclo) {
            int opcion;
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(mensajeMenu));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El dato ingresado no es un número");
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    vistaOpcion1();
                    break;
                case 2:
                    vistaOpcion2();
                    break;
                case 3:
                    vistaOpcion3();
                    break;
                case 4:
                    vistaOpcion4();
                    break;
                case 5:
                    vistaOpcion5();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Hasta pronto");
                    terminarCiclo = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Digite una opción valida.");
                    break;
            }
        }
    }

    private void vistaOpcion1() {
        String mensaje = "Ingresar estudiante\n" + "Ingresar nombres:";
        String nombres = JOptionPane.showInputDialog(mensaje);
        String apellidos = JOptionPane.showInputDialog("Ingresar apellidos:");
        String fechaNacimiento = JOptionPane.showInputDialog("Ingresar fecha de nacimiento (YYYY-MM-DD):");
        String correoInstitucional = JOptionPane.showInputDialog("Ingresar correo institucional:");
        String correoPersonal = JOptionPane.showInputDialog("Ingresar correo personal:");
        long numeroCelular;
        try {
            numeroCelular = Long.parseLong(JOptionPane.showInputDialog("Ingresar número de celular:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El dato ingresado no es un número");
            numeroCelular = 0;
        }
        long numeroFijo;
        try {
            numeroFijo = Long.parseLong(JOptionPane.showInputDialog("Ingresar número fijo:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El dato ingresado no es un número");
            numeroFijo = 0;
        }
        String carrera = JOptionPane.showInputDialog("Ingresar programa:");
        
        try {
            controlador.getPrograma().agregarEstudianteDB(
                    nombres, apellidos, fechaNacimiento, correoInstitucional, 
                    correoPersonal, numeroCelular, numeroFijo, carrera);
        } catch( ClassNotFoundException | SQLException e ) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        
        JOptionPane.showMessageDialog(null, "Se agregó el estudiante");
    }

    private void vistaOpcion2() {
        String mensaje = "Buscar estudiante\n" + "Ingresar correo institucional:";
        String correoInstitucional = JOptionPane.showInputDialog(mensaje);
        
        Estudiante e = null;
        try {
            e = controlador.getPrograma().buscarEstudianteBD(correoInstitucional); 
        } catch ( ClassNotFoundException | SQLException ex ) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        
        if ( e != null ) {
            JOptionPane.showMessageDialog(null, "Información del estudiante\n" + e);
        } else {
            JOptionPane.showMessageDialog(null, "El estudiante no se encuentra registrado en el instituto");
        }
    }

    private void vistaOpcion3() {
        String mensaje = "Modificar estudiante\n" + "Ingresar correo institucional:";
        String correoInstitucional = JOptionPane.showInputDialog(mensaje);
        Estudiante es = null;
        
        try {
            es = controlador.getPrograma().buscarEstudianteBD(correoInstitucional);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        
        if (es != null) {
            String correoPersonal = JOptionPane.showInputDialog("Ingresar correo personal:");
            
            long numeroCelular;
            try {
                numeroCelular = Long.parseLong(
                        JOptionPane.showInputDialog("Ingresar número de celular:")
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El dato ingresado no es un número");
                numeroCelular = 0;
            }
            
            long numeroFijo;
            try {
                numeroFijo = Long.parseLong(
                        JOptionPane.showInputDialog("Ingresar número fijo:")
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El dato ingresado no es un número");
                numeroFijo = 0;
            }
            
            String carrera = JOptionPane.showInputDialog("Ingresar programa:");
            
            try {
                controlador.getPrograma().modificarDatosEstudianteDB(
                        correoInstitucional, correoPersonal, numeroCelular, 
                        numeroFijo, carrera);
            } catch ( ClassNotFoundException | SQLException ex ) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
            
            JOptionPane.showMessageDialog(null, "Se modificó el estudiante");
        } else {
            JOptionPane.showMessageDialog(
                    null, "El estudiante no se encuentra registrado en el instituto"
            );
        }
    }

    private void vistaOpcion4() {
        String mensaje = "Eliminar estudiante\n" + "Ingresar correo institucional:";
        String correoInstitucional = JOptionPane.showInputDialog(mensaje);
        
        Estudiante estudiante = null;
        
        try {
            estudiante = controlador.getPrograma().buscarEstudianteBD(correoInstitucional);
        } catch ( ClassNotFoundException | SQLException e ) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        
        if ( estudiante != null ) {
            try {
                controlador.getPrograma().eliminarEstudianteDB(correoInstitucional);
            } catch ( ClassNotFoundException | SQLException e ) {
                JOptionPane.showMessageDialog(null, "Error: " + e);
            }
            JOptionPane.showMessageDialog(null, "Se eliminó el estudiante");
        } else {
            JOptionPane.showMessageDialog(null, 
                "El estudiante no se encuentra registrado en el instituto");
        }
    }

    private void vistaOpcion5() {
        try {
            controlador.getPrograma().cargarEstudiantesDesdeBD();
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        ArrayList<Estudiante> estudiantes = controlador.getPrograma().getEstudiantes();
        String mensaje = "El directorio de los estudiantes\n";
        for (var e : estudiantes) {
            mensaje += e + "\n";
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
