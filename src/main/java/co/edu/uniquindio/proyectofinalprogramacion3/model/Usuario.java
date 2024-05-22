package co.edu.uniquindio.agencia20241.model;

import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Usuario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Reserva> listaReservas;

    private String contrasenia;

    public Usuario(String nombre, String id, String correoElectronico, List<Reserva> listaReservas, String contrasenia) {
        super(nombre, id, correoElectronico);
        this.listaReservas = listaReservas;
        this.contrasenia = contrasenia;
    }
    public Usuario(){

    }


    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}