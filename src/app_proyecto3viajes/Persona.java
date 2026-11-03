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
    //Crear accesos directos con protectec para 
    //las clases hijas 
    protected String dpi;
    protected String nombre;

    //Constructor
    public Persona(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }
    
    //Métodos Getter y Setter

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    //Método abstracto sin cuerpo
    public abstract String descripcion();
    
    

    
    
    
}
