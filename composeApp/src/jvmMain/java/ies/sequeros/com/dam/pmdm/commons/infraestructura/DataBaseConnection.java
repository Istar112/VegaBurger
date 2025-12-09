package ies.sequeros.com.dam.pmdm.commons.infraestructura;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBaseConnection {
    private String config_path;
    private String connection_string;
    private Connection conexion;

    public DataBaseConnection() {
    }
    public void open() throws Exception {
        //Leer el archivo de configuracion
        FileReader fr = null;
        File f =new File(System.getProperty("user.dir")+ this.getConfig_path());
        fr = new FileReader(f);
        Properties props = new Properties();
        try {
            props.load(fr);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        // Creamos el objeto para encryptar la contraseña
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword("contrasenya");

        // Establecer la conexion
        String database = props.getProperty("database.path");
        String user = encryptor.decrypt(props.getProperty("database.user"));
        String password = encryptor.decrypt(props.getProperty("database.password"));
        this.conexion = DriverManager.getConnection(database, user, password);
    }

    public Connection getConnection() {
        return this.conexion;
    }
    public void close() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
        conexion = null;
    }

    public String getConfig_path() {
        return config_path;
    }
    public void setConfig_path(String config_path) {
        this.config_path = config_path;
    }
}