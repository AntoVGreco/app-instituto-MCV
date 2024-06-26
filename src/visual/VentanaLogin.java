package visual;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

/**
 * La clase VentanaLogin representa la interfaz gráfica de usuario para el
 * inicio de sesión.
 */
public class VentanaLogin extends javax.swing.JFrame {

    /**
     * Crea una nueva instancia de la ventana de inicio de sesión.
     */
    public VentanaLogin() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextDNIUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();
        jPasswordLogin = new javax.swing.JPasswordField();
        jLabelErrorDni = new javax.swing.JLabel();
        jLabelErrorContraseña = new javax.swing.JLabel();
        jLabelErrorLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Inicio de sesión");

        jLabel2.setText("DNI");

        jTextDNIUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDNIUsuarioKeyTyped(evt);
            }
        });

        jLabel3.setText("Contraseña");

        jButtonLogin.setText("Log in");
        jButtonLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPasswordLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordLoginActionPerformed(evt);
            }
        });
        jPasswordLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPasswordLoginKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 345, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLoginLayout.createSequentialGroup()
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(53, 53, 53)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLoginLayout.createSequentialGroup()
                                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextDNIUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(jButtonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPasswordLogin))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelErrorDni, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                    .addComponent(jLabelErrorContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18))
                            .addGroup(jPanelLoginLayout.createSequentialGroup()
                                .addComponent(jLabelErrorLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelErrorDni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextDNIUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelErrorContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButtonLogin)
                .addGap(18, 18, 18)
                .addComponent(jLabelErrorLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordLoginActionPerformed

    /**
     * Método invocado cuando se presiona una tecla en el campo de contraseña
     * durante el inicio de sesión. Verifica si se excede la longitud máxima y
     * muestra un mensaje de error si es necesario.
     *
     * @param evt el evento de teclado generado
     */
    private void jPasswordLoginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordLoginKeyTyped
        //Verificar si se excede la longitud máxima
        if (jPasswordLogin.getText().length() >= 8) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();// Consumir el evento para evitar que se ingrese el carácter
        }
        // Limpiar mensaje de error
        jLabelErrorContraseña.setText("");
    }//GEN-LAST:event_jPasswordLoginKeyTyped

    /**
     * Método invocado cuando se presiona una tecla en el campo de texto del DNI
     * de usuario durante el inicio de sesión. Verifica si la tecla presionada
     * es un dígito y no excede la longitud máxima permitida. Si la tecla
     * presionada no es un dígito o es la tecla de borrar, consume el evento y
     * emite un beep. Limpia los mensajes de error asociados al campo.
     *
     * @param evt el evento de teclado generado
     */
    private void jTextDNIUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDNIUsuarioKeyTyped
        char c = evt.getKeyChar();
        // Verificar si la tecla presionada no es un dígito o es la tecla de borrar
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || jTextDNIUsuario.getText().length() >= 8) {
            evt.consume();
            // Solo emitir el beep si no es la tecla de borrar
            if (c != KeyEvent.VK_BACK_SPACE) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        // Limpiar mensajes de error
        jLabelErrorDni.setText("");
        jLabelErrorContraseña.setText("");
    }//GEN-LAST:event_jTextDNIUsuarioKeyTyped

    /**
     * Método principal para ejecutar la aplicación.
     *
     * @param args los argumentos de la línea de comandos (no se utilizan en
     * esta aplicación)
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabelErrorContraseña;
    public javax.swing.JLabel jLabelErrorDni;
    public javax.swing.JLabel jLabelErrorLogin;
    private javax.swing.JPanel jPanelLogin;
    public javax.swing.JPasswordField jPasswordLogin;
    public javax.swing.JTextField jTextDNIUsuario;
    // End of variables declaration//GEN-END:variables
}
