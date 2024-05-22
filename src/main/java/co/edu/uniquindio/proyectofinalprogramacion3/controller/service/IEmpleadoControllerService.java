package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.model.Empleado;

import java.util.ArrayList;

public interface IEmpleadoControllerService {
    Empleado crearEmpleado(String nombre, String id, String correoElectronico, String eventosAsiganados, String contrasenia) throws EmpleadoException;
    boolean eliminarEmpleado(String id) throws EmpleadoException;
    boolean actualizarEmpleado(String cedulaActual, String nombre, String correo, String eventos,String contrasenia) throws EmpleadoException;
    boolean  verificarEmpleadoExistente(String cedula) throws EmpleadoException;
    Empleado obtenerEmpleado(String cedula);
    ArrayList<Empleado> obtenerEmpleados();
}