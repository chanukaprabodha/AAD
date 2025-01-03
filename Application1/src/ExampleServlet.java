import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-02
 * Time: 10:48 AM
 * Description:
 */
@WebServlet(urlPatterns = "/example")
public class ExampleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.addHeader("token", "1234");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
