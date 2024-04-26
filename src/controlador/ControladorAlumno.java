package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.Alumno;
import modelo.Curso;
import modelo.Instituto;

import visual.VentanaAlumno;
import visual.VentanaCambioPassword;

/**
 * Esta clase representa el controlador para la ventana de un alumno en el
 * sistema. Se encarga de manejar la lógica de interacción entre la interfaz de
 * usuario para los alumnos y los datos del modelo. Implementa ActionListener
 * para manejar eventos de botón y utiliza varios listeners para otros eventos
 * de la interfaz.
 */
public class ControladorAlumno implements ActionListener {

    private VentanaAlumno vista;
    private Instituto instituto;
    private Alumno usuarioLogeado;
    private DefaultListModel<Curso> cursosDisponibles;
    private DefaultListModel<Curso> cursosAgregados;

    /**
     * Constructor de la clase ControladorAlumno.
     *
     * @param vista La ventana de alumno asociada a este controlador.
     * @param instituto El instituto del sistema.
     * @param usuarioLogeado El alumno que ha iniciado sesión.
     */
    public ControladorAlumno(VentanaAlumno vista, Instituto instituto, Alumno usuarioLogeado) {
        this.vista = vista;
        this.instituto = instituto;
        this.usuarioLogeado = usuarioLogeado;
        this.cursosDisponibles = new DefaultListModel<>();
        this.cursosAgregados = new DefaultListModel<>();
        this.vista.jButtonAgregarCurso.addActionListener(this);
        this.vista.jButtonQuitarCurso.addActionListener(this);
        this.vista.jButtonFinalizarInscripcion.addActionListener(this);
        this.vista.jButtonCambiarContraseña.addActionListener(this);
        agregarChangeListener();
        agregarListSelectionListener();

    }

    /**
     * Inicia la ventana del alumno. Configura el título de la ventana, muestra
     * los datos del usuario y actualiza el contenido de la pestaña activa.
     */
    public void iniciar() {
        vista.setTitle("Sesión Alumno - Hola " + usuarioLogeado.getNombre() + "!");
        mostrarDatosUsuario();
        vista.setLocationRelativeTo(null);
        actualizarPestaña0();

    }

