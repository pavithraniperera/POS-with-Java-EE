package com.example.posbackend.Controller;

import com.example.posbackend.Bo.CustomerBo;
import com.example.posbackend.Bo.impl.CustomerBoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.UserDto;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
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

@WebServlet(urlPatterns = "/customer")
public class Customer extends HttpServlet {
    private CustomerBo customerBO = new CustomerBoImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("id");
        System.out.println(customerId);
        resp.setContentType("application/json");
        try (var writer = resp.getWriter()) {

            Jsonb jsonb = JsonbBuilder.create();

            if (customerId != null ) {
                // Case 1: Specific Customer Search
                CustomerDto customer = customerBO.findCustomerById(customerId);


                if (customer != null) {
                    String jsonResponse = jsonb.toJson(customer);
                    writer.write(jsonResponse);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer.write("{\"message\":\"Customer not found\"}");
                }
            } else {
                // Case 2: Retrieve All Customers
                List<CustomerDto> customers = customerBO.getAllCustomers();
                String jsonResponse = jsonb.toJson(customers);
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
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto customerDto = jsonb.fromJson(req.getReader(), CustomerDto.class);

            boolean isSaved =customerBO.save(customerDto);
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
        try (var writer = resp.getWriter()) {

            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto customerDTO = jsonb.fromJson(req.getReader(), CustomerDto.class);


            boolean isUpdated = customerBO.updateCustomer(customerDTO);
            if (isUpdated) {
                writer.write("Customer Updated Successfully");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                writer.write("Failed to Update Customer");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (JsonbException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Extract the customer ID from the request
        var customerId = req.getParameter("id");
        try (var writer = resp.getWriter()) {

            if (customerBO.deleteCustomer(customerId)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete Failed");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }

    }
}
