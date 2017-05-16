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

    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/pizzaexplorer", "root", "");
            // connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "PROFEA1","1234");
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

    public boolean insertarCliente(Cliente cli) throws SQLException {
        String sql = "INSERT INTO Cliente (Nombre, Telefono) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cli.getNombre()); //stmt.setString(1, cli.getNombre);
        stmt.setInt(2, cli.getTelefono());
        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }

    public List<Cliente> obtenerClientes() throws SQLException {
        ResultSet rset;
        List<Cliente> lista = new ArrayList();
        String sql = "SELECT IdCliente, Nombre, Telefono FROM Cliente";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Cliente(rset.getInt("IdCliente"), rset.getString("Nombre"), rset.getInt("Telefono")));

        }
        finalizarConexion();
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

    public boolean actualizarCliente(Cliente cli) throws SQLException {
        boolean result;
        String sql = "UPDATE cliente SET nombre = ?, telefono = ? WHERE idcliente = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cli.getNombre()); //stmt.setString(1, cli.getNombre);
        stmt.setInt(2, cli.getTelefono());
        stmt.setInt(3, cli.getIdCliente());

        int res = stmt.executeUpdate();
        if (res == 0) {
            result = insertarCliente(cli);
        } else {
            result = true;
        }

        return (result);
    }

    public boolean eliminarCliente(int id) throws SQLException {

        String sql = "DELETE FROM cliente WHERE idcliente = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        int res = stmt.executeUpdate();

        return (res == 1);
    }

}
