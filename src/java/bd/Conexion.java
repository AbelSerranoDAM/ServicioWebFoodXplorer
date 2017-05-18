/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lluis_2
 */
public class Conexion {

    Connection connection;

    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://mysql-dev-dam.blusoft.net/pizzaexplorer", "dam", "123456");
            // connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "PROFEA1","1234");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/pizzaexplorer", "root", "");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void finalizarConexion() throws SQLException {
        connection.close();
    }

    public boolean insertarUsuario(Usuario u) {
        int res = 0;
        try {
            String sql = "INSERT INTO Usuarios (nombreUsuario, contrasena) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, u.getNombreUsuario());
            stmt.setString(2, u.getContrasena());
            res = stmt.executeUpdate();
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (res == 1);
    }

    public boolean insertarDireccion(Direccion d) {
        int res = 0;
        try {
            String sql = "INSERT INTO Direcciones (calle, piso, poblacion, codPostal, Usuarios_idUsuarios) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, d.getCalle());
            stmt.setString(2, d.getPiso());
            stmt.setString(3, d.getPoblacion());
            stmt.setString(4, d.getCodPostal());
            stmt.setObject(5, d.getIdUsuario());
            res = stmt.executeUpdate();
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (res == 1);
    }

    public boolean insertarPedido(Pedido p) throws ParseException {
        int res = 0;
        String sql = "INSERT INTO pedidos (Direcciones_idDireccion, idEstado, fecha_pedido, Usuarios_idUsuarios, fecha_entrega) "
                + "VALUES (?,?,?,?,?)";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date fechaSalida = formatter.parse(p.getFechaSalida());
        java.sql.Date fechaSalidaSQL = new java.sql.Date(fechaSalida.getTime());
        java.util.Date fechaEntrega = formatter.parse(p.getFechaEntrega());
        java.sql.Date fechaEntregaSQL = new java.sql.Date(fechaEntrega.getTime());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, p.getIdDireccion());
            stmt.setInt(2, p.getIdEstado());
            stmt.setDate(3, fechaSalidaSQL);
            stmt.setInt(4, p.getIdUsuario());
            stmt.setDate(5, fechaEntregaSQL);
            res = stmt.executeUpdate();
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (res == 1);
    }

    public boolean insertarLineasPedido(LineasPedido lp) {
        int res = 0;
        try {
            String sql = "INSERT INTO lineaspedidos (idPedidos, Producto_idProducto, cantidad, precio, IVA) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, lp.getIdPedido());
            stmt.setInt(2, lp.getIdProducto());
            stmt.setInt(3, lp.getCantidad());
            stmt.setDouble(4, lp.getPrecio());
            stmt.setInt(5, lp.getIva());
            res = stmt.executeUpdate();
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (res == 1);
    }

    public List<Producto> obtenerProductosPorTipo(int idTipoProducto) {
        List<Producto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM Productos WHERE activo = 1 AND tipoProductos_idTipoProducto = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, idTipoProducto);
            rset = stmt.executeQuery();
            while (rset.next()) {
                lista.add(new Producto(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getDouble(4), rset.getInt(5),
                        rset.getInt(6), rset.getInt(7), rset.getInt(8), rset.getString(9)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<Direccion> obtenerDireccionesPorUsuario(int idUsuario) {
        List<Direccion> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM direcciones WHERE usuarios_idUsuarios = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rset = stmt.executeQuery();
            while (rset.next()) {
                lista.add(new Direccion(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getInt(6)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Direccion obtenerDireccion(int idDireccion) {
        Direccion d = null;
        try {
            ResultSet rset;
            String sql = "SELECT * FROM direcciones WHERE idDireccion = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, idDireccion);
            rset = stmt.executeQuery();
            while (rset.next()) {
                d = new Direccion(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getInt(6));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public List<Producto> obtenerOfertas() {
        List<Producto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM Productos WHERE ofertaDescuento <> 0 AND activo = 1";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            rset = stmt.executeQuery();
            while (rset.next()) {
                lista.add(new Producto(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getDouble(4), rset.getInt(5),
                        rset.getInt(6), rset.getInt(7), rset.getInt(8), rset.getString(9)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
      public List<Producto> obtenerTodosProductos() {
        List<Producto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM Productos";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            rset = stmt.executeQuery();
            while (rset.next()) {
                lista.add(new Producto(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getDouble(4), rset.getInt(5),
                        rset.getInt(6), rset.getInt(7), rset.getInt(8), rset.getString(9)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<Pedido> obtenerPedidosPorUsuario(int idUsuario) {
        List<Pedido> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM pedidos WHERE Usuarios_idUsuarios = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rset = stmt.executeQuery();
            SimpleDateFormat salida = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat entrega = new SimpleDateFormat("dd-MM-yyyy");
            while (rset.next()) {
                lista.add(new Pedido(rset.getInt(1), rset.getInt(2), rset.getInt(3), salida.format(rset.getDate(4)),
                        rset.getInt(5), entrega.format(rset.getDate(6))));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Pedido obtenerPedido(int idPedido) {
        Pedido p = null;
        try {
            ResultSet rset;
            String sql = "SELECT * FROM pedidos WHERE idPedidos = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, idPedido);
            rset = stmt.executeQuery();
            while (rset.next()) {
                p = new Pedido(rset.getInt(1), rset.getInt(2), rset.getInt(3), rset.getString(4), rset.getInt(5), rset.getString(6));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public List<TipoProducto> obtenerTiposProducto() {
        List<TipoProducto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM tipoproductos";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            rset = stmt.executeQuery();
            while (rset.next()) {
                lista.add(new TipoProducto(rset.getInt(1), rset.getString(2)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public boolean loguear(String nombreUsuario, String password) {
        boolean estado = false;
        ResultSet rset;
        try {
            String sql = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND contrasena = ?";
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, password);
            rset = stmt.executeQuery();
            if (rset.next()) {
                estado = true;
            }
            System.out.println(estado);
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

//    public boolean actualizarCliente(Cliente cli) throws SQLException {
//        boolean result;
//        String sql = "UPDATE cliente SET nombre = ?, telefono = ? WHERE idcliente = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, cli.getNombre()); //stmt.setString(1, cli.getNombre);
//        stmt.setInt(2, cli.getTelefono());
//        stmt.setInt(3, cli.getIdCliente());
//
//        int res = stmt.executeUpdate();
//        if (res == 0) {
//            result = insertarCliente(cli);
//        } else {
//            result = true;
//        }
//
//        return (result);
//    }
    public List<LineasPedido> obtenerProductosPedido(int idPedido) {
        List<LineasPedido> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM lineaspedidos WHERE idPedidos = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, idPedido);
            rset = stmt.executeQuery();
            while (rset.next()) {
                lista.add(new LineasPedido(rset.getInt(1), rset.getInt(2), rset.getInt(3), rset.getDouble(4), rset.getInt(5)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public boolean actualizarEstado(Estado es) throws SQLException {
        String sql = "UPDATE estados SET nomEstado = ?, tiempo = ? WHERE idEstados = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, es.getNombreEstado());
        stmt.setInt(2, es.getTiempo());
        stmt.setInt(3, es.getIdEstado());
        int res = stmt.executeUpdate();
        return res == 1;
    }

    public boolean eliminarCliente(int id) throws SQLException {

        String sql = "DELETE FROM cliente WHERE idcliente = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        int res = stmt.executeUpdate();
        return (res == 1);
    }

}
