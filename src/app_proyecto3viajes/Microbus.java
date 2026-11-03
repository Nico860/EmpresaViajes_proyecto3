package app_proyecto3viajes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angls
 */
public class Microbus extends Vehiculo implements Persistible{
    
    public Microbus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo){
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
    }
    
    
    @Override
    public double calcularCobro(Ruta ruta, int cantidad){
    //Declarar varialbe del recargo fijo por pasajero
    double recargoFijo = 5.00;
    
    //Desarrollar la operación 
    return (ruta.getTarifaBase() + recargoFijo)*cantidad;
    }
    
    @Override
    public Double registrarKilometros(){
        
    }
    
    @Override
    public String aLineaTexto(){
        return ("");
    }
    
    
    
}
