package co.edu.uniquindio.proyectofinalprogramacion3.controller.service;



import co.edu.uniquindio.proyectofinalprogramacion3.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IAgenciaService extends IEventoService,IReservasService,IEmpleadoControllerService, IUsuarioService {


    List<Usuario> obtenerUsuarioId(String id);


    //validar datos para archivo properties
    boolean validarUsuarioProperties(String usuario, String contrasena);


}
