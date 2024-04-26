package excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a una cuenta suspendida.
 * Se debe comunicar con el administrador para resolver el problema.
 */
public class CuentaSuspendidaException extends Exception {

    /**
     * Constructor de la excepción.
     * Crea una nueva instancia de CuentaSuspendidaException con un mensaje predeterminado.
     */
    public CuentaSuspendidaException() {
        super("Su cuenta ha sido suspendida, comuníquese con el administrador.");
    }
}
