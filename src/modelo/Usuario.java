package modelo;

import java.io.Serializable;
import static modelo.PassHasher.hash;

/**
 * Clase abstracta que representa a un usuario del sistema.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public abstract class Usuario implements Serializable {

    private String dni;
    protected String nombre;
    protected String apellido;
    private String password;
    private boolean sancionado;
    protected String perfil; //admin, alumno, docente

    /**
     * Constructor de la clase Usuario. Se utiliza metodo hash importado de la clase 
     * passHasher para encriptar la contraseña de cada usuario y así almacenarla. 
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param dni El DNI del usuario.
     */
    public Usuario(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.password = hash(dni);
        this.sancionado = false;
    }

    /**
     * Devuelve el apellido del usuario.
     * @return El apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Devuelve el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Indica si el usuario está sancionado.
     * @return true si el usuario está sancionado, false en caso contrario.
     */
    public boolean isSancionado() {
        return sancionado;
    }

    /**
     * Restablece la contraseña del usuario.
     */
    public void blanquearPassword() {
        this.password = hash(dni);
    }

    /**
     * Cambia la contraseña del usuario.
     * @param pass La nueva contraseña.
     */
    public void cambioPassword(String pass) {
        this.password = hash(pass);
    }

    /**
     * Devuelve el DNI del usuario.
     * @return El DNI del usuario.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Devuelve el perfil del usuario.
     * @return El perfil del usuario.
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * Reactiva el usuario, eliminando cualquier sanción.
     */
    public void reactivarUsuario() {
        this.sancionado = false;
    }

    /**
     * Suspende el usuario, aplicando una sanción.
     */
    public void suspenderUsuario() {
        this.sancionado = true;
    }

    /**
     * Se sobreescribe el metodo toString para mostrar en JList.
     * @return Apellido y Nombre del usuario.
     */
    @Override
    public String toString() {
        return this.getApellido() + ", " + this.getNombre();
    }

    /**
     * Devuelve la información detallada del usuario.
     * @return Una cadena que contiene el perfil, nombre, apellido y DNI del usuario.
     */
    public String toStringUsuario() {
        return perfil + "\nNombre: " + nombre + "\nApellido:" + apellido + "\nDni: " + dni;
    }

    /**
     * Devuelve el estado de la cuenta del usuario (activo o sancionado).
     * @return "Activa" si la cuenta no está sancionada, "Sancionada" en caso contrario.
     */
    public String getEstadoCuenta() {
        if (isSancionado()) {
            return "Sancionada";
        } else {
            return "Activa";
        }
    }
}
