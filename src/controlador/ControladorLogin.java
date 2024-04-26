package controlador;

import excepciones.ContraseñaInvalidaException;
import excepciones.CuentaSuspendidaException;
import excepciones.NoExisteUsuarioException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Docente;
import modelo.Usuario;
import modelo.Instituto;
import static modelo.PassHasher.hash;
import visual.VentanaLogin;
import visual.VentanaAdmin;
import visual.VentanaAlumno;
import visual.VentanaCambioPassword;
import visual.VentanaDocente;

/**
 * Controlador para el inicio de sesión.
 */
public class ControladorLogin implements ActionListener {

    private VentanaLogin vista;
    private Instituto instituto;

    /**
     * Constructor de la clase ControladorLogin.
     *
     * @param vista La ventana de inicio de sesión.
     * @param instituto El instituto al que pertenece el usuario.
     */
    public ControladorLogin(VentanaLogin vista, Instituto instituto) {
        this.vista = vista;
        this.instituto = instituto;
        this.vista.jButtonLogin.addActionListener(this);
    }

    /**
     * Inicializa la ventana de inicio de sesión.
     */
    public void iniciar() {
        vista.setTitle("Inicio de sesión");
        vista.setLocationRelativeTo(null);
    }

    /**
     * Maneja el evento de acción si se presiona el botón de inicio de sesión en
     * la interfaz gráfica. Sino captura cualquier tipo de excepcion que pudiera
     * suceder.
     *
     * @param e El evento de acción generado por el botón de inicio de sesión.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            if (e.getSource() == vista.jButtonLogin) {
                ejecutarLogin();//serializado
            }
        } catch (NoExisteUsuarioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            limpiarVentanaLogin();
            return;
        } catch (ContraseñaInvalidaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            limpiarVentanaLogin();
            return;
        } catch (CuentaSuspendidaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            limpiarVentanaLogin();
            return;
        }
    }

    /**
     * Si laautenticación es exitosa, verifica si es el primer inicio de sesión
     * y muestra la ventana de cambio de contraseña si es así. De lo contrario,
     * abre la ventana correspondiente según el perfil del usuario autenticado.
     * Si hay algún error durante el proceso de inicio de sesión, muestra un
     * mensaje de error y limpia la ventana de inicio de sesión.
     */
    private void ejecutarLogin() throws NoExisteUsuarioException, ContraseñaInvalidaException, CuentaSuspendidaException {
        String dni = vista.jTextDNIUsuario.getText();
        String pass = hash(vista.jPasswordLogin.getText());

        if (validarEntradaUsuario(dni, vista.jPasswordLogin.getText())) {

            Usuario usuario = null;
            usuario = validarUsuario(dni);
            verificarContraseña(usuario, pass);
            verificarCuentaSuspendida(usuario);
            if (hash(usuario.getDni()).equals(usuario.getPassword())) {
                verificarPrimerIngreso(usuario);
            } else {
                abrirVentanaSegunPerfil(usuario);
            }
            vista.dispose();

        }
    }

    /**
     * Valida la entrada de usuario verificando si el DNI y la contraseña no
     * están vacíos.
     *
     * @param dni El DNI ingresado por el usuario.
     * @param pass La contraseña ingresada por el usuario.
     * @return true si el DNI y la contraseña no están vacíos, false de lo
     * contrario.
     */
    private boolean validarEntradaUsuario(String dni, String pass) {
        if (dni.isEmpty()) {
            vista.jLabelErrorDni.setText("Ingrese el DNI");
            return false;
        }
        if (pass.isEmpty()) {
            vista.jLabelErrorContraseña.setText("Ingrese contraseña");
            return false;
        }
        return true;
    }

