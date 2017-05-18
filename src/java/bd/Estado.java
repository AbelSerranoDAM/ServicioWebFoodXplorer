package bd;

public class Estado {

    private int idEstado;
    private String nombreEstado;
    private int tiempo;

    public Estado() {
    }

    public Estado(int idEstado, String nombreEstado, int tiempo) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.tiempo = tiempo;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Estado{" + "idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + ", tiempo=" + tiempo + '}';
    }

}
