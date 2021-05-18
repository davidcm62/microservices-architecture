package ga.davidcm.authservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Auth {

    @Id
    private String id;
    private String username;
    private String password;

    public Auth() {
    }

    public String getId() {
        return id;
    }

    public Auth setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Auth setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Auth setPassword(String password) {
        this.password = password;
        return this;
    }
}
