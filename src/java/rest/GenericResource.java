/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.Cliente;
import bd.Conexion;
import bd.Usuario;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Lluis_2
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarClientes() {
        Conexion conexion = new Conexion();
        List<Cliente> lista = null;
        try {
            lista = conexion.obtenerClientes();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(lista);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarCliente(String cli) {
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Cliente cliente;
        cliente = gson.fromJson(cli, Cliente.class);
        boolean result = true;
        try {

            conexion.actualizarCliente(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    @GET
    @Path("{nombre}/{password}")
    public boolean loguear(@PathParam("nombre") String nombre, @PathParam("password") String password) {
        boolean estado;
        Conexion conexion = new Conexion();
        estado = conexion.loguear(nombre, password);  
        return estado;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarCliente(String cli) {
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Cliente cliente;
        cliente = gson.fromJson(cli, Cliente.class);
        boolean result = true;
        try {

            conexion.insertarCliente(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        }
        return result;
    }

    @DELETE
    @Path("/delete/{id}")

    public boolean eliminarCliente(@PathParam("id") int id) {
        Conexion conexion = new Conexion();
        boolean result = true;

        try {

            conexion.eliminarCliente(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        }
        return result;
    }
}
