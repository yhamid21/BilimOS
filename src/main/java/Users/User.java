package Users;

import java.util.Date;
import java.awt.image.BufferedImage;

public abstract class User {
    protected String id;
    protected String name;
    protected String email;
    protected Date dob;
    protected BufferedImage profilePicture;
    protected String password; // Added password field

    public User(String id, String name, String email, Date dob, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.password = password;
    }

    public void setProfilePicture(BufferedImage profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob()
    {
        return dob.toString();
    }
}