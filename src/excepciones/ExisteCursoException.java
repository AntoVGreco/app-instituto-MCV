package excepciones;

/**
 * Excepción lanzada cuando se intenta crear un curso con un nombre que ya existe.
 */
public class ExisteCursoException extends Exception {

    /**
     * Constructor de la excepción.
     * Crea una nueva instancia de ExisteCursoException con un mensaje personalizado que indica el nombre del curso que ya existe.
     * @param nombreCurso El nombre del curso que ya existe.
     */
    public ExisteCursoException(String nombreCurso) {
        super("El curso " + nombreCurso + " ya existe.");
    }
}
