import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-01
 * Time: 11:40 PM
 * Description:
 */
@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company",
                    "root",
                    "Chanu@acc2002");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
