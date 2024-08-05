package servlets;

import bo.BOFActory;
import bo.custom.CustomerBo;
import bo.custom.OrderBO;
import bo.custom.impl.OrderBOIMPL;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Listener.MyListener.ds;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

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

    public OrderServlet() throws NamingException {
    }

    OrderBO orderBO=(OrderBO) BOFActory.getBoFactory().getBO(BOFActory.BOTypes.ORDER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader ("Content-Type", "application/json");

       // String line = req.getParameter("line");

        PrintWriter writer ;

        String customerId = req.getParameter("itemCode");
        if (customerId != null) {
            getAll(customerId, resp);
            return;
        }
        try {
            List<OrderDTO> allorder = orderBO.getAllOrder();
            JsonArrayBuilder allOrderArray = Json.createArrayBuilder();

            for (OrderDTO o : allorder) {
                JsonObjectBuilder orderObject = Json.createObjectBuilder()
                        .add("id", o.getOrderID())
                        .add("date", (JsonValue) o.getOrderDate())
                        .add("cusId", o.getCusID());

                allOrderArray.add(orderObject);
            }
            writer = resp.getWriter();
            writer.print(allOrderArray.build());
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error in doGet method", e);
        }



    }


    private void getAll(String oId, HttpServletResponse response) {
       // response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        try (Connection connection = ds.getConnection()) {
            String sql = "SELECT * FROM orders WHERE id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, oId);
            ResultSet rst = pstm.executeQuery();

            PrintWriter writer = response.getWriter();

            JsonArrayBuilder customerArray = Json.createArrayBuilder();

            while (rst.next()) {
                String id = rst.getString(1);
                Date date = rst.getDate(2);
                String cusId = rst.getString(3);

                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("id", id)
                        .add("date", (JsonValue) date)
                        .add("cusId", cusId);

                customerArray.add(customerObject);
            }
            writer.print(customerArray.build());
        } catch ( SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void search(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
       // resp.addHeader("Access-Control-Allow-Origin","*");//
        String id = req.getParameter("oid");
        System.out.println(id);
        Jsonb jsonb = JsonbBuilder.create();
        try {
            OrderDTO dto = orderBO.searchOrder(id);
            if(dto != null){
                jsonb.toJson(dto,resp.getWriter());
                resp.setStatus(HttpServletResponse.SC_OK);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        System.out.println("post method log");

     //   resp.addHeader("Access-Control-Allow-Origin","*");//
        Jsonb jsonb = JsonbBuilder.create();
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
        String orderId = orderDTO.getOrderID();
        String cusId = orderDTO.getCusID();
        List<OrderDetailDTO> dto = orderDTO.getOrderDetails();

        for (OrderDetailDTO list : dto) {
            String code = list.getItmCode();
            String price = String.valueOf(list.getItmPrice());
            String qty = String.valueOf(list.getItmQTY());

        }
        try {

            boolean isSaved = orderBO.saveOrder(orderDTO);
            if (isSaved){
                System.out.println("Order added");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                System.out.println("Order not added");
            }
        } catch (SQLException throwables) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }


//    @Override
//    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
//       resp.addHeader("Access-Control-Allow-Credentials", "true");
//        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//    }

}


