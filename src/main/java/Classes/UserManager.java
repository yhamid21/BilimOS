package Classes;

import Users.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class UserManager {
    public enum UserType { STUDENT, TEACHER, ADMIN }

    // Store actual User objects
    private static final Map<String, User> users = new ConcurrentHashMap<>();

    // For backward compatibility
    public static class UserSessionEntry {
        public final String name, email, password, dob;
        public final UserType type;
        public UserSessionEntry(String name, String email, String password, String dob, UserType type) {
            this.name = name; this.email = email; this.password = password; this.dob = dob; this.type = type;
        }
    }

    public static boolean registerUser(String name, String email, String password, String dob, UserType type) {
        if (users.containsKey(email)) return false;

        try {
            // Parse date of birth
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dobDate = dateFormat.parse(dob);

            // Create appropriate user type
            User user;
            String id = UUID.randomUUID().toString(); // Generate a unique ID

            switch (type) {
                case STUDENT:
                    user = new Student(id, name, email, dobDate, password);
                    break;
                case TEACHER:
                    user = new Teacher(id, name, email, dobDate, password);
                    break;
                case ADMIN:
                    user = new Admin(id, name, email, dobDate, password);
                    break;
                default:
                    return false;
            }

            users.put(email, user);
            return true;
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return false;
        }
    }

    public static UserSessionEntry authenticate(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            UserType type;
            if (user instanceof Student) {
                type = UserType.STUDENT;
            } else if (user instanceof Teacher) {
                type = UserType.TEACHER;
            } else if (user instanceof Admin) {
                type = UserType.ADMIN;
            } else {
                return null;
            }
            return new UserSessionEntry(user.getName(), user.getEmail(), user.getPassword(), user.getDob(), type);
        }
        return null;
    }

    // Get a specific user by email
    public static User getUser(String email) {
        return users.get(email);
    }

    // Get all users
    public static List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    // Get all users of a specific type
    public static List<User> getUsersByType(UserType type) {
        return users.values().stream()
            .filter(user -> {
                switch (type) {
                    case STUDENT: return user instanceof Student;
                    case TEACHER: return user instanceof Teacher;
                    case ADMIN: return user instanceof Admin;
                    default: return false;
                }
            })
            .collect(Collectors.toList());
    }

    // Get all students
    public static List<Student> getAllStudents() {
        return users.values().stream()
            .filter(user -> user instanceof Student)
            .map(user -> (Student) user)
            .collect(Collectors.toList());
    }

    // Get all teachers
    public static List<Teacher> getAllTeachers() {
        return users.values().stream()
            .filter(user -> user instanceof Teacher)
            .map(user -> (Teacher) user)
            .collect(Collectors.toList());
    }

    // Get all admins
    public static List<Admin> getAllAdmins() {
        return users.values().stream()
            .filter(user -> user instanceof Admin)
            .map(user -> (Admin) user)
            .collect(Collectors.toList());
    }
}
