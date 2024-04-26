package controlador;

import excepciones.ExisteCursoException;

import modelo.Alumno;
import modelo.Curso;
import modelo.Docente;
import modelo.Instituto;

import visual.VentanaCambioPassword;
import visual.VentanaDocente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controlador para la interfaz de usuario de un docente. Maneja las
 * interacciones entre la vista y el modelo para un docente.
 */
public class ControladorDocente implements ActionListener {

    private VentanaDocente vista;
    private Instituto instituto;
    private Docente usuarioLogeado;

    /**
     * Constructor de la clase ControladorDocente.
     *
     * @param vista La ventana asociada al controlador.
     * @param instituto El instituto al que pertenece el docente.
     * @param usuarioLogeado El docente que está utilizando la ventana.
     */
    public ControladorDocente(VentanaDocente vista, Instituto instituto, Docente usuarioLogeado) {
        this.vista = vista;
        this.instituto = instituto;
        this.usuarioLogeado = usuarioLogeado;
        this.vista.jButtonProponerCurso.addActionListener(this);
        this.vista.jButtonCerrarInscripcion.addActionListener(this);
        this.vista.jButtonFinalizarCurso.addActionListener(this);
        this.vista.jButtonCalificar.addActionListener(this);
        this.vista.jButtonReiniciarCurso.addActionListener(this);
        this.vista.jButtonCambiarContraseña.addActionListener(this);
        agregarChangeListener();
        agregarListSelectionListener();

    }

    /**
     * Inicia la ventana del docente con los datos del usuario logeado.
     */
    public void iniciar() {
        vista.setTitle("Sesión Docente - Hola " + usuarioLogeado.getNombre() + "!");
        mostrarDatosUsuario();
        vista.setLocationRelativeTo(null);
    }

    /**
     * Maneja las acciones de los botones en la ventana del docente.
     *
     * @param e El evento de acción que desencadena la ejecución del método.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Obtener datos de la interfaz
            if (e.getSource() == vista.jButtonProponerCurso) {
                ejecutarProponerCurso();//serializado
            } else if (e.getSource() == vista.jButtonCerrarInscripcion) {
                ejecutarCerrarInscripcion();//serializado
            } else if (e.getSource() == vista.jButtonCalificar) {
                ejecutarCalificar();
            } else if (e.getSource() == vista.jButtonFinalizarCurso) {
                ejecutarFinalizarCurso();//serializado
            } else if (e.getSource() == vista.jButtonReiniciarCurso) {
                ejecutarReiniciarCurso();//serializado
            } else if (e.getSource() == vista.jButtonCambiarContraseña) {
                ejecutarCambiarContraseña();
            }
        } catch (ExisteCursoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Propone un nuevo curso en el sistema. Extrae el nombre del curso, la
     * descripción y el índice de los cursos requeridos desde los campos de
     * entrada de la interfaz gráfica. Verifica si el curso ya existe en el
     * sistema. Si las entradas son válidas, crea un nuevo curso utilizando los
     * datos proporcionados y lo agrega al instituto. Luego, restablece los
     * campos de entrada de la interfaz y muestra un mensaje de confirmación.
     * Finalmente, serializa los datos actualizados del instituto en un archivo.
     *
     * @throws ExisteCursoException Si ya existe un curso con el mismo nombre en
     * el sistema.
     */
    private void ejecutarProponerCurso() throws ExisteCursoException {
        String nombreCurso = vista.jTextFieldNombreCurso.getText();
        String descripcionCurso = vista.jTextAreaDescripcionCurso.getText();
        int cursosRequeridosIndex = vista.jComboBoxCursosRequeridos.getSelectedIndex();
        existeCurso(nombreCurso);
        if (validarEntradasProponerCurso()) {
            Curso nuevoCurso = instituto.crearCurso(nombreCurso, descripcionCurso, cursosRequeridosIndex, usuarioLogeado);
            instituto.altaCurso(nuevoCurso);
            vista.jTextFieldNombreCurso.setText("");
            vista.jTextAreaDescripcionCurso.setText("");
            vista.jComboBoxCursosRequeridos.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Curso propuesto.");
            instituto.serializarInstituto("instituto.dat");
        }
    }

