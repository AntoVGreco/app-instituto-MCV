package controlador;

import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.table.DefaultTableModel;

import modelo.Administrador;
import modelo.Curso;
import modelo.Instituto;
import modelo.Usuario;

import visual.VentanaAdmin;
import visual.VentanaCambioPassword;

/**
 * Controlador para la funcionalidad administrativa de la aplicación.
 */
public class ControladorAdmin implements ActionListener {

    private VentanaAdmin vista;
    private Instituto instituto;
    private Administrador usuarioLogeado;

    /**
     * Constructor para el controlador de administrador.
     *
     * @param vista La ventana de administrador.
     * @param instituto El instituto asociado.
     * @param usuarioLogeado El administrador que ha iniciado sesión.
     */
    public ControladorAdmin(VentanaAdmin vista, Instituto instituto, Administrador usuarioLogeado) {
        // Inicialización de atributos y configuración de escuchadores de eventos
        this.vista = vista;
        this.instituto = instituto;
        this.usuarioLogeado = usuarioLogeado;
        this.vista.jButtonCrearUsuario.addActionListener(this);
        this.vista.jButtonBuscarUsuario.addActionListener(this);
        this.vista.jButtonBlanquearClave.addActionListener(this);
        this.vista.jButtonSuspenderCuenta.addActionListener(this);
        this.vista.jButtonReactivarCuenta.addActionListener(this);
        this.vista.jComboBoxFiltroCursos.addActionListener(this);
        this.vista.jComboBoxModificarEstadoCurso.addActionListener(this);
        this.vista.jButtonModificarEstadoCurso.addActionListener(this);
        this.vista.jButtonCambiarContraseña.addActionListener(this);
        this.vista.jRadioButtonCuentaAlumno.addActionListener(this);
        this.vista.jRadioButtonCuentaDocente.addActionListener(this);
        agregarListSelectionListener();
        agregarFocusListener();
    }

    /**
     * Inicia la ventana adminitrador con los datos necesarios.
     */
    public void iniciar() {
        vista.setTitle("Sesión Administrador - Hola " + usuarioLogeado.getNombre() + "!");
        mostrarDatosUsuario();
        cargarListadoUsuarios();
        vista.setLocationRelativeTo(null);
    }

    /**
     * Maneja los eventos de acción generados por la interfaz de usuario.
     * Ejecuta las acciones correspondientes según el componente de la interfaz
     * que generó el evento.
     *
     * @param e El evento de acción generado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == vista.jButtonCrearUsuario) {
                ejecutarCrearUsuario();//serializado
            } else if (e.getSource() == vista.jButtonBuscarUsuario) {
                ejecutarBuscarUsuario();
            } else if (e.getSource() == vista.jButtonBlanquearClave) {
                ejecutarBlanquearClave();//serializado
            } else if (e.getSource() == vista.jButtonSuspenderCuenta) {
                ejecutarSuspenderCuenta();//serializado
            } else if (e.getSource() == vista.jButtonReactivarCuenta) {
                ejecutarReactivarCuenta();//serializado
            } else if (e.getSource() == vista.jComboBoxFiltroCursos) {
                ejecutarFiltroCursos();
            } else if (e.getSource() == vista.jComboBoxModificarEstadoCurso) {
                ejecutarHabilitarTopeAlumnos();
            } else if (e.getSource() == vista.jButtonModificarEstadoCurso) {
                ejecutarModificarCurso();//serializado
            } else if (e.getSource() == vista.jButtonCambiarContraseña) {
                ejecutarCambiarContraseña();
            } else if (e.getSource() == vista.jRadioButtonCuentaAlumno || e.getSource() == vista.jRadioButtonCuentaDocente) {
                habilitarBotonCrearUsuario();
            }
        } catch (ExisteUsuarioException | NoExisteUsuarioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Ejecuta la acción de crear un nuevo usuario en el sistema. El método
     * verifica si el usuario ya existe antes de crearlo. Si el usuario no
     * existe y las entradas de nombre, apellido y DNI son válidas, crea un
     * nuevo usuario (alumno o docente) según la selección del radio button
     * correspondiente. Luego, muestra un mensaje de éxito, limpia los campos
     * del registro, carga el listado de usuarios actualizado y guarda los datos
     * en un archivo.
     *
     * @throws ExisteUsuarioException Si el usuario ya existe en el sistema.
     */
    private void ejecutarCrearUsuario() throws ExisteUsuarioException {//crea usuario y lo guarda
        String nombre = vista.jTextFieldNombreUser.getText();
        String apellido = vista.jTextFieldApellido.getText();
        String dni = vista.jTextFieldDNIUser.getText();

        existeUsuario(dni);

        if (validarEntradasCrearUsuario(nombre, apellido, dni)) {
            if (vista.jRadioButtonCuentaAlumno.isSelected()) {
                instituto.crearAlumno(nombre, apellido, dni);
                JOptionPane.showMessageDialog(null, "Alumno " + nombre + " " + apellido + " DNI " + dni + " creado con éxito.");
                limpiarCamposRegistro();

            } else if (vista.jRadioButtonCuentaDocente.isSelected()) {
                instituto.crearDocente(nombre, apellido, dni);
                JOptionPane.showMessageDialog(null, "Docente " + nombre + " " + apellido + " DNI " + dni + " creado con éxito.");
                limpiarCamposRegistro();
            }
            cargarListadoUsuarios();
            instituto.serializarInstituto("instituto.dat");//se guardan los datos en archivo
        }
    }

