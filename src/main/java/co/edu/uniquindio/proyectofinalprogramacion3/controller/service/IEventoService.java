package co.edu.uniquindio.proyectofinalprogramacion3.controller.service;

import co.edu.uniquindio.proyectofinalprogramacion3.exception.EventoException;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Eventos;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface IEventoService {

    Eventos crearEvento(String nombreEvento,
                        String descripcionEvento,
                        String fechaEvento,
                        String horaEvento,
                        String ubicacionEvento,
                        int capacidadMaximaEvento) throws EventoException;
    boolean eliminarEvento(String nombre) throws EventoException;
    boolean actualizarEvento(String nombreEvento,
                             String descripcionEvento,
                             String fechaEvento,
                             String horaEvento,
                             String ubicacionEvento,
                             int capacidadMaximaEvento) throws EventoException;
    boolean  verificarEventoExistente(String nombre) throws EventoException;
    Eventos obtenerEvento(String nombre);
    ArrayList<Eventos> obtenerEventos();
}
