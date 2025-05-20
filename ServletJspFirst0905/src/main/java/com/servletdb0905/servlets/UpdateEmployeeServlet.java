// UpdateEmployeeServlet.java

package com.servletdb0905.servlets;

import com.servletdb0905.dao.EmployeeDAO;
import com.servletdb0905.model.Employee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id       = req.getParameter("empId");
        String name     = req.getParameter("empName");
        String position = req.getParameter("position");
        String phone    = req.getParameter("phone");

        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Employee emp = new Employee();
            emp.setEmpId(id);
            emp.setEmpName(name);
            emp.setPosition(position);
            emp.setPhone(phone);

            boolean ok = new EmployeeDAO().updateEmployee(emp);
            out.write("{\"success\":" + ok + "}");
        } catch (Exception e) {
            resp.setStatus(500);
            try (PrintWriter out = resp.getWriter()) {
                out.write("{\"success\":false, \"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
}