    /**
     * Ejecuta la búsqueda de un usuario en el sistema utilizando el DNI
     * proporcionado. Si las entradas de DNI son válidas, busca al usuario con
     * el DNI ingresado. Luego, muestra la descripción del usuario encontrado en
     * el área de texto correspondiente y actualiza los botones de la cuenta
     * según el estado del usuario.
     *
     * @return El usuario encontrado, o null si no se encontró ningún usuario
     * con el DNI proporcionado.
     * @throws NoExisteUsuarioException Si no se encuentra ningún usuario con el
     * DNI proporcionado.
     */
    private Usuario ejecutarBuscarUsuario() throws NoExisteUsuarioException {
        String dni = vista.jTextFieldBuscarUsuario.getText();

        if (validarEntradasBuscarUsuario(dni)) {
            Usuario user = buscarUsuarioIngresado(dni);
            mostrarDescripcionUsuario(user);
            actualizarBotonesCuenta(user);
            return user;
        }

        return null;
    }

    /**
     * Ejecuta el proceso de blanqueo de la contraseña de un usuario en el
     * sistema. Utiliza el DNI proporcionado en el campo de búsqueda para
     * encontrar al usuario correspondiente. Luego, realiza el blanqueo de la
     * contraseña del usuario encontrado. Actualiza los botones de la cuenta
     * según el estado del usuario. Muestra un mensaje de éxito indicando que la
     * contraseña ha sido restaurada. Finalmente, serializa los datos
     * actualizados del instituto en un archivo.
     *
     * @throws NoExisteUsuarioException Si no se encuentra ningún usuario con el
     * DNI proporcionado.
     */
    private void ejecutarBlanquearClave() throws NoExisteUsuarioException {
        String dni = vista.jTextFieldBuscarUsuario.getText();
        Usuario user = buscarUsuarioIngresado(dni);
        user.blanquearPassword();
        actualizarBotonesCuenta(user);
        JOptionPane.showMessageDialog(null, "Clave restaurada con éxito.");
        instituto.serializarInstituto("instituto.dat");
    }

