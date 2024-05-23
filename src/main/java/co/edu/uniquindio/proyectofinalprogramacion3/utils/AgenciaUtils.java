package co.edu.uniquindio.proyectofinalprogramacion3.utils;

import co.edu.uniquindio.proyectofinalprogramacion3.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgenciaUtils {


    public static Agencia inicializarDatos() {

        Agencia agencia = new Agencia();

        // Crear un usuario
        Usuario usuario = new Usuario();
        usuario.setId("00");
        usuario.setNombre("Maria");
        usuario.setCorreoElectronico("Maria@gmail");
        usuario.setContrasenia("00");
        agencia.obtenerUsuarios().add(usuario);


        // Crear un usuario
        usuario = new Usuario();
        usuario.setId("11");
        usuario.setNombre("Juana");
        usuario.setCorreoElectronico("Juana@gmail");
        usuario.setContrasenia("11");
        agencia.obtenerUsuarios().add(usuario);

        // Crear un empleado
        Empleado empleado = new Empleado();
        empleado.setNombre("juan");
        empleado.setCorreoElectronico("juan@");
        empleado.setId("123");
        empleado.setRolEmpleado("Cocina");
        empleado.setContrasenia("123");
        agencia.obtenerEmpleados().add(empleado);

        // Crear un evento
        Eventos evento = new Eventos();
        evento.setCapacidadMaximaEvento(10021);
        evento.setDescripcionEvento("Baile");
        evento.setFechaEvento("2023-01-30");
        evento.setHoraEvento("02:43");
        evento.setNombreEvento("Fiesta");
        evento.setUbicacionEvento("Bolo Club");
        agencia.obtenerEventos().add(evento);

        evento = new Eventos();
        evento.setCapacidadMaximaEvento(12100);
        evento.setDescripcionEvento("Comedor");
        evento.setFechaEvento("2023-02-20");
        evento.setHoraEvento("02:43");
        evento.setNombreEvento("Comida");
        evento.setUbicacionEvento("Universidad");
        agencia.obtenerEventos().add(evento);


        evento = new Eventos();
        evento.setCapacidadMaximaEvento(3100);
        evento.setDescripcionEvento("Comedor");
        evento.setFechaEvento("2023-02-20");
        evento.setHoraEvento("02:43");
        evento.setNombreEvento("Comida");
        evento.setUbicacionEvento("Universidad");

        agencia.obtenerEventos().add(evento);


        Reserva reserva = new Reserva();
        reserva.setEstadoReserva("Aceptado");
        reserva.setId("121");
        reserva.setFechaSolicitud(LocalDate.now());

        // Crear un nuevo evento para poder enviarle el nombre
        Eventos eventoReserva = new Eventos();
        eventoReserva.setNombreEvento("Tango");

        //crear un nuevo usuario para poder enviarle el id
        Usuario idUsuario= new Usuario();
        idUsuario.setId("10928");

        // Establecer el evento en la reserva
        reserva.setEvento(eventoReserva);
        reserva.setUsuario(idUsuario);

        // Agregar la reserva a la lista de reservas de la agencia
        agencia.obtenerReservas().add(reserva);

        System.out.println("Datos setiados de la agencia cargardos");
        return agencia;
    }

}
