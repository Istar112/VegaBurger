package ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedidos;

import java.sql.SQLException;
import java.util.List;

import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
public class BBDDRepositorioPedidosJava {
    private final DataBaseConnection db;
    private PedidoDao dao;

    public BBDDRepositorioPedidosJava(String path) throws Exception{
        super();
        this.db=new DataBaseConnection();
        this.db.setConfig_path(path);
        this.db.open();
        dao=new PedidoDao();
        dao.setConn(this.db);

    }
    public void add(Pedido item){
        this.dao.insert(item);
    }

    public boolean remove(Pedido item){
        this.dao.delete(item);
        return true;
    }
    public boolean remove(String id){
        var item=this.dao.getById(id);
        if(item!=null){
            this.dao.delete(item);
            return true;
        }
        return false;
    }
    public boolean  update(Pedido item){
        this.dao.update(item);
        return true;
    }
    public List<Pedido> getAll() {
        return this.dao.getAll();
    }

    // nombre_usuario del pedido
    public Pedido findByName(String name){
        return this.dao.findByName(name);

    }
    public Pedido  getById(String id){
        return this.dao.getById(id);

    }
    public List<Pedido> findByIds(List<String> ids){
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
