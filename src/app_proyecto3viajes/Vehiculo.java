/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public abstract class Vehiculo implements Persistible {
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

    //Getters y setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getKilometraje() { return kilometraje; }
    public void setKilometraje(double kilometraje) { this.kilometraje = kilometraje; }

    public double getKmDesdeServicio() { return kmDesdeServicio; }
    public void setKmDesdeServicio(double kmDesdeServicio) { this.kmDesdeServicio = kmDesdeServicio; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    //Métodos que define lo que se hace con todo vehículo
    public abstract double calcularCobro(Ruta ruta, int cantidad);
    public abstract boolean esDeCarga();
    public abstract boolean registrarKilometros(double km);
    // aLineaTexto() viene de Persistible y lo implementa cada hijo
}
