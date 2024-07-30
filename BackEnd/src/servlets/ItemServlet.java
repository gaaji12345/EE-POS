package servlets;

import bo.custom.ItemBO;
import bo.custom.impl.ItemBOIMPL;
import dto.CustomerDTO;
import dto.ItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Listener.MyListener.ds;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    InitialContext ctx;

    {
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    DataSource ds = (DataSource) ctx.lookup
            ("java:comp/env/jdbc/pool");

    public ItemServlet() throws NamingException {
    }

    ItemBO itemBO=new ItemBOIMPL();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("Item do get");
//        try {
//            resp.addHeader("ABCD","GAAJI");
//            String option = req.getParameter("option");
           PrintWriter writer = resp.getWriter();
//
//
//            Connection connection = ds.getConnection();
//
           resp.addHeader("Access-Control-Allow-Origin","*");
//
//
//          //  switch (option){
//               // case "SEARCH":
//
//                   // break;
//             //   case "GETALL":
//                    ResultSet rst = connection.prepareStatement("select * from item").executeQuery();
                   resp.addHeader ("Content-Type", "application/json");
//
                  JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//                    while (rst.next()) {
//                        String code = rst.getString(1);
//                        String name = rst.getString(2);
//                       // double price  = rst.getDouble(3);
//                        String price= rst.getString(3);
//                        String  qty = rst.getString(4);
//
//                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();//creat json object 1
//                        objectBuilder.add("code", code  ); // Handle null ID
//                        objectBuilder.add("name", name ); // Handle null name
//                        objectBuilder.add("price", price ); // Handle null address
//                        objectBuilder.add("qty", qty );
//                        arrayBuilder.add(objectBuilder.build());
//
//                    }
//                    //  resp.getWriter().print(arrayBuilder.build());
//                    JsonObjectBuilder responce = Json.createObjectBuilder();
//                    responce.add("status",200);
//                    responce.add("message","Done");
//                    responce.add("data",arrayBuilder.build());
//                    writer.print(responce.build());
//                 //   break;
//
//            //}
//           connection.close();
//
//        } catch (Exception e  ) {
//            e.printStackTrace();
//        }

        String customerId = req.getParameter("itemCode");
        if (customerId != null) {
            getAll(customerId, resp);
            return;
        }
        try {
            List<ItemDTO> allItem = itemBO.getAllItems();
            JsonArrayBuilder allCustomersArray = Json.createArrayBuilder();

            for (ItemDTO i : allItem) {
                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("code", i.getCode())
                        .add("name", i.getDescription())
                        .add("price", i.getUnitPrice())
                        .add("qty", i.getQtyOnHand());
                allCustomersArray.add(customerObject);
            }
             writer = resp.getWriter();
            writer.print(allCustomersArray.build());
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error in doGet method", e);
        }


    }


    private void getAll(String itemCode, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        try (Connection connection = ds.getConnection()) {
            String sql = "SELECT * FROM item WHERE code=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, itemCode);
            ResultSet rst = pstm.executeQuery();

            PrintWriter writer = response.getWriter();

            JsonArrayBuilder itemArray = Json.createArrayBuilder();

            while (rst.next()) {
                String code = rst.getString(1);
                String description = rst.getString(2);
                double price = Double.parseDouble(rst.getString(3));
                int qty = (int) rst.getDouble(4);

                JsonObjectBuilder itemObject = Json.createObjectBuilder()
                        .add("code", code)
                        .add("description", description)
                        .add("price", price)
                        .add("qty", qty);

                itemArray.add(itemObject);
            }
            writer.print(itemArray.build());
        } catch ( SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=  req.getParameter("itemCode");
        String name=  req.getParameter("itemDesc");
        double price= Double.parseDouble(req.getParameter("itemPrice"));

      int   qty= Integer.parseInt(req.getParameter("itemQty"));
//
//        System.out.println(code+" "+ name+ " "+prce+" " +qty);
        PrintWriter writer = resp.getWriter();
        resp.setContentType ( "application/json");
//
       resp.addHeader("Access-Control-Allow-Origin","*");
//
//        try {
//
//            Connection connection = ds.getConnection();
//            PreparedStatement pstm = connection.prepareStatement("insert into item values (?,?,?,?)");
//            pstm.setObject(1, code);
//            pstm.setObject(2, name);
//            pstm.setObject(3, prce);
//            pstm.setObject(4, qty);
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


        boolean isSaved ;
        try {
            isSaved = itemBO.saveItem(new ItemDTO(code,name,price,qty));

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

        //resp.getWriter();
        PrintWriter writer = resp.getWriter();
        resp.setContentType ( "application/json");

        resp.addHeader("Access-Control-Allow-Origin","*");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String code = jsonObject.getString("code");
        String desc = jsonObject.getString("desc");
        double price = Double.parseDouble(jsonObject.getString("price"));
        int qty = Integer.parseInt(jsonObject.getString("qty" ));


        System.out.println(code+" "+desc+" "+price+" "+qty);


//        try {
//
//            Connection connection = ds.getConnection();
//            PreparedStatement pstm = connection.prepareStatement("update item set   description =?,unitPrice=?,qtyOnHand =?  where code=? ");
//
//            pstm.setObject(1, desc);
//            pstm.setObject(2, price);
//            pstm.setObject(3, qty);
//            pstm.setObject(4, code);
//            boolean b = pstm.executeUpdate() > 0;
//
//            if (b) {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//                objectBuilder.add("data", "");
//                objectBuilder.add("message", "Succefully Updated");
//                objectBuilder.add("status", 200);
//
//                writer.print(objectBuilder.build());
//
//            } else {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//                objectBuilder.add("data", "");
//                objectBuilder.add("message", "Update Failed");
//                objectBuilder.add("status", 400);
//                writer.print(objectBuilder.build());
//            }
//            connection.close();
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }catch (JsonParsingException e){
//
//        }

        try {

        boolean isUpdateditem = itemBO.updateItem(new ItemDTO(code, desc, price,qty));

        if (isUpdateditem) {
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
        System.out.println("item delete");
        String code=req.getParameter("itemCode");
        System.out.println(code);
        PrintWriter writer = resp.getWriter();
        resp.setContentType ( "application/json");

        resp.addHeader("Access-Control-Allow-Origin","*");
//        try {
//
//            Connection connection = ds.getConnection();
//            PreparedStatement pstm = connection.prepareStatement("delete from item where code=?");
//            pstm.setObject(1, code);
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
////            throw new RuntimeException(e);
//            throwables.printStackTrace();
//            //  resp.sendError(500,throwables.getMessage());JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            objectBuilder.add("status",500);
//            objectBuilder.add("data",throwables.getLocalizedMessage());
//            objectBuilder.add("message","Error");
//
//            writer.print(objectBuilder.build());
//        }

        boolean isDeleted ;
        try {
            isDeleted = itemBO.deleteItem(code);


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
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");//
        resp.addHeader("Access-Control-Allow-Methods","DELETE, PUT");
        resp.addHeader("Access-Control-Allow-Headers","Content-Type");
    }
}
