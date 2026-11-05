/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_proyecto3viajes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

                rutas.add(new Ruta("Cobán", "San Pedro Carchá", 7.0, 5.00, true));
        rutas.add(new Ruta("Cobán", "San Cristóbal Verapaz", 17.0, 10.00, true));
        rutas.add(new Ruta("Cobán", "Tactic", 36.0, 15.00, true));
    }

    // Getters para consultar listas de la GUI (incluyen registros dados de baja)
    public ArrayList<Vehiculo> getVehiculos() { return vehiculos; }
    public ArrayList<Piloto> getPilotos() { return pilotos; }
    public ArrayList<Ruta> getRutas() { return rutas; }
    public ArrayList<Viaje> getViajes() { return viajes; }
    public ArrayList<ServicioMantenimiento> getMantenimientos() { return mantenimientos; }

    //Un campo de texto no puede estar vacío ni contener comas (los archivos de texto se separan por comas)
    private void exigirTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede estar vacío.");
        }
        if (valor.contains(",") || valor.contains("\n") || valor.contains("\r")) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede contener comas ni saltos de línea.");
        }
    }

    
    //Registra un vehículo validando campos, números y placa repetida.
    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo a registrar.");
        }
        exigirTexto(vehiculo.getPlaca(), "Placa");
        if (vehiculo.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número mayor que cero.");
        }
        if (vehiculo.getKilometraje() < 0) {
            throw new IllegalArgumentException("El kilometraje no puede ser negativo.");
        }
        if (vehiculo.getKmDesdeServicio() < 0) {
            throw new IllegalArgumentException("Los kilómetros desde el último servicio no pueden ser negativos.");
        }
        if (vehiculo.getKmDesdeServicio() > vehiculo.getKilometraje()) {
            throw new IllegalArgumentException("Los kilómetros desde el último servicio no pueden superar el kilometraje total.");
        }
        cambiarEstado(vehiculo, vehiculo.getEstado());   //valida y normaliza el estado

        vehiculo.setPlaca(vehiculo.getPlaca().trim().toUpperCase());
        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Ya existe un vehículo con la placa " + vehiculo.getPlaca() + ".");
        }
        vehiculos.add(vehiculo);
    }

    //Busca un vehículo por placa (activo o inactivo). Retorna null si no existe.
    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null) {
            return null;
        }
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa.trim())) {
                return v;
            }
        }
        return null;
    }

    //Modifica la capacidad de un vehículo existente.
    public void modificarVehiculo(String placa, int capacidad) {
        Vehiculo v = buscarVehiculo(placa);
        if (v == null) {
            throw new IllegalArgumentException("No existe un vehículo con la placa " + placa + ".");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número mayor que cero.");
        }
        v.setCapacidad(capacidad);
    }

   
    public void darBajaVehiculo(String placa) {
        Vehiculo v = buscarVehiculo(placa);
        if (v == null) {
            throw new IllegalArgumentException("No existe un vehículo con la placa " + placa + ".");
        }
        vehiculos.remove(v);
        mantenimientos.removeIf(m -> m.getVehiculo() == v);   //sus servicios dejan de tener a quién pertenecer
    }

    public ArrayList<Vehiculo> getVehiculosActivos() {
        ArrayList<Vehiculo> activos = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.isActivo()) {
                activos.add(v);
            }
        }
        return activos;
    }

   
    //Registra un piloto validando campos y rechazando DPI o licencia repetidos.
    public void agregarPiloto(Piloto piloto) {
        if (piloto == null) {
            throw new IllegalArgumentException("Debe indicar el piloto a registrar.");
        }
        exigirTexto(piloto.getDpi(), "DPI");
        exigirTexto(piloto.getNombre(), "Nombre");
        exigirTexto(piloto.getNumLicencia(), "Número de licencia");
        exigirTexto(piloto.getTipoLicencia(), "Tipo de licencia");

        piloto.setDpi(piloto.getDpi().trim());
        piloto.setNombre(piloto.getNombre().trim());
        piloto.setNumLicencia(piloto.getNumLicencia().trim());
        piloto.setTipoLicencia(piloto.getTipoLicencia().trim());

        if (buscarPiloto(piloto.getDpi()) != null) {
            throw new IllegalArgumentException("Ya existe un piloto con el DPI " + piloto.getDpi() + ".");
        }
        for (Piloto p : pilotos) {
            if (p.getNumLicencia().equalsIgnoreCase(piloto.getNumLicencia())) {
                throw new IllegalArgumentException("Ya existe un piloto con el número de licencia " + piloto.getNumLicencia() + ".");
            }
        }
        pilotos.add(piloto);
    }

    //Busca un piloto por DPI (activo o inactivo). Retorna null si no existe.
    public Piloto buscarPiloto(String dpi) {
        if (dpi == null) {
            return null;
        }
        for (Piloto p : pilotos) {
            if (p.getDpi().equalsIgnoreCase(dpi.trim())) {
                return p;
            }
        }
        return null;
    }
    
    public void darBajaPiloto(String dpi) {
        Piloto p = buscarPiloto(dpi);
        if (p == null) {
            throw new IllegalArgumentException("No existe un piloto con el DPI " + dpi + ".");
        }
        pilotos.remove(p);
    }

    public ArrayList<Piloto> getPilotosActivos() {
        ArrayList<Piloto> activos = new ArrayList<>();
        for (Piloto p : pilotos) {
            if (p.isActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }

    
    //Registra una ruta validando que kilómetros y tarifa sean mayores que cero.
    public void agregarRuta(Ruta ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("Debe indicar la ruta a registrar.");
        }
        exigirTexto(ruta.getOrigen(), "Origen");
        exigirTexto(ruta.getDestino(), "Destino");

        ruta.setOrigen(ruta.getOrigen().trim());
        ruta.setDestino(ruta.getDestino().trim());

        if (ruta.getOrigen().equalsIgnoreCase(ruta.getDestino())) {
            throw new IllegalArgumentException("El origen y el destino deben ser distintos.");
        }
        if (!(ruta.getKilometros() > 0)) {
            throw new IllegalArgumentException("Los kilómetros deben ser un número mayor que cero.");
        }
        if (!(ruta.getTarifaBase() > 0)) {
            throw new IllegalArgumentException("La tarifa base debe ser un número mayor que cero.");
        }
        if (buscarRuta(ruta.getOrigen(), ruta.getDestino()) != null) {
            throw new IllegalArgumentException("Ya existe la ruta " + ruta.getOrigen() + " - " + ruta.getDestino() + ".");
        }
        rutas.add(ruta);
    }

    //Busca una ruta por origen y destino (activa o dada de baja). Retorna null si no existe.
    public Ruta buscarRuta(String origen, String destino) {
        if (origen == null || destino == null) {
            return null;
        }
        for (Ruta r : rutas) {
            if (r.getOrigen().equalsIgnoreCase(origen.trim()) && r.getDestino().equalsIgnoreCase(destino.trim())) {
                return r;
            }
        }
        return null;
    }

   
    public void darBajaRuta(String origen, String destino) {
        Ruta r = buscarRuta(origen, destino);
        if (r == null) {
            throw new IllegalArgumentException("No existe la ruta " + origen + " - " + destino + ".");
        }
        rutas.remove(r);
    }

    public ArrayList<Ruta> getRutasActivas() {
        ArrayList<Ruta> activas = new ArrayList<>();
        for (Ruta r : rutas) {
            if (r.isActiva()) {
                activas.add(r);
            }
        }
        return activas;
    }

   
    public boolean hayChoque(Vehiculo vehiculo, Piloto piloto, LocalDate fecha, LocalTime hora){
        //Viaje candidato solo para comparar (sin ruta ni cobro)
        Viaje candidato = new Viaje(fecha, hora, null, vehiculo, piloto, 0, 0);
        for (Viaje v : viajes) {
            if (v.chocaCon(candidato)) {
                return true;
            }
        }
        return false;
    }

    //Registra un viaje. El cobro lo calcula el propio vehículo (polimorfismo).
    //Retorna el viaje creado (el cobro se lee con getCobro()).
    public Viaje registrarViaje(Ruta ruta, Vehiculo vehiculo, Piloto piloto, LocalDate fecha, LocalTime hora, int cantidad){
        if (ruta == null) {
            throw new IllegalArgumentException("Debe seleccionar una ruta.");
        }
        if (piloto == null) {
            throw new IllegalArgumentException("Debe seleccionar un piloto.");
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe seleccionar un vehículo.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("Debe indicar la fecha del viaje.");
        }
        if (hora == null) {
            throw new IllegalArgumentException("Debe indicar la hora del viaje.");
        }

    
        if (!ruta.isActiva()) {
            throw new IllegalArgumentException("La ruta " + ruta.getOrigen() + " - " + ruta.getDestino() + " está inactiva.");
        }
        if (!piloto.isActivo()) {
            throw new IllegalArgumentException("El piloto " + piloto.getNombre() + " está inactivo.");
        }
        if (!vehiculo.isActivo()) {
            throw new IllegalArgumentException("El vehículo " + vehiculo.getPlaca() + " está inactivo.");
        }

        if (vehiculo.getEstado().equalsIgnoreCase("En Mantenimiento")) {
            throw new IllegalArgumentException("El vehículo " + vehiculo.getPlaca() + " está 'En Mantenimiento' y no puede salir a viajar.");
        }


        String unidad = vehiculo.esDeCarga() ? "quintales" : "pasajeros";
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de " + unidad + " debe ser un número mayor que cero.");
        }
        if (cantidad > vehiculo.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de " + unidad + " (" + cantidad + ") supera la capacidad máxima del vehículo (" + vehiculo.getCapacidad() + ").");
        }


        if (hayChoque(vehiculo, piloto, fecha, hora)) {
            throw new IllegalArgumentException("El vehículo o el piloto ya tienen un viaje registrado exactamente a esa misma fecha y hora.");
        }

        //El cobro se calcula llamando a calcularCobro sin importar si es Bus, Microbus o Pickup
        double cobroCalculado = vehiculo.calcularCobro(ruta, cantidad);

        Viaje nuevoViaje = new Viaje(fecha, hora, ruta, vehiculo, piloto, cantidad, cobroCalculado);
        viajes.add(nuevoViaje);

    
        vehiculo.registrarKilometros(ruta.getKilometros());

        return nuevoViaje;
    }

  
    public void cambiarEstado(Vehiculo vehiculo, String estado) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo.");
        }
        if (estado != null && estado.trim().equalsIgnoreCase("Activo")) {
            vehiculo.setEstado("Activo");
        } else if (estado != null && estado.trim().equalsIgnoreCase("En Mantenimiento")) {
            vehiculo.setEstado("En Mantenimiento");
        } else {
            throw new IllegalArgumentException("Estado no válido. Use 'Activo' o 'En Mantenimiento'.");
        }
    }

    
    public void registrarMantenimiento(Vehiculo vehiculo, LocalDate fecha, String descripcion) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("Debe indicar la fecha del servicio.");
        }
        exigirTexto(descripcion, "Descripción");

        vehiculo.setKmDesdeServicio(0.0);
        vehiculo.setEstado("Activo");
        mantenimientos.add(new ServicioMantenimiento(fecha, descripcion.trim(), vehiculo));
    }

   

    private boolean enRango(Viaje v, LocalDate desde, LocalDate hasta) {
        return !v.getFecha().isBefore(desde) && !v.getFecha().isAfter(hasta);
    }

    //Cada fila: {"Origen - Destino", cantidad de viajes (Integer), total cobrado (Double)}
    public ArrayList<Object[]> reportePorRuta(LocalDate desde, LocalDate hasta) {
        Map<Ruta, double[]> acumulado = new LinkedHashMap<>(); // {viajes, total}
        for (Viaje v : viajes) {
            if (enRango(v, desde, hasta)) {
                double[] datos = acumulado.computeIfAbsent(v.getRuta(), k -> new double[2]);
                datos[0] += 1;
                datos[1] += v.getCobro();
            }
        }
        ArrayList<Object[]> filas = new ArrayList<>();
        for (Map.Entry<Ruta, double[]> e : acumulado.entrySet()) {
            Ruta r = e.getKey();
            filas.add(new Object[]{r.getOrigen() + " - " + r.getDestino(), (int) e.getValue()[0], e.getValue()[1]});
        }
        return filas;
    }

    //Cada fila: {placa, tipo, cantidad de viajes (Integer), total cobrado (Double)}
    public ArrayList<Object[]> reportePorVehiculo(LocalDate desde, LocalDate hasta) {
        Map<Vehiculo, double[]> acumulado = new LinkedHashMap<>(); // {viajes, total}
        for (Viaje v : viajes) {
            if (enRango(v, desde, hasta)) {
                double[] datos = acumulado.computeIfAbsent(v.getVehiculo(), k -> new double[2]);
                datos[0] += 1;
                datos[1] += v.getCobro();
            }
        }
        ArrayList<Object[]> filas = new ArrayList<>();
        for (Map.Entry<Vehiculo, double[]> e : acumulado.entrySet()) {
            Vehiculo ve = e.getKey();
            filas.add(new Object[]{ve.getPlaca(), ve.getClass().getSimpleName(), (int) e.getValue()[0], e.getValue()[1]});
        }
        return filas;
    }

    //Suma de lo cobrado en el rango de fechas (inclusive)
    public double totalIngresos(LocalDate desde, LocalDate hasta) {
        double total = 0;
        for (Viaje v : viajes) {
            if (enRango(v, desde, hasta)) {
                total += v.getCobro();
            }
        }
        return total;
    }
    
    //Persistencia de datos del archivo
    public void guardarDatos(String rutaArchivo, java.util.ArrayList<? extends Persistible> lista) {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(rutaArchivo))) {
            for (Persistible p : lista) {
                pw.println(p.aLineaTexto());
            }
        } catch (java.io.IOException e) {
            System.err.println("Error al guardar en " + rutaArchivo + ": " + e.getMessage());
        }
    }

    public void guardarTodoElSistema() {
        guardarDatos("rutas.txt", rutas);
        guardarDatos("pilotos.txt", pilotos);
        guardarDatos("vehiculos.txt", vehiculos);
    }
}
