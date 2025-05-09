package me.elordenador.redsocialpobreza.models;


import com.google.common.hash.Hashing;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import me.elordenador.redsocialpobreza.DBManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @NotNull
    int user_id;

    @NotNull
    String username, password, fullname, email, phone, description;

    @ManyToMany
    Set<User> friends;

    @OneToMany
    Set<FriendRequest> friendRequests;

    @NotNull
    boolean initialized = false;
    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    private void addFriend(User user) {
        friends.add(user);
    }

    public User[] getFriends() {
        return friends.toArray(new User[friends.size()]);
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean setPassword(String originalPassword, String newPassword) {
        String hashedOriginalPassword = Hashing.sha256().hashString(originalPassword, StandardCharsets.UTF_8).toString();
        String hashedNewPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        if (hashedOriginalPassword.equals(hashedNewPassword)) return false;
        if (!hashedOriginalPassword.equals(password)) return false;
        password = hashedNewPassword;
        return true;
    }

    public boolean setLiteralPassword(String originalPassword, String newPassword) {
        if (originalPassword.equals(newPassword)) return false;
        if (!originalPassword.equals(password)) return false;
        password = newPassword;
        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // TODO: Fix issue with this code
    private class PermissionManager {
        public static String permission;
        public static boolean permissionFound = false;
        public static User user;
        public static void transaction(Session session) {
            System.out.println("Checking if user haves '*' permission");
            Query<Permission> query = session.createQuery("from Permission p where User = :user AND permission = '*'", Permission.class);
            query.setParameter("user", user);

            Permission permission = query.uniqueResult();
            if (permission != null) {
                permissionFound = true;
                return;
            }
            System.out.println("Checking if user haves '"+permission+"' permission");
            query = session.createQuery("from Permission p where User = :user AND permission = :permission", Permission.class);
            query.setParameter("user", user);
            query.setParameter("permission", permission);
            permission = query.uniqueResult();
            permissionFound = permission != null;
        }
    }
    public boolean havePermission(String permission1) {
        SessionFactory sessionFactory = DBManager.getSessionFactory();

        PermissionManager.permission = permission1;
        PermissionManager.user = this;
        sessionFactory.inTransaction(session -> {
            PermissionManager.transaction(session);
        });


        return PermissionManager.permissionFound;
    }
}
