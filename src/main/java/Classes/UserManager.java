package Classes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Simple session-only manager.
public class UserManager {
    public enum UserType { STUDENT, TEACHER, ADMIN }

    // Naive password storage for prototype! Don't use in real-world apps.
    public static class UserSessionEntry {
        public final String name, email, password, dob;
        public final UserType type;
        public UserSessionEntry(String name, String email, String password, String dob, UserType type) {
            this.name = name; this.email = email; this.password = password; this.dob = dob; this.type = type;
        }
    }

    private static final Map<String, UserSessionEntry> users = new ConcurrentHashMap<>();

    public static boolean registerUser(String name, String email, String password, String dob, UserType type) {
        if (users.containsKey(email)) return false;
        users.put(email, new UserSessionEntry(name, email, password, dob, type));
        return true;
    }

    public static UserSessionEntry authenticate(String email, String password) {
        UserSessionEntry entry = users.get(email);
        if (entry != null && entry.password.equals(password)) return entry;
        return null;
    }
}