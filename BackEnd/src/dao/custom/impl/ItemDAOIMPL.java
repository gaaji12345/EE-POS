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
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String string) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Item search(String string) throws SQLException, ClassNotFoundException {
        return null;
    }
}
