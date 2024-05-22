package co.edu.uniquindio.agencia20241.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Eventos implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombreEvento;
    private String descripcionEvento;
    private String fechaEvento;
    private String horaEvento;
    private String ubicacionEvento;
    private int capacidadMaximaEvento;
    private String estado;

    public Eventos(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento, String estado) {
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.ubicacionEvento = ubicacionEvento;
        this.capacidadMaximaEvento = capacidadMaximaEvento;
        this.estado= estado;
    }

    public Eventos() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getUbicacionEvento() {
        return ubicacionEvento;
    }

    public void setUbicacionEvento(String ubicacionEvento) {
        this.ubicacionEvento = ubicacionEvento;
    }

    public int getCapacidadMaximaEvento() {
        return capacidadMaximaEvento;
    }

    public void setCapacidadMaximaEvento(int capacidadMaximaEvento) {
        this.capacidadMaximaEvento = capacidadMaximaEvento;
    }
}