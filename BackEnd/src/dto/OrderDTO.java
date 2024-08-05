package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private String orderID;
    private Date orderDate;
    private String cusID;
    List<OrderDetailDTO> orderDetails;


    public OrderDTO(String oid, Date date, String cusID) {
        this.orderID = oid;
        this.orderDate = date;
        this.cusID = cusID;
    }

}
