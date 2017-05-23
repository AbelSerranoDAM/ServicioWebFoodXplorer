package bd;

public class Usuario {

    private int idUsuario;
    private String correo;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(int idUsuario, String correo, String contrasena) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", correo=" + correo + ", contrasena=" + contrasena + '}';
    }

}
