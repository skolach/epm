package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class PasswordHash {

    private static final Logger log = Logger.getLogger(PasswordHash.class);

    private PasswordHash() {}

    public static String hashPassword(String password) {

        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Cannot hash password", e);
        }
        return hashedPassword;
    }

    public static void main(String[] args) {
        System.out.println("");
        System.out.println(hashPassword("")); // NOSONAR
        System.out.println(hashPassword("").length()); // NOSONAR
    }
}