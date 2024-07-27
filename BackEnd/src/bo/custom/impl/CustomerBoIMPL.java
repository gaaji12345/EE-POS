package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomDAOIMPL;
import dto.CustomerDTO;
import entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoIMPL implements CustomerBo {


    CustomerDAO customerDAO = new CustomDAOIMPL();

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getId(), c.getName(), c.getAddress(), c.getSalary()));
        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }
}
