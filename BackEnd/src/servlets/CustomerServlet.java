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
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
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

    public CustomerServlet() throws NamingException {
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("customer do get");
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
                    ResultSet rst = connection.prepareStatement("select * from customer").executeQuery();
                    resp.addHeader ("Content-Type", "application/json");

                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()) {
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        double salary = rst.getDouble(4);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();//creat json object 1
                        objectBuilder.add("id", id ); // Handle null ID
                        objectBuilder.add("name", name ); // Handle null name
                        objectBuilder.add("address", address ); // Handle null address
                        objectBuilder.add("salary", salary );
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
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");//
    }
}
