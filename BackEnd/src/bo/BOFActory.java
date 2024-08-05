package bo;/*  gaajiCode
    99
    05/08/2024
    */

import bo.custom.impl.CustomerBoIMPL;
import bo.custom.impl.ItemBOIMPL;
import bo.custom.impl.OrderBOIMPL;
import bo.custom.impl.OrderDetailBOIMPL;

public class BOFActory {

    public static BOFActory boFactory;
    public BOFActory() {
    }

    public static BOFActory  getBoFactory(){
        if (boFactory==null){
            boFactory=new BOFActory ();
        }
        return boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }

    public static SuperBo getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBoIMPL();
            case ITEM:
                return new ItemBOIMPL();
            case ORDER:
                return new OrderBOIMPL();
            case ORDER_DETAIL:
                return new OrderDetailBOIMPL();
            default:
                return null;
        }
    }


}
