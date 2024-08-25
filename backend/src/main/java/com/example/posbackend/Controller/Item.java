package com.example.posbackend.Controller;

import com.example.posbackend.Bo.ItemBo;
import com.example.posbackend.Bo.impl.ItemBoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.ItemDto;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/item")
@MultipartConfig
public class Item extends HttpServlet {
    private ItemBo itemBo = new ItemBoImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemName = req.getParameter("name");
        System.out.println(itemName);
        resp.setContentType("application/json");
        try (var writer = resp.getWriter()) {

            Jsonb jsonb = JsonbBuilder.create();

            if (itemName != null ) {
                // Case 1: Specific Customer Search
                ItemDto  itemDto = itemBo.findCustomerById(itemName);



                if (itemDto != null) {
                    String jsonResponse = jsonb.toJson(itemDto);
                    writer.write(jsonResponse);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer.write("{\"message\":\"Customer not found\"}");
                }
            } else {

                // Case 2: Retrieve All Customers
                List<ItemDto> items = itemBo.getAllCustomers();

                String jsonResponse = jsonb.toJson(items);
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
        resp.setContentType("application/json");

        // Initialize JSONB for handling JSON data
        Jsonb jsonb = JsonbBuilder.create();
        try (var writer = resp.getWriter()) {
            // Retrieve the JSON string from the form data
            String itemDataJson = req.getParameter("itemData");
           // System.out.println(itemDataJson);
            // Deserialize JSON data from request
            ItemDto itemDto = jsonb.fromJson(itemDataJson, ItemDto.class);
           // System.out.println(itemDto);

            // Handle file upload
            Part filePart = req.getPart("imageFile"); // Retrieves the file part
            String fileName = null;
            if (filePart != null && filePart.getSize() > 0) {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Gets the file name

                // Define the absolute path where the image will be saved
                String uploadDir = "/home/pavithrani/IdeaProjects/POS-frontend/assets/images";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) uploadDirFile.mkdirs();

                // Save the file to the specified directory
                filePart.write(uploadDir + File.separator + fileName);
            }

            // Save item  to the database
            if (itemBo.saveItem(itemDto)) {
                System.out.println("ok");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("{\"message\":\"Item saved successfully\"}");
            } else {
                System.out.println("failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("{\"message\":\"Failed to save item\"}");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while saving the item.");
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {

            Jsonb jsonb = JsonbBuilder.create();
            ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);

            System.out.println(itemDto);

            boolean isUpdated = itemBo.updateItem(itemDto);
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
        int itemId = Integer.parseInt(req.getParameter("id"));

        try (var writer = resp.getWriter()) {

            if (itemBo.deleteCustomer(itemId)) {
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
