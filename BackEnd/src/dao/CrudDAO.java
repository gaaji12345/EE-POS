package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO {


    ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;

}
