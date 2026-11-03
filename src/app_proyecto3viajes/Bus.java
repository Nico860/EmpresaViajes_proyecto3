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
    
    //Constructor
    public Bus(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        super(placa, capacidad, estado, kilometraje, kmDesdeServicio, activo);
    }
    
    @Override
    public double calcularCobro(Ruta ruta, int cantidad) {
        //Se cbra la tarifa por la cantidad de pasajeros
        return ruta.getTarifaBase() * cantidad;
    }

    @Override
    public boolean esDeCarga() {
        return false;
    }

    //Acumulador de kilometros
    @Override
    public boolean registrarKilometros() {
        //Dos acumuladores
        setKilometraje(getKilometraje() + km);
        setKmDesdeServicio(getKmDesdeServicio() + km);
        
        //Verificar la regla del ultimo servicio
        if (getKmDesdeServicio() >= 5000.0){
            //Setera estado
            setEstado("En Mantenimiento");
            return true; //Se retorna true para solicitar servicio
        }
        return false;
    }
    
    //formato para guardar el bus en archivo de texto
    @Override
    public String aLineaTexto() {
        return "BUS," + getPlaca() + "," + getCapacidad() + "," + getEstado() + "," + getKilometraje() + "," + getKmDesdeServicio() + "," + isActivo();
    }
}