    /**
     * Suspende la cuenta de un usuario en el sistema. Utiliza el DNI
     * proporcionado en el campo de búsqueda para encontrar al usuario
     * correspondiente. Luego, realiza la suspensión de la cuenta del usuario
     * encontrado. Muestra la descripción actualizada del usuario suspendido.
     * Actualiza los botones de la cuenta según el estado del usuario. Muestra
     * un mensaje indicando que la cuenta ha sido suspendida. Recarga el listado
     * de usuarios en la interfaz. Finalmente, serializa los datos actualizados
     * del instituto en un archivo.
     *
     * @throws NoExisteUsuarioException Si no se encuentra ningún usuario con el
     * DNI proporcionado.
     */
    private void ejecutarSuspenderCuenta() throws NoExisteUsuarioException {
        String dni = vista.jTextFieldBuscarUsuario.getText();
        Usuario user = buscarUsuarioIngresado(dni);
        user.suspenderUsuario();
        mostrarDescripcionUsuario(user);
        actualizarBotonesCuenta(user);
        JOptionPane.showMessageDialog(null, "Cuenta suspendida.");
        cargarListadoUsuarios();
        instituto.serializarInstituto("instituto.dat");
    }

    /**
     * Reactiva la cuenta de un usuario en el sistema. Utiliza el DNI
     * proporcionado en el campo de búsqueda para encontrar al usuario
     * correspondiente. Luego, realiza la reactivación de la cuenta del usuario
     * encontrado. Muestra la descripción actualizada del usuario con la cuenta
     * reactivada. Actualiza los botones de la cuenta según el estado del
     * usuario. Muestra un mensaje indicando que la cuenta ha sido reactivada.
     * Recarga el listado de usuarios en la interfaz. Finalmente, serializa los
     * datos actualizados del instituto en un archivo.
     *
     * @throws NoExisteUsuarioException Si no se encuentra ningún usuario con el
     * DNI proporcionado.
     */
    private void ejecutarReactivarCuenta() throws NoExisteUsuarioException {
        String dni = vista.jTextFieldBuscarUsuario.getText();
        Usuario user = buscarUsuarioIngresado(dni);
        user.reactivarUsuario();
        mostrarDescripcionUsuario(user);
        actualizarBotonesCuenta(user);
        JOptionPane.showMessageDialog(null, "Cuenta reactivada con éxito.");
        cargarListadoUsuarios();
        instituto.serializarInstituto("instituto.dat");
    }

    /**
     * Ejecuta el proceso de filtrado de cursos según el estado seleccionado en
     * el JComboBox de la vista. Actualiza la lista de cursos mostrados en la
     * interfaz según el estado seleccionado. Deshabilita los botones
     * relacionados con la modificación del estado del curso.
     */
    private void ejecutarFiltroCursos() {
        actualizarListaCursos();
        deshabilitarBotonesModificarEstado();
    }

    /**
     * Ejecuta la modificación del estado y tope de alumnos de un curso
     * seleccionado. Obtiene el curso seleccionado en la lista de cursos de la
     * vista. Actualiza el estado del curso según lo seleccionado en el
     * JComboBox de modificación de estado. Valida la entrada del tope de
     * alumnos antes de aplicar los cambios. Si la entrada del tope de alumnos
     * es válida, establece el tope de alumnos del curso. Si el estado del curso
     * es "Habilitado" y no tiene una cursada activa, se da de alta una cursada
     * activa. Muestra un mensaje de éxito al usuario. Deshabilita los botones
     * relacionados con la modificación del estado del curso. Actualiza la lista
     * de cursos mostrados en la interfaz. Serializa los datos del instituto en
     * el archivo "instituto.dat".
     */
    private void ejecutarModificarCurso() {
        Curso curso = vista.jListCursos.getSelectedValue();
        curso.setEstadoCurso((String) vista.jComboBoxModificarEstadoCurso.getSelectedItem());
        if (validarEntradaTopeAlumno(vista.jTextFieldTopeAlumnos.getText())) {
            curso.setTopeAlumnos(Integer.parseInt(vista.jTextFieldTopeAlumnos.getText()));
            if ("Habilitado".equals((String) vista.jComboBoxModificarEstadoCurso.getSelectedItem())) {
                if (curso.getCursadaActiva() == null) {
                    curso.altaCursada(curso.getDocente());
                }
            }
            JOptionPane.showMessageDialog(null, "Cambios aplicados con éxito.");
            deshabilitarBotonesModificarEstado();
            actualizarListaCursos();
            instituto.serializarInstituto("instituto.dat");
        }
    }

