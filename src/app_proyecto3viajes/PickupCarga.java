/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public class PickupCarga extends Vehiculo {

    //Flete por quintal (Q)
    public static final double FLETE_POR_DEFECTO = 15.00;

    private double flete;

    public PickupCarga(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        this(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo, FLETE_POR_DEFECTO);
    }

    public PickupCarga(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo, double flete) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
        this.flete = flete;
    }

    public double getFlete() { return flete; }
    public void setFlete(double flete) { this.flete = flete; }

    @Override
    public double calcularCobro(Ruta ruta, int cantidad) {
        //Flete por quintal multiplicado por la cantidad de quintales
        return flete * cantidad;
    }

    //La pickup transporta carga (la cantidad son quintales)
    @Override
    public boolean esDeCarga() {
        return true;
    }
}
