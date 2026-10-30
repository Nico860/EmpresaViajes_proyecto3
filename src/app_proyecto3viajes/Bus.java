/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public class Bus extends Vehiculo{

    public Bus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
    }

    @Override
    public double calcularCobro() {
        return Viaje.getCapacidad() * 2.50;
    }

    @Override
    public boolean esDeCarga() {
        if(
    }

    @Override
    public boolean registrarKilometros() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String aLineaTexto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    
    
    
    
}
