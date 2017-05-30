package bd;

public class Pedido {

    private long idPedido;
    private int idDireccion;
    private int idEstado;
    private String fechaSalida;
    private String idUsuario;
    private String fechaEntrega;

    public Pedido() {
    }

    public Pedido(long idPedido, int idDireccion, int idEstado, String fechaSalida, String fechaEntrega, String idUsuario) {
        this.idPedido = idPedido;
        this.idDireccion = idDireccion;
        this.idEstado = idEstado;
        this.fechaSalida = fechaSalida;
        this.idUsuario = idUsuario;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", idDireccion=" + idDireccion + ", idEstado=" + idEstado + ", fechaSalida=" + fechaSalida + ", idUsuario=" + idUsuario + ", fechaEntrega=" + fechaEntrega + '}';
    }

}
