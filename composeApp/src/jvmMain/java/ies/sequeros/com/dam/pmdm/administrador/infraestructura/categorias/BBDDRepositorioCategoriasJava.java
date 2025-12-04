package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import java.sql.SQLException;
import java.util.List;

import javax.xml.crypto.Data;

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class BBDDRepositorioCategoriasJava {
    private final DataBaseConnection db;
    private CategoriaDao dao;

    public BBDDRepositorioCategoriasJava(String path) throws Exception {
        super();
        this.db = new DataBaseConnection();
        this.db.setConfig_path(path);
        this.db.open();
        dao = new CategoriaDao();
        dao.setConn(this.db);
    }
    public BBDDRepositorioCategoriasJava(DataBaseConnection con) throws Exception {
        super();
        this.db = con;
        dao = new CategoriaDao();
        dao.setConn(this.db);
    }


    public void add(Categoria item) {
        this.dao.insert(item);
    }

    public void remove(Categoria item) {
        this.dao.delete(item);
    }

    public boolean remove(String id) {
        var item = this.dao.getById(id);
        if (item != null) {
            this.dao.delete(item);
            return true;
        }
        return false;
    }

    public boolean update(Categoria item) {
        this.dao.update(item);
        return true;
    }

    public List<Categoria> getAll() {
        return this.dao.getAll();

    }
    public Categoria findByName(String name) {
        return this.dao.findByName(name);
    }
    public Categoria getById(String id) {
        return this.dao.getById(id);
    }
    public List<Categoria> findByIds(List<String> ids) {
        return null;
    }
    public void close(){
        try {
            this.db.close();
        //no hace caso de esta excepción
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
