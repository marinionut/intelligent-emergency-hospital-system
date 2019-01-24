package ro.ionutmarin.iehs.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ionut on 10/21/2017.
 */
@Entity
@Table(name="users")
@Data
@NamedQueries({
    @NamedQuery(
            name="UserEntity.findByUsername",
            query="SELECT u FROM UserEntity u WHERE u.username = :username"
    ),
    @NamedQuery(
            name="UserEntity.findAll",
            query="SELECT u FROM UserEntity u"
    )
})
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public UserEntity() {
    }

    public UserEntity(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return password;
    }

    public void setParola(String parola) {
        this.password = parola;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

