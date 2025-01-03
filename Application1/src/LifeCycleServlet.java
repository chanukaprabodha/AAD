import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-02
 * Time: 02:25 PM
 * Description:
 */
@WebServlet(urlPatterns = "/lifecycle")
public class LifeCycleServlet extends HttpServlet {
    public LifeCycleServlet() {
        System.out.println("Inside Constructor");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Inside doGet");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Inside init");
    }

    @Override
    public void destroy() {
        System.out.println("Inside destroy");
    }
}
