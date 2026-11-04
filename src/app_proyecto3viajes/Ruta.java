/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author angls
 */
public class Ruta implements Persistible {
    //Atributos de la clase
    private String origen;
    private String destino;
    private double kilometros;
    private double tarifaBase;
    private boolean activa;
    
    
    //constructor

    public Ruta(String origen, String destino, double kilometros, double tarifaBase, boolean activa) {
        this.origen = origen;
        this.destino = destino;
        this.kilometros = kilometros;
        this.tarifaBase = tarifaBase;
        this.activa = activa;
    }
    
    //getters

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
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
    
    //Formato para guardar la ruta en archivo de texto
    @Override
    public String aLineaTexto() {
        return getOrigen() + "," + getDestino() + "," + getKilometros() + "," + getTarifaBase() + "," + isActiva();
    }

}
