package bd;

public class Usuario {

    private int idUsuarios;
    private String nombreUsuario;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(int idUsuarios, String nombreUsuario, String contrasena) {
        this.idUsuarios = idUsuarios;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuarios=" + idUsuarios + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + '}';
    }

}
