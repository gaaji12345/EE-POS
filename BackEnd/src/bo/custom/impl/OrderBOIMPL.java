package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DaoFacTory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.OrderDAOIMPL;
import dao.custom.impl.OrderDeatilDAO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Custom;
import entity.Item;
import entity.Order;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static Listener.MyListener.ds;

public class OrderBOIMPL implements OrderBO {

    ItemDAO itemDAO= (ItemDAO) DaoFacTory.getDaoFactory().getDao(DaoFacTory.DAOTypes.ITEM);

    OrderDAO order=new OrderDAOIMPL();



    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);

            String insertOrderSql = "INSERT INTO orders(id, date, customerId) VALUES(?, ?, ?)";
            try (PreparedStatement pstm = con.prepareStatement(insertOrderSql)) {
                pstm.setString(1, dto.getOrderID());
                pstm.setDate(2, Date.valueOf(dto.getOrderDate().toLocalDate()));
                pstm.setString(3, dto.getCusID());

                if (pstm.executeUpdate() <= 0) {
                    System.out.println("Order not added blog");
                    con.rollback();
                    return false;
                }
            }

            String insertOrderDetailsSql = "INSERT INTO order_detail(orderId, itemCode, qty, unitPrice) VALUES(?, ?, ?, ?)";
            String updateItemSql = "UPDATE item SET description = ?, unitPrice = ?,qtyOnHand = ? WHERE code = ?";

            for (OrderDetailDTO odDTO : dto.getOrderDetails()) {
                System.out.println("OrderDetailsDTO loop blog");

                try (PreparedStatement pstm2 = con.prepareStatement(insertOrderDetailsSql)) {
                    pstm2.setString(1, odDTO.getOid());
                    pstm2.setString(2, odDTO.getItmCode());
                    pstm2.setInt(3, odDTO.getItmQTY());
                    pstm2.setDouble(4, odDTO.getItmPrice());

                    if (pstm2.executeUpdate() <= 0) {
                        System.out.println("OrderDetailsDTO loop detailAdded blog");
                        con.rollback();
                        return false;
                    }

                    System.out.println("item update start blog");
                    Item item = (Item) itemDAO.search(odDTO.getItmCode());

                    if (item != null) {
                        int current = item.getQtyOnHand();
                        int ordered = odDTO.getItmQTY();
                        item.setQtyOnHand(current - ordered);
                    } else {
                        System.out.println("Item not found");
                        con.rollback();
                        return false;
                    }

                    System.out.println("item convert blog");

                    try (PreparedStatement pstm3 = con.prepareStatement(updateItemSql)) {
                        pstm3.setString(1, item.getDescription());
                        pstm3.setDouble(2, item.getUnitPrice());
                        pstm3.setInt(3, item.getQtyOnHand());
                        pstm3.setString(4, odDTO.getItmCode());

                        if (pstm3.executeUpdate() <= 0) {
                            System.out.println("item added  blog");
                            con.rollback();
                            return false;
                        }

                        System.out.println("item update");
                    }
                }
            }
            con.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQL Error");
            return false;
        }
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<Order> all = order.getAll();
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order o : all) {
            OrderDTO orderDTO = new OrderDTO(o.getOrderID(), o.getOrderDate(), o.getCusID());
            orderDTOS.add(orderDTO);
        }

        return orderDTOS;
    }

    @Override
    public OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException {
       Order order1=order.search(id);
       return new OrderDTO(order1.getOrderID(),order1.getOrderDate(),order1.getCusID());

    }
}
