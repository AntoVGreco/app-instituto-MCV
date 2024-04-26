package modelo;

import javax.swing.DefaultListModel;

/**
 * Clase que representa a un alumno en el sistema.
 * Extiende la clase Usuario.
 */
public class Alumno extends Usuario {

    private DefaultListModel<Curso> cursosAprobados;
    private DefaultListModel<Curso> cursosInscriptos;
    private String calificacion;

    /**
     * Constructor de la clase Alumno.
     * @param nombre El nombre del alumno.
     * @param apellido El apellido del alumno.
     * @param dni El DNI del alumno.
     */
    public Alumno(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
        this.perfil = "Alumno";
        this.calificacion = "Sin calificar"; // para uso del docente al momento de calificar.
        this.cursosInscriptos = new DefaultListModel<>();
        this.cursosAprobados = new DefaultListModel<>();
    }

    /**
     * Obtiene el total de cursos aprobados por el alumno.
     * @return El número total de cursos aprobados.
     */
    protected int totalCursosAprobados() {
        return cursosAprobados.size();
    }

    /**
     * Obtiene la lista de cursos en los que el alumno está inscripto.
     * @return La lista de cursos inscriptos.
     */
    public DefaultListModel<Curso> getCursosInscriptos() {
        return cursosInscriptos;
    }

    /**
     * Obtiene la lista de cursos que el alumno ha aprobado.
     * @return La lista de cursos aprobados.
     */
    public DefaultListModel<Curso> getCursosAprobados() {
        return cursosAprobados;
    }

    /**
     * Metodo para obtener en la visual el estado temporal de la calificacion del alumno
     * al momento que el docente cierra las notas.
     * @return La calificación del alumno.
     */
    public String getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación del alumno de manera temporal para cerrar la cursada.
     * @param calificacion La nueva calificación del alumno.
     */
    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
    
    /**
     * Agrega un curso aprobado por el alumno.
     * @param cursoAprobado El curso aprobado que se va a agregar.
     */
    public void agregarCursoAprobado(Curso cursoAprobado){
        cursosAprobados.addElement(cursoAprobado);
    }
    
    /**
     * Agrega un nuevo curso en el que el alumno se inscribe.
     * @param nuevoCurso El nuevo curso en el que se inscribe el alumno.
     */
    public void agregarCursoInscripto (Curso nuevoCurso){
        cursosInscriptos.addElement(nuevoCurso);
    }
    
    /**
     * Quita un curso en el que el alumno estaba inscripto.
     * @param cursoFinalizado El curso que el alumno ha finalizado y se quita de su lista de 
     * cursos inscriptos.
     */
    public void quitarCursoInscripto (Curso cursoFinalizado){
        cursosInscriptos.removeElement(cursoFinalizado);
    }  
}