    /**
     * Busca un usuario en la lista de usuarios del instituto mediante su número
     * de DNI.
     *
     * @param dni El número de DNI del usuario a buscar.
     * @return El usuario encontrado con el DNI proporcionado.
     * @throws NoExisteUsuarioException Si no se encuentra ningún usuario con el
     * DNI especificado.
     */
    private Usuario buscarUsuarioIngresado(String dni) throws NoExisteUsuarioException {
        DefaultListModel<Usuario> listaUsuarios = instituto.getUsuarios();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario user = listaUsuarios.getElementAt(i);
            if (user.getDni().equals(dni)) {
                return user;
            }
        }
        throw new NoExisteUsuarioException(dni);
    }

    /**
     * Actualiza la disponibilidad de los botones relacionados con la cuenta del
     * usuario.
     *
     * @param user El usuario cuyos botones de cuenta se actualizarán.
     */
    private void actualizarBotonesCuenta(Usuario user) {
        vista.jButtonReactivarCuenta.setEnabled(user.isSancionado());
        vista.jButtonSuspenderCuenta.setEnabled(!user.isSancionado());
        vista.jButtonBlanquearClave.setEnabled(true);
        vista.jLabelErrorBuscarUsuario.setText("");
    }

    /**
     * Actualiza la lista de cursos según el estado seleccionado y restablece el
     * campo de tope de alumnos.
     */
    private void actualizarListaCursos() {
        // Obtener la lista de cursos del estado seleccionado
        deshabilitarBotonesModificarEstado();
        String estadoSeleccionado = (String) vista.jComboBoxFiltroCursos.getSelectedItem();
        DefaultListModel<Curso> cursosPorEstado = instituto.getCursosPorEstado(estadoSeleccionado);
        vista.jListCursos.setModel(cursosPorEstado);
        vista.jTextFieldTopeAlumnos.setText("");
    }

    /**
     * Retorna una cadena que describe el curso seleccionado, incluyendo el
     * nombre y apellido del docente asignado y la descripción del curso.
     *
     * @return La descripción del curso seleccionado.
     */
    public String mostrarDescripcionCurso() {
        String descripcionCurso;
        String docenteAsignado;
        docenteAsignado = "Docente: " + vista.jListCursos.getSelectedValue().getDocente().getNombre() + " " + vista.jListCursos.getSelectedValue().getDocente().getApellido();
        descripcionCurso = "\nDescripción: " + vista.jListCursos.getSelectedValue().getDescripcionCurso();

        return docenteAsignado + descripcionCurso;
    }

    /**
     * Muestra la descripción del usuario en el área de texto del estado de
     * cuenta.
     *
     * @param user El usuario cuya descripción se mostrará.
     */
    private void mostrarDescripcionUsuario(Usuario user) {
        vista.jTextAreaEstadoCuenta.setText(user.toStringUsuario());
    }

    /**
     * Limpia los campos de registro en la ventana de administrador.
     */
    private void limpiarCamposRegistro() {
        vista.jTextFieldNombreUser.setText("");
        vista.jTextFieldApellido.setText("");
        vista.jTextFieldDNIUser.setText("");
        vista.buttonGroupTipoUsuario.clearSelection();
        vista.jLabelErrorNombre.setText("");
        vista.jLabelErrorApellido.setText("");
        vista.jLabelErrorDni.setText("");
    }

    /**
     * Deshabilita los botones relacionados con las acciones de la cuenta de
     * usuario en la interfaz gráfica.
     */
    private void deshabilitarBotonesCuentaUsuario() {
        vista.jButtonBlanquearClave.setEnabled(false);
        vista.jButtonReactivarCuenta.setEnabled(false);
        vista.jButtonSuspenderCuenta.setEnabled(false);
    }

    /**
     * Agrega un ListSelectionListener al componente JList de cursos, que
     * actualiza la descripción del curso seleccionado en el JTextArea
     * correspondiente, habilita los componentes necesarios para modificar el
     * estado del curso y carga la cantidad máxima de alumnos permitida en el
     * JTextField correspondiente.
     */
    private void agregarListSelectionListener() {   //
        this.vista.jListCursos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {

                    if ((vista.jListCursos.getSelectedValue()) != null) {
                        vista.jTextAreaDescripcionCurso.setText(mostrarDescripcionCurso());
                        vista.jComboBoxModificarEstadoCurso.setEnabled(true);
                        vista.jComboBoxModificarEstadoCurso.setSelectedIndex(vista.jComboBoxFiltroCursos.getSelectedIndex());
                        vista.jTextFieldTopeAlumnos.setText(String.valueOf(vista.jListCursos.getSelectedValue().getTopeAlumnos()));
                        vista.jTextFieldTopeAlumnos.setEnabled(true);
                        vista.jButtonModificarEstadoCurso.setEnabled(true);
                    }
                }
            }
        });
    }

    /**
     * Agrega un FocusListener al JTextField para buscar usuario, que
     * deshabilita los botones relacionados con la cuenta de usuario cuando el
     * campo de búsqueda gana foco.
     */
    private void agregarFocusListener() {
        this.vista.jTextFieldBuscarUsuario.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                deshabilitarBotonesCuentaUsuario();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    /**
     * Deshabilita los componentes relacionados con la modificación del estado
     * de un curso. Esto incluye el JComboBox para seleccionar el nuevo estado
     * del curso, el JTextField para el tope de alumnos, el JButton para
     * modificar el estado del curso, el JTextArea para mostrar la descripción
     * del curso y el JLabel para mostrar mensajes de error relacionados con el
     * tope de alumnos.
     */
    private void deshabilitarBotonesModificarEstado() {
        vista.jComboBoxModificarEstadoCurso.setEnabled(false);
        vista.jComboBoxModificarEstadoCurso.setSelectedIndex(vista.jComboBoxFiltroCursos.getSelectedIndex());
        vista.jTextFieldTopeAlumnos.setEnabled(false);
        vista.jButtonModificarEstadoCurso.setEnabled(false);
        vista.jTextAreaDescripcionCurso.setText("");
        vista.jLabelErrorTopeAlumnos.setText("");
    }

    /**
     * Muestra los datos del administrador. Actualiza los JLabel
     * correspondientes para mostrar el nombre, apellido y DNI del usuario.
     */
    private void mostrarDatosUsuario() {
        vista.jLabelNombreAdmin.setText("Nombre:   " + usuarioLogeado.getNombre());
        vista.jLabelApellidoAdmin.setText("Apellido:   " + usuarioLogeado.getApellido());
        vista.jLabelDniAdmin.setText("DNI:   " + usuarioLogeado.getDni());
    }

    /**
     * Ejecuta el proceso de cambio de contraseña. Abre una ventana para que el
     * usuario pueda ingresar la nueva contraseña y la confirme. Se crea un
     * nuevo controlador para manejar este proceso.
     */
    private void ejecutarCambiarContraseña() {
        VentanaCambioPassword ventanaPassword = new VentanaCambioPassword();
        ventanaPassword.setVisible(true);
        ControladorCambioPassword ctrlPassword = new ControladorCambioPassword(ventanaPassword, usuarioLogeado, instituto);
        ctrlPassword.iniciar();
    }

    /**
     * Valida las entradas proporcionadas para la creación de un nuevo usuario.
     * Comprueba que el nombre, apellido y DNI no estén vacíos y que el DNI
     * tenga exactamente 8 caracteres. Si alguna validación falla, se actualizan
     * los mensajes de error en la interfaz gráfica.
     *
     * @param nombre El nombre del usuario a validar.
     * @param apellido El apellido del usuario a validar.
     * @param dni El DNI del usuario a validar.
     * @return true si todas las validaciones son exitosas, false de lo
     * contrario.
     */
    private boolean validarEntradasCrearUsuario(String nombre, String apellido, String dni) {
        boolean retorno = true;

        if (nombre.isEmpty()) {
            vista.jLabelErrorNombre.setText("Ingrese el Nombre");
            retorno = false;
        }

        if (apellido.isEmpty()) {
            vista.jLabelErrorApellido.setText("Ingrese el Apellido");
            retorno = false;
        }

        if (dni.length() != 8) {
            vista.jLabelErrorDni.setText("DNI inválido");
            retorno = false;
        }

        return retorno;

    }

    /**
     * Habilita el botón para crear un nuevo usuario si se selecciona un tipo de
     * cuenta (alumno o docente). Si se selecciona alguno de los tipos de
     * cuenta, se habilita el botón de crear usuario.
     */
    private void habilitarBotonCrearUsuario() {
        if (vista.jRadioButtonCuentaAlumno.isSelected() || vista.jRadioButtonCuentaDocente.isSelected()) {
            vista.jButtonCrearUsuario.setEnabled(true);
        }
    }

    /**
     * Verifica si un usuario ya existe en la lista de usuarios del instituto.
     *
     * @param dniUsuario El DNI del usuario a verificar.
     * @throws ExisteUsuarioException Si el usuario ya existe en la lista de
     * usuarios.
     */
    private void existeUsuario(String dniUsuario) throws ExisteUsuarioException {
        DefaultListModel<Usuario> listaUsuarios = instituto.getUsuarios();

        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = listaUsuarios.getElementAt(i);
            if (usuario.getDni().equals(dniUsuario)) {
                throw new ExisteUsuarioException(dniUsuario);
            }
        }
    }

    /**
     * Valida la entrada del DNI al buscar un usuario.
     *
     * @param dni El DNI ingresado.
     * @return true si el DNI no está vacío, false de lo contrario.
     */
    private boolean validarEntradasBuscarUsuario(String dni) {
        boolean retorno = true;

        if (dni.isEmpty()) {
            vista.jLabelErrorBuscarUsuario.setText("Ingrese el DNI");
            retorno = false;
        }

        return retorno;
    }

    /**
     * Valida la entrada del tope de alumnos.
     *
     * @param tope El valor del tope de alumnos ingresado.
     * @return true si el tope no está vacío, false de lo contrario.
     */
    private boolean validarEntradaTopeAlumno(String tope) {
        boolean retorno = true;

        if (tope.isEmpty()) {
            vista.jLabelErrorTopeAlumnos.setText("Ingrese tope");
            retorno = false;
        }

        return retorno;
    }

    /**
     * Habilita o deshabilita el campo de texto para el tope de alumnos según el
     * estado del curso seleccionado.
     */
    private void ejecutarHabilitarTopeAlumnos() {
        if ((vista.jComboBoxModificarEstadoCurso.getSelectedIndex() == 0) || (vista.jComboBoxModificarEstadoCurso.getSelectedIndex() == 1)) {
            vista.jTextFieldTopeAlumnos.setEnabled(true);
        } else {
            vista.jTextFieldTopeAlumnos.setEnabled(false);
        }
    }

    /**
     * Carga el listado de usuarios en una tabla en la vista.
     */
    private void cargarListadoUsuarios() {
        DefaultListModel<Usuario> listadoUsuarios = instituto.getUsuarios();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("DNI");
        model.addColumn("Perfil");
        model.addColumn("Estado Cuenta");

        for (int i = 0; i < listadoUsuarios.size(); i++) {
            // Obtiene el usuario en la posición i
            Usuario usuario = listadoUsuarios.getElementAt(i);

            // Obtiene los valores de los atributos del usuario
            String nombre = usuario.getNombre();
            String apellido = usuario.getApellido();
            String dni = usuario.getDni();
            String perfil = usuario.getPerfil();
            String estadoCuenta = usuario.getEstadoCuenta();

            // Agrega una fila al modelo de tabla con los valores obtenidos
            model.addRow(new Object[]{nombre, apellido, dni, perfil, estadoCuenta});
        }

        // Establece el modelo de tabla en la JTable de la vista
        vista.jTableListadoUsuarios.setModel(model);
    }

}
