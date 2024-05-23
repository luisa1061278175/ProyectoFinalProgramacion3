package co.edu.uniquindio.proyectofinalprogramacion3.mapping.mappers;

import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.EventoDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.ReservaDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.UsuarioDto;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Empleado;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Eventos;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Reserva;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Usuario;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AgenciaMapper {

    AgenciaMapper INSTANCE = Mappers.getMapper(AgenciaMapper.class);

    // EMPLEADOS
    @Named("empleadoToEmpleadoDto")
    EmpleadoDto empleadoToEmpleadoDto(Empleado empleado);

    Empleado empleadoDtoToEmpleado(EmpleadoDto empleadoDto);

    @IterableMapping(qualifiedByName = "empleadoToEmpleadoDto")
    List<EmpleadoDto> getEmpleadosDto(List<Empleado> listaEmpleados);

    @Named("mappingToEmpeladoDto")
    EmpleadoDto mappingToEmpeladoDto(Empleado empleado);

    @Mapping(target = "nombre", source = "empleado.nombre")
    @IterableMapping(qualifiedByName = "empleadoToEmpleadoDto")
    EmpleadoDto clienteToClienteDto(Empleado empleado);

    // USUARIOS
    @Named("usuarioToUsuarioDto")
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @IterableMapping(qualifiedByName = "usuarioToUsuarioDto")
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);

    @Named("mappingToUsuarioDto")
    UsuarioDto mappingToUsuarioDto(Usuario usuario);

    // EVENTOS
    @Named("eventoToEventoDto")
    EventoDto eventoToEventoDto(Eventos eventos);

    Eventos eventoDtoToEvento(EventoDto eventoDto);

    @IterableMapping(qualifiedByName = "eventoToEventoDto")
    List<EventoDto> getEventosDto(List<Eventos> listaEventos);

    // RESERVAS
    @Named("reservaToReservaDto")
    @Mapping(source = "reserva.usuario", target = "usuario")
    @Mapping(source = "reserva.evento", target = "evento")
    @Mapping(target = "fechaSolicitud", expression = "java(reserva.getFechaSolicitud() != null ? reserva.getFechaSolicitud() : java.time.LocalDate.now())") // Asegura que fechaSolicitud no sea null
    ReservaDto reservaToReservaDto(Reserva reserva);

    Reserva reservaDtoToReserva(ReservaDto reservaDto);

    @IterableMapping(qualifiedByName = "reservaToReservaDto")
    List<ReservaDto> getReservasDto(List<Reserva> listaReservas);

    @Named("mappingToReservaDto")
    ReservaDto mappingToReservaDto(Reserva reserva);
}
