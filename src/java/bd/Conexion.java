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
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lluis_2
 */
public class Conexion {

    Connection connection;

    /**
     * Se crea una conexión Mysql.
     */
    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://server.blusoft.net:3306/pizzaExplorer", "dam", "1234");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost/pizzaexplorer", "dam", "");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Retorna una conexión.
     *
     * @return Conexión
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión
     *
     * @throws SQLException
     */
    public void finalizarConexion() throws SQLException {
        connection.close();
    }

    /**
     * INSERTAR USUARIO
     *
     * @param u USUARIO
     * @return RESULTADO INSERCIÓN
     */
    public boolean insertarUsuario(Usuario u) {
        int res = 0;
        try {
            String sql = "INSERT INTO usuarios (correo, contrasena) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, u.getCorreo());
            stmt.setString(2, u.getContrasena());
            res = stmt.executeUpdate();
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (res == 1);
    }

    /**
     * INSERTAR DIRECCION
     *
     * @param d DIRECCION
     * @return RESULTADO INSERCIÓN
     */
    public int insertarDireccion(Direccion d) {
        int res = 0;
        PreparedStatement stmt;
        ResultSet rset;
        int idDireccion = 0;
        try {
            if (d.getIdUsuario() != 0) {
                String sql = "INSERT INTO direcciones (calle, piso, poblacion, codPostal, Usuarios_idUsuarios) VALUES (?,?,?,?,?)";
                stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, d.getCalle());
                stmt.setString(2, d.getPiso());
                stmt.setString(3, d.getPoblacion());
                stmt.setString(4, d.getCodPostal());
                stmt.setInt(5, d.getIdUsuario());
            } else {
                String sql = "INSERT INTO direcciones (calle, piso, poblacion, codPostal) VALUES (?,?,?,?)";
                stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, d.getCalle());
                stmt.setString(2, d.getPiso());
                stmt.setString(3, d.getPoblacion());
                stmt.setString(4, d.getCodPostal());
            }
            stmt.executeUpdate();
            rset=  stmt.getGeneratedKeys();  
            rset.next(); 
            idDireccion= rset.getInt(1);
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idDireccion;
    }

