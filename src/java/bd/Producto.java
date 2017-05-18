package bd;

public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;

    private double precio;
    private int iva;
    private int ofertaDescuento;
    private int activo;
    private int idTipoProducto;
    private String urlImagen;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String descripcion, double precio, int iva, int ofertaDescuento, int activo, int idTipoProducto, String urlImagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.iva = iva;
        this.ofertaDescuento = ofertaDescuento;
        this.activo = activo;
        this.idTipoProducto = idTipoProducto;
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getOfertaDescuento() {
        return ofertaDescuento;
    }

    public void setOfertaDescuento(int ofertaDescuento) {
        this.ofertaDescuento = ofertaDescuento;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", iva=" + iva + ", ofertaDescuento=" + ofertaDescuento + ", activo=" + activo + ", idTipoProducto=" + idTipoProducto + ", urlImagen=" + urlImagen + '}';
    }

}
