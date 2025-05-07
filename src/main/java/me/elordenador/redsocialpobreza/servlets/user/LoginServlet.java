package me.elordenador.redsocialpobreza.servlets.user;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.elordenador.redsocialpobreza.DBManager;
import me.elordenador.redsocialpobreza.models.Token;
import me.elordenador.redsocialpobreza.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/api/v1/login")
public class LoginServlet extends HttpServlet {
    private class Data  {
        public String username;
        public String password;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Data body = getBody(request, response);
        if (body == null) {
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        SessionFactory sessionFactory = DBManager.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            Query<User> query = session.createQuery("from User where username = :username and password = :password", User.class);
            query.setParameter("username", body.username);
            query.setParameter("password", body.password);
            User user = query.uniqueResult();
            if (user == null) {
                try {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Token token = new Token(user);
                session.persist(token);
                String token1 = token.getToken();
                response.addCookie(new Cookie("authorization_token", token1));
                try {
                    response.sendRedirect("/");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public Data getBody(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String contentType = request.getContentType();
        if (!("application/json".equals(contentType))) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Invalid Content Type");
            return null;
        }
        try (BufferedReader reader = request.getReader()) {
            Gson gson = new Gson();
            Data data = gson.fromJson(reader, Data.class);
            return data;
        }
    }
}
