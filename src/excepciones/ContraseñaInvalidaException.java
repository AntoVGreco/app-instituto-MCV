package excepciones;

/**
 * Excepción lanzada cuando se detecta una contraseña inválida.
 */
public class ContraseñaInvalidaException extends Exception {

    /**
     * Constructor de la excepción.
     * Crea una nueva instancia de ContraseñaInvalidaException con un mensaje predeterminado.
     */
    public ContraseñaInvalidaException() {
        super("Contraseña inválida.");
    }
}

