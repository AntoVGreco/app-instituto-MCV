package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase que proporciona métodos para generar un hash seguro a partir de una 
 * cadena de texto utilizando el algoritmo SHA-256.
 */
public class PassHasher {

    /**
     * Genera un hash seguro a partir de una cadena de texto utilizando el algoritmo SHA-256.
     * @param dni La cadena de texto a hashear.
     * @return El hash resultante en formato hexadecimal, o null si ocurre un error.
     */
    public static String hash(String dni) {
        try {
            // Obtener una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convertir el DNI a bytes y aplicar el hash
            byte[] hashBytes = digest.digest(dni.getBytes());

            // Convertir los bytes del hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Retornar el hash como una cadena hexadecimal
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar el caso en que el algoritmo no esté disponible
            e.printStackTrace();
            return null;
        }
    }
}
