/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author tzulmoran
 */
public class Piloto extends Persona implements Persistible{
    private String numLicencia;
    private String tipoLicencia;
    private boolean activo;

    public Piloto(String dpi, String nombre, String numLicencia, String tipoLicencia, boolean activo) {
        super(dpi, nombre);
        this.numLicencia = numLicencia;
        this.tipoLicencia = tipoLicencia;
        this.activo = activo;
    }

    public String getNumLicencia() {
        return numLicencia;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setNumLicencia(String numLicencia) {
        this.numLicencia = numLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    @Override
    public String descripcion(){
        return nombre + "(DPI: " + dpi + "Licencia: " + numLicencia + ")";
    }
    
    
    public String aLineaTexto(){
        return dpi + ", " + nombre + ", " + numLicencia + ", " + tipoLicencia + ", " + activo; 
    }
    
    
    
    
    
    
}
