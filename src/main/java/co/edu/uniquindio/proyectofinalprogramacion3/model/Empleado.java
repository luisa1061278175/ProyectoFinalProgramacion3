package co.edu.uniquindio.agencia20241.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Empleado extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private String rolEmpleado;

    private String contrasenia;

    public Empleado() {


    }

    public Empleado(String nombre, String id, String correoElectronico, String rolEmpleado, String contrasenia) {
        super(nombre, id, correoElectronico);
        this.rolEmpleado = rolEmpleado;
        this.contrasenia = contrasenia;
    }

    public String getRolEmpleado() {
        return rolEmpleado;
    }

    public void setRolEmpleado(String eventosAsiganados) {
        this.rolEmpleado = eventosAsiganados;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}