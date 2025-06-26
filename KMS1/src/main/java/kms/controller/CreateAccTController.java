package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kms.dao.*;
import kms.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class CreateAccTController
 */
@WebServlet("/CreateAccTController")
public class CreateAccTController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccTController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	List<teacher> adminList = teacherDAO.getAllAdmins(); // get all admin teachers
        request.setAttribute("adminList", adminList);        // send to JSP

        RequestDispatcher rd = request.getRequestDispatcher("createTeacher.jsp"); // your JSP form
        rd.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String name = request.getParameter("name");
	    String email = request.getParameter("email");
	    String pass = request.getParameter("password");
	    String role = request.getParameter("role");
	    String phone = request.getParameter("phone");
	    String type = request.getParameter("teacherType");
	    
	    teacher teach;
	    
	    if ("admin".equalsIgnoreCase(role)) {
	        teach = new fullTime();     // admin is always fullTime
	        type = "FullTime";     
	    }// force type to FullTime
	    else if ("FullTime".equalsIgnoreCase(type)) {
	        teach = new fullTime();
	    } else if ("PartTime".equalsIgnoreCase(type)) {
	        teach = new partTime();
	    } else {
	        teach = new teacher();
	    }
	    
	    String adminIdParam = request.getParameter("adminId");
		  
		//retrieve input from html
		  teach.setTeacherName(name);
		    teach.setTeacherEmail(email);
		    teach.setTeacherPass(pass);
		    teach.setTeacherPhone(phone);
		    teach.setTeacherRole(role);
		    teach.setTeacherType(type);
		    
		    if (adminIdParam != null && !adminIdParam.isEmpty()) {
		        teach.setAdminId(Integer.parseInt(adminIdParam));
		    } else {
		        teach.setAdminId(null);  // <-- this is safe
		    }
		   

		
		
		    teacherDAO.addTeacher(teach, type);

		
		//set attribute to a servlet request. Set the attribute name to shawls and call getAllShawls() from ShawlDAO class
				request.setAttribute("teachers", teacherDAO.getAllTeacher());
				
				//Obtain the RequestDispatcher from the request object. The pathname to the resource is listShawl.jsp
				RequestDispatcher req = request.getRequestDispatcher("login.jsp");
				
				System.out.println("Teach instanceof fullTime: " + (teach instanceof fullTime));
				System.out.println("Teach instanceof partTime: " + (teach instanceof partTime));
				System.out.println("Teach class: " + teach.getClass().getSimpleName());

				
				//Dispatch the request to another resource using forward() methods of the RequestDispatcher
				req.forward(request, response);
		

	}

}
