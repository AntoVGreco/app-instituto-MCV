package excepciones;

/**
 * Excepción lanzada cuando se intenta crear un usuario con un DNI que ya existe.
 */
public class ExisteUsuarioException extends Exception {

    /**
     * Constructor de la excepción.
     * Crea una nueva instancia de ExisteUsuarioException con un mensaje personalizado que indica el DNI del usuario que ya existe.
     * @param dni El DNI del usuario que ya existe.
     */
    public ExisteUsuarioException(String dni) {
        super("El usuario " + dni + " ya existe.");
    }
}
