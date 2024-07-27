package dao.custom.impl;

import dao.custom.CustomerDAO;
import dao.custom.impl.util.SqlUtil;
import entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Listener.MyListener.ds;

public class CustomDAOIMPL implements CustomerDAO {


    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM customer");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
            }
        }
        return allCustomers;
    }

    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            pstm.setString(1, c.getId());
            pstm.setString(2, c.getName());
            pstm.setString(3, c.getAddress());
            pstm.setDouble(4, c.getSalary());

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        try (Connection connection = ds.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from customer where id=?");
            pstm.setObject(1, id);

            if (pstm.executeUpdate() > 0) {
                System.out.println("Deleted");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
