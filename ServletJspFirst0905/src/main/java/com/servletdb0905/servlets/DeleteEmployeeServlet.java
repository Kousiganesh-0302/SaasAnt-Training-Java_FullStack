// DeleteEmployeeServlet.java

package com.servletdb0905.servlets;

import com.servletdb0905.dao.EmployeeDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DeleteEmployeeServlet")
public class DeleteEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("empId");

        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            boolean ok = new EmployeeDAO().deleteEmployeeById(id);
            out.write("{\"success\":" + ok + "}");
        } catch (Exception e) {
            resp.setStatus(500);
            try (PrintWriter out = resp.getWriter()) {
                out.write("{\"success\":false, \"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
}
