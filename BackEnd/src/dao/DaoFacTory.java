package dao;

import dao.custom.impl.CustomDAOIMPL;
import dao.custom.impl.ItemDAOIMPL;

public class DaoFacTory {
    public static DaoFacTory daoFactory;

    public DaoFacTory() {
    }

    public static DaoFacTory getDaoFactory(){
        if (daoFactory==null){
            daoFactory=new DaoFacTory ();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,PLACEORDER
    }
    public SuperDAO getDao(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomDAOIMPL();
            case ITEM:
                return new ItemDAOIMPL();
            default:
                return null;
        }
    }
}
