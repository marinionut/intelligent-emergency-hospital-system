package ro.ionutmarin.iehs.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public static boolean matchingPassword(String hashPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, hashPassword);
    }
}
