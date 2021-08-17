package model;

import model.Estudiante;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class Programa {

    private final String USUARIO = "root";
    private final String CONTRASEÑA = "";
    private final String URL = "jdbc:mysql://localhost:3306/db_estudiantes";

    private ArrayList<Estudiante> estudiantes;

    public Programa() {
        estudiantes = new ArrayList<>();
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void agregarEstudiante(String nombre, String apellido, String fechaNacimiento,
            String correoInstitucional, String correoPersonal,
            long numeroCelular, long numeroFijo, String carrera) {
        Estudiante e = new Estudiante(nombre, apellido, fechaNacimiento,
                correoInstitucional, correoPersonal, numeroCelular, numeroFijo, carrera);
        estudiantes.add(e);
    }

    public boolean existenciaEstudiante(String correoInstitucional) {
        boolean existe = false;
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getCorreoInstitucional().equals(correoInstitucional)) {
                existe = true;
            }
        }
        return existe;
    }

    public Estudiante buscarEstudiante(String correoInstitucional) {
        Estudiante estudiante = null;
        boolean existe = existenciaEstudiante(correoInstitucional);
        if (existe) {
            for (int i = 0; i < estudiantes.size(); i++) {
                if (estudiantes.get(i).getCorreoInstitucional().equals(correoInstitucional)) {
                    estudiante = estudiantes.get(i);
                }
            }
        }
        return estudiante;
    }

    public void modificarDatosEstudiante(String correoInstitucional, String correoPersonal,
            long numeroCelular, long numeroFijo, String carrera) {
        boolean existe = existenciaEstudiante(correoInstitucional);
        if (existe) {
            for (int i = 0; i < estudiantes.size(); i++) {
                if (estudiantes.get(i).getCorreoInstitucional().equals(correoInstitucional)) {
                    estudiantes.get(i).setCorreoPersonal(correoPersonal);
                    estudiantes.get(i).setNumeroCelular(numeroCelular);
                    estudiantes.get(i).setNumeroFijo(numeroFijo);
                    estudiantes.get(i).setCarrera(carrera);
                }
            }
        }
    }

    public void eliminarEstudiante(String correoInstitucional) {
        boolean existe = existenciaEstudiante(correoInstitucional);
        if (existe) {
            for (int i = 0; i < estudiantes.size(); i++) {
                if (estudiantes.get(i).getCorreoInstitucional().equals(correoInstitucional)) {
                    estudiantes.remove(i);
                }
            }
        }
    }

    public void cargarEstudiantesDesdeBD()
            throws ClassNotFoundException, SQLException {
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        String nombres;
        String apellidos;
        String fechaNacimiento;
        String correoInstitucional;
        String correoPersonal;
        long numeroCelular;
        long numeroFijo;
        String carrera;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        ps = connection.prepareStatement(
                "SELECT * FROM estudiantes;"
        );
        rs = ps.executeQuery();

        while (rs.next()) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            fechaNacimiento = rs.getString("FechaNacimiento");
            correoInstitucional = rs.getString("CorreoInstitucional");
            correoPersonal = rs.getString("CorreoPersonal");
            numeroCelular = Long.parseLong(rs.getString("NumeroCelular"));
            numeroFijo = Long.parseLong(rs.getString("NumeroFijo"));
            carrera = rs.getString("Carrera");
            estudiantes.add(new Estudiante(
                    nombres, apellidos, fechaNacimiento, correoInstitucional,
                    correoPersonal, numeroCelular, numeroFijo,
                    carrera
            ));
        }
    }

    public void agregarEstudianteDB(String nombres, String apellidos, String fechaNacimiento, String correoInstitucional, String correoPersonal, long numeroCelular,
            long numeroFijo, String carrera)
            throws ClassNotFoundException, SQLException {
        Connection connection;
        PreparedStatement ps;
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        ps = connection.prepareStatement(
                "INSERT INTO estudiantes(Nombres, Apellidos, FechaNacimiento,"
                + "CorreoInstitucional, CorreoPersonal, NumeroCelular,"
                + "NumeroFijo, Carrera) VALUES(?, ?, ?, ?, ?, ?, ?, ?);"
        );
        ps.setString(1, nombres);
        ps.setString(2, apellidos);
        ps.setString(3, fechaNacimiento);
        ps.setString(4, correoInstitucional);
        ps.setString(5, correoPersonal);
        ps.setString(6, "" + numeroCelular);
        ps.setString(7, "" + numeroFijo);
        ps.setString(8, carrera);
        ps.executeUpdate();
        agregarEstudiante(nombres, apellidos, fechaNacimiento, correoInstitucional, correoPersonal, numeroCelular, numeroFijo, carrera);
    }

    public void eliminarEstudianteDB(String correoInstitucional)
            throws ClassNotFoundException, SQLException {
        Connection connection;
        PreparedStatement ps;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        ps = connection.prepareStatement(
                "DELETE FROM estudiantes WHERE CorreoInstitucional=?;"
        );
        ps.setString(1, correoInstitucional);
        ps.executeUpdate();
        eliminarEstudiante(correoInstitucional);
    }

    public void modificarDatosEstudianteDB(String correoInstitucional, String correoPersonal, long numeroCelular,
            long numeroFijo, String carrera)
            throws ClassNotFoundException, SQLException {
        Connection connection;
        PreparedStatement ps;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        ps = connection.prepareStatement(
                "UPDATE estudiantes SET CorreoPersonal=?, NumeroCelular=?,"
                + "NumeroFijo=?, Carrera=? WHERE CorreoInstitucional=?;"
        );
        ps.setString(5, correoInstitucional);
        ps.setString(1, correoPersonal);
        ps.setString(2, "" + numeroCelular);
        ps.setString(3, "" + numeroFijo);
        ps.setString(4, carrera);
        ps.executeUpdate();
        
        modificarDatosEstudiante(correoInstitucional, correoPersonal, numeroCelular, numeroFijo, carrera);
    }
    
    public Estudiante buscarEstudianteBD( String correoInstitucional )
    throws ClassNotFoundException, SQLException 
    {
        Estudiante estudiante = null;
        
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        
        String nombres;
        String apellidos;
        String fechaNacimiento;
        String correoPersonal;
        long numeroCelular;
        long numeroFijo;
        String carrera;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        
        ps = connection.prepareStatement(
            "SELECT * FROM estudiantes WHERE CorreoInstitucional=?;"
        );
        ps.setString(1, correoInstitucional);
        rs = ps.executeQuery();
        
        if ( rs.next() ) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            fechaNacimiento = rs.getString("FechaNacimiento");
            correoInstitucional = rs.getString("CorreoInstitucional");
            correoPersonal = rs.getString("CorreoPersonal");
            numeroCelular = Long.parseLong(rs.getString("NumeroCelular"));
            numeroFijo = Long.parseLong(rs.getString("NumeroFijo"));
            carrera = rs.getString("Carrera");
            estudiante = new Estudiante(nombres, apellidos, fechaNacimiento, 
                    correoInstitucional, correoPersonal, numeroCelular, 
                    numeroFijo, carrera);
        }
        return estudiante;
    }
    
    public ArrayList<Estudiante> buscarEstudiantePorApellidosBD( String apellidos )
    throws ClassNotFoundException, SQLException
    {
        ArrayList<Estudiante> estudiantesBD = new ArrayList<>();
        
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        
        String nombres;
        String fechaNacimiento;
        String correoInstitucional;
        String correoPersonal;
        long numeroCelular;
        long numeroFijo;
        String carrera;
        
        ps = connection.prepareStatement
        (
           "SELECT * FROM estudiantes WHERE Apellidos=?;"
        );
        ps.setString(1, apellidos);
        rs = ps.executeQuery();
        
        while ( rs.next() ) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            fechaNacimiento = rs.getString("FechaNacimiento");
            correoInstitucional = rs.getString("CorreoInstitucional");
            correoPersonal = rs.getString("CorreoPersonal");
            numeroCelular = Long.parseLong(rs.getString("NumeroCelular"));
            numeroFijo = Long.parseLong(rs.getString("NumeroFijo"));
            carrera = rs.getString("Carrera");
            estudiantesBD.add(new Estudiante
                (nombres, apellidos, fechaNacimiento, correoInstitucional, correoPersonal,
                numeroCelular, numeroFijo, carrera)
            );
        }
        
        return estudiantesBD;
    }
    
    public ArrayList<Estudiante> buscarEstudiantesPorCarreraBD( String carrera )
    throws ClassNotFoundException, SQLException
    {
        ArrayList<Estudiante> estudiantesBD = new ArrayList<>();
        
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        
        String nombres;
        String apellidos;
        
        ps = connection.prepareStatement
        (
           "SELECT Nombres, Apellidos FROM estudiantes WHERE Carrera=?;"
        );
        ps.setString(1, carrera);
        rs = ps.executeQuery();
        
        while ( rs.next() ) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            estudiantesBD.add(new Estudiante(nombres, apellidos));
        }
        
        return estudiantesBD;
    }
    
    public int contarEstudiantesPorCarreraBD( String carrera )
    throws ClassNotFoundException, SQLException
    {
        int resultado;
        
        ArrayList<Estudiante> estudiantesBD = new ArrayList<>();
        
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        
        String nombres;
        String apellidos;
        
        ps = connection.prepareStatement
        (
           "SELECT Nombres, Apellidos FROM estudiantes WHERE Carrera=?;"
        );
        ps.setString(1, carrera);
        rs = ps.executeQuery();
        
        while ( rs.next() ) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            estudiantesBD.add(new Estudiante(nombres, apellidos));
        }
        
        resultado = estudiantesBD.size();
        
        return resultado;
    }
    
    public ArrayList<Estudiante> buscarEstudiantesPorFechaNacimientoBD( String fechaNacimiento )
    throws ClassNotFoundException, SQLException   
    {
        ArrayList<Estudiante> estudiantesBD = new ArrayList<>();
        
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        
        String nombres;
        String apellidos;
        String correoInstitucional;
        String correoPersonal;
        long numeroCelular;
        long numeroFijo;
        String carrera;
        
        ps = connection.prepareStatement
        (
           "SELECT * FROM estudiantes WHERE FechaNacimiento=?;"
        );
        ps.setString(1, fechaNacimiento);
        rs = ps.executeQuery();
        
        while ( rs.next() ) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            fechaNacimiento = rs.getString("FechaNacimiento");
            correoInstitucional = rs.getString("CorreoInstitucional");
            correoPersonal = rs.getString("CorreoPersonal");
            numeroCelular = Long.parseLong(rs.getString("NumeroCelular"));
            numeroFijo = Long.parseLong(rs.getString("NumeroFijo"));
            carrera = rs.getString("Carrera");
            estudiantesBD.add(new Estudiante
                (nombres, apellidos, fechaNacimiento, correoInstitucional, correoPersonal,
                numeroCelular, numeroFijo, carrera)
            );
        }
        
        return estudiantesBD;
    }
    
    public Estudiante buscarEstudiantePorTelefonoBD( long numeroCelular )
    throws ClassNotFoundException, SQLException 
    {
        Estudiante estudiante = null;
        
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        
        String nombres;
        String apellidos;
        String fechaNacimiento;
        String correoInstitucional;
        String correoPersonal;
        long numeroFijo;
        String carrera;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        
        ps = connection.prepareStatement(
            "SELECT * FROM estudiantes WHERE NumeroCelular=?;"
        );
        ps.setString(1, "" + numeroCelular);
        rs = ps.executeQuery();
        
        if ( rs.next() ) {
            nombres = rs.getString("Nombres");
            apellidos = rs.getString("Apellidos");
            fechaNacimiento = rs.getString("FechaNacimiento");
            correoInstitucional = rs.getString("CorreoInstitucional");
            correoPersonal = rs.getString("CorreoPersonal");
            numeroCelular = Long.parseLong(rs.getString("NumeroCelular"));
            numeroFijo = Long.parseLong(rs.getString("NumeroFijo"));
            carrera = rs.getString("Carrera");
            estudiante = new Estudiante(nombres, apellidos, fechaNacimiento, 
                    correoInstitucional, correoPersonal, numeroCelular, 
                    numeroFijo, carrera);
        }
        return estudiante;

    }

}
