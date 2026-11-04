/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public class Bus extends Vehiculo {

    //Constructor
    public Bus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
    }

    @Override
    public double calcularCobro(Ruta ruta, int cantidad) {
        //Se cobra la tarifa base por la cantidad de pasajeros
        return ruta.getTarifaBase() * cantidad;
    }

    @Override
    public boolean esDeCarga() {
        return false;
    }
}