    /**
     * INSERTAR PEDIDO
     *
     * @param p PEDIDO
     * @return RESULTADO INSERCIÓN
     * @throws ParseException
     */
    public int insertarPedido(Pedido p) throws ParseException {
        int res = 0;
        String sql = "INSERT INTO pedidos (Direcciones_idDireccion, idEstado, fecha_pedido, correo, fecha_entrega) "
                + "VALUES (?,?,?,?,?)";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        java.util.Date fechaSalida = formatter.parse(p.getFechaSalida());
        java.sql.Timestamp fechaSalidaSQL = new java.sql.Timestamp(fechaSalida.getTime());
        System.out.println(fechaSalida);
        System.out.println(fechaSalidaSQL);

        try {
            PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            if (p.getFechaEntrega() != null) {
                java.util.Date fechaEntrega = formatter.parse(p.getFechaEntrega());
                java.sql.Date fechaEntregaSQL = new java.sql.Date(fechaEntrega.getTime());
                stmt.setDate(5, fechaEntregaSQL);
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.setInt(1, p.getIdDireccion());
            stmt.setInt(2, p.getIdEstado());
            stmt.setTimestamp(3, fechaSalidaSQL);
            stmt.setString(4, p.getCorreo());
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            ResultSet rset=  stmt.getGeneratedKeys();  
            rset.next(); 
            res= rset.getInt(1);
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /**
     * INSERTAR LINEAS DE PEDIDO
     *
     * @param lp LINEASPEDIDO
     * @return RESULTADO INSERCION
     */
    public boolean insertarLineasPedido(LineasPedido lp) {
        int res = 0;
        try {
            String sql = "INSERT INTO lineasPedidos (idPedidos, Producto_idProducto, cantidad, precio, IVA) VALUES (?,?,?,?,?)";
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

    /**
     * OBTENER PRODUCTOS POR TIPO
     *
     * @param idTipoProducto
     * @return LISTA DE PRODUCTOS
     */
    public List<Producto> obtenerProductosPorTipo(int idTipoProducto) {
        List<Producto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM productos WHERE activo = 1 AND tipoProductos_idTipoProducto = ?";
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

    /**
     * OBTENER DIRECCIONES POR USUARIO
     *
     * @param idUsuario
     * @return LISTA DIRECCIONES
     */
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

    public List<Direccion> obtenerDireccionesPorCorreo(String correo) {
        List<Direccion> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT d.idDireccion, d.Calle, d.Piso, d.Poblacion, d.CodPostal , d.Usuarios_idUsuarios "
                    + "FROM direcciones d, usuarios u WHERE d.Usuarios_idUsuarios = u.idUsuarios AND u.correo = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, correo);
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

    /**
     * OBTENER DIRECCION POR ID
     *
     * @param idDireccion
     * @return UNA DIRECCION
     */
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

    /**
     * BUSCAR PRODUCTOS CON OFERTA
     *
     * @return LISTA DE OFERTAS
     */
    public List<Producto> obtenerOfertas() {
        List<Producto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM productos WHERE ofertaDescuento <> 0 AND activo = 1";
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

    /**
     * OBTENER TODOS LOS PRODUCTOS
     *
     * @return LISTA DE PRODUCTOS
     */
    public List<Producto> obtenerTodosProductos() {
        List<Producto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM productos";
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

    /**
     * OBTENER PEDIDOS POR USUARIO
     *
     * @param correo
     * @return LISTA DE PEDIDOS
     */
    public List<Pedido> obtenerPedidosPorUsuario(String correo) {
        List<Pedido> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM pedidos WHERE correo = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, correo);
            rset = stmt.executeQuery();
            SimpleDateFormat salida = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat entrega = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            while (rset.next()) {
                if (rset.getDate(5) == null) {
                    lista.add(new Pedido(rset.getLong(1), rset.getInt(2), rset.getInt(3), salida.format(rset.getTimestamp(4)),
                            null, rset.getString(6)));
                } else {
                    lista.add(new Pedido(rset.getLong(1), rset.getInt(2), rset.getInt(3), salida.format(rset.getDate(4)),
                            entrega.format(rset.getTimestamp(5)), rset.getString(6)));
                }
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /**
     * OBTENER PEDIDO POR ID
     *
     * @param idPedido
     * @return UN PEDIDO
     */
    public Pedido obtenerPedido(String idPedido) {
        Pedido p = null;
        try {
            ResultSet rset;
            String sql = "SELECT * FROM pedidos WHERE idPedidos = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, idPedido);
            rset = stmt.executeQuery();
            while (rset.next()) {
                p = new Pedido(rset.getLong(1), rset.getInt(2), rset.getInt(3), rset.getString(4), rset.getString(5), rset.getString(6));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    /**
     * OBTENER TODOS LOS TIPOS DE PRODUCTO
     *
     * @return LISTA DE TIPOS DE PRODUCTO
     */
    public List<TipoProducto> obtenerTiposProducto() {
        List<TipoProducto> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM tipoProductos";
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

    /**
     * SE LOGUEA UN USUARIO MEDIANTE NOMBRE DE USUARIO Y CONTRASEÑA
     *
     * @param correo
     * @param password
     * @return RESULTADO DEL LOGUEO
     */
    public boolean loguear(String correo, String password) {
        boolean estado = false;
        ResultSet rset;
        try {
            String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, correo);
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
    /**
     * OBTENER LINEAS DE PEDIDO DE UN PEDIDO
     *
     * @param idPedido
     * @return LISTA DE LINEAS DE PEDIDO
     */
    public List<LineasPedido> obtenerProductosPedido(int idPedido) {
        List<LineasPedido> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM lineasPedidos WHERE idPedidos = ?";
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

    public Estado obtenerEstado(String idEstado) {
        Estado es = null;
        try {
            ResultSet rset;
            String sql = "SELECT * FROM estados WHERE idEstados = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, idEstado);
            rset = stmt.executeQuery();
            while (rset.next()) {
                es = new Estado(rset.getInt(1), rset.getString(2), rset.getInt(3));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return es;
    }

    public List<Producto> obtenerProductosPorIdPedido(String idPedido) {
        List<Producto> listaProductos = new ArrayList();
        try {
            ResultSet rset;
            String sql = "SELECT p.idProducto, p.Nombre, p.Descripcion, "
                    + "p.Precio, p.Iva, p.OfertaDescuento, p.Activo, "
                    + "p.tipoProductos_idTipoProducto, p.urlImagen "
                    + "FROM pedidos ped, lineasPedidos l, productos p "
                    + "WHERE ped.idPedidos = l.idPedidos "
                    + "AND l.Producto_idProducto = p.idProducto AND \n"
                    + "ped.idPedidos=?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, idPedido);
            rset = stmt.executeQuery();
            while (rset.next()) {
                listaProductos.add(new Producto(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getDouble(4), rset.getInt(5),
                        rset.getInt(6), rset.getInt(7), rset.getInt(8), rset.getString(9)));
            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProductos;
    }

    public List<Pedido> obtenerTodosLosPedidosParaCocinar() {
        List<Pedido> lista = null;
        try {
            ResultSet rset;
            lista = new ArrayList();
            String sql = "SELECT * FROM pedidos WHERE idEstado = 1";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            rset = stmt.executeQuery();
            SimpleDateFormat salida = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat entrega = new SimpleDateFormat("dd-MM-yyyy");
            while (rset.next()) {
                if (rset.getDate(5) == null) {
                    lista.add(new Pedido(rset.getLong(1), rset.getInt(2), rset.getInt(3), salida.format(rset.getTimestamp(4)),
                            null, rset.getString(6)));
                } else {
                    lista.add(new Pedido(rset.getLong(1), rset.getInt(2), rset.getInt(3), salida.format(rset.getDate(4)),
                            entrega.format(rset.getTimestamp(5)), rset.getString(6)));
                }

            }
            finalizarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /**
     * ACTUALIZAR ESTADO DEL PRODUCTO
     *
     * @param p
     * @return RESULTADO DE LA ACTUALIZACION
     * @throws SQLException
     */
    public boolean actualizarEstadoPedido(Pedido p) throws SQLException {
        String sql = "UPDATE pedidos SET idEstado = ? WHERE idPedidos = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, p.getIdEstado());
        stmt.setLong(2, p.getIdPedido());
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
