package ies.sequeros.com.dam.pmdm.administrador.infraestructura.productos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.productos.ProductoDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

public class ProductoDao implements IDao<Producto> {
    private DataBaseConnection conn;
    private final String table_name = "producto";
    private final String selectall = "select * from " + table_name; 
    private final String selectbyid = "select * from " + table_name + " where id=?";
    private final String findbyname = "select * from " + table_name + " where nombre=?";
    private final String deletebyid = "delete from " + table_name + " where id=?";
    private final String insert = "INSERT INTO " + table_name + 
            " (id, id_categoria, nombre, precio, pendiente_entrega, descripcion, img_path, activar) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
    
    private final String update = "UPDATE " + table_name +
            " SET id_categoria = ?, nombre = ?, precio = ?, pendiente_entrega = ?, descripcion = ?, img_path = ? , activar = ? " +
            "WHERE id = ?";
    
    public ProductoDao() {
    }
    
    public DataBaseConnection getConn(){
        return this.conn;
    }
    
    public void setConn(final DataBaseConnection conn){
        this.conn=conn;
    }

    @Override
    public Producto getById(final String id) {
        Producto sp = null;// = new Producto();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    public Producto findByName(final String name) {
        Producto sp = null;// = new Producto();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }
    @Override
    public List<Producto> getAll() {
        final ArrayList<Producto> scl = new ArrayList<>();
        Producto tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }

    @Override
    public void update(final Producto item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getIdCategoria());
            pst.setString(2, item.getNombre());
            pst.setFloat(3, item.getPrecio());
            pst.setBoolean(4, item.getPendienteEntrega());
            pst.setString(5, item.getDescripcion());
            pst.setString(6, item.getImgPath());
            pst.setBoolean(7, item.getActivar());
            pst.setString(8, item.getId());


            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getIdCategoria() +
                            ", [2]=" + item.getNombre() +
                            ", [3]=" + item.getPrecio() +
                            ", [4]=" + item.getPendienteEntrega() +
                            ", [5]=" + item.getDescripcion() +
                            ", [6]=" + item.getImgPath() +
                            ", [7]=" + item.getActivar() +
                            "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    @Override
    public void delete(final Producto item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final Producto item) {

        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getIdCategoria());
            pst.setString(3, item.getNombre());
            pst.setFloat(4, item.getPrecio());
            pst.setBoolean(5, item.getPendienteEntrega());
            pst.setString(6,item.getDescripcion());
            pst.setString(7, item.getImgPath());
            pst.setBoolean(8, item.getActivar());


            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + insert +
                            " | Params: [1]=" + item.getId() +
                            ", [2]="+ item.getIdCategoria() +
                            ", [3]=" + item.getNombre() +
                            ", [4]=" + item.getPrecio() +
                            ", [5]=" + item.getPendienteEntrega() +
                            ", [6]=" + item.getDescripcion() +
                            ", [7]=" + item.getImgPath() +
                            ", [8]=" + item.getActivar() +

                            "]"
            );

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeeto
    private Producto registerToObject(final ResultSet r) {
        Producto sc =null;

        try {
            sc=new Producto(
                    r.getString("id"),
                    r.getString("nombre"),
                    r.getString("img_path"),
                    r.getBoolean("pendiente_entrega"),
                    r.getString("id_categoria"),
                    r.getString("descripcion"),
                    r.getFloat("precio"),
                    r.getBoolean("activar")
            );
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }



}
