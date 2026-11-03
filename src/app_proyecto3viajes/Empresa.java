/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author angls
 */
public class Empresa {
    // listas de coleccion en memoria 
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Piloto> pilotos;
    private ArrayList<Ruta> rutas;
    private ArrayList<Viaje> viajes;
    private ArrayList<ServicioMantenimiento> mantenimientos;
        
    //Kilometraje de mantenimiento
    public static final double KM_MANTENIMIENTO = 5000.0;
        
    //Constructor
    public Empresa() {
    vehiculos = new ArrayList<>();
    pilotos = new ArrayList<>();
    rutas = new ArrayList<>();
    viajes = new ArrayList<>();
    mantenimientos = new ArrayList<>();
    }
    
    // Getters para consultar listas de la GUI
    public ArrayList<Vehiculo> getVehiculos() { return vehiculos; }
    public ArrayList<Piloto> getPilotos() { return pilotos; }
    public ArrayList<Ruta> getRutas() { return rutas; }
    public ArrayList<Viaje> getViajes() { return viajes; }
    public ArrayList<ServicioMantenimiento> getMantenimientos() { return mantenimientos; }
    
    //Verificación de conflictos de vehiculo, piloto, fecha y hora.
    public boolean hayChoque(Vehiculo vehiculo, Piloto piloto, LocalDate fecha, LocalTime hora){
        for (Viaje v : viajes) {
                // Si el viaje inspeccionado coincide en fecha y hora...
                if (v.chocaCon(fecha, hora)) {
                    // ...y además coincide en el mismo vehículo o mismo piloto
                    if (v.getVehiculo().getPlaca().equalsIgnoreCase(vehiculo.getPlaca()) ||
                        v.getPiloto().getDpi().equalsIgnoreCase(piloto.getDpi())) {
                        return true; // ¡Existe un choque de horario!
                    }
                } 
        }
        return false;
    }
    
    public String registrarViaje(Ruta ruta, Vehiculo vehiculo, Piloto piloto, LocalDate fecha, LocalTime hora, int cantidad){
        if (vehiculo.getEstado().equalsIgnoreCase("En Mantenimiento")) {
            return "ERROR: El vehículo " + vehiculo.getPlaca() + " está 'En Mantenimiento' y no puede salir a viajar.";
        }
        
        // RF-06: Validación de capacidad (no cero, no negativa, no mayor a capacidad)
        if (cantidad <= 0) {
            return "ERROR: La cantidad debe ser un número mayor a cero.";
        }
        if (cantidad > vehiculo.getCapacidad()) {
            return "ERROR: La cantidad (" + cantidad + ") supera la capacidad máxima del vehículo (" + vehiculo.getCapacidad() + ").";
        }

        // RF-07: Validación de tipo de servicio (Pickup = solo carga, Bus/Microbus = solo pasajeros)
        // Usamos el método polimórfico 'esDeCarga()' que responde cada tipo de vehículo
        boolean esTipoCarga = vehiculo.esDeCarga(); 
        if (esTipoCarga) {
            // Es un Pickup: la cantidad representa quintales
            // (La interfaz de usuario debe asegurar indicar si es carga)
        } else {
            // Es un Bus o Microbús: la cantidad representa pasajeros
        }

        // RF-09: Validación de choque de fechas y horas
        if (hayChoque(vehiculo, piloto, fecha, hora)) {
            return "ERROR: El vehículo o el piloto ya tienen un viaje agendado exactamente a esa misma fecha y hora.";
        }

        // --- SI PASA TODAS LAS VALIDACIONES, SE PROCESA EL VIAJE ---

        // POLIMORFISMO REAL: El cobro se calcula llamando a calcularCobro sin importar si es Bus, Microbus o Pickup
        double cobroCalculado = vehiculo.calcularCobro(ruta, cantidad);

        // Crear y guardar el nuevo objeto Viaje
        Viaje nuevoViaje = new Viaje(fecha, hora, ruta, vehiculo, piloto, cantidad, cobroCalculado);
        viajes.add(nuevoViaje);

        // RF-10: Acumular kilómetros recorridos y verificar si requiere mantenimiento
        boolean necesitaMantenimiento = vehiculo.registrarKilometros(ruta.getKilometros());

        // Construir mensaje de éxito
        String mensajeRespuesta = "¡Viaje registrado con éxito!\nCobro Total: Q " + String.format("%.2f", cobroCalculado);
        
        if (necesitaMantenimiento) {
            mensajeRespuesta += "\n AVISO: El vehículo alcanzó los 5,000 km desde su último servicio y ha pasado a estado 'En Mantenimiento'.";
        }

        return mensajeRespuesta;
    }
    
    //Método para cambiar el estado del vehículo a "Activo", resetea sus km de servicio a 0 y registra la entrada en el historial.
    
    public String registrarMantenimiento(Vehiculo vehiculo, LocalDate fecha, String descripcion) {
        // Resetear contador de km desde último servicio a 0 y cambiar estado a "Activo"
        vehiculo.setKmDesdeServicio(0.0);
        vehiculo.setEstado("Activo");

        // Guardar el registro de servicio en la lista de mantenimientos
        ServicioMantenimiento registro = new ServicioMantenimiento(fecha, descripcion, vehiculo);
        mantenimientos.add(registro);

        return "Servicio de mantenimiento registrado correctamente. El vehículo " + vehiculo.getPlaca() + " vuelve a estar Activo.";
    }
}
