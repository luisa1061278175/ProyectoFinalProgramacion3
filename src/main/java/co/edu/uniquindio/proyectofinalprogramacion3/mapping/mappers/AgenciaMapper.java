package co.edu.uniquindio.agencia20241.mapping.mappers;

import co.edu.uniquindio.agencia20241.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.dto.UsuarioDto;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Reserva;
import co.edu.uniquindio.agencia20241.model.Usuario;


import java.util.List;

@Mapper
public interface AgenciaMapper {

    AgenciaMapper INSTANCE = Mappers.getMapper(AgenciaMapper.class);

    //EMPLEADOS


    @Named("empleadoToEmpleadoDto")
    EmpleadoDto empleadoToEmpleadoDto(Empleado empleado);

    Empleado empleadoDtoToEmpleado(EmpleadoDto empleadoDto);

    @IterableMapping(qualifiedByName = "empleadoToEmpleadoDto")
    List<EmpleadoDto> getEmpleadosDto(List<Empleado> listaEmpleados);

    @Named("mappingToEmpeladoDto")
    EmpleadoDto mappingToEmpeladoDto(Empleado empleado);

    @Mapping(target = "nombre", source = "empleado.nombre")
    @IterableMapping(qualifiedByName = "cunetaToCuentaDto")
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

    @Named("reservasToReservasDto")
    ReservaDto reservasToReservasDto(Reserva reserva);

    Reserva reservasToReservasDto(ReservaDto reservaDto);

    @IterableMapping(qualifiedByName = "reservasToReservasDto")
    List<ReservaDto> getReservasDto(List<Reserva> listaReservas);

    @Named("mappingToReservasDto")
    ReservaDto mappingToReservasDto(Reserva reserva);

    @Mapping(target = "id", source = "reserva.id")
    @IterableMapping(qualifiedByName = "cunetaToCuentaDto")
    ReservaDto cunetaToCuentaDto(Reserva reserva);



}
