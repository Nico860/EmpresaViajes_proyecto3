/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

import java.time.LocalDate;

/**
 *
 * @author angls
 */
public class ServicioMantenimiento implements Persistible {
    //Atributos privados
    private LocalDate fecha;
    private String descripcion;
    private Vehiculo vehiculo;
    
    //Constructor completo 
    public ServicioMantenimiento(LocalDate fecha, String descripcion, Vehiculo vehiculo){
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.vehiculo = vehiculo;
    }
    
    //Getters y Setters

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    //Sobre escritura en el persistible
    @Override
    public String aLineaTexto(){
        //Guardar fecha placa y descripción del servicio
        return fecha.toString() + "," + vehiculo.getPlaca() + "," + descripcion;
    }
    
    
    
}
