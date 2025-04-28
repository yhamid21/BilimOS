package Users;


import java.util.Date;

public abstract class User {
    protected String id;
    protected String name;
    protected String email;
    protected Date dob;


    public User(String id, String name, String email, Date dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
