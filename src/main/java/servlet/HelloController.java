package servlet;

import jakarta.servlet.annotation.WebServlet;
import model.Greeting;
import service.GreetingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloController", value = "/hello")
public class HelloController extends HttpServlet {

    private GreetingService greetingService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.greetingService = new GreetingService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Greeting greeting = greetingService.createGreeting(name);
        req.setAttribute("greeting", greeting);
        req.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(req, resp);
    }
}
