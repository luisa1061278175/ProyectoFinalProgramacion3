package co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoDto(
        String nombreEvento,
        String descripcionEvento,
        String fechaEvento,
        String horaEvento,
        int capacidadMaximaEvento,
        String ubicacionEvento

) {


}
