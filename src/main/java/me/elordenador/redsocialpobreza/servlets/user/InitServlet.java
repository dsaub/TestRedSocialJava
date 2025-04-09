package me.elordenador.redsocialpobreza.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import me.elordenador.redsocialpobreza.DBManager;

@WebServlet(name = "InitServlet", value="/", loadOnStartup=1)
public class InitServlet {
    public void init() throws ServletException {
        System.out.println(FigletFont.convertOneLine("RED SOCIAL"));
        DBManager.initialize();
    }
}
