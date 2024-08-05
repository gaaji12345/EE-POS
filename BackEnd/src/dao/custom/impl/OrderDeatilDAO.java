package dao.custom.impl;

import bo.custom.OrderDetailBo;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.util.SqlUtil;
import dto.OrderDetailDTO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDeatilDAO implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return new SqlUtil().execute(
                "INSERT INTO orderDetails (oid, itmCode, itmQTY, itmPrice) VALUES (?, ?, ?, ?)",
                dto.getOrderID(), dto.getItemCode(), dto.getQuantity(), dto.getItemPrice()
        );

    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String string) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String string) throws SQLException, ClassNotFoundException {
        return null;
    }
}
