package modelo;

/**
 * Clase que representa a un docente en el sistema.
 * Extiende la clase Usuario.
 */
public class Docente extends Usuario {

    /**
     * Constructor de la clase Docente.
     * @param nombre El nombre del docente.
     * @param apellido El apellido del docente.
     * @param dni El DNI del docente.
     */
    public Docente(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
        this.perfil = "Docente";
    }

}
