package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;

public class Persistencia {

    public static final String RUTA_ARCHIVO_USUARIOS = "/src/main/resources/persistencia/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/AgenciaLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_AGENCIA_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_AGENCIA_XML = "src/main/resources/persistencia/model.xml";
    private static final String RUTA_ARCHIVO_CLIENTES ="/src/main/resources/persistencia/archivoClientes.txt" ;
    private static final String RUTA_ARCHIVO_EMPLEADOS = "/src/main/resources/persistencia/archivoEmpleados.txt";
    private static final String RUTA_ARCHIVO_RESERVAS ="/src/main/resources/persistencia/archivoReservas.txt" ;
    private static final String RUTA_ARCHIVO_EVENTOS = "/src/main/resources/persistencia/archivoEventos.txt";

    public static void cargarDatosArchivos(Agencia agencia) throws FileNotFoundException, IOException {
        // cargar archivo de clientes

        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if(usuariosCargados.size() > 0)
            agencia.getListaUsuarios().addAll(usuariosCargados);


        // cargar archivos empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if(empleadosCargados.size() > 0)
            agencia.getListaEmpleados().addAll(empleadosCargados);


    }
//--------------SAVE---------------------------------

    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados) throws IOException {
        String contenido = "";
        for(Empleado empleado:listaEmpleados)
        {
            contenido+= empleado.getNombre()+
                    ","+empleado.getId()+
                    ","+empleado.getRolEmpleado()+
                    ","+empleado.getContrasenia()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
    }
    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        String contenido = "";
        for(Usuario usuario:listaUsuarios)
        {
            contenido+= usuario.getNombre()+
                    ","+usuario.getId()+
                    ","+usuario.getContrasenia()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);
    }
    public static void guardarEvento(ArrayList<Eventos> listaEventos) throws IOException {
        String contenido = "";
        for(Eventos evento:listaEventos)
        {
            contenido+= evento.getNombreEvento()+
                    ","+evento.getDescripcionEvento()+
                    ","+evento.getFechaEvento()+
                    ","+evento.getHoraEvento()+
                    ","+evento.getUbicacionEvento()+
                    ","+evento.getCapacidadMaximaEvento()+
                    ","+evento.getEstado()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EVENTOS, contenido, false);
    }


//    public static void guardarReservas(ArrayList<Reserva> listaReservas) throws IOException {
//        String contenido = "";
//        for(Reserva reserva:listaReservas)
//        {
//            contenido+= reserva.getEvento().getNombreEvento()+
//                    ","+reserva.getId()+
//                    ","+reserva.getEstadoReserva()+"\n";
//        }
//        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
//    }




    // ----------------------LOADS------------------------

    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Usuario usuario = new Usuario();
            usuario.setNombre(linea.split(",")[0]);
            usuario.setId(linea.split(",")[1]);
            usuario.setCorreoElectronico(linea.split(",")[2]);

            usuarios.add(usuario);
        }
        return usuarios;
    }
//    public static ArrayList<Reserva> cargarReservas() throws FileNotFoundException, IOException {
//        ArrayList<Reserva> reservas = new ArrayList<Reserva>();
//        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_RESERVAS);
//        String linea = "";
//        for (int i = 0; i < contenido.size(); i++) {
//            linea = contenido.get(i);
//            Reserva reserva = new Reserva();
//            reserva.setId(linea.split(",")[0]);
//            reserva.setUsuario(linea.split(",")[1]);
//            reserva.setEvento(linea.split(",")[2]);
//            reserva.setFechaSolicitud(linea.split(",")[3]);
//            reserva.setEstadoReserva(linea.split(",")[4]);
//
//            reservas.add(reserva);
//        }
//        return reservas;
//    }

    public static ArrayList<Empleado> cargarEmpleados( ) throws FileNotFoundException, IOException {
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Empleado empleado = new Empleado();
            empleado.setNombre(linea.split(",")[0]);
            empleado.setId(linea.split(",")[1]);
            empleado.setCorreoElectronico(linea.split(",")[2]);
            empleado.setRolEmpleado(linea.split(",")[3]);
            empleado.setContrasenia(linea.split(",")[3]);
            empleados.add(empleado);
        }
        return empleados;
    }

    public static ArrayList<Eventos> cargarEventos() throws FileNotFoundException, IOException {
        ArrayList<Eventos> eventos = new ArrayList<Eventos>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EVENTOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Eventos evento = new Eventos();
            evento.setNombreEvento(linea.split(",")[0]);
            evento.setDescripcionEvento(linea.split(",")[1]);
            evento.setFechaEvento(linea.split(",")[2]);
            evento.setHoraEvento(linea.split(",")[3]);
            evento.setCapacidadMaximaEvento(Integer.parseInt(linea.split(",")[4]));
            eventos.add(evento);
        }
        return eventos;
    }

    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

//    public static boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioExcepcion, UsuarioException {
//        if (validarUsuario(usuario, contrasenia)) {
//            return true;
//        } else {
//            throw new UsuarioException("Usuario no existe");
//        }
//    }

//    private static boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException {
//        ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);
//        for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) {
//            Usuario usuarioAux = usuarios.get(indiceUsuario);
//            if (usuarioAux.getUsuario().equalsIgnoreCase(usuario) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
//                return true;
//            }
//        }
//        return false;
//    }



    //------------------------------------SERIALIZACIÓN y XML

    public static Agencia cargarRecursoAgenciaBinario() {
        Agencia agencia = null;
        try {
            agencia = (Agencia) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_AGENCIA_BINARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agencia;
    }

    public static void guardarRecursoBancoBinario(Agencia agencia) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_AGENCIA_BINARIO, agencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Agencia cargarRecursoAgenciaXML() {
        Agencia agencia = null;
        try {
            agencia = (Agencia) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_AGENCIA_XML);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agencia;
    }

    public static void guardarRecursoBancoXML(Agencia agencia) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_AGENCIA_XML, agencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
