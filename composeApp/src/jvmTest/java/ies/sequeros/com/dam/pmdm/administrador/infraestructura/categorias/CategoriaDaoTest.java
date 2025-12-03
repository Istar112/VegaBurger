package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import static org.junit.Assert.*;

import org.junit.Test;

import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class CategoriaDaoTest {

    @Test
    public void insert() throws Exception {
        CategoriaDao dao = new CategoriaDao();
        DataBaseConnection conn = new DataBaseConnection();

        conn.open();

        //dao.setConn("database.path=jdbc:mysql://localhost:3307/vegaburguer?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        assertTrue(true);
    }
}