package controlador;

import model.Programa;
import java.util.Scanner;

public class Controlador {

    private Scanner lectorDatos;
    private Programa programa;

    public Controlador() {
        lectorDatos = new Scanner(System.in);
        programa = new Programa();
    }

    public Scanner getLectorDatos() {
        return lectorDatos;
    }

    public Programa getPrograma() {
        return programa;
    }

    public int capturarRetornarOpcionMenu() {
        int opcion;
        opcion = Integer.parseInt(lectorDatos.nextLine());
        return opcion;
    }

}
