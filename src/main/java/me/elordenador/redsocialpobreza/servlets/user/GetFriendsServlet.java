package me.elordenador.redsocialpobreza.servlets.user;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.elordenador.redsocialpobreza.DBManager;
import me.elordenador.redsocialpobreza.models.User;
import me.elordenador.redsocialpobreza.outmodels.oUser;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetUserServlet", value = "/api/v1/getFriends")
public class GetFriendsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        if (username == null) {
            out.println("{\"error\": \"Username is required\"}");
            return;
        }

        SessionFactory sessionFactory = DBManager.getSessionFactory();
        sessionFactory.inTransaction(session -> {
           Query<User> query = session.createQuery("from User where username = :username", User.class);
           query.setParameter("username", username);
           User user = query.getSingleResult();
           if (user == null) {
               out.println("{\"error\": \"User not found\"}");
               return;
           }
           User[] friends = user.getFriends();
           oUser[] friendlist = new oUser[friends.length];
           for (int i = 0; i < friends.length; i++) {
               friendlist[i] = new oUser();
               friendlist[i].username = friends[i].getUsername();
               friendlist[i].fullname = friends[i].getFullName();
           }

           Gson gson = new Gson();
           String json = gson.toJson(friendlist);
           out.println(json);
        });
    }
}
