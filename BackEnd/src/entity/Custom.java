package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Custom {
    private String id;
    private String name;
    private String address;

    // item
    private String code;
    private String itemName;
    private int qty;
    private double unitPrice;

    // order details
    private String orderDetailsOrderID;
    private String itemID;
    private int orderItemQty;

    // orders
    private String orderID;
    private String date;
    private String cusID;
    private int discount;
    private double total;
}
