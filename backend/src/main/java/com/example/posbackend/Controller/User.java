package com.example.posbackend.Controller;

import com.example.posbackend.Bo.UserBo;
import com.example.posbackend.Bo.impl.UserBoImpl;
import com.example.posbackend.Dto.UserDto;
import jakarta.json.JsonException;
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

@WebServlet(urlPatterns = "/user")
public class User extends HttpServlet {
    private final UserBo userBo = new UserBoImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            List<UserDto> users = userBo.getAllUsers();
            Jsonb jsonb = JsonbBuilder.create();
            String jsonResponse = jsonb.toJson(users);
            resp.setContentType("application/json");
            writer.write(jsonResponse);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching users.");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            // Deserialization
            Jsonb jsonb = JsonbBuilder.create();
            UserDto userDTO = jsonb.fromJson(req.getReader(), UserDto.class);
            System.out.println(userDTO);

            // Save User
            if (userBo.saveUser(userDTO)) {
                writer.write("User Saved Successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Saving user failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException e) {
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
