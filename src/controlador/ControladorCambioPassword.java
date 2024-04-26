package controlador;

import excepciones.ContraseñaInvalidaException;
import modelo.Instituto;
import modelo.Usuario;
import static modelo.PassHasher.hash;
import visual.VentanaCambioPassword;
import visual.VentanaLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador para la funcionalidad de cambio de contraseña.
 */
public class ControladorCambioPassword implements ActionListener {

    private VentanaCambioPassword vista;
    private Usuario usuarioLogeado;
    private Instituto instituto;

    /**
     * Constructor de la clase ControladorCambioPassword.
     *
     * @param vista La ventana de cambio de contraseña.
     * @param usuarioLogeado El usuario logeado que desea cambiar su contraseña.
     * @param instituto El instituto al que pertenece el usuario.
     */
    public ControladorCambioPassword(VentanaCambioPassword vista, Usuario usuarioLogeado, Instituto instituto) {
        this.vista = vista;
        this.usuarioLogeado = usuarioLogeado;
        this.instituto = instituto;
        this.vista.jButtonCambioPassword.addActionListener(this);
    }

    /**
     * Inicializa la ventana de cambio de contraseña.
     */
    public void iniciar() {
        vista.setTitle("Cambio de Contraseña");
        vista.setLocationRelativeTo(null);
    }

    /**
     * Maneja los eventos de acción realizados en la ventana de cambio de
     * contraseña. Ejecuta el método para cambiar la contraseña del usuario actual.
     * Captura y maneja cualquier excepción de tipo ContraseñaInvalidaException
     * que pueda ocurrir durante el proceso.
     *
     * @param e El evento de acción que ha ocurrido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            if (e.getSource() == vista.jButtonCambioPassword) {
                ejecutarCambioContraseña();//serializado
            }
        } catch (ContraseñaInvalidaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Ejecuta el proceso de cambio de contraseña del usuario logeado. Verifica
     * las entradas de contraseña y realiza el cambio si son válidas. Muestra
     * mensajes de error si la contraseña actual es incorrecta o si la nueva
     * contraseña y su confirmación no coinciden. En caso de éxito, actualiza la
     * contraseña del usuario en el sistema, guarda los cambios en el archivo
     * del instituto, muestra un mensaje de éxito, cierra la ventana de cambio
     * de contraseña y abre la ventana de inicio de sesión.
     *
     * @throws ContraseñaInvalidaException Si la contraseña actual es incorrecta
     * o si la nueva contraseña y su confirmación no coinciden.
     */
    private void ejecutarCambioContraseña() throws ContraseñaInvalidaException {

        if (validarEntradasCambiarContraseña()) {

            String contraseñaActual = hash(vista.jPasswordFieldActual.getText());
            String contraseñaNueva = hash(vista.jPasswordFieldNueva.getText());
            String contraseñaConfirmada = hash(vista.jPasswordFieldConfirmar.getText());
            validarContraseña();//verifica que pass y dni sean distintas
            if (usuarioLogeado.getPassword().equals(contraseñaActual)) {
                if (contraseñaNueva.equals(contraseñaConfirmada)) {
                    usuarioLogeado.cambioPassword(vista.jPasswordFieldNueva.getText());
                    instituto.serializarInstituto("instituto.dat");
                    JOptionPane.showMessageDialog(null, "Contraseña actualizada con éxito.");
                    vista.dispose();
                    VentanaLogin ventanaLogin = new VentanaLogin();
                    ventanaLogin.setVisible(true);
                    ControladorLogin controladorLogin = new ControladorLogin(ventanaLogin, instituto);
                    controladorLogin.iniciar();
                } else {
                    vista.jLabelErrorConfirmarContraseña.setText("No coinciden");
                }
            } else {
                vista.jLabelErrorContraseñaActual.setText("Contraseña errónea");
            }

        }
    }

    /**
     * Valida las entradas de contraseña para asegurarse de que tienen una
     * longitud de 8 caracteres. Muestra mensajes de error en caso de que alguna
     * de las contraseñas no cumpla con esta longitud.
     *
     * @return true si todas las contraseñas tienen una longitud de 8
     * caracteres, false en caso contrario.
     */
    private boolean validarEntradasCambiarContraseña() {
        boolean retorno = true;
        if (vista.jPasswordFieldActual.getText().length() != 8) {
            vista.jLabelErrorContraseñaActual.setText("Debe contener 8 caracteres");
            retorno = false;
        }

        if (vista.jPasswordFieldNueva.getText().length() != 8) {
            vista.jLabelErrorContraseñaNueva.setText("Debe contener 8 caracteres");
            retorno = false;
        }

        if (vista.jPasswordFieldConfirmar.getText().length() != 8) {
            vista.jLabelErrorConfirmarContraseña.setText("Debe contener 8 caracteres");
            retorno = false;
        }
        return retorno;
    }

    /**
     * Valida que la nueva contraseña no sea igual al DNI del usuario.
     *
     * @throws ContraseñaInvalidaException Si la nueva contraseña es igual al
     * DNI del usuario.
     */
    private void validarContraseña() throws ContraseñaInvalidaException {
        if (usuarioLogeado.getDni().equals(vista.jPasswordFieldNueva.getText())) {
            throw new ContraseñaInvalidaException();
        }

    }

}
