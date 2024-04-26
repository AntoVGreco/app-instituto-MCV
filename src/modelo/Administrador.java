package modelo;

/**
 * Clase que representa a un administrador en el sistema. Extiende la clase
 * Usuario.
 */
public class Administrador extends Usuario {

    /**
     * Constructor de la clase Administrador.
     *
     * @param nombre El nombre del administrador.
     * @param apellido El apellido del administrador.
     * @param dni El DNI del administrador.
     */
    public Administrador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
        this.perfil = "Admin";
    }

}
