package me.elordenador.redsocialpobreza.servlets.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import me.elordenador.redsocialpobreza.DBManager;
import com.github.lalyos.jfiglet.FigletFont;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "InitServlet", value="/", loadOnStartup=1)
public class InitServlet extends HttpServlet {
    public void init() throws ServletException, IOException {
        System.out.println(FigletFont.convertOneLine("RED SOCIAL"));
        DBManager.initialize();
    }
}
