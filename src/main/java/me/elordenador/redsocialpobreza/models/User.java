package me.elordenador.redsocialpobreza.models;


import com.google.common.hash.Hashing;
import com.sun.istack.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @NotNull
    int user_id;

    @NotNull
    String username, password, fullname, email, phone;

    @ManyToMany
    Set<User> friends;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public boolean setPassword(String originalPassword, String newPassword) {
        String hashedOriginalPassword = Hashing.sha256().hashString(originalPassword, StandardCharsets.UTF_8).toString();
        String hashedNewPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        if (hashedOriginalPassword.equals(hashedNewPassword)) return false;
        if (!hashedOriginalPassword.equals(password)) return false;
        password = hashedNewPassword;
        return true;
    }

}
