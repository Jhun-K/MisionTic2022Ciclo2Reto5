package ui;

import model.Estudiante;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;
import controlador.Controlador;
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
        /*
        String mensaje = "Modificar estudiante\n" + "Ingresar correo institucional:";
        String correoInstitucional = JOptionPane.showInputDialog(mensaje);
        boolean existe = controlador.getPrograma().existenciaEstudiante(correoInstitucional);
        if (existe) {
            System.out.println("Ingresar correo personal:");
            String correoPersonal = lectorDatos.nextLine();
            System.out.println("Ingresar número de celular:");
            long numeroCelular;
            try {
                numeroCelular = Long.parseLong(lectorDatos.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("El dato ingresado no es un número");
                numeroCelular = 0;
            }
            System.out.println("Ingresar número fijo:");
            long numeroFijo;
            try {
                numeroFijo = Long.parseLong(lectorDatos.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("El dato ingresado no es un número");
                numeroFijo = 0;
            }
            System.out.println("Ingresar programa:");
            String carrera = lectorDatos.nextLine();
            
            try {
                controlador.getPrograma().modificarDatosEstudianteDB(
                        correoInstitucional, correoPersonal, numeroCelular, 
                        numeroFijo, carrera);
            } catch ( ClassNotFoundException | SQLException e ) {
                System.out.println("Error: " + e);
            }
            
            System.out.println("Se modificó el estudiante");
            System.out.println();
        } else {
            System.out.println("El estudiante no se encuentra registrado en el instituto");
            System.out.println();
        }
        */
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
            mensaje += e;
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
