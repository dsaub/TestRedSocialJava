package me.elordenador.redsocialpobreza.models;

import jakarta.persistence.*;

@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @ManyToOne
    public User origin;
    @ManyToOne
    public User destination;
}
