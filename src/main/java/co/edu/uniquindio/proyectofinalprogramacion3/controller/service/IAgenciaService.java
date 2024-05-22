package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Reserva;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IAgenciaService extends IEmpleadoControllerService,IUsuarioService,IEventoService,IReservasService{


    List<Usuario> obtenerUsuarioId(String id);


    //validar datos para archivo properties
    boolean validarUsuarioProperties(String usuario, String contrasena);


}
