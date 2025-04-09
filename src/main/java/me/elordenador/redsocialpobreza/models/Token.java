package me.elordenador.redsocialpobreza.models;


import com.sun.istack.NotNull;
import jakarta.persistence.*;

import java.security.SecureRandom;

@Entity
@Table(name = "token")
public class Token {
    @Id
    String token;

    @ManyToOne
    @JoinColumn(name = "id")
    @NotNull
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Token() {
        this.token = generateRandomToken(16);
    }

    public Token(User user) {
        this.user = user;
        this.token = generateRandomToken(16);
    }

    private static String generateRandomToken(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            token.append(characters.charAt(index));
        }

        return token.toString();
    }
}
