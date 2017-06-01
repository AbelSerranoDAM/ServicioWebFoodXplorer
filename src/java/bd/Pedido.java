package bd;

import java.util.ArrayList;

public class Pedido {

    private long idPedido;
    private int idDireccion;
    private int idEstado;
    private String fechaSalida;
    private String fechaEntrega;
    private String correo;

    public Pedido() {
    }

    public Pedido(long idPedido, int idDireccion, int idEstado, String fechaSalida, String fechaEntrega, String correo) {
        this.idPedido = idPedido;
        this.idDireccion = idDireccion;
        this.idEstado = idEstado;
        this.fechaSalida = fechaSalida;
        this.correo = correo;
        this.fechaEntrega = fechaEntrega;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", idDireccion=" + idDireccion + ", idEstado=" + idEstado + ", fechaSalida=" + fechaSalida + ", correo=" + correo + ", fechaEntrega=" + fechaEntrega + '}';
    }

}
