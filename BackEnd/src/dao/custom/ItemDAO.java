package dao.custom;

import dao.CrudDAO;
import entity.Item;

public interface ItemDAO  extends CrudDAO<Item,String> {

    public Boolean updateQty(String id,int qty) throws Exception;
}
