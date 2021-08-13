package model;

import model.Persona;

public class Estudiante extends Persona {

    private String correoInstitucional;
    private String correoPersonal;
    private long numeroCelular;
    private long numeroFijo;
    private String carrera;

    public Estudiante(String nombre, String apellido, String fechaNacimiento,
            String correoInstitucional, String correoPersonal,
            long numeroCelular, long numeroFijo, String carrera) {
        super(nombre, apellido, fechaNacimiento);
        this.correoInstitucional = correoInstitucional;
        this.correoPersonal = correoPersonal;
        this.numeroCelular = numeroCelular;
        this.numeroFijo = numeroFijo;
        this.carrera = carrera;
    }
    
    public Estudiante ( String nombres, String apellidos ) {
        super(nombres, apellidos);
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public long getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(long numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public long getNumeroFijo() {
        return numeroFijo;
    }

    public void setNumeroFijo(long numeroFijo) {
        this.numeroFijo = numeroFijo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    @Override
    public String toString() {
        String datos = "";
        datos += "Nombres: " + super.getNombre() + "\n";
        datos += "Apellidos: " + super.getApellido() + "\n";
        datos += "Fecha nacimiento: " + super.getFechaNacimiento() + "\n";
        datos += "Correo institucional: " + correoInstitucional + "\n";
        datos += "Correo personal: " + correoPersonal + "\n";
        datos += "Número de teléfono celular: " + numeroCelular + "\n";
        datos += "Número de teléfono fijo: " + numeroFijo + "\n";
        datos += "Programa académico: " + carrera + "\n";
        return datos;
    }
}
