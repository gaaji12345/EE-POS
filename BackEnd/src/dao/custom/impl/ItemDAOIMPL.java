package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Listener.MyListener.ds;

public class ItemDAOIMPL implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Item");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allItems.add(new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4)));
            }
        }
        return allItems;
    }

    @Override
    public boolean add(Item i) throws SQLException, ClassNotFoundException {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            pstm.setString(1, i.getCode());
            pstm.setString(2, i.getDescription());
            pstm.setDouble(3, i.getUnitPrice());
            pstm.setInt(4, i.getQtyOnHand());

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Item i) throws SQLException, ClassNotFoundException {
        try (Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
            pstm.setString(1, i.getDescription());
            pstm.setDouble(2, i.getUnitPrice());
            pstm.setInt(3, i.getQtyOnHand());
            pstm.setString(4, i.getCode());

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        try (Connection connection = ds.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from item where code=?");
            pstm.setObject(1, code);

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
    public Item search(String string) throws SQLException, ClassNotFoundException {
        return null;
    }
}
