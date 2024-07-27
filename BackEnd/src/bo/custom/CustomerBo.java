package bo.custom;

import dto.CustomerDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo {
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

}
