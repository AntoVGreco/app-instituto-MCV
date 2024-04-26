package app;

import controlador.ControladorLogin;
import modelo.Instituto;
import visual.VentanaLogin;
import java.io.File;

public class Main {

    /**
     * Método principal que inicializa la aplicación. Verifica la existencia del
     * archivo de datos del Instituto. Si el archivo no existe, crea un nuevo
     * Instituto y lo serializa. Si el archivo existe, carga los datos
     * serializados del Instituto desde el archivo. Luego, crea la ventana de
     * inicio de sesión y muestra la interfaz de usuario.
     *
     * @param args los argumentos de la línea de comandos (no se utilizan en
     * este caso)
     */
    public static void main(String[] args) {
        Instituto icet;

        // Verificar si el archivo de datos del Instituto ya existe
        File archivo = new File("instituto.dat");
        if (!archivo.exists()) {
            // Si el archivo no existe, es la primera vez que se ejecuta el programa,
            // así que creamos un nuevo Instituto y lo serializamos en el archivo
            icet = new Instituto();
            icet.serializarInstituto("instituto.dat");
        } else {
            // Si el archivo existe, cargamos los datos serializados del Instituto desde el archivo
            icet = Instituto.deserializarInstituto("instituto.dat");
        }

        // Crear la ventana de inicio de sesión y el controlador asociado
        VentanaLogin ventanaLogin = new VentanaLogin();
        ControladorLogin controladorLogin = new ControladorLogin(ventanaLogin, icet);

        // Iniciar y mostrar la ventana de inicio de sesión
        controladorLogin.iniciar();
        ventanaLogin.setVisible(true);

    }
}
