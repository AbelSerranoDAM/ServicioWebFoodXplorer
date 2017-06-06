/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.Cliente;
import bd.Conexion;
import bd.Direccion;
import bd.Estado;
import bd.LineasPedido;
import bd.Pedido;
import bd.Producto;
import bd.TipoProducto;
import bd.Usuario;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * TODOS LOS METODOS GENERADOS EN ESTE SERVICIO WEB SON LLAMADAS DE LOS METODOS
 * COMENTADOS EN LA CLASE CONEXION. LA UNICA DIFERENCIA SON LAS ETIQUETAS GET
 * (UTILIZADA PARA OBTENER DATOS) Y LA ETIQUETA PUT O POST (UTILIZADA PARA
 * PERSISTIR DATOS EN LA BASE DE DATOS. ADEMAS ESTAN LOS PATH, QUE SIRVEN PARA
 * ESPECIFICARLE LA RUTA DEL SERVICIO WEB A LOS METODOS SE RETORNA TODO EN
 * FORMATO JSON.
 *
 * @author Abel
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
    @Path("/productos/tipo/buscarTiposProducto")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarProductosPorTipo() {
        Conexion conexion = new Conexion();
        List<TipoProducto> lista;
        lista = conexion.obtenerTiposProducto();
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("/productos/tipo/{idTipoProducto}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarProductosPorTipo(@PathParam("idTipoProducto") int idTipoProducto) {
        Conexion conexion = new Conexion();
        List<Producto> lista;
        lista = conexion.obtenerProductosPorTipo(idTipoProducto);
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("/productos/ofertas")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarOfertas() {
        Conexion conexion = new Conexion();
        List<Producto> lista;
        lista = conexion.obtenerOfertas();
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("listarTodosLosProductos")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarTodosLosProductos() {
        Conexion conexion = new Conexion();
        List<Producto> lista;
        lista = conexion.obtenerTodosProductos();
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("/pedidos/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarPedidosPorUsuario(@PathParam("correo") String correo) {
        Conexion conexion = new Conexion();
        List<Pedido> lista;
        lista = conexion.obtenerPedidosPorUsuario(correo);
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("/obtenerPedido/{idPedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarPedido(@PathParam("idPedido") String idPedido) {
        Conexion conexion = new Conexion();
        Pedido p;
        p = conexion.obtenerPedido(idPedido);
        Gson gson = new Gson();
        return gson.toJson(p);
    }

    @GET
    @Path("/obtenerEstado/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerEstado(@PathParam("idEstado") String idEstado) {
        Conexion conexion = new Conexion();
        Estado es;
        es = conexion.obtenerEstado(idEstado);
        Gson gson = new Gson();
        return gson.toJson(es);
    }

    @GET
    @Path("/obtenerProductosPorIdPedido/{idPedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerProductosPorIdPedido(@PathParam("idPedido") String idPedido) {
        Conexion conexion = new Conexion();
        List<Producto> listaProductos;
        listaProductos = conexion.obtenerProductosPorIdPedido(idPedido);
        Gson gson = new Gson();
        return gson.toJson(listaProductos);
    }

    @PUT
    @Path("InsertarUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarUsuario(String u) {
        boolean result;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Usuario usu;
        usu = gson.fromJson(u, Usuario.class);
        result = conexion.insertarUsuario(usu);
        return result;
    }

    @POST
    @Path("direccion/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public int insertarDireccion(String d) {
        int result;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Direccion dir;
        dir = gson.fromJson(d, Direccion.class);
        result = conexion.insertarDireccion(dir);
        return result;
    }

    @POST
    @Path("pedido/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public int insertarPedido(String p) throws ParseException {
        int result;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Pedido ped;
        ped = gson.fromJson(p, Pedido.class);
        result = conexion.insertarPedido(ped);
        return result;
    }

    @POST
    @Path("lineasPedido/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarLineasPedido(String lp) {
        boolean result;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        LineasPedido lin;
        lin = gson.fromJson(lp, LineasPedido.class);
        System.out.println("la linea a insertar es: " + lp);
        result = conexion.insertarLineasPedido(lin);
        return result;
    }

    @GET
    @Path("/direccion/obtener/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarDireccionesPorUsuario(@PathParam("idUsuario") int idUsuario) {
        Conexion conexion = new Conexion();
        List<Direccion> lista;
        lista = conexion.obtenerDireccionesPorUsuario(idUsuario);
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("/direccion/obtenerPorCorreo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarDireccionesPorCorreo(@PathParam("correo") String correo) {
        Conexion conexion = new Conexion();
        List<Direccion> lista;
        lista = conexion.obtenerDireccionesPorCorreo(correo);
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("/direccion/obtener2/{idDireccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarDireccion(@PathParam("idDireccion") int idDireccion) {
        Conexion conexion = new Conexion();
        Direccion d;
        d = conexion.obtenerDireccion(idDireccion);
        Gson gson = new Gson();
        return gson.toJson(d);
    }

    @GET
    @Path("/loguearUsuario/{nombre}/{password}")
    public Integer loguear(@PathParam("nombre") String nombre, @PathParam("password") String password) {
        int estado;
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
        //conexion.insertarCliente(cliente);
        return result;
    }

    @GET
    @Path("/pedido/obtenerDetalles/{idPedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarProductosPorPedido(@PathParam("idPedido") int idPedido) {
        Conexion conexion = new Conexion();
        List<LineasPedido> lp;
        lp = conexion.obtenerProductosPedido(idPedido);
        Gson gson = new Gson();
        return gson.toJson(lp);
    }

    @GET
    @Path("/obtenerPedidosPorCocinar")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarPedidosPorCocinar() {
        Conexion conexion = new Conexion();
        List<Pedido> lp;
        lp = conexion.obtenerTodosLosPedidosParaCocinar();
        Gson gson = new Gson();
        return gson.toJson(lp);
    }

    @POST
    @Path("actualizarEstado")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarEstado(String ped) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Pedido p;
        p = gson.fromJson(ped, Pedido.class);
        try {
            result = conexion.actualizarEstadoPedido(p);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @POST
    @Path("actualizarEstadoPedidoEntregado")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarEstadoPedidoEntregado(String ped) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Pedido p;
        p = gson.fromJson(ped, Pedido.class);
        try {
            result = conexion.actualizarEstadoPedidoEntregado(p);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @POST
    @Path("actualizarEstadoPedidoEnReparto")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarEstadoPedidoEnReparto(String ped) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Pedido p;
        p = gson.fromJson(ped, Pedido.class);
        try {
            result = conexion.actualizarEstadoPedidoEnReparto(p);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @GET
    @Path("/obtenerPedidosParaRepartir")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarPedidosParaRepartir() {
        Conexion conexion = new Conexion();
        List<Pedido> lp;
        lp = conexion.obtenerPedidosParaRepartir();
        Gson gson = new Gson();
        return gson.toJson(lp);
    }

    @POST
    @Path("actualizarFechaEntregaPedido")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarFechaEntregaPedido(String ped) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Pedido p;
        p = gson.fromJson(ped, Pedido.class);
        try {
            result = conexion.actualizarFechaEntrega(p);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
