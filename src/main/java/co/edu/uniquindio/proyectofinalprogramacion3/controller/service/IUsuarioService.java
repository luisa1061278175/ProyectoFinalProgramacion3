package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.*;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IUsuarioService {
    Usuario crearUsuario(String nombre, String id, String correoElectronico, List reservasRealizadas,String contrasenia) throws UsuarioException;
    boolean eliminarUsuario(String id) throws UsuarioException;
    boolean actualizarUsuario(String cedulaActual, String nombre, String correo,String contrasenia) throws UsuarioException;
    boolean  verificarUsuarioExistente(String cedula) throws UsuarioException;
    Usuario obtenerUsuario(String cedula);
    ArrayList<Usuario> obtenerUsuarios();
}
