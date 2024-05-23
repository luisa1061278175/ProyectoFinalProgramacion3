package co.edu.uniquindio.proyectofinalprogramacion3.utils;

import co.edu.uniquindio.proyectofinalprogramacion3.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import co.edu.uniquindio.proyectofinalprogramacion3.exception.UsuarioException;

public class Persistencia {


    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/AgenciaLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_AGENCIA_XML = "src/main/resources/persistencia/model.xml";


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }


    //------------------------------------SERIALIZACIÓN y XML


    public static Agencia cargarRecursoAgenciaXML() {
        Agencia agencia = null;
        try {
            agencia = (Agencia) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_AGENCIA_XML);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agencia;
    }

    public static void guardarRecursoAgenciaXML(Agencia agencia) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_AGENCIA_XML, agencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
