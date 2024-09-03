package databaselayer;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Password {

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
        String password = null;
        String encryptedpassword = null;
        try {

            MessageDigest m = MessageDigest.getInstance("SHA256");
            password = plainpass;

            /* Add plain-text password bytes to digest using MD5 update() method. */
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
            e.printStackTrace();
        }
        return encryptedpassword;
    }
}