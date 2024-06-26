package co.edu.uniquindio.proyectofinalprogramacion3.controller;


import co.edu.uniquindio.proyectofinalprogramacion3.controller.service.IAgenciaService;
import co.edu.uniquindio.proyectofinalprogramacion3.controller.service.IModelFactoryService;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.EmpleadoException;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.EventoException;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.UsuarioException;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.ReservaDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.proyectofinalprogramacion3.model.*;
import co.edu.uniquindio.proyectofinalprogramacion3.utils.AgenciaUtils;
import co.edu.uniquindio.proyectofinalprogramacion3.utils.ArchivoUtil;
import co.edu.uniquindio.proyectofinalprogramacion3.utils.Persistencia;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IAgenciaService, Runnable , IModelFactoryService {

    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    Agencia agencia;
    AgenciaMapper mapper = AgenciaMapper.INSTANCE;

    BoundedSemaphore semaphore = new BoundedSemaphore(1);
    String mensaje = "";
    int nivel = 0;
    String accion = "";


    Thread hilo1GuardarXml;
    Thread hilo2GuardarLog;


    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        cargarResourceXML();
        initRabbitConnection();
        System.out.println("Agencia cargada: " + (agencia != null));

        if(agencia == null){
            cargarDatosBase();
            System.out.println("Datos base cargados");
            guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");
    }
    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecidad");
    }
    //RABBIT

    @Override
    public void producirMensaje(String queue, String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void cargarDatosBase() {
        agencia = AgenciaUtils.inicializarDatos();


    }


    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    @Override
    public Empleado crearEmpleado(String nombre, String id, String correoElectronico, String eventosAsiganados, String contrasenia) throws EmpleadoException {
        registrarAccionesSistema("Se agrego un empleado",1,"");
        guardarResourceXML();
        return agencia.crearEmpleado(nombre, id, correoElectronico, eventosAsiganados,contrasenia);
    }

    @Override
    public boolean eliminarEmpleado(String id) throws EmpleadoException {
        registrarAccionesSistema("Se eliminó un empleado",1,"");
        guardarResourceXML();
        return agencia.eliminarEmpleado(id);
    }

    public Empleado buscarEmpleado(String id){

        return agencia.buscarEmpleado(id);
    }




    @Override
    public boolean actualizarEmpleado(String cedulaActual, String nombre, String correo, String eventos,String contrasenia) throws EmpleadoException {
        registrarAccionesSistema("Se actualizó un empleado",1,"");
        guardarResourceXML();
        return agencia.actualizarEmpleado(cedulaActual, nombre, correo, eventos,contrasenia);
    }

    @Override
    public boolean verificarEmpleadoExistente(String cedula) throws EmpleadoException {
        ;
        return agencia.verificarEmpleadoExistente(cedula);
    }

    @Override
    public Empleado obtenerEmpleado(String cedula) {
        registrarAccionesSistema("Se obtuvo un empleado",1,"");
        guardarResourceXML();
        return agencia.obtenerEmpleado(cedula);
    }

    @Override
    public ArrayList<Empleado> obtenerEmpleados() {
        registrarAccionesSistema("Se obtuvieron varios empleados",1,"");
        guardarResourceXML();
        return agencia.obtenerEmpleados();
    }




    //USUARIO

    @Override
    public Usuario crearUsuario(String nombre, String id, String correoElectronico, List eventosAsiganados, String contrasenia) throws UsuarioException {
        registrarAccionesSistema("Se creó un usuario",1,"");
        guardarResourceXML();
        return agencia.crearUsuario(nombre, id, correoElectronico, eventosAsiganados,contrasenia);
    }

    @Override
    public boolean eliminarUsuario(String id) throws UsuarioException {
        registrarAccionesSistema("Se eliminó un usuario",1,"");
        guardarResourceXML();
        return agencia.eliminarUsuario(id);
    }

    @Override
    public boolean actualizarUsuario(String nombre, String cedulaActual, String correo, String contrasenia) throws UsuarioException {
        registrarAccionesSistema("Se Actualizó un usuario",1,"");
        guardarResourceXML();
        return agencia.actualizarUsuario(nombre, cedulaActual, correo,contrasenia);
    }

    @Override
    public boolean verificarUsuarioExistente(String cedula) throws UsuarioException {
        return agencia.verificarUsuarioExistente(cedula);
    }

    @Override
    public Usuario obtenerUsuario(String cedula) {
        return agencia.obtenerUsuario(cedula);
    }

    @Override
    public ArrayList<Usuario> obtenerUsuarios() {
        return agencia.obtenerUsuarios();
    }


    // EVENTOS


    @Override
    public Eventos crearEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EventoException {
        registrarAccionesSistema("Se creó un evento",1,"");
        guardarResourceXML();
        return agencia.crearEvento(nombreEvento,descripcionEvento,fechaEvento,horaEvento,ubicacionEvento,capacidadMaximaEvento);
    }

    @Override
    public boolean eliminarEvento(String nombre) throws EventoException {
        registrarAccionesSistema("Se eliminó un evento",1,"");
        guardarResourceXML();
        return agencia.eliminarEvento(nombre);
    }

    @Override
    public boolean actualizarEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EventoException {
        registrarAccionesSistema("Se actualizó un evento",1,"");
        guardarResourceXML();
        return agencia.actualizarEvento(nombreEvento, descripcionEvento, fechaEvento, horaEvento, ubicacionEvento, capacidadMaximaEvento);
    }

    @Override
    public boolean verificarEventoExistente(String nombre) throws EventoException {
        return agencia.verificarEventoExistente(nombre);
    }

    @Override
    public Eventos obtenerEvento(String nombre) {
        return agencia.obtenerEvento(nombre);
    }

    @Override
    public ArrayList<Eventos> obtenerEventos() {
        return agencia.obtenerEventos();
    }

    public Usuario buscarUsuario(String id){
        return agencia.buscarUsuario(id);
    }



    @Override
    public List<Usuario> obtenerUsuarioId(String id) {
        return agencia.obtenerUsuarioId(id);
    }


    @Override
    public boolean validarUsuarioProperties(String id, String contrasena) {

        return agencia.validarUsuarioProperties(id, contrasena);
    }

    //RESERVA
    @Override
    public void agregarReserva(String id, Usuario usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva) {
        registrarAccionesSistema("Se creó una reserva",1,"");
        guardarResourceXML();
        agencia.agregarReserva( id,  usuario,  evento,  fechaSolicitud, estadoReserva);
    }

    @Override
    public ArrayList<Reserva> obtenerReservas() {

        return agencia.obtenerReservas();
    }

    //METODOS PARA SERIALIZAR

    public void cargarResourceXML() {
        agencia = Persistencia.cargarRecursoAgenciaXML();
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoAgenciaXML(agencia);
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        this.mensaje = mensaje;
        this.nivel = nivel;
        this.accion = accion;
        hilo2GuardarLog = new Thread(this);
        hilo2GuardarLog.start();
    }

    @Override
    public void run() {
        Thread hiloActual = Thread.currentThread();
        ocupar();
        if(hiloActual == hilo1GuardarXml){
            Persistencia.guardarRecursoAgenciaXML(agencia);
            liberar();
        }
        if(hiloActual == hilo2GuardarLog){
            Persistencia.guardaRegistroLog(mensaje, nivel, accion);
            liberar();
        }
    }

    private void ocupar() {
        try {
            semaphore.ocupar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void liberar() {
        try {
            semaphore.liberar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
