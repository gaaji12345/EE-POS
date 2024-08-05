package bo.custom;

import bo.SuperBo;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBo  extends SuperBo {

    ArrayList<OrderDetailDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException ;
}
