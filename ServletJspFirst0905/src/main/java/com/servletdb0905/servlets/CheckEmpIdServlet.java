package com.servletdb0905.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/CheckEmpId")
public class CheckEmpIdServlet extends HttpServlet {
    private static final String JDBC_URL  = "jdbc:mysql://localhost:3307/saasant_billing";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String empId = req.getParameter("empId");
        boolean exists = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
                 PreparedStatement ps = conn.prepareStatement(
                     "SELECT 1 FROM empformservlet WHERE emp_id = ?")) {
                ps.setString(1, empId);
                try (ResultSet rs = ps.executeQuery()) {
                    exists = rs.next();
                }
            }
        } catch (Exception e) {
            exists = true;  // fail-safe
        }

        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            out.write("{\"exists\":" + exists + "}");
        }
    }
}

