package servlets;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Item do get");
        try {
            resp.addHeader("ABCD","GAAJI");
            String option = req.getParameter("option");
            PrintWriter writer = resp.getWriter();


            Connection connection = ds.getConnection();

            resp.addHeader("Access-Control-Allow-Origin","*");


            switch (option){
                case "SEARCH":
                    break;
                case "GETALL":
                    ResultSet rst = connection.prepareStatement("select * from item").executeQuery();
                    resp.addHeader ("Content-Type", "application/json");

                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()) {
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        double price  = rst.getDouble(3);
                        int qty = rst.getInt(4);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();//creat json object 1
                        objectBuilder.add("id", id ); // Handle null ID
                        objectBuilder.add("name", name ); // Handle null name
                        objectBuilder.add("price", price ); // Handle null address
                        objectBuilder.add("qty", qty );
                        arrayBuilder.add(objectBuilder.build());

                    }
                    //  resp.getWriter().print(arrayBuilder.build());
                    JsonObjectBuilder responce = Json.createObjectBuilder();
                    responce.add("status",200);
                    responce.add("message","Done");
                    responce.add("data",arrayBuilder.build());
                    writer.print(responce.build());
                    break;

            }
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=  req.getParameter("itemCode");
        String name=  req.getParameter("itemDesc");
        String prce=   req.getParameter("itemPrice");
        String qty=   req.getParameter("itemQty");

        System.out.println(code+" "+ name+ " "+prce+" " +qty);
        PrintWriter writer = resp.getWriter();
        resp.setContentType ( "application/json");

        resp.addHeader("Access-Control-Allow-Origin","*");

        try {

            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into item values (?,?,?,?)");
            pstm.setObject(1, code);
            pstm.setObject(2, name);
            pstm.setObject(3, prce);
            pstm.setObject(4, qty);
            boolean b = pstm.executeUpdate() > 0;

//

            if (b) {
                //resp.setStatus(200);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//browser is read
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Added");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());

            }
            connection.close();


        } catch (SQLException throwables) {
            resp.setStatus(200);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",400);
            objectBuilder.add("messages","Error");
            objectBuilder.add("data",throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
            throwables.printStackTrace();
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
        String price = jsonObject.getString("price");
        String qty = jsonObject.getString("qty" );


        System.out.println(code+" "+desc+" "+price+" "+qty);


        try {

            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("update item set   description =?,unitPrice=?,qtyOnHand =?  where code=? ");

            pstm.setObject(1, desc);
            pstm.setObject(2, price);
            pstm.setObject(3, qty);
            pstm.setObject(4, code);
            boolean b = pstm.executeUpdate() > 0;

            if (b) {
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
            connection.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (JsonParsingException e){

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
        try {

            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from item where code=?");
            pstm.setObject(1, code);
            boolean b = pstm.executeUpdate() > 0;


            if (b) {
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
            connection.close();

        } catch (SQLException throwables) {
            resp.setStatus(200);
//            throw new RuntimeException(e);
            throwables.printStackTrace();
            //  resp.sendError(500,throwables.getMessage());JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",500);
            objectBuilder.add("data",throwables.getLocalizedMessage());
            objectBuilder.add("message","Error");

            writer.print(objectBuilder.build());
        }
    }
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");//
        resp.addHeader("Access-Control-Allow-Methods","DELETE, PUT");
        resp.addHeader("Access-Control-Allow-Headers","Content-Type");
    }
}
