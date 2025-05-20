//package com.servletpackagecontrollers;
//
//import java.io.IOException;
//import javax.servlet.*;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * Servlet implementation class EmployeeServlet
// */
//@WebServlet("/EmployeeServlet0905First")
//public class EmployeeServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public EmployeeServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
////	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		// TODO Auto-generated method stub
////		response.getWriter().append("Served at: ").append(request.getContextPath());
////	}
//    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Redirect to form if accessed via GET
//        response.sendRedirect("index.html");
//    }
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
////	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		// TODO Auto-generated method stub
////		doGet(request, response);
////	}
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String name = request.getParameter("empName");
//        String id = request.getParameter("empId");
//        String position = request.getParameter("position");
//        String phone = request.getParameter("phone");
//
//        // Optional: server-side validation
//







//        request.setAttribute("name", name);
//        request.setAttribute("id", id);
//        request.setAttribute("position", position);
//        request.setAttribute("phone", phone);
//
//        // Forward to JSP
//        RequestDispatcher dispatcher = request.getRequestDispatcher("success0905.jsp");
//        dispatcher.forward(request, response);
//    }
//
//}


//
//
////servlet with db
//package com.servletpackagecontrollers;
//
//import java.io.IOException;
//import java.sql.*;
//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//
//@WebServlet("/EmployeeServlet0905First")
//public class EmployeeServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    
//    // DB connection settings – adjust as needed
//   // private static final String JDBC_URL      = "jdbc:mysql://localhost:3306/companydb?useSSL=false&serverTimezone=UTC";
//    private static final String JDBC_URL      = "jdbc:mysql://localhost:3307/saasant_billing";
//    private static final String JDBC_USER     = "root";
//    private static final String JDBC_PASS     = "12345";
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        // 1. Read form parameters
//        String name     = request.getParameter("empName");
//        String id       = request.getParameter("empId");
//        String position = request.getParameter("position");
//        String phone    = request.getParameter("phone");
//
//        // 2. Insert into DB
//        try {
//            // Load driver (optional with JDBC 4+)
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
//                String sql = "INSERT INTO empformservlet(emp_id, emp_name, position, phone) VALUES (?, ?, ?, ?)";
//                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                    stmt.setString(1, id);
//                    stmt.setString(2, name);
//                    stmt.setString(3, position);
//                    stmt.setString(4, phone);
//                    stmt.executeUpdate();
//                }
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            // Log and forward to an error page (you could create error.jsp)
//            e.printStackTrace();
//            request.setAttribute("error", "Database error: " + e.getMessage());
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//            return;
//        }
//
//        // 3. Set attributes for success page
//        request.setAttribute("name", name);
//        request.setAttribute("id", id);
//        request.setAttribute("position", position);
//        request.setAttribute("phone", phone);
//
//        // 4. Forward to JSP
//        RequestDispatcher dispatcher = request.getRequestDispatcher("success0905.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.sendRedirect("index.html");
//    }
//}


//
//
//package com.servletdb0905.servlets;
//import com.servletdb0905.model.Employee;
//import com.servletdb0905.dao.EmployeeDAO;
//
//import java.io.IOException;
//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//
//@WebServlet("/EmployeeServlet0905First")
//public class EmployeeServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String name     = request.getParameter("empName");
//        String id       = request.getParameter("empId");
//        String position = request.getParameter("position");
//        String phone    = request.getParameter("phone");
//
//        Employee emp = new Employee();
//        emp.setEmpId(id);
//        emp.setEmpName(name);
//        emp.setPosition(position);
//        emp.setPhone(phone);
//
//        try {
//            EmployeeDAO dao = new EmployeeDAO();
//            dao.insertEmployee(emp);
//
//            request.setAttribute("name", name);
//            request.setAttribute("id", id);
//            request.setAttribute("position", position);
//            request.setAttribute("phone", phone);
//
//            RequestDispatcher dispatcher = request.getRequestDispatcher("success0905.jsp");
//            dispatcher.forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Database error: " + e.getMessage());
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//        }
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.sendRedirect("index.html");
//    }
//}
//
//


package com.servletdb0905.servlets;

import com.servletdb0905.model.Employee;
import com.servletdb0905.dao.EmployeeDAO;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EmployeeServlet0905First")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name     = request.getParameter("empName");
        String id       = request.getParameter("empId");
        String position = request.getParameter("position");
        String phone    = request.getParameter("phone");

        Employee emp = new Employee();
        emp.setEmpId(id);
        emp.setEmpName(name);
        emp.setPosition(position);
        emp.setPhone(phone);

        try {
            new EmployeeDAO().insertEmployee(emp);

            request.setAttribute("name", name);
            request.setAttribute("id", id);
            request.setAttribute("position", position);
            request.setAttribute("phone", phone);

            RequestDispatcher dispatcher = request.getRequestDispatcher("success0905.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}
