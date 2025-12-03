package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;


import org.junit.Before;
import org.junit.Test;

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class BBDDRepositorioCategoriasJavaTest {
    private DataBaseConnection db;
    private BBDDRepositorioCategoriasJava repo;

    @Before
    public void setUp() throws Exception {
        /*db.setConfig_path("config.properties");
        db.open();
        this.repo = new BBDDRepositorioCategoriasJava(db);*/
    }
    @Test
    public void testAdd() {
        //Categoria c= new Categoria("dfsdf","prueba","foto.png",true);
        //this.repo.add(c);


    }

    public void testRemove() {
    }

    public void testUpdate() {
    }

    public void testGetAll() {
    }

    public void testFindByName() {
    }

    public void testGetById() {
    }

    public void testFindByIds() {
    }

    public void testClose() {
    }
}