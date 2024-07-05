package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");//
        resp.addHeader("Access-Control-Allow-Methods","DELETE, PUT");
        resp.addHeader("Access-Control-Allow-Headers","Content-Type");
    }
}