    /**
     * Agrega un ChangeListener al JTabbedPane en la ventana del docente para
     * detectar cambios de pestaña. Obtiene el índice de la pestaña seleccionada
     * Actualiza el contenido de la pestaña según el índice seleccionado
     */
    private void agregarChangeListener() {
        this.vista.jTabbedPaneVentanaDocente.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = vista.jTabbedPaneVentanaDocente.getSelectedIndex();
                if (selectedIndex == 1) {
                    actualizarPestaña1();
                } else if (selectedIndex == 2) {
                    actualizarPestaña2();
                } else if (selectedIndex == 3) {
                    actualizarPestaña3();
                }
            }
        });
    }

    /**
     * Actualiza la pestaña 1 de la interfaz del docente. Filtra los cursos
     * disponibles para calificar por el docente según su estado. Habilita la
     * lista de cursos para calificar en la interfaz gráfica. Establece los
     * modelos de lista de cursos y alumnos para calificar en la interfaz.
     * Deshabilita los botones de la pestaña 1 de la interfaz.
     */
    private void actualizarPestaña1() {
        DefaultListModel<Curso> cursosCalificar = new DefaultListModel<>();
        DefaultListModel<Alumno> alumnosCalificar = new DefaultListModel<>();
        cursosCalificar = instituto.filtrarCursosPorDocente(usuarioLogeado, "Habilitado", "Cerrado");
        vista.jListCursosCalificar.setEnabled(true);
        vista.jListCursosCalificar.setModel(cursosCalificar);
        vista.jListAlumnosCalificar.setModel(alumnosCalificar);
        deshabilitarBotonesPestaña1();
    }

    /**
     * Actualiza la pestaña 2 de la interfaz del docente. Filtra los cursos
     * finalizados por el docente. Deshabilita el botón de reiniciar curso en la
     * interfaz. Establece el modelo de lista de cursos finalizados en la
     * interfaz.
     */
    private void actualizarPestaña2() {
        DefaultListModel<Curso> cursosFinalizados = new DefaultListModel<>();
        vista.jButtonReiniciarCurso.setEnabled(false);
        cursosFinalizados = instituto.filtrarCursosPorDocente(usuarioLogeado, "Finalizado", "");
        vista.jListCursosFinalizados.setModel(cursosFinalizados);
    }

    /**
     * Actualiza la pestaña 3 de la interfaz del docente. Filtra los cursos
     * propuestos y cancelados por el docente. Establece los modelos de lista de
     * cursos propuestos y cancelados en la interfaz.
     */
    private void actualizarPestaña3() {
        DefaultListModel<Curso> cursosPropuestos = new DefaultListModel<>();
        cursosPropuestos = instituto.filtrarCursosPorDocente(usuarioLogeado, "Propuesto", "");
        vista.jListCursosPropuestos.setModel(cursosPropuestos);
        DefaultListModel<Curso> cursosCancelados = new DefaultListModel<>();
        cursosCancelados = instituto.filtrarCursosPorDocente(usuarioLogeado, "Cancelado", "");
        vista.jListCursosCancelados.setModel(cursosCancelados);
    }

    /**
     * Agrega ListSelectionListeners a los componentes JList relacionados con la
     * calificación de cursos.
     *
     * 1. Para la lista de cursos finalizados: Cuando se selecciona un curso
     * finalizado, habilita el botón para reiniciar el curso.
     *
     * 2. Para la lista de cursos a calificar: Cuando se selecciona un curso a
     * calificar, actualiza la lista de alumnos, resetea las calificaciones, y
     * habilita o deshabilita el botón para cerrar la inscripción según el
     * estado del curso.
     *
     * 3. Para la lista de alumnos a calificar: Cuando se selecciona un alumno y
     * el estado del curso es "Cerrado", habilita los botones para calificar y
     * muestra el estado de calificación en el campo correspondiente.
     */
    private void agregarListSelectionListener() {
        this.vista.jListCursosFinalizados.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    if ((vista.jListCursosFinalizados.getSelectedValue()) != null) {
                        vista.jButtonReiniciarCurso.setEnabled(true);
                    }
                }
            }
        });

        this.vista.jListCursosCalificar.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    if ((vista.jListCursosCalificar.getSelectedValue()) != null) {
                        vista.jListAlumnosCalificar.setModel(vista.jListCursosCalificar.getSelectedValue().getCursadaActiva().getAlumnos());
                        resetearCalificaciones();
                        if ("Habilitado".equals(vista.jListCursosCalificar.getSelectedValue().getEstadoCurso())) {
                            deshabilitarBotonesPestaña1();
                            vista.jButtonCerrarInscripcion.setEnabled(true);
                        } else {
                            vista.jButtonCerrarInscripcion.setEnabled(false);
                        }
                    }
                }
            }
        });

        this.vista.jListAlumnosCalificar.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    if (((vista.jListAlumnosCalificar.getSelectedValue()) != null) && ("Cerrado".equals(vista.jListCursosCalificar.getSelectedValue().getEstadoCurso()))) {
                        habilitarBotonesCalificar();
                        vista.jTextFieldCalificado.setText(vista.jListAlumnosCalificar.getSelectedValue().getCalificacion());
                    }
                }
            }
        });
    }

    /**
     * Reinicia un curso finalizado seleccionado por el docente. Al reiniciar el
     * curso, se almacena la información de la cursada anterior, se reinicia la
     * cursada actual con el docente actual como responsable, se muestra un
     * mensaje de confirmación y se actualiza la pestaña 2 de la interfaz del
     * docente. Finalmente, se serializan los datos actualizados del instituto
     * en un archivo.
     */
    private void ejecutarReiniciarCurso() {
        Curso curso = vista.jListCursosFinalizados.getSelectedValue();
        curso.almacenarCursada(curso);
        curso.reiniciarCursada(usuarioLogeado);
        JOptionPane.showMessageDialog(null, "Se reinició el curso.");
        instituto.serializarInstituto("instituto.dat");
        actualizarPestaña2();
    }

    /**
     * Deshabilita los botones en la pestaña 1 de la interfaz del docente. Estos
     * botones incluyen el de cerrar inscripción, los botones relacionados con
     * la calificación de los alumnos, y el botón para finalizar el curso.
     */
    private void deshabilitarBotonesPestaña1() {
        vista.jButtonCerrarInscripcion.setEnabled(false);
        vista.jTextFieldCalificado.setEnabled(false);
        vista.jTextFieldCalificado.setText("");
        vista.jRadioButtonCursadaAprobada.setEnabled(false);
        vista.jRadioButtonCursadaDesaprobada.setEnabled(false);
        vista.jButtonCalificar.setEnabled(false);
        vista.jButtonFinalizarCurso.setEnabled(false);
    }

    /**
     * Habilita los botones relacionados con la calificación de los alumnos en
     * la pestaña 1 de la interfaz del docente. Estos botones incluyen las
     * opciones de calificar la cursada de un alumno como aprobada o
     * desaprobada, el campo para ingresar la calificación y el botón para
     * realizar la calificación.
     */
    private void habilitarBotonesCalificar() {
        vista.jRadioButtonCursadaAprobada.setEnabled(true);
        vista.jRadioButtonCursadaDesaprobada.setEnabled(true);
        vista.jButtonCalificar.setEnabled(true);
        vista.jTextFieldCalificado.setEnabled(true);
    }

    /**
     * Cierra la inscripción de un curso seleccionado para calificar por el
     * docente. Establece el estado del curso como "Cerrado" y muestra un
     * mensaje de confirmación. Luego, actualiza la pestaña 1 de la interfaz del
     * docente y serializa los datos actualizados del instituto en un archivo.
     */
    private void ejecutarCerrarInscripcion() {
        vista.jListCursosCalificar.getSelectedValue().setEstadoCurso("Cerrado");
        JOptionPane.showMessageDialog(null, "Se cerró la inscripción");
        actualizarPestaña1();
        instituto.serializarInstituto("instituto.dat");
    }

    /**
     * Califica la cursada de un alumno seleccionado por el docente. Si se
     * selecciona la opción de cursada aprobada, establece la calificación del
     * alumno como "Aprobado". Si se selecciona la opción de cursada
     * desaprobada, establece la calificación del alumno como "Desaprobado".
     * Luego, actualiza el campo de calificación del alumno, verifica si todos
     * los alumnos han sido calificados y habilita el botón para finalizar el
     * curso si corresponde.
     */
    private void ejecutarCalificar() {
        if (vista.jListCursosCalificar.getSelectedValue() != null) {
            vista.jListCursosCalificar.setEnabled(false);
        }
        Alumno alumno = vista.jListAlumnosCalificar.getSelectedValue();

        // Verificar si ningún radio button ha sido seleccionado
        if (!vista.jRadioButtonCursadaDesaprobada.isSelected() && !vista.jRadioButtonCursadaAprobada.isSelected()) {
            JOptionPane.showMessageDialog(null, "Seleccione una opción");
            return; // Salir del método para evitar continuar con la lógica
        }// Arrojar advertencia si ninguna opción ha sido seleccionada

        if (vista.jRadioButtonCursadaAprobada.isSelected()) {
            alumno.setCalificacion("Aprobado");
            vista.jTextFieldCalificado.setText(alumno.getCalificacion());
            comprobarCalificados();
        } else if (vista.jRadioButtonCursadaDesaprobada.isSelected()) {
            alumno.setCalificacion("Desaprobado");
            vista.jTextFieldCalificado.setText(alumno.getCalificacion());
            comprobarCalificados();
        } 
    }

    /**
     * Verifica si todos los alumnos de la cursada han sido calificados. Si
     * todos los alumnos tienen una calificación diferente de "Sin calificar",
     * habilita el botón para finalizar el curso en la interfaz del docente; de
     * lo contrario, deshabilita el botón.
     */
    private void comprobarCalificados() {
        DefaultListModel<Alumno> alumnosCalificar = (DefaultListModel<Alumno>) vista.jListAlumnosCalificar.getModel();
        int i = 0;
        boolean botonFinalizar = true;
        while (i < alumnosCalificar.getSize()) {
            if ("Sin calificar".equals(alumnosCalificar.getElementAt(i).getCalificacion())) {
                botonFinalizar = false;
            }
            i++;
        }
        vista.jButtonFinalizarCurso.setEnabled(botonFinalizar);
    }

    /**
     * Finaliza un curso seleccionado para calificar por el docente. Agrega los
     * cursos aprobados por los alumnos a sus listas de cursos aprobados y
     * elimina el curso finalizado de sus listas de cursos inscriptos. Establece
     * el estado del curso como "Finalizado". Luego, resetea las calificaciones
     * de los alumnos a "Sin calificar", muestra un mensaje de confirmación y
     * actualiza la pestaña 1 de la interfaz del docente. Finalmente, serializa
     * los datos actualizados del instituto en un archivo.
     */
    private void ejecutarFinalizarCurso() {
        DefaultListModel<Alumno> alumnosCalificar = (DefaultListModel<Alumno>) vista.jListAlumnosCalificar.getModel();
        int i = 0;
        Alumno alumno;
        while (i < alumnosCalificar.getSize()) {
            alumno = alumnosCalificar.getElementAt(i);
            if ("Aprobado".equals(alumnosCalificar.getElementAt(i).getCalificacion())) {
                alumno.agregarCursoAprobado(vista.jListCursosCalificar.getSelectedValue()); //agrega a la lista alu
            }
            alumno.quitarCursoInscripto(vista.jListCursosCalificar.getSelectedValue());//borra de lista inscrpto
            i++;
        }
        vista.jListCursosCalificar.getSelectedValue().setEstadoCurso("Finalizado");
        resetearCalificaciones();
        JOptionPane.showMessageDialog(null, "Se cerraron las calificaciones. Curso Finalizado.");
        instituto.serializarInstituto("instituto.dat");
        actualizarPestaña1();
    }

    /**
     * Resetea las calificaciones de los alumnos a "Sin calificar" en la pestaña
     * de calificaciones de la interfaz del docente.
     */
    private void resetearCalificaciones() {
        DefaultListModel<Alumno> alumnosCalificar = (DefaultListModel<Alumno>) vista.jListAlumnosCalificar.getModel();
        int i = 0;

        while (i < alumnosCalificar.getSize()) {
            alumnosCalificar.getElementAt(i).setCalificacion("Sin calificar");
            i++;
        }
    }

    /**
     * Muestra los datos del docente, incluyendo nombre, apellido y DNI.
     */
    private void mostrarDatosUsuario() {
        vista.jLabelNombreDocente.setText("Nombre:   " + usuarioLogeado.getNombre());
        vista.jLabelApellidoDocente.setText("Apellido:   " + usuarioLogeado.getApellido());
        vista.jLabelDniDocente.setText("DNI:   " + usuarioLogeado.getDni());
    }

    /**
     * Ejecuta el cambio de contraseña del docente logeado. Muestra la ventana
     * para cambiar la contraseña, crea el controlador para gestionar el cambio
     * de contraseña y lo inicia.
     */
    private void ejecutarCambiarContraseña() {
        VentanaCambioPassword ventanaPassword = new VentanaCambioPassword();
        ventanaPassword.setVisible(true);
        ControladorCambioPassword ctrlPassword = new ControladorCambioPassword(ventanaPassword, usuarioLogeado, instituto);
        ctrlPassword.iniciar();
    }

    /**
     * Valida las entradas para proponer un nuevo curso. Verifica que el campo
     * de nombre y descripción del curso no estén vacíos. Devuelve true si las
     * entradas son válidas, de lo contrario, devuelve false.
     *
     * @return true si las entradas son válidas, de lo contrario, false.
     */
    private boolean validarEntradasProponerCurso() {
        boolean retorno = true;

        if (vista.jTextFieldNombreCurso.getText().isEmpty()) {
            vista.jLabelErrorNombreCurso.setText("Ingrese nombre");
            retorno = false;
        }

        if (vista.jTextAreaDescripcionCurso.getText().isEmpty()) {
            vista.jLabelErrorDescripcionCurso.setText("Ingrese descripcion");
            retorno = false;
        }

        return retorno;
    }

    /**
     * Verifica si ya existe un curso con el nombre proporcionado. Si existe,
     * lanza una excepción de tipo ExisteCursoException con el nombre del curso
     * existente.
     *
     * @param nombreCurso El nombre del curso a verificar.
     * @throws ExisteCursoException Si ya existe un curso con el nombre
     * proporcionado.
     */
    private void existeCurso(String nombreCurso) throws ExisteCursoException {
        DefaultListModel<Curso> listaCursos = instituto.getCursos();

        for (int i = 0; i < listaCursos.size(); i++) {
            Curso curso = listaCursos.getElementAt(i);
            if (curso.getNombreCurso().equals(nombreCurso)) {
                throw new ExisteCursoException(nombreCurso);
            }
        }
    }
}