    /**
     * Maneja eventos de acción generados por componentes de la interfaz de
     * usuario. Este método determina qué acción realizar en función del
     * componente que generó el evento.
     *
     * @param e El evento de acción que se ha producido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButtonAgregarCurso) {
            ejecutarAgregarCurso();
        } else if (e.getSource() == vista.jButtonQuitarCurso) {
            ejecutarQuitarCurso();
        } else if (e.getSource() == vista.jButtonFinalizarInscripcion) {
            ejecutarFinalizarInscripcion();//serializado
        } else if (e.getSource() == vista.jButtonCambiarContraseña) {
            ejecutarCambiarContraseña();
        }

    }

    /**
     * Agrega un ChangeListener al JTabbedPane de la ventana del alumno. Este
     * ChangeListener detecta cambios en la pestaña activa y realiza acciones
     * correspondientes.
     */
    private void agregarChangeListener() {
        this.vista.jTabbedPaneVentanaAlumno.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = vista.jTabbedPaneVentanaAlumno.getSelectedIndex();
                if (selectedIndex == 0) {
                    actualizarPestaña0();
                } else if (selectedIndex == 1) {
                    actualizarPestaña1();
                }
            }
        });
    }

    /**
     * Agrega ListSelectionListeners a las listas de cursos disponibles y
     * agregados. Estos ListSelectionListeners detectan cambios de selección en
     * las listas y actualizan los botones correspondientes.
     */
    private void agregarListSelectionListener() {
        this.vista.jListCursosDisponibles.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {

                    if ((vista.jListCursosDisponibles.getSelectedValue()) != null) {
                        vista.jButtonAgregarCurso.setEnabled(true);
                        vista.jButtonQuitarCurso.setEnabled(false);
                        vista.jListCursosAgregados.clearSelection();
                    }
                }
            }
        });
        this.vista.jListCursosAgregados.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {

                    if ((vista.jListCursosAgregados.getSelectedValue()) != null) {
                        vista.jButtonQuitarCurso.setEnabled(true);
                        vista.jButtonAgregarCurso.setEnabled(false);
                        vista.jListCursosDisponibles.clearSelection();
                    }
                }
            }
        });
    }

    /**
     * Agrega un curso seleccionado de la lista de cursos disponibles a la lista
     * de cursos agregados. Este método se ejecuta cuando se presiona el botón
     * para agregar un curso. Actualiza los modelos de lista y el estado de los
     * botones en la interfaz de usuario.
     */
    private void ejecutarAgregarCurso() {
        Curso cursoAgregado = vista.jListCursosDisponibles.getSelectedValue();
        if (cursoAgregado != null) {
            cursosAgregados.addElement(cursoAgregado);
            cursosDisponibles.removeElement(cursoAgregado);
        }
        vista.jListCursosDisponibles.setModel(cursosDisponibles);
        vista.jListCursosAgregados.setModel(cursosAgregados);
        if (cursosDisponibles.isEmpty()) {
            vista.jButtonAgregarCurso.setEnabled(false);
        }
        refrescarBotonFinalizar();
        vista.jButtonAgregarCurso.setEnabled(false);
    }

    /**
     * Quita un curso seleccionado de la lista de cursos agregados y lo devuelve
     * a la lista de cursos disponibles. Este método se ejecuta cuando se
     * presiona el botón para quitar un curso. Actualiza los modelos de lista y
     * el estado de los botones en la interfaz de usuario.
     */
    private void ejecutarQuitarCurso() {
        Curso cursoQuitado = vista.jListCursosAgregados.getSelectedValue();
        if (cursoQuitado != null) {
            cursosDisponibles.addElement(cursoQuitado);
            cursosAgregados.removeElement(cursoQuitado);
        }
        vista.jListCursosDisponibles.setModel(cursosDisponibles);
        vista.jListCursosAgregados.setModel(cursosAgregados);
        if (cursosAgregados.isEmpty()) {
            vista.jButtonQuitarCurso.setEnabled(false);
        }
        refrescarBotonFinalizar();
        vista.jButtonQuitarCurso.setEnabled(false);
    }

    /**
     * Finaliza el proceso de inscripción a cursos. Este método se ejecuta
     * cuando se presiona el botón para finalizar la inscripción. Agrega los
     * cursos inscriptos al alumno, serializa el instituto, limpia la lista de
     * cursos agregados y muestra un mensaje de éxito en la interfaz de usuario.
     */
    private void ejecutarFinalizarInscripcion() {
        agregarCursosInscriptos(usuarioLogeado);
        instituto.serializarInstituto("instituto.dat");
        cursosAgregados.removeAllElements();
        JOptionPane.showMessageDialog(null, "Inscripción realizada con éxito.");
        actualizarPestaña0();
    }

    /**
     * Actualiza el contenido de la pestaña 0 de la interfaz de usuario del
     * alumno. Esto incluye la lista de cursos disponibles y agregados. Además,
     * deshabilita los botones correspondientes en función del estado.
     */
    private void actualizarPestaña0() {
        deshabilitarBotonesPestaña0();

        cursosDisponibles = instituto.filtrarCursosPorAlumno(usuarioLogeado);
        vista.jListCursosDisponibles.setModel(cursosDisponibles);
        vista.jListCursosAgregados.setModel(cursosAgregados);
    }

    /**
     * Actualiza el contenido de la pestaña 1 de la interfaz de usuario del
     * alumno. Esto incluye la lista de cursos inscriptos y aprobados por el
     * alumno.
     */
    private void actualizarPestaña1() {
        vista.jListCursosInscriptos.setModel(usuarioLogeado.getCursosInscriptos());
        vista.jListCursosAprobados.setModel(usuarioLogeado.getCursosAprobados());
    }

    /**
     * Deshabilita los botones en la pestaña 0 de la interfaz de usuario del
     * alumno. Esto se utiliza para evitar acciones no deseadas mientras se
     * actualiza el contenido.
     */
    private void deshabilitarBotonesPestaña0() {
        vista.jButtonAgregarCurso.setEnabled(false);
        vista.jButtonQuitarCurso.setEnabled(false);
        vista.jButtonFinalizarInscripcion.setEnabled(false);
    }

    /**
     * Refresca el estado del botón para finalizar la inscripción en la interfaz
     * de usuario. El botón se activa si hay cursos agregados, y se desactiva si
     * no hay cursos agregados.
     */
    private void refrescarBotonFinalizar() {
        if (!cursosAgregados.isEmpty()) {
            vista.jButtonFinalizarInscripcion.setEnabled(true);
        } else {
            vista.jButtonFinalizarInscripcion.setEnabled(false);
        }
    }

    /**
     * Agrega los cursos seleccionados a la lista de cursos inscriptos del
     * usuario logeado. Este método se utiliza al finalizar la inscripción a
     * cursos. Además, inscribe al usuario en los cursos seleccionados en el
     * instituto.
     *
     * @param usuarioLogeado El alumno que está realizando la inscripción.
     */
    private void agregarCursosInscriptos(Alumno usuarioLogeado) {
        int i = 0;
        Curso cursoAgregado;
        while (i < cursosAgregados.size()) {
            cursoAgregado = cursosAgregados.getElementAt(i);
            usuarioLogeado.agregarCursoInscripto(cursoAgregado);
            cursoAgregado.inscribirAlumno(usuarioLogeado); // Se agrega el alumno al DefaultList del instituto
            i++;
        }
    }

    /**
     * Muestra los datos del usuario logeado en la interfaz de usuario. Esto
     * incluye el nombre, apellido y DNI del usuario.
     */
    private void mostrarDatosUsuario() {
        vista.jLabelNombreAlumno.setText("Nombre:   " + usuarioLogeado.getNombre());
        vista.jLabelApellidoAlumno.setText("Apellido:   " + usuarioLogeado.getApellido());
        vista.jLabelDniAlumno.setText("DNI:   " + usuarioLogeado.getDni());
    }

    /**
     * Ejecuta el proceso para cambiar la contraseña del usuario logeado.
     * Muestra una ventana para cambiar la contraseña y crea un controlador para
     * manejar dicho proceso.
     */
    private void ejecutarCambiarContraseña() {
        VentanaCambioPassword ventanaPassword = new VentanaCambioPassword();
        ventanaPassword.setVisible(true);
        ControladorCambioPassword ctrlPassword = new ControladorCambioPassword(ventanaPassword, usuarioLogeado, instituto);
        ctrlPassword.iniciar();

    }

}
