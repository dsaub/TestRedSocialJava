package me.elordenador.redsocialpobreza.models;

import jakarta.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
    User user;

    @Id
    String permission;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission() {}

    public Permission(User user, String permission) {
        this.user = user;
        this.permission = permission;
    }
}
