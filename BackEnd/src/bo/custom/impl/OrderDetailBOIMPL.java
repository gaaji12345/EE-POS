package bo.custom.impl;

import bo.custom.OrderBO;
import bo.custom.OrderDetailBo;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.OrderDAOIMPL;
import dao.custom.impl.OrderDeatilDAO;
import dto.OrderDetailDTO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOIMPL implements OrderDetailBo {

  OrderDetailDAO orderDetailDAO=new OrderDeatilDAO();
    @Override
    public ArrayList<OrderDetailDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> all = orderDetailDAO.getAll();
        ArrayList<OrderDetailDTO> orderAr = new ArrayList<>();

        for (OrderDetail o : all) {
            OrderDetailDTO dto = new OrderDetailDTO(o.getOrderID(),o.getItemCode(),o.getQuantity(),o.getItemPrice());
            orderAr.add(dto);
        }
        return orderAr;
    }
    }

