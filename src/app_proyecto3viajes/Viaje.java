/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author tzulmoran
 */
public class Viaje implements Serializable {
    
    //Atributos privados
    private LocalDate fecha;
    private LocalTime hora;
    private Ruta ruta;
    private Vehiculo vehiculo;
    private Piloto piloto;
    private int cantidad;
    private double cobro;

    public Viaje(LocalDate fecha, LocalTime hora, Ruta ruta, Vehiculo vehiculo, Piloto piloto, int cantidad, double cobro) {
        this.fecha = fecha;
        this.hora = hora;
        this.ruta = ruta;
        this.vehiculo = vehiculo;
        this.piloto = piloto;
        this.cantidad = cantidad;
        this.cobro = cobro;
    }
    
    //Getters y setters

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCobro() {
        return cobro;
    }

    public void setCobro(double cobro) {
        this.cobro = cobro;
    }
    
    //Validación de conflictos: otro viaje choca con éste si es a la misma fecha y hora
    //y comparte el vehículo o el piloto. Retorna true si hay conflicto.
    public boolean chocaCon(Viaje otro) {
        if (!this.fecha.equals(otro.fecha) || !this.hora.equals(otro.hora)) {
            return false;
        }
        boolean mismoVehiculo = this.vehiculo.getPlaca().equalsIgnoreCase(otro.vehiculo.getPlaca());
        boolean mismoPiloto = this.piloto.getDpi().equalsIgnoreCase(otro.piloto.getDpi());
        return mismoVehiculo || mismoPiloto;
    }
}