    /**
     * Valida la existencia de un usuario en el sistema mediante su número de
     * identificación (DNI).
     *
     * @param dni El número de identificación del usuario a validar.
     * @return El usuario si existe en el sistema.
     * @throws NoExisteUsuarioException Si no se encuentra ningún usuario con el
     * DNI proporcionado.
     */
    private Usuario validarUsuario(String dni) throws NoExisteUsuarioException {
        Usuario buscado = existeUsuario(dni);

        if (buscado == null) {
            throw new NoExisteUsuarioException(dni);
        }

        return buscado;
    }

    /**
     * Verifica si el usuario existe dentro del listado del instituto.
     *
     * @param dniUsuario El DNI del usuario.
     * @return El usuario si existe, null si no.
     */
    private Usuario existeUsuario(String dniUsuario) {
        DefaultListModel<Usuario> listaUsuarios = instituto.getUsuarios();

        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = listaUsuarios.getElementAt(i);
            if (usuario.getDni().equals(dniUsuario)) {
                return usuario;
            }
        }

        return null;
    }

    /**
     * Verifica si la contraseña proporcionada coincide con la contraseña del
     * usuario.
     *
     * @param user El usuario cuya contraseña se va a verificar.
     * @param contraseña La contraseña proporcionada para verificar.
     * @throws ContraseñaInvalidaException Si la contraseña proporcionada no
     * coincide con la contraseña del usuario.
     */
    private void verificarContraseña(Usuario user, String contraseña) throws ContraseñaInvalidaException {
        if (!user.getPassword().equals(contraseña)) {
            throw new ContraseñaInvalidaException();
        }
    }

    /**
     * Verifica si la cuenta del usuario está suspendida.
     *
     * @param user El usuario.
     * @throws CuentaSuspendidaException si la cuenta está suspendida.
     */
    private void verificarCuentaSuspendida(Usuario user) throws CuentaSuspendidaException {
        if (user.isSancionado()) {
            throw new CuentaSuspendidaException();
        }
    }

    /**
     * Verifica si es el primer inicio de sesión del usuario y muestra la
     * ventana de cambio de contraseña si corresponde.
     *
     * @param usuarioValidado El usuario validado.
     */
    private void verificarPrimerIngreso(Usuario usuarioValidado) {
        VentanaCambioPassword ventanaPassword = new VentanaCambioPassword();
        ventanaPassword.setVisible(true);
        ControladorCambioPassword ctrlPassword = new ControladorCambioPassword(ventanaPassword, usuarioValidado, instituto);
        ctrlPassword.iniciar();
    }

    /**
     * Abre la ventana correspondiente según el perfil del usuario.
     *
     * @param usuarioValidado El usuario validado.
     */
    private void abrirVentanaSegunPerfil(Usuario usuarioValidado) {
        switch (usuarioValidado.getPerfil()) {
            case "Admin":
                VentanaAdmin ventanaAdmin = new VentanaAdmin();
                ventanaAdmin.setVisible(true);
                ControladorAdmin ctrlAdmin = new ControladorAdmin(ventanaAdmin, instituto, (Administrador) usuarioValidado);
                ctrlAdmin.iniciar();
                break;
            case "Alumno":
                VentanaAlumno ventanaAlumno = new VentanaAlumno();
                ventanaAlumno.setVisible(true);
                ControladorAlumno ctrlAlumno = new ControladorAlumno(ventanaAlumno, instituto, (Alumno) usuarioValidado);
                ctrlAlumno.iniciar();
                break;
            case "Docente":
                VentanaDocente ventanaDocente = new VentanaDocente();
                ventanaDocente.setVisible(true);
                ControladorDocente ctrlDocente = new ControladorDocente(ventanaDocente, instituto, (Docente) usuarioValidado);
                ctrlDocente.iniciar();
                break;
            default:
                break;
        }
    }

    /**
     * Limpia los mensajes de error en la ventana de inicio de sesión.
     */
    private void limpiarVentanaLogin() {
        vista.jLabelErrorContraseña.setText("");
        vista.jLabelErrorDni.setText("");
        vista.jLabelErrorLogin.setText("");
    }
}
