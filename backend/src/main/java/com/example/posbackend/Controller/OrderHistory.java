package com.example.posbackend.Controller;

import com.example.posbackend.Bo.OrderBo;
import com.example.posbackend.Bo.impl.OrderBoImpl;
import com.example.posbackend.Tm.OrderTm;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orderHistory")
public class OrderHistory extends HttpServlet {
    private OrderBo orderBo = new OrderBoImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("id");
        var writer = resp.getWriter();
        if (customerId!=null){
            Jsonb jsonb = JsonbBuilder.create();
            List<OrderTm> orderTm = null;
            try {
                orderTm = orderBo.getOrderByCustomer(customerId);
                System.out.println(orderTm);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
            if (orderTm != null) {
                String jsonResponse = jsonb.toJson(orderTm);
                writer.write(jsonResponse);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writer.write("{\"message\":\"Customer not found\"}");
            }

        }else {
            try {

            String lastOrderId = orderBo.getLastOrderId();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"lastOrderId\":\"" + lastOrderId + "\"}");
            } catch (SQLException | NamingException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Unable to retrieve last order ID\"}");
            }
        }


    }





}
