package ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido.LineaPedidoDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido;
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

public class LineaPedidoDao implements IDao<LineaPedido> {
    private DataBaseConnection conn;
    private final String table_name = "linea_pedido";
    private final String selectall = "select * from " + table_name;
    private final String selectbyid = "select * from " + table_name + " where id=?";
    private final String selectbyid_producto = "select * from " + table_name + " where id_producto=?";
    private final String selectbyid_pedido = "select * from " + table_name + " where id_pedido=?";
    private final String deletebyid = "delete from " + table_name + " where id=?";
    private final String insert = "INSERT INTO " + table_name + " (id, id_producto, id_pedido, unidades, precio) "
            + "VALUES (?, ?, ?, ?, ?)";
    
    private final String update = "UPDATE " + table_name + " SET id_producto = ?, id_pedido = ?, unidades = ?, precio = ? "
            + "WHERE id = ?";
    
    public LineaPedidoDao() {
    }
    
    public DataBaseConnection getConn() {
        return this.conn;
    }
    
    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }

    @Override
    public LineaPedido getById(final String id) {
        LineaPedido sp = null;// = new LineaPedido();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
        
    }


    @Override
    public List<LineaPedido> getAll() {
        final ArrayList<LineaPedido> scl = new ArrayList<>();
        LineaPedido tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }

    @Override
    public void update(final LineaPedido item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getIdProducto());
            pst.setString(2, item.getIdPedido());
            pst.setInt(3, item.getUnidades());
            pst.setFloat(4, item.getPrecio());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getIdProducto() +
                            ", [2]=" + item.getIdPedido() +
                            ", [3]=" + item.getUnidades() +
                            ", [4]=" + item.getPrecio() +
                            "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    @Override
    public void delete(final LineaPedido item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final LineaPedido item) {

        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getIdProducto());
            pst.setString(3, item.getIdPedido());
            pst.setInt(4, item.getUnidades());
            pst.setFloat(5,item.getPrecio());


            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + insert +
                            " | Params: [1]=" + item.getId() +
                            ", [2]="+ item.getUnidades() +
                            ", [3]=" + item.getIdProducto() +
                            ", [4]=" + item.getIdPedido() +
                            ", [5]=" + item.getPrecio() +
                            "]"
            );

        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeeto
    private LineaPedido registerToObject(final ResultSet r) {
        LineaPedido sc =null;
        try {
            sc=new LineaPedido(
                    r.getString("id"),
                    r.getInt("unidades"),
                    r.getString("idProducto"),
                    r.getString("idPedido"),
                    r.getFloat("precio")
                  );
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }
}





