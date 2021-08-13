package main;

import ui.Vista;

public class Main {

    private Vista vista;

    public Main() {
        vista = new Vista();
        vista.mostrarMenu();
    }

    public static void main(String[] args) {
        Main m = new Main();
    }

}
