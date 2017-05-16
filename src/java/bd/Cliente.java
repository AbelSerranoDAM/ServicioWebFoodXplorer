/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Lluis_2
 */
public class Cliente {
    
        private int idcliente;
        private String nombre;
        private int telefono;
      

    public Cliente(int id, String nombre, int telefono) {
        this.idcliente = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Cliente() {
    }

    public String getNombre() {
        return nombre;
    }

    public int getTelefono() {
        return telefono;
    }
    public int getIdCliente()
    {
        return idcliente;
    }

      
    
}
