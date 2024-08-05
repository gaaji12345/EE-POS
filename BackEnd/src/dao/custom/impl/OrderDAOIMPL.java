package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Listener.MyListener.ds;

public class OrderDAOIMPL implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allorders = new ArrayList<>();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Item");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allorders.add(new Order(resultSet.getString(1), resultSet.getDate(2),resultSet.getString(3)));
            }
        }
        return allorders;
    }

    @Override
    public boolean add(Order o) throws SQLException, ClassNotFoundException {
        try(Connection connection = ds.getConnection()){
            String sql = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,o.getOrderID());
            pstm.setDate(2, o.getOrderDate());
            pstm.setString(3,o.getCusID());
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String string) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
//       try(Connection connection = ds.getConnection()) {
//           ResultSet rst = SqlUtil.execute("SELECT * FROM orders where id=?",string);
//           if (rst.next()){
//               return new Order(rst.getString(1), rst.getDate(2), rst.getString(3));
//
//           }
//       }
//       return  null;

        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getString(1),
                        rs.getDate(2),
                        rs.getString(3)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
