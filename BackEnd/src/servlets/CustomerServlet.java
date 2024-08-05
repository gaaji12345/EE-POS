package servlets;

import bo.BOFActory;
import bo.custom.CustomerBo;
import bo.custom.impl.CustomerBoIMPL;
import dto.CustomerDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static Listener.MyListener.ds;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {


    CustomerBo customerBo= (CustomerBo) BOFActory.getBoFactory().getBO(BOFActory.BOTypes.CUSTOMER);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("customer do get");

    //    resp.addHeader("Access-Control-Allow-Origin", "*");//

        String customerId = req.getParameter("customerId");
        if (customerId != null) {
            getAll(customerId, resp);
            return;
        }
        try {
            List<CustomerDTO> allCustomers = customerBo.getAllCustomers();
            JsonArrayBuilder allCustomersArray = Json.createArrayBuilder();

            for (CustomerDTO customer : allCustomers) {
                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("id", customer.getId())
                        .add("name", customer.getName())
                        .add("address", customer.getAddress())
                        .add("salary", customer.getSalary());
                allCustomersArray.add(customerObject);
            }
            PrintWriter writer = resp.getWriter();
            writer.print(allCustomersArray.build());
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error in doGet method", e);
        }






    }




    private void getAll(String customerId, HttpServletResponse response) {
      //  response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        try (Connection connection = ds.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, customerId);
            ResultSet rst = pstm.executeQuery();

            PrintWriter writer = response.getWriter();

            JsonArrayBuilder customerArray = Json.createArrayBuilder();

            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name)
                        .add("address", address)
                        .add("salary", salary);

                customerArray.add(customerObject);
            }
            writer.print(customerArray.build());
        } catch ( SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }






    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id=  req.getParameter("customerId");
//        String name=  req.getParameter("customerName");
//        String add=   req.getParameter("customerAddress");
//        String salary=   req.getParameter("customerSalary");
//
//        System.out.println(id+" "+ name+ " "+add+" " +salary);
        PrintWriter writer = resp.getWriter();
    resp.setContentType ( "application/json");

    //  resp.addHeader("Access-Control-Allow-Origin","*");
//
//        try {
//
//            Connection connection = ds.getConnection();
//            PreparedStatement pstm = connection.prepareStatement("insert into customer values (?,?,?,?)");
//            pstm.setObject(1, id);
//            pstm.setObject(2, name);
//            pstm.setObject(3, add);
//            pstm.setObject(4, salary);
//            boolean b = pstm.executeUpdate() > 0;
//
////
//
//            if (b) {
//                //resp.setStatus(200);
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//                resp.setStatus(HttpServletResponse.SC_CREATED);//browser is read
//                objectBuilder.add("status", 200);
//                objectBuilder.add("message", "Successfully Added");
//                objectBuilder.add("data", "");
//                writer.print(objectBuilder.build());
//
//            }
//            connection.close();
//
//
//        } catch (SQLException throwables) {
//            resp.setStatus(200);
//
//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            objectBuilder.add("status",400);
//            objectBuilder.add("messages","Error");
//            objectBuilder.add("data",throwables.getLocalizedMessage());
//            writer.print(objectBuilder.build());
//            throwables.printStackTrace();
//        }


     //   resp.setContentType("text/plain")

                String id=  req.getParameter("customerId");
        String name=  req.getParameter("customerName");
        String add=   req.getParameter("customerAddress");
        double salary= Double.parseDouble(req.getParameter("customerSalary"));

        boolean isSaved ;
        try {
            isSaved = customerBo.addCustomer(new CustomerDTO(id,name,add,salary));

            if (isSaved){
                                //resp.setStatus(200);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//browser is read
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Added");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
        } catch (SQLException e) {
            resp.setStatus(200);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",400);
            objectBuilder.add("messages","Error");
            objectBuilder.add("data",e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("update me");

        PrintWriter writer = resp.getWriter();
        resp.setContentType ( "application/json");

      //  resp.addHeader("Access-Control-Allow-Origin","*");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        double salary = Double.parseDouble(jsonObject.getString("salary" ));



        System.out.println(id+" "+name+" "+address+" "+salary);


        try {

//            Connection connection = ds.getConnection();
//            PreparedStatement pstm = connection.prepareStatement("update customer set  name=?,address=?,salary=?  where id=? ");
//
//            pstm.setObject(1, name);
//            pstm.setObject(2, address);
//            pstm.setObject(3, salary);
//            pstm.setObject(4, id);
//            boolean b = pstm.executeUpdate() > 0;

            boolean isUpdated = customerBo.updateCustomer(new CustomerDTO(id, name, address,salary));

            if (isUpdated) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Succefully Updated");
                objectBuilder.add("status", 200);

                writer.print(objectBuilder.build());

            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("status", 400);
                writer.print(objectBuilder.build());
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (JsonParsingException e){

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("cus delete");
//        String id=req.getParameter("cusID");
//        System.out.println(id);
        PrintWriter writer = resp.getWriter();
        resp.setContentType ( "application/json");
//
     // resp.addHeader("Access-Control-Allow-Origin","*");
//        try {
//
//            Connection connection = ds.getConnection();
//            PreparedStatement pstm = connection.prepareStatement("delete from customer where id=?");
//            pstm.setObject(1, id);
//            boolean b = pstm.executeUpdate() > 0;
//
//
//            if (b) {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//                objectBuilder.add("data", "");
//                objectBuilder.add("message", "Succefully deletd");
//                objectBuilder.add("status", 200);
//
//                writer.print(objectBuilder.build());
//
//            } else {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//                objectBuilder.add("status", 400);
//                objectBuilder.add("data", "Wrong data");
//                objectBuilder.add("message", "");
//
//
//                writer.print(objectBuilder.build());
//            }
//            connection.close();
//
//        } catch (SQLException throwables) {
//            resp.setStatus(200);
//
//            throwables.printStackTrace();
//            //  resp.sendError(500,throwables.getMessage());JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            objectBuilder.add("status",500);
//            objectBuilder.add("data",throwables.getLocalizedMessage());
//            objectBuilder.add("message","Error");
//
//            writer.print(objectBuilder.build());
//        }

        String cusID = req.getParameter("cusID");

        boolean isDeleted ;
        try {
            isDeleted = customerBo.deleteCustomer(cusID);


            if (isDeleted) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Succefully deletd");
                objectBuilder.add("status", 200);

                writer.print(objectBuilder.build());

            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong data");
                objectBuilder.add("message", "");


                writer.print(objectBuilder.build());
            }
        } catch (SQLException e) {
                        resp.setStatus(200);

            e.printStackTrace();
            //  resp.sendError(500,throwables.getMessage());JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",500);
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Error");

            writer.print(objectBuilder.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


//    @Override
//    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin","*");//
//        resp.addHeader("Access-Control-Allow-Methods","DELETE, PUT");
//        resp.addHeader("Access-Control-Allow-Headers","Content-Type");
//    }
}
