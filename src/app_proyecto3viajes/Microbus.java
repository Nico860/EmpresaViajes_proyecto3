package app_proyecto3viajes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angls
 */
public class Microbus extends Vehiculo {

    //Recargo fijo por pasajero (Q)
    private double recargo;

    public Microbus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
        this.recargo = 5.00;
    }

    @Override
    public double calcularCobro(Ruta ruta, int cantidad) {
        //Tarifa base más el recargo, por cada pasajero
        return (ruta.getTarifaBase() + recargo) * cantidad;
    }

    //El microbus transporta personas
    @Override
    public boolean esDeCarga() {
        return false;
    }
}
