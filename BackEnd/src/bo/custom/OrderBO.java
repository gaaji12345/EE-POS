package bo.custom;

import bo.SuperBo;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO  extends SuperBo {

    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;


    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException ;

    OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException;



}
