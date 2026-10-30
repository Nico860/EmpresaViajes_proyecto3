/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;

/**
 *
 * @author tzulmoran
 */
public abstract class Persona {
    private String dpi;
    private String nombre;

    public Persona(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }


    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public abstract String descripcion();
    
    
    
    
}
