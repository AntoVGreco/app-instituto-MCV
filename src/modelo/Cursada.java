package modelo;

import java.io.Serializable;
import javax.swing.DefaultListModel;

/**
 * Clase que representa una cursada de un curso en el sistema.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class Cursada implements Serializable{
    private DefaultListModel<Alumno> alumnos;
    private Docente docente;

    /**
     * Constructor de la clase Cursada.
     * @param docente El docente a cargo de la cursada.
     */
    public Cursada(Docente docente) {  //constructor para instanciar (realizado por admin)
        this.docente = docente;
        this.alumnos = new DefaultListModel<>();
    }

    /**
     * Obtiene la lista de alumnos inscriptos en la cursada.
     * @return La lista de alumnos inscriptos en la cursada.
     */
    public DefaultListModel<Alumno> getAlumnos() {
        return alumnos;
    }
}
