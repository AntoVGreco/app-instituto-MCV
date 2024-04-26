package excepciones;

/**
 * Excepción lanzada cuando se intenta buscar un usuario que no existe.
 */
public class NoExisteUsuarioException extends Exception {

    /**
     * Constructor de la excepción.
     * Crea una nueva instancia de NoExisteUsuarioException con un mensaje 
     * personalizado que indica el DNI del usuario que no existe.
     * @param dni El DNI del usuario que no existe.
     */
    public NoExisteUsuarioException(String dni) {
        super("El usuario buscado: " + dni + " no existe.");
    }
}
