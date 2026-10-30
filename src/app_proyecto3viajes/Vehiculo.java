/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public abstract class Vehiculo{
    //Atributos privados
    private String placa;
    private int capacidad;
    private String estado;
    private double kilometraje;
    private double kmDesdeServicio;
    private boolean activo;

    public Vehiculo(String placa, int capacidad, String estado, double kilometraje, double kmDesdeServicio, boolean activo) {
        this.placa = placa;
        this.capacidad = capacidad;
        this.estado = estado;
        this.kilometraje = kilometraje;
        this.kmDesdeServicio = kmDesdeServicio;
        this.activo = activo;
    }
    
    
    
    
    public abstract double calcularCobro();
    public abstract boolean esDeCarga();
    public abstract boolean registrarKilometros();
    public abstract String aLineaTexto();
    
}