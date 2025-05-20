package com.servletdb0905.servlets;

import com.servletdb0905.dao.EmployeeDAO;
import com.servletdb0905.model.Employee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListEmployeeServlet")
public class ListEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Employee> employees = new EmployeeDAO().findAll();
            req.setAttribute("employees", employees);
            req.getRequestDispatcher("employee_list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Cannot retrieve employees", e);
        }
    }
}
