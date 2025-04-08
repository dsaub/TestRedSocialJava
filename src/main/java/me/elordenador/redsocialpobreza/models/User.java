package me.elordenador.redsocialpobreza.models;


import com.sun.istack.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    int user_id;

    @NotNull
    String username, password, fullname, email, phone;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
