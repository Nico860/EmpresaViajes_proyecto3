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
 * Clase central del sistema. Guarda las colecciones en memoria y aplica las
 * reglas de negocio (RF-01 a RF-05).
 *
 * Convención de errores: cuando una operación no se puede realizar (dato
 * vacío, duplicado, regla incumplida) se lanza IllegalArgumentException con un
 * mensaje listo para mostrarse al usuario. La interfaz solo lo atrapa y lo muestra.
 *
 * @author angls
 */
public class Empresa {
    // listas de coleccion en memoria
    // Nota (RF-04): estas listas guardan TODOS los registros, también los dados de baja.
    // La baja es lógica (activo = false) para no perder el historial de viajes.
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
        cargarRutasPorDefecto();
    }

    //RF-03: la aplicación incluye, al menos, estas tres rutas.
    //Los kilómetros y tarifas son valores iniciales que se pueden modificar desde la pestaña Rutas.
    private void cargarRutasPorDefecto() {
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

    // ------------------------------------------------------------------
    // Utilidades de validación
    // ------------------------------------------------------------------
    private static boolean vacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private static void exigirTexto(String valor, String campo) {
        if (vacio(valor)) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede estar vacío.");
        }
    }

    // ------------------------------------------------------------------
    // RF-01  Vehículos
    // ------------------------------------------------------------------

    //Registra un vehículo validando campos, números y placa repetida.
    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo a registrar.");
        }
        exigirTexto(vehiculo.getPlaca(), "Placa");
        exigirTexto(vehiculo.getEstado(), "Estado");

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

        vehiculo.setPlaca(vehiculo.getPlaca().trim().toUpperCase());
        validarEstado(vehiculo.getEstado());
        vehiculo.setEstado(normalizarEstado(vehiculo.getEstado()));

        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Ya existe un vehículo con la placa " + vehiculo.getPlaca() + ".");
        }
        vehiculos.add(vehiculo);
    }

    //Busca un vehículo por placa (activo o dado de baja). Retorna null si no existe.
    public Vehiculo buscarVehiculo(String placa) {
        if (vacio(placa)) {
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

    //RF-04: baja lógica. El vehículo ya no se ofrece para nuevos viajes pero se conservan sus viajes.
    public void darBajaVehiculo(String placa) {
        Vehiculo v = buscarVehiculo(placa);
        if (v == null) {
            throw new IllegalArgumentException("No existe un vehículo con la placa " + placa + ".");
        }
        if (!v.isActivo()) {
            throw new IllegalArgumentException("El vehículo " + v.getPlaca() + " ya está dado de baja.");
        }
        v.setActivo(false);
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

    // ------------------------------------------------------------------
    // RF-02  Pilotos
    // ------------------------------------------------------------------

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
        if (existeLicencia(piloto.getNumLicencia(), null)) {
            throw new IllegalArgumentException("Ya existe un piloto con el número de licencia " + piloto.getNumLicencia() + ".");
        }
        pilotos.add(piloto);
    }

    //Busca un piloto por DPI (activo o dado de baja). Retorna null si no existe.
    public Piloto buscarPiloto(String dpi) {
        if (vacio(dpi)) {
            return null;
        }
        for (Piloto p : pilotos) {
            if (p.getDpi().equalsIgnoreCase(dpi.trim())) {
                return p;
            }
        }
        return null;
    }

    //Modifica los datos de un piloto. El DPI identifica al piloto y no cambia.
    public void modificarPiloto(String dpi, String nombre, String numLicencia, String tipoLicencia) {
        Piloto p = buscarPiloto(dpi);
        if (p == null) {
            throw new IllegalArgumentException("No existe un piloto con el DPI " + dpi + ".");
        }
        exigirTexto(nombre, "Nombre");
        exigirTexto(numLicencia, "Número de licencia");
        exigirTexto(tipoLicencia, "Tipo de licencia");

        if (existeLicencia(numLicencia.trim(), p)) {
            throw new IllegalArgumentException("Ya existe otro piloto con el número de licencia " + numLicencia.trim() + ".");
        }
        p.setNombre(nombre.trim());
        p.setNumLicencia(numLicencia.trim());
        p.setTipoLicencia(tipoLicencia.trim());
    }

    //Indica si algún piloto (distinto de 'excepto') ya usa ese número de licencia
    private boolean existeLicencia(String numLicencia, Piloto excepto) {
        for (Piloto p : pilotos) {
            if (p != excepto && p.getNumLicencia().equalsIgnoreCase(numLicencia.trim())) {
                return true;
            }
        }
        return false;
    }

    //RF-04: baja lógica del piloto
    public void darBajaPiloto(String dpi) {
        Piloto p = buscarPiloto(dpi);
        if (p == null) {
            throw new IllegalArgumentException("No existe un piloto con el DPI " + dpi + ".");
        }
        if (!p.isActivo()) {
            throw new IllegalArgumentException("El piloto " + p.getNombre() + " ya está dado de baja.");
        }
        p.setActivo(false);
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

    // ------------------------------------------------------------------
    // RF-03  Rutas
    // ------------------------------------------------------------------

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
        validarKmYTarifa(ruta.getKilometros(), ruta.getTarifaBase());

        if (buscarRuta(ruta.getOrigen(), ruta.getDestino()) != null) {
            throw new IllegalArgumentException("Ya existe la ruta " + ruta.getOrigen() + " - " + ruta.getDestino() + ".");
        }
        rutas.add(ruta);
    }

    //Busca una ruta por origen y destino (activa o dada de baja). Retorna null si no existe.
    public Ruta buscarRuta(String origen, String destino) {
        if (vacio(origen) || vacio(destino)) {
            return null;
        }
        for (Ruta r : rutas) {
            if (r.getOrigen().equalsIgnoreCase(origen.trim()) && r.getDestino().equalsIgnoreCase(destino.trim())) {
                return r;
            }
        }
        return null;
    }

    //Modifica los kilómetros y la tarifa base de una ruta. Origen y destino la identifican.
    public void modificarRuta(String origen, String destino, double kilometros, double tarifaBase) {
        Ruta r = buscarRuta(origen, destino);
        if (r == null) {
            throw new IllegalArgumentException("No existe la ruta " + origen + " - " + destino + ".");
        }
        validarKmYTarifa(kilometros, tarifaBase);
        r.setKilometros(kilometros);
        r.setTarifaBase(tarifaBase);
    }

    private void validarKmYTarifa(double kilometros, double tarifaBase) {
        if (!(kilometros > 0)) {
            throw new IllegalArgumentException("Los kilómetros deben ser un número mayor que cero.");
        }
        if (!(tarifaBase > 0)) {
            throw new IllegalArgumentException("La tarifa base debe ser un número mayor que cero.");
        }
    }

    //RF-04: baja lógica de la ruta
    public void darBajaRuta(String origen, String destino) {
        Ruta r = buscarRuta(origen, destino);
        if (r == null) {
            throw new IllegalArgumentException("No existe la ruta " + origen + " - " + destino + ".");
        }
        if (!r.isActiva()) {
            throw new IllegalArgumentException("La ruta " + origen + " - " + destino + " ya está dada de baja.");
        }
        r.setActiva(false);
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

    // ------------------------------------------------------------------
    // RF-05  Viajes
    // ------------------------------------------------------------------

    //Verificación de conflictos de vehiculo, piloto, fecha y hora.
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

        //RF-04: lo dado de baja no se ofrece para nuevos viajes
        if (!ruta.isActiva()) {
            throw new IllegalArgumentException("La ruta " + ruta.getOrigen() + " - " + ruta.getDestino() + " está dada de baja.");
        }
        if (!piloto.isActivo()) {
            throw new IllegalArgumentException("El piloto " + piloto.getNombre() + " está dado de baja.");
        }
        if (!vehiculo.isActivo()) {
            throw new IllegalArgumentException("El vehículo " + vehiculo.getPlaca() + " está dado de baja.");
        }
        if (Vehiculo.ESTADO_MANTENIMIENTO.equalsIgnoreCase(vehiculo.getEstado())) {
            throw new IllegalArgumentException("El vehículo " + vehiculo.getPlaca() + " está 'En Mantenimiento' y no puede salir a viajar.");
        }

        // Validación de cantidad (pasajeros o quintales según el tipo de vehículo)
        String unidad = vehiculo.esDeCarga() ? "quintales" : "pasajeros";
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de " + unidad + " debe ser un número mayor que cero.");
        }
        if (cantidad > vehiculo.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de " + unidad + " (" + cantidad + ") supera la capacidad máxima del vehículo (" + vehiculo.getCapacidad() + ").");
        }

        //Verificación del conflicto de vehiculo y piloto, respecto a fecha y hora.
        if (hayChoque(vehiculo, piloto, fecha, hora)) {
            throw new IllegalArgumentException("El vehículo o el piloto ya tienen un viaje registrado exactamente a esa misma fecha y hora.");
        }

        //El cobro se calcula llamando a calcularCobro sin importar si es Bus, Microbus o Pickup
        double cobroCalculado = vehiculo.calcularCobro(ruta, cantidad);

        Viaje nuevoViaje = new Viaje(fecha, hora, ruta, vehiculo, piloto, cantidad, cobroCalculado);
        viajes.add(nuevoViaje);

        //Acumular kilómetros; si llega al límite el vehículo pasa a 'En Mantenimiento'
        vehiculo.registrarKilometros(ruta.getKilometros());

        return nuevoViaje;
    }

    //Cambia el estado de un vehículo (Activo / En Mantenimiento)
    public void cambiarEstado(Vehiculo vehiculo, String estado) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo.");
        }
        validarEstado(estado);
        vehiculo.setEstado(normalizarEstado(estado));
    }

    private void validarEstado(String estado) {
        if (vacio(estado)
                || !(Vehiculo.ESTADO_ACTIVO.equalsIgnoreCase(estado.trim())
                  || Vehiculo.ESTADO_MANTENIMIENTO.equalsIgnoreCase(estado.trim()))) {
            throw new IllegalArgumentException("Estado no válido. Use '" + Vehiculo.ESTADO_ACTIVO + "' o '" + Vehiculo.ESTADO_MANTENIMIENTO + "'.");
        }
    }

    private String normalizarEstado(String estado) {
        return Vehiculo.ESTADO_ACTIVO.equalsIgnoreCase(estado.trim()) ? Vehiculo.ESTADO_ACTIVO : Vehiculo.ESTADO_MANTENIMIENTO;
    }

    //Registra un servicio: resetea los km de servicio a 0, deja el vehículo 'Activo' y guarda el historial.
    public void registrarMantenimiento(Vehiculo vehiculo, LocalDate fecha, String descripcion) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("Debe indicar la fecha del servicio.");
        }
        exigirTexto(descripcion, "Descripción");

        vehiculo.setKmDesdeServicio(0.0);
        vehiculo.setEstado(Vehiculo.ESTADO_ACTIVO);
        mantenimientos.add(new ServicioMantenimiento(fecha, descripcion.trim(), vehiculo));
    }

    // ------------------------------------------------------------------
    // Reportes (RF-04: incluyen viajes de recursos dados de baja)
    // ------------------------------------------------------------------

    private static boolean enRango(Viaje v, LocalDate desde, LocalDate hasta) {
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

    //Cada fila: {placa, tipo, cantidad de viajes (Integer), kilómetros recorridos (Double), total cobrado (Double)}
    public ArrayList<Object[]> reportePorVehiculo(LocalDate desde, LocalDate hasta) {
        Map<Vehiculo, double[]> acumulado = new LinkedHashMap<>(); // {viajes, km, total}
        for (Viaje v : viajes) {
            if (enRango(v, desde, hasta)) {
                double[] datos = acumulado.computeIfAbsent(v.getVehiculo(), k -> new double[3]);
                datos[0] += 1;
                datos[1] += v.getRuta().getKilometros();
                datos[2] += v.getCobro();
            }
        }
        ArrayList<Object[]> filas = new ArrayList<>();
        for (Map.Entry<Vehiculo, double[]> e : acumulado.entrySet()) {
            Vehiculo ve = e.getKey();
            double[] d = e.getValue();
            filas.add(new Object[]{ve.getPlaca(), ve.getTipo(), (int) d[0], d[1], d[2]});
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
