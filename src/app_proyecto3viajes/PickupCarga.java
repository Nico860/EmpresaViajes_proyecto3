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

    public PickupCarga(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
    }
    
    
    @Override
    public double calcularCobro(Ruta ruta, int cantidad){
        //Definir el costo de flete por quintal
        double precioPorQuintal = 15.00;
        
        //Retornar el precio multiplicado por cantidad de quintales
        return precioPorQuintal * cantidad;
    }
    
    //Verificar si es de carga
    @Override 
    public boolean esDeCarga(){
        return true;
    }
    
    //Contador de kilometros
    @Override
    public boolean registrarKilometros(double km){
        setKilometraje(getKilometraje() + km);
        setKmDesdeServicio(getKmDesdeServicio() + km);
        
        if(getKmDesdeServicio() >= 5000.0){
            setEstado("En Mantenimiento");
            return true;
        }
        return false;
    }
    
    //Formato del texto en archivo
    @Override
    public String aLineaTexto() {
        return "PICKUP," + getPlaca() + "," + getCapacidad() + "," + getEstado() + "," + getKilometraje() + "," + getKmDesdeServicio() + "," + isActivo();
    }
}
