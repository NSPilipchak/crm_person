package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hammer on 06.08.2017.
 */
@Entity
@Table(name = "user")
public class User implements Serializable{
    public User() {
    }

    @Id
    @Column(name = "user_id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_sequence", allocationSize = 1)
    private int id;
    @Column(unique = true, nullable = false, length = 10)
    private String user;
    @Column(nullable = false, length = 10)
    private String password;
    @Column(nullable = false, length = 2)
    private int permission;
    @Column(nullable = false, length = 2)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user1 = (User) o;

        if (id != user1.id) return false;
        if (permission != user1.permission) return false;
        if (status != user1.status) return false;
        if (user != null ? !user.equals(user1.user) : user1.user != null) return false;
        return password != null ? password.equals(user1.password) : user1.password == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + permission;
        result = 31 * result + status;
        return result;
    }
}
