package me.elordenador.redsocialpobreza.servlets.user;

import java.util.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import me.elordenador.redsocialpobreza.DBManager;
import com.github.lalyos.jfiglet.FigletFont;

@WebServlet(name = "InitServlet", value="/", loadOnStartup=1)
public class InitServlet {
    public void init() throws ServletException, IOException {
        System.out.println(FigletFont.convertOneLine("RED SOCIAL"));
        DBManager.initialize();
    }
}
