package ui;

import model.Estudiante;
import java.util.ArrayList;
import java.sql.SQLException;
import controlador.Controlador;
import javax.swing.JOptionPane;

public class Vista {

    private Controlador controlador;

    public Vista() {
        controlador = new Controlador();
    }

    public void mostrarMenu() {
        try {
            controlador.getPrograma().cargarEstudiantesDesdeBD();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        
        boolean terminarCiclo = false;

        String mensajeMenu = "INSTITUTO LA FLORESTA\n"
                + "Seleccione tarea a realizar:\n"
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
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }

        JOptionPane.showMessageDialog(null, "Se agregó el estudiante");
    }

    private void vistaOpcion2() {
        String mensaje = "Buscar un estudiante.\n"
                + "1. Buscar estudiante por medio de su correo institucional.\n"
                + "2. Buscar estudiantes por medio de un apellido.\n"
                + "3. Mostrar los datos de los estudiantes que estan en un determinado programa.\n"
                + "4. Mostrar el numero de estudiantes que estan en un determinado programa.\n"
                + "5. Mostrar estudiantes nacidos en una determinada fecha.\n"
                + "6. Buscar estudiante por medio de su numero de telefono.\n";

        int opcion;

        try {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
        } catch (NumberFormatException ex) {
            opcion = 0;
        }

        switch (opcion) {
            case 1:
                opcion1VO2();
                break;
            case 2:
                opcion2VO2();
                break;
            case 3:
                opcion3VO2();
                break;
            case 4:
                opcion4VO2();
                break;
            case 5:
                opcion5VO2();
                break;
            case 6:
                opcion6VO2();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Digita una opcion valida.");
                break;
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
            } catch (ClassNotFoundException | SQLException ex) {
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
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }

        if (estudiante != null) {
            try {
                controlador.getPrograma().eliminarEstudianteDB(correoInstitucional);
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e);
            }
            JOptionPane.showMessageDialog(null, "Se eliminó el estudiante");
        } else {
            JOptionPane.showMessageDialog(null,
                    "El estudiante no se encuentra registrado en el instituto");
        }
    }

    private void vistaOpcion5() {
        ArrayList<Estudiante> estudiantes = controlador.getPrograma().getEstudiantes();
        String mensaje = "El directorio de los estudiantes\n";
        for (var e : estudiantes) {
            mensaje += e + "\n";
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private void opcion1VO2() {
        String mensaje = "Buscar estudiante\n" + "Ingresar correo institucional:";
        String correoInstitucional = JOptionPane.showInputDialog(mensaje);

        Estudiante e = null;
        try {
            e = controlador.getPrograma().buscarEstudianteBD(correoInstitucional);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        if (e != null) {
            JOptionPane.showMessageDialog(null, "Información del estudiante\n" + e);
        } else {
            JOptionPane.showMessageDialog(null, "El estudiante no se encuentra registrado en el instituto");
        }
    }

    private void opcion2VO2() {
        String mensaje = "Ingresa el apellido: ";
        String apellidos = JOptionPane.showInputDialog(mensaje);

        ArrayList<Estudiante> est;

        try {
            est = controlador.getPrograma().buscarEstudiantePorApellidosBD(apellidos);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            est = new ArrayList<>();
        }

        if (est.size() != 0) {
            for (var e : est) {
                JOptionPane.showMessageDialog(null, e + "\n");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay estudiantes con ese apellido.");
        }
    }

    private void opcion3VO2() {
        String mensaje = "Ingresa la carrera: ";
        String carrera = JOptionPane.showInputDialog(mensaje);

        ArrayList<Estudiante> est;

        try {
            est = controlador.getPrograma().buscarEstudiantesPorCarreraBD(carrera);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            est = new ArrayList<>();
        }

        if (est.size() != 0) {
            String msj = "";
            for (var e : est) {
                msj += "Nombre: " + e.getNombre() + ", Apellido: " + e.getApellido() + "\n";
            }
            JOptionPane.showMessageDialog(null, msj);
        } else {
            JOptionPane.showMessageDialog(null, "No hay estudiantes en esa carrera registrados.");
        }
    }

    private void opcion4VO2() {
        String mensaje = "Ingresa la carrera: ";
        String carrera = JOptionPane.showInputDialog(mensaje);

        try {
            int x = controlador.getPrograma().contarEstudiantesPorCarreraBD(carrera);
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "No hay estudiantes en esta carrera.");
            } else {
                String msj = "El numero de estudiantes de esta carrera son: " + x;
                JOptionPane.showMessageDialog(null, msj);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    private void opcion5VO2() {
        String mensaje = "Ingresa la fecha de nacimiento.";
        String fechaNacimiento = JOptionPane.showInputDialog(mensaje);
        String datos = "";
        ArrayList<Estudiante> est;

        try {
            est = controlador.getPrograma().buscarEstudiantesPorFechaNacimientoBD(fechaNacimiento);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            est = new ArrayList<>();
        }

        if (est.size() != 0) {
            for (var e : est) {
                datos += e + "\n";
            }
            JOptionPane.showMessageDialog(null, datos);
        } else {
            JOptionPane.showMessageDialog(null, "No hay estudiantes nacidos en esa fecha.");
        }
    }

    private void opcion6VO2() {
        String mensaje = "Digita el numero del estudiante.";
        long numeroCelular;

        try {
            numeroCelular = Long.parseLong(JOptionPane.showInputDialog(mensaje));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Digite numeros solamente.");
            numeroCelular = 0;
        }

        String datos = "Datos estudiante.\n";
        Estudiante e = null;

        if (numeroCelular != 0) {
            try {
                e = controlador.getPrograma().buscarEstudiantePorTelefonoBD(numeroCelular);
            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }

            if (e != null) {
                datos += e.getNombre() + "\n";
                datos += e.getCarrera();
                JOptionPane.showMessageDialog(null, datos);
            } else {
                JOptionPane.showMessageDialog(null, "No existe ningun estudiante con este numero.");
            }
        }
    }
}
