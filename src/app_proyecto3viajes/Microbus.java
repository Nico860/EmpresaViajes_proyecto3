package app_proyecto3viajes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angls
 */
public class Microbus {
    
    @Override
    public double calcularCobro(Ruta ruta, int cantidad){
    //Declarar varialbe del recargo fijo por pasajero
    double recargoFijo = 5.00;
    
    //Desarrollar la operación 
    return (ruta.getTarifaBase() + recargoFijo)*cantidad;
    }
    
    
    
}
