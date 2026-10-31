/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public class PickupCarga {
    
    @Override
    public double calcularCobro(Ruta ruta, int cantidad){
        //Definir el costo de flete por quintal
        double precioPorQuintal = 15.00;
        
        //Retornar el precio multiplicado por cantidad de quintales
        return precioPorQuintal * cantidad;
    }
    
}
