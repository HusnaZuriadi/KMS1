package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import kms.dao.*;
import kms.model.*;

/**
 * Servlet implementation class loginController
 */
@WebServlet("/loginController")
public class loginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String role = request.getParameter("role");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        HttpSession session = request.getSession();
	        

	        if (role.equals("Parent")) {
	        	
	        	session.setAttribute("role", "parent");

	            parent p = parentDAO.validate(email, password);
	            if (p != null) {
	                session.setAttribute("parentId", p.getParentId());
	                session.setAttribute("parentName", p.getParentName());
	                response.sendRedirect("parentDashboard.jsp");
	            } else {
	                response.sendRedirect("login.jsp?msg=fail");
	            }

	        } else if (role.equals("Teacher")) {
	        	
	        	session.setAttribute("role", "teacher");

	            teacher teach = teacherDAO.validate(email, password);
	            if (teach != null && !"admin".equalsIgnoreCase(teach.getTeacherRole()))
 {
	                session.setAttribute("teacherId", teach.getTeacherId());
	                session.setAttribute("teacherName", teach.getTeacherName());
	                response.sendRedirect("teacherDashboard.jsp");
	            } else {
	                response.sendRedirect("login.jsp?msg=fail");
	            }

	        } else if (role.equals("Admin")) {
	        	
	        	session.setAttribute("role", "admin");

	            teacher admin = teacherDAO.validate(email, password);
	            if (admin != null && "admin".equalsIgnoreCase(admin.getTeacherRole()))
 {
	                session.setAttribute("adminId", admin.getAdminId());
	                session.setAttribute("adminName", admin.getTeacherName());
	                response.sendRedirect("adminDashboard.jsp");
	            } else {
	                response.sendRedirect("login.jsp?msg=fail");
	            }

	        } else {
	            response.sendRedirect("login.jsp?msg=fail");
	        }
	}

}
