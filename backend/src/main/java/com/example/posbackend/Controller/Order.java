package com.example.posbackend.Controller;

import com.example.posbackend.Bo.OrderBo;
import com.example.posbackend.Bo.impl.OrderBoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.OrderDto;
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

@WebServlet(urlPatterns = "/order")
public class Order extends HttpServlet {
    private OrderBo orderBo = new OrderBoImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

    }
}
