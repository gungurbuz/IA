import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Password {
    private static String password = null;
    private static String encryptedpassword = null;

    public static String makePass(String plainpass) {
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