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
    //Estados válidos de un vehículo
    public static final String ESTADO_ACTIVO = "Activo";
    public static final String ESTADO_MANTENIMIENTO = "En Mantenimiento";

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

    //Métodos que cada tipo de vehículo debe definir
    public abstract double calcularCobro(Ruta ruta, int cantidad);
    public abstract boolean esDeCarga();

    //Acumula los kilómetros recorridos (comportamiento común a todo vehículo).
    //Retorna true si el vehículo alcanzó el kilometraje de servicio y pasa a mantenimiento.
    public boolean registrarKilometros(double km) {
        kilometraje += km;
        kmDesdeServicio += km;

        if (kmDesdeServicio >= Empresa.KM_MANTENIMIENTO) {
            estado = ESTADO_MANTENIMIENTO;
            return true;
        }
        return false;
    }

    //Nombre del tipo de vehículo (Bus, Microbus, PickupCarga)
    public String getTipo() {
        return getClass().getSimpleName();
    }

    //Formato para guardar el vehículo en archivo de texto
    @Override
    public String aLineaTexto() {
        return getTipo().toUpperCase() + "," + placa + "," + capacidad + "," + estado + ","
                + kilometraje + "," + kmDesdeServicio + "," + activo;
    }
}
