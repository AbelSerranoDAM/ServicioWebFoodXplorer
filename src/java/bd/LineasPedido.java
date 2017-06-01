package bd;

public class LineasPedido {

    private int idPedido;
    private int idProducto;
    private int cantidad;
    private double precio;
    private int iva;

    public LineasPedido() {
    }

    public LineasPedido(int idPedido, int idProducto, int cantidad, double precio, int iva) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.iva = iva;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    
    
    @Override
    public String toString() {
        return "LineasPedidos{" + "idPedido=" + idPedido + ", idProducto=" + idProducto + ", cantidad=" + cantidad + ", precio=" + precio + ", iva=" + iva + '}';
    }

}
