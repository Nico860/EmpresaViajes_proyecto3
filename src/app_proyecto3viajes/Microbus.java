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
    public static final double RECARGO_POR_DEFECTO = 5.00;

    private double recargo;

    public Microbus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        this(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo, RECARGO_POR_DEFECTO);
    }

    public Microbus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo, double recargo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
        this.recargo = recargo;
    }

    public double getRecargo() { return recargo; }
    public void setRecargo(double recargo) { this.recargo = recargo; }

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
