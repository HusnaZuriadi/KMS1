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

		
	    if ("FullTime".equalsIgnoreCase(type)) {
	        fullTime ft = new fullTime();
	        // Do not set salary — leave as null
	        teach = ft;
	    } else if ("PartTime".equalsIgnoreCase(type)) {
	        partTime pt = new partTime();
	        // Do not set contract — leave as null
	        teach = pt;
	    } else {
	        teach = new teacher();
	    }
		  
		//retrieve input from html
		  teach.setTeacherName(name);
		    teach.setTeacherEmail(email);
		    teach.setTeacherPass(pass);
		    teach.setTeacherPhone(phone);
		    teach.setTeacherRole(role);
		    teach.setTeacherType(type);

		
		
		teacherDAO.addTeacher(teach);
		
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
