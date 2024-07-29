package bo.custom.impl;

import bo.custom.ItemBO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOIMPL;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOIMPL  implements ItemBO {

    ItemDAO itemDAO=new ItemDAOIMPL();

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item i : all) {
            ItemDTO itemDTO = new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand());
            itemDTOS.add(itemDTO);
        }

        return itemDTOS;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
