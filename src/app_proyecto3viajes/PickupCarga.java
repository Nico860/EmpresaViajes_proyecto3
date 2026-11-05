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
    private double flete;

    public PickupCarga(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
        this.flete = 15.00;
    }

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
