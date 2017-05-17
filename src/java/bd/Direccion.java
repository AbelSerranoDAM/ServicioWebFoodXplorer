package bd;

public class Direccion {

    private int idDireccion;
    private String calle;
    private String piso;
    private String poblacion;
    private String codPostal;
    private int idUsuario;

    public Direccion() {
    }

    public Direccion(int idDireccion, String calle, String piso, String poblacion, String codPostal, int idUsuario) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.piso = piso;
        this.poblacion = poblacion;
        this.codPostal = codPostal;
        this.idUsuario = idUsuario;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Direccion{" + "idDireccion=" + idDireccion + ", calle=" + calle + ", piso=" + piso + ", poblacion=" + poblacion + ", codPostal=" + codPostal + ", idUsuario=" + idUsuario + '}';
    }

}
