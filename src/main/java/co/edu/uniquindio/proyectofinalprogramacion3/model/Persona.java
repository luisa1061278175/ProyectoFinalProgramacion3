package co.edu.uniquindio.proyectofinalprogramacion3.model;

import java.io.Serializable;

public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Nombre;
    private String Id;
    private String CorreoElectronico;

    public Persona(String nombre, String id, String correoElectronico) {
        Nombre = nombre;
        Id = id;
        CorreoElectronico = correoElectronico;
    }

    public Persona() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }
}
