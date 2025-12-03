package ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedidos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.DependienteDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;


/**
 * he terminado los atributos, el resgiterobject y estoy implementando los metodos de la interfaz de idao
 *
 * */

public class PedidoDao implements IDao<Pedido> {
    private DataBaseConnection conn;
    private final String table_name = "pedido";
    private final String selectall = "SELECT * FROM " + table_name;
    private final String selectbyid = "SELECT * FROM " + table_name + " WHERE id = ?";
    private final String findbyname = "select * from " + table_name + " where nombre_usuario=?";
    private final String deletebyid = "delete from " + table_name + " where id='?'";
    private final String insert = "INSERT INTO " + table_name + " (id, id_dependiente, np_totales, nombre_usuario,importe_total,np_pendiente_entrega) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String update = "UPDATE " + table_name + " id_dependiente = = ?, np_totales = ?, nombre_usuario = ?, importe_total = ?, np_pendiente_entrega = ? " + "WHERE id = ?";


    public PedidoDao() {
    }
    public DataBaseConnection getConn(){
        return this.conn;
    }
    public void setConn(final DataBaseConnection conn){
        this.conn=conn;
    }


    // Hecho
    @Override
    public Pedido getById(final String id) {
        Pedido sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(DependienteDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    // no se este es propio de dependiente
    public Pedido findByName(final String name) {
        Pedido sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(DependienteDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    //hecho
    @Override
    public List<Pedido> getAll() {
        final ArrayList<Pedido> scl = new ArrayList<>();
        Pedido tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }
    // hecho
    @Override
    public void update(final Pedido item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getId());
            pst.setString(2, item.getIdDependiente());
            pst.setInt(3, item.getNpTotales());
            pst.setString(4, item.getNombreUsuario());
            pst.setFloat(5, item.getImporteTotal());
            pst.setInt(6, item.getNpPendientesEntrega());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(DependienteDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getId() +
                            ", [2]=" + item.getIdDependiente() +
                            ", [3]=" + item.getNpTotales() +
                            ", [4]=" + item.getNombreUsuario() +
                            ", [5]=" + item.getImporteTotal() +
                            ", [6]=" + item.getNpPendientesEntrega() +
                            ", [7]=" + item.getId() +
                            "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    @Override
    public void delete(final Pedido item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(DependienteDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //hecho
    @Override
    public void insert(final Pedido item) {

        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getIdDependiente());
            pst.setInt(3, item.getNpTotales());
            pst.setString(4, item.getNombreUsuario());
            pst.setFloat(5,item.getImporteTotal());
            pst.setInt(6, item.getNpPendientesEntrega());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(DependienteDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + insert +
                            " | Params: [1]=" + item.getId() +
                            ", [2]="+ item.getId() +
                            ", [3]=" + item.getIdDependiente() +
                            ", [4]=" + item.getNpTotales() +
                            ", [5]=" + item.getImporteTotal() +
                            ", [6]=" + item.getNpPendientesEntrega() +
                            "]"
            );

        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    // hecho
    private Pedido registerToObject(final ResultSet r) {
        Pedido sc =null;

        try {

            sc=new Pedido(
                    r.getString("id"),
                    r.getString("id_dependiente"),
                    r.getInt("np_totales"),
                    r.getString("nombre_usuario"),
                    r.getFloat("importe_total"),
                    r.getInt("np_pendiente_entrega")
            );

            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }
}