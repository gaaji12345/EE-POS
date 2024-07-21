package dao.custom;

import dao.CrudDAO;
import entity.Customer;

public interface CustomerDAO<C, S> extends CrudDAO<Customer,String> {

}
