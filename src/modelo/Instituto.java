package modelo;

import java.io.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * Clase que representa al instituto en el sistema. Implementa la interfaz
 * Serializable para permitir la serialización de objetos.
 */
public class Instituto implements Serializable {

    private DefaultListModel<Usuario> usuarios;
    private DefaultListModel<Curso> cursos;
    private Administrador admin;

    /**
     * Constructor de la clase Instituto. Inicializa las listas de usuarios y
     * cursos, y crea un administrador predeterminado.
     */
    public Instituto() {
        this.usuarios = new DefaultListModel<>();
        this.cursos = new DefaultListModel<>();
        this.admin = new Administrador("Administrador", "Instituto", "1234");//CAMBIAR ANTES DE ENTREGAR
        this.admin.cambioPassword("1");//MODIFICAR ANTES DE ENTREGAR
        this.altaUsuario(admin);
    }

    /**
     * Crea un nuevo alumno y lo registra en el instituto.
     *
     * @param nombre El nombre del alumno.
     * @param apellido El apellido del alumno.
     * @param dni El DNI del alumno.
     */
    public void crearAlumno(String nombre, String apellido, String dni) {
        nombre = darFormatoTexto(nombre);
        apellido = darFormatoTexto(apellido);
        Alumno alumno = new Alumno(nombre, apellido, dni);
        altaUsuario(alumno);
    }

    /**
     * Crea un nuevo docente y lo registra en el instituto.
     *
     * @param nombre El nombre del docente.
     * @param apellido El apellido del docente.
     * @param dni El DNI del docente.
     */
    public void crearDocente(String nombre, String apellido, String dni) {
        nombre = darFormatoTexto(nombre);
        apellido = darFormatoTexto(apellido);
        Docente docente = new Docente(nombre, apellido, dni);
        altaUsuario(docente);
    }

    /**
     * Se agrega un nuevo usuario en la lista del instituto.
     *
     * @param user El usuario a registrar.
     */
    private void altaUsuario(Usuario user) {
        usuarios.addElement(user);
    }

    /**
     * Se agrega un nuevo curso en el instituto.
     *
     * @param curso El curso a registrar.
     */
    public void altaCurso(Curso curso) {
        cursos.addElement(curso);
    }

    /**
     * Obtiene la lista de usuarios registrados en el instituto.
     *
     * @return La lista de usuarios del instituto.
     */
    public DefaultListModel<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Obtiene la lista de cursos ofrecidos por el instituto.
     *
     * @return La lista de cursos del instituto.
     */
    public DefaultListModel<Curso> getCursos() {
        return cursos;
    }

    /**
     * Filtra y devuelve la lista de cursos del instituto según su estado.
     *
     * @param estado El estado de los cursos a filtrar.
     * @return La lista de cursos filtrada por estado.
     */
    public DefaultListModel<Curso> getCursosPorEstado(String estado) {
        DefaultListModel<Curso> cursosPorEstado = new DefaultListModel<>();
        int i = 0;

        while (i < cursos.size()) {
            Curso curso = cursos.getElementAt(i);
            if (curso.getEstadoCurso().equals(estado)) {
                cursosPorEstado.addElement(curso);
            }
            i++;
        }
        return cursosPorEstado;
    }

    /**
     * Filtra y devuelve la lista de cursos del instituto según el docente y los
     * estados especificados.
     *
     * @param usuarioLogeado El docente logeado en el sistema.
     * @param filtro1 El primer estado a filtrar.
     * @param filtro2 El segundo estado a filtrar.
     * @return La lista de cursos filtrada por docente y estado.
     */
    public DefaultListModel<Curso> filtrarCursosPorDocente(Docente usuarioLogeado, String filtro1, String filtro2) {
        DefaultListModel<Curso> cursosPorDocente = new DefaultListModel<>();
        int i = 0;

        while (i < cursos.size()) {
            Curso curso = cursos.getElementAt(i);
            if (curso.getDocente().getDni().equals(usuarioLogeado.getDni())) {
                if ((curso.getEstadoCurso().equals(filtro1)) || (curso.getEstadoCurso().equals(filtro2))) {
                    cursosPorDocente.addElement(curso);
                }
            }
            i++;
        }
        return cursosPorDocente;
    }

    /**
     * Filtra y devuelve la lista de cursos disponibles según el alumno logeado
     * para la inscripción.
     *
     * @param usuarioLogeado El alumno logeado en el sistema.
     * @return La lista de cursos filtrada por alumno.
     */
    public DefaultListModel<Curso> filtrarCursosPorAlumno(Alumno usuarioLogeado) {
        DefaultListModel<Curso> cursosPorAlumno = new DefaultListModel<>();
        int i = 0;

        while (i < cursos.size()) {
            Curso curso = cursos.getElementAt(i);
            // Verificar si el curso está habilitado y el alumno cumple con los requisitos
            if ("Habilitado".equals(curso.getEstadoCurso()) && usuarioLogeado.totalCursosAprobados() >= curso.getCursosRequeridos()) {
                // Verificar si el curso no está en las listas de cursos inscriptos o cursos aprobados del alumno
                if (!usuarioLogeado.getCursosInscriptos().contains(curso) && !usuarioLogeado.getCursosAprobados().contains(curso)) {
                    cursosPorAlumno.addElement(curso);
                }
            }
            i++;
        }
        return cursosPorAlumno;
    }

    /**
     * Da formato al texto capitalizando la primera letra y convirtiendo el
     * resto en minúsculas.
     *
     * @param texto El texto a formatear.
     * @return El texto con formato aplicado.
     */
    private String darFormatoTexto(String texto) {
        String primeraLetraMayuscula = texto.substring(0, 1).toUpperCase();
        String restoMinusculas = texto.substring(1).toLowerCase();
        return primeraLetraMayuscula + restoMinusculas;
    }

    /**
     * Crea un nuevo curso con los datos proporcionados.
     *
     * @param nombreCurso El nombre del curso.
     * @param descripcionCurso La descripción del curso.
     * @param cursosRequeridosIndex El número de cursos requeridos para el
     * curso.
     * @param usuarioLogeado El docente a cargo del curso.
     * @return El curso creado.
     */
    public Curso crearCurso(String nombreCurso, String descripcionCurso, int cursosRequeridosIndex, Docente usuarioLogeado) {
        nombreCurso = darFormatoTexto(nombreCurso);
        Curso cursoNuevo = new Curso(nombreCurso, descripcionCurso, cursosRequeridosIndex, usuarioLogeado);
        return cursoNuevo;
    }

    /**
     * Serializa el objeto Instituto y lo guarda en un archivo con el nombre
     * proporcionado.
     *
     * @param nombreArchivo El nombre del archivo donde se guardará el
     * Instituto.
     */
    public void serializarInstituto(String nombreArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(this);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos");
        }
    }

    /**
     * Deserializa un objeto Instituto desde un archivo con el nombre
     * proporcionado.
     *
     * @param nombreArchivo El nombre del archivo desde donde se leerá el
     * Instituto.
     * @return El Instituto deserializado, o null si ocurre un error.
     * @throws FileNotFoundException Si no se encuentra el archivo especificado.
     * @throws IOException Si ocurre un error de entrada o salida durante la
     * deserialización.
     * @throws ClassNotFoundException Si la clase del objeto deserializado no se
     * encuentra.
     */
    public static Instituto deserializarInstituto(String nombreArchivo) {
        Instituto instituto = null;
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            instituto = (Instituto) entrada.readObject();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo " + nombreArchivo + " no se encontró.");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar los datos");
        }
        return instituto;
    }

}
