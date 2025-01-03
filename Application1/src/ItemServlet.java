import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.*;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = getConnection();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM item").executeQuery();
            JsonArrayBuilder allItems = Json.createArrayBuilder();
            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String description = resultSet.getString(2);
                double price = Double.parseDouble(resultSet.getString(3));
                double qty = Double.parseDouble(resultSet.getString(4));
                JsonObjectBuilder item = Json.createObjectBuilder();
                item.add("code", code);
                item.add("description", description);
                item.add("price", price);
                item.add("qty", qty);
                allItems.add(item.build());
            }
            resp.setContentType("application/json");
            resp.getWriter().write(allItems.build().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String code = req.getParameter("code");
       String description = req.getParameter("description");
       double price = Double.parseDouble(req.getParameter("price"));
       double qty = Double.parseDouble(req.getParameter("qty"));

       if (code == null || description == null || price == 0 || qty == 0) {
           resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
           resp.getWriter().write("{\"error\": \"All fields are required\"}");
           return;
       }
       Connection connection = getConnection();
       try {
           PreparedStatement pstm = connection.prepareStatement("INSERT INTO item(code, description, price, qty) VALUES(?, ?, ?, ?)");
           pstm.setString(1, code);
           pstm.setString(2, description);
           pstm.setString(3, String.valueOf(price));
           pstm.setString(4, String.valueOf(qty));
           pstm.executeUpdate();
           resp.setStatus(HttpServletResponse.SC_CREATED);
           resp.getWriter().write("{\"success\": \"Item added successfully\"}");

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        double qty = Double.parseDouble(req.getParameter("qty"));

        if (code == null || description == null || price == 0 || qty == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"All fields are required\"}");
            return;
        }
        Connection connection = getConnection();
        ItemDTO item = findItem(code);
        try {
            if (item != null) {
                PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description = ?, price = ?, qty = ? WHERE code = ?");
                pstm.setString(1, description);
                pstm.setString(2, String.valueOf(price));
                pstm.setString(3, String.valueOf(qty));
                pstm.setString(4, code);
                pstm.executeUpdate();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"success\": \"Item updated successfully\"}");
            }else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Item not found\"}");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ItemDTO findItem(String code) {
        Connection connection = getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code = ?");
            pstm.setString(1, code);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String description = resultSet.getString(2);
                double price = Double.parseDouble(resultSet.getString(3));
                double qty = Double.parseDouble(resultSet.getString(4));
                return new ItemDTO(code, description, price, qty);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        ItemDTO item = findItem(code);
        if (item == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Item not found\"}");
            return;
        }
        Connection connection = getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code = ?");
            pstm.setString(1, code);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write("{\"success\": \"Item deleted successfully\"}");
            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"Failed to delete item\"}");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
