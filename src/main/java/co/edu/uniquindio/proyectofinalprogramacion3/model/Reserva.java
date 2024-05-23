package co.edu.uniquindio.proyectofinalprogramacion3.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private Usuario usuario;
    private Eventos evento;
    private LocalDate fechaSolicitud;
    private String estadoReserva;

    public Reserva(String id, Usuario usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
        this.fechaSolicitud = LocalDate.now();
        this.estadoReserva = estadoReserva;
    }

    public Reserva(){

    }

    public Reserva(String idReserva, String s, Usuario usuario, LocalDate now, String estado) {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}
