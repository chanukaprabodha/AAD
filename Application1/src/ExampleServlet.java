import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        /*resp.setContentType("text/html");
        resp.addHeader("token", "1234");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);*/
        ServletContext servletContext = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("dataSource");
        try {
            Connection connection = ds.getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from customer")
                    .executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                System.out.println(id + " " + name + " " + address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
