/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public class Ruta {
    //Atributos de la clase
    private String Origen;
    private String Destino;
    private double kilometros;
    private double tarifaBase;
    private boolean activa;
    
    
    //constructor

    public Ruta(String Origen, String Destino, double kilometros, double tarifaBase, boolean activa) {
        this.Origen = Origen;
        this.Destino = Destino;
        this.kilometros = kilometros;
        this.tarifaBase = tarifaBase;
        this.activa = activa;
    }
    
    //getters

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String Origen) {
        this.Origen = Origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String Destino) {
        this.Destino = Destino;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    //Sobreescritura del formato 
    @Override
    public void aLineaTexto(){
        return "El origen: " + getOrigen + "Destino" + getDestino + " Kilómetros: " + getKilometros
    }
    
    
    
}
