package co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto;



import co.edu.uniquindio.proyectofinalprogramacion3.model.Eventos;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Usuario;

import java.time.LocalDate;

public record ReservaDto(
        String id,
        Usuario usuario,
        Eventos evento,
        LocalDate fechaSolicitud,
        String estadoReserva
) {
}
