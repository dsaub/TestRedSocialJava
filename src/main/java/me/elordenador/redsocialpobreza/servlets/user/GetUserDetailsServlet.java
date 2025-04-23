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

@WebServlet(name = "GetUserServlet", value = "/api/v1/getUser")
public class GetUserDetailsServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        SessionFactory sessionFactory = DBManager.getSessionFactory();


        sessionFactory.inTransaction(session -> {
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();
            Gson gson = new Gson();
            oUser ouser = new oUser();
            ouser.username = user.getUsername();
            ouser.fullname = user.getFullName();
            response.setContentType(request.getContentType());
            response.setCharacterEncoding(request.getCharacterEncoding());
            try {
                response.addHeader("Access-Control-Allow-Origin", System.getenv("BACKEND_URL"));
                response.addHeader("Access-Control-Allow-Methods", "POST");
                response.addHeader("Access-Control-Allow-Credentials", "true");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(ouser));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
