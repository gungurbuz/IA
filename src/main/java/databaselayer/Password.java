package databaselayer;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Password {
    
    private final WindowBasedTextGUI gui = GUIConnector.getTextGUI();
    
    /**
     * Generates an encrypted version of the plain text password utilizing SHA-256.
     *
     * @param plainpass the plain text password to be encrypted
     * @return the encrypted password in hexadecimal format
     */
    public String makePass(String plainpass) {
        return makePassPrivate(plainpass);
    }

    private String makePassPrivate(String plainpass) {
        String password;
        String encryptedpassword = null;
        try {

            MessageDigest m = MessageDigest.getInstance("SHA-256");
            password = plainpass;

            /* Add plain-text password bytes to digest using SHA-256 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /*
             * The bytes array has bytes in decimal form. Converting it into hexadecimal
             * format.
             */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            MessageDialog.showMessageDialog(gui, "Error", "Error during password encryption:" + e.getMessage());
        }
        return encryptedpassword;
    }
}