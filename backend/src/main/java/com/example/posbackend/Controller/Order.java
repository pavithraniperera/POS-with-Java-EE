package com.example.posbackend.Controller;

import com.example.posbackend.Bo.OrderBo;
import com.example.posbackend.Bo.impl.OrderBoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.OrderDto;
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

@WebServlet(urlPatterns = "/order")
public class Order extends HttpServlet {
    private OrderBo orderBo = new OrderBoImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("order");
        String orderId = req.getParameter("id");
       // System.out.println(orderId);
        resp.setContentType("application/json");
        try (var writer = resp.getWriter()) {

            Jsonb jsonb = JsonbBuilder.create();

            if (orderId != null ) {
                // Case 1: Specific Customer Search
                OrderDto orderDto = orderBo.findOrderById(orderId);
                System.out.println("orderDto"+orderDto);

                if (orderDto != null) {
                    String jsonResponse = jsonb.toJson(orderDto);
                    writer.write(jsonResponse);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer.write("{\"message\":\"Customer not found\"}");
                }
            } else {
                System.out.println("come");
                // Case 2: Retrieve All Customers
                List<OrderTm> orders = orderBo.getAllOrders();
                System.out.println("object: "+orders);
                String jsonResponse = jsonb.toJson(orders);
                writer.write(jsonResponse);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType()==null){
            System.out.println("failed");
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDto orderDto = jsonb.fromJson(req.getReader(), OrderDto.class);

            boolean isSaved =orderBo.save(orderDto);
            if (isSaved) {
                writer.write("Customer Saved Successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Failed to Save Customer");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var orderId = req.getParameter("id");
        try (var writer = resp.getWriter()) {

            if (orderBo.deleteOrder(orderId)) {
                System.out.println("Deleted");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete Failed");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }

    }
}
