package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias.CategoriaDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

public class CategoriaDao implements IDao<Categoria> {
    private DataBaseConnection conn;
    private final String table_name = "categoria";
    private final String selectall = "select * from " + table_name;
    private final String selectbyid = "select * from " + table_name + " where id=?";
    private final String findbyname = "select * from " + table_name + " where nombre=?";
    private final String deletebyid = "delete from " + table_name + " where id='?'";
    private final String insert = "INSERT INTO " + table_name + " (id, nombre, img_path,activar) " +
            "VALUES (?, ?, ?, ?)";
    private final String update = "UPDATE " + table_name + " SET nombre = ?, img_path = ?, activar = ? " +
            "WHERE id = ?";
    
    public CategoriaDao() {
    }
    public DataBaseConnection getConn(){
        return this.conn;
    }
    public void setConn(final DataBaseConnection conn){
        this.conn=conn;
    }

    @Override
    public Categoria getById(final String id) {
        Categoria sp = null;// = new Categoria();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    public Categoria findByName(final String name) {
        Categoria sp = null;// = new Categoria();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }
    @Override
    public List<Categoria> getAll() {
        final ArrayList<Categoria> scl = new ArrayList<>();
        Categoria tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }

    @Override
    public void update(final Categoria item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getNombre());
            pst.setString(2, item.getImgPath());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getNombre() +
                            ", [2]=" + item.getImgPath() +
                            ", [3]=" + item.getActivar() +
                            "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    @Override
    public void delete(final Categoria item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final Categoria item) {

        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getNombre());
            pst.setString(3, item.getImgPath());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + insert +
                            " | Params: [1]=" + item.getId() +
                            ", [2]="+ item.getNombre() +
                            ", [3]=" + item.getImgPath() +
                            ", [4]=" + item.getActivar() +
                            "]"
            );

        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeeto
    private Categoria registerToObject(final ResultSet r) {
        Categoria sc =null;

        try {

            sc=new Categoria(
                    r.getString("id"),
                    r.getString("nombre"),
                    r.getString("imgPath"),
                    r.getBoolean("activar")
            );
                  
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }


}
