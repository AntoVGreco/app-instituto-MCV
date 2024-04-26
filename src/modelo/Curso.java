package modelo;

import java.io.Serializable;
import javax.swing.DefaultListModel;

/**
 * Clase que representa un curso en el sistema.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class Curso implements Serializable{

    private String nombreCurso;
    private String estadoCurso;
    private String descripcionCurso;
    private int cursosRequeridos;
    private int topeAlumnos;
    private Docente docente; // se asigna cuando el docente lo propone
    private Cursada cursadaActiva; // lo instancia el admin
    private DefaultListModel<Cursada> cursadasFinalizadas; //contiene todas las cursadas finalizadas de cada curso

    /**
     * Constructor de la clase Curso.
     * @param nombreCurso El nombre del curso.
     * @param descripcionCurso La descripción del curso.
     * @param requeridos El número de cursos requeridos.
     * @param docente El docente a cargo del curso.
     */
    public Curso(String nombreCurso, String descripcionCurso, int requeridos, Docente docente) {
        this.nombreCurso = nombreCurso;
        this.descripcionCurso = descripcionCurso;
        this.cursosRequeridos = requeridos;
        this.estadoCurso = "Propuesto";
        this.topeAlumnos = 0;
        this.docente = docente;
        this.cursadasFinalizadas = new DefaultListModel<>();
        this.cursadaActiva = null;
    }

    /**
     * Obtiene la cursada activa del curso.
     * @return La cursada activa del curso.
     */
    public Cursada getCursadaActiva() {
        return cursadaActiva;
    }

    /**
     * Obtiene el límite de alumnos para el curso.
     * @return El límite de alumnos para el curso.
     */
    public int getTopeAlumnos() {
        return topeAlumnos;
    }

    /**
     * Obtiene el nombre del curso.
     * @return El nombre del curso.
     */
    public String getNombreCurso() {
        return nombreCurso;
    }
    
    /**
     * Se sobreescribe el toString para mostrar en el listado de cursos.
     * @return El nombre del curso.
     */
    @Override
    public String toString() {
        return nombreCurso;
    }

    /**
     * Obtiene el docente a cargo del curso.
     * @return El docente a cargo del curso.
     */
    public Docente getDocente() {
        return docente;
    }

    /**
     * Obtiene el estado actual del curso ya sea habilitado, propuesto, finalizado, 
     * cerrado o cancelado.
     * @return El estado actual del curso.
     */
    public String getEstadoCurso() {
        return estadoCurso;
    }

    /**
     * Establece el estado del curso.
     * @param estadoCurso El nuevo estado del curso.
     */
    public void setEstadoCurso(String estadoCurso) {
        this.estadoCurso = estadoCurso;
    }

    /**
     * Obtiene la descripción del curso.
     * @return La descripción del curso.
     */
    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    /**
     * Obtiene el número de cursos requeridos.
     * @return El número de cursos requeridos.
     */
    protected int getCursosRequeridos() {
        return cursosRequeridos;
    }

    /**
     * Establece el límite de alumnos para el curso.
     * @param topeAlumnos El nuevo límite de alumnos para el curso.
     */
    public void setTopeAlumnos(int topeAlumnos) {
        this.topeAlumnos = topeAlumnos;
    }

    /**
     * Almacena la cursada finalizada del curso.
     * @param curso El curso cuya cursada se va a almacenar.
     */
    public void almacenarCursada(Curso curso) {
        this.cursadasFinalizadas.addElement(cursadaActiva);
    }

    /**
     * Da de alta una nueva cursada del curso.
     * @param docente El docente a cargo de la nueva cursada.
     */
    public void altaCursada(Docente docente) {
        this.cursadaActiva = new Cursada(docente);
    }

    /**
     * Reinicia la cursada activa del curso.
     * @param docente El docente que reinicia la cursada activa.
     */
    public void reiniciarCursada(Docente docente) { // método llamado por el docente
        Cursada nuevaCursada = new Cursada(docente);
        this.cursadaActiva = nuevaCursada;
        this.setEstadoCurso("Habilitado");
    }
    
    

    /**
     * Inscribe un alumno en la cursada activa del curso.
     * @param alumno El alumno que se va a inscribir en la cursada activa.
     */
    public void inscribirAlumno(Alumno alumno) {
        int inscriptos = cursadaActiva.getAlumnos().size();

        if (inscriptos < topeAlumnos) {
            cursadaActiva.getAlumnos().addElement(alumno);
            inscriptos = cursadaActiva.getAlumnos().size();
            if (inscriptos == topeAlumnos) {
                this.setEstadoCurso("Cerrado");
            }
        }
    }
}
