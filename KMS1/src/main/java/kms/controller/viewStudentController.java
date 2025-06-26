package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kms.connection.ConnectionManager;
import kms.dao.studentDAO;
import kms.model.student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Servlet implementation class viewStudentController
 */
@WebServlet("/viewStudentController")
public class viewStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewStudentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int studId = Integer.parseInt(request.getParameter("studId"));
		
		student stud = studentDAO.getStudent(studId);

		//set attribute to a servlet request. Set the attribute name to shawl and call getShawl() from ShawlDAO class
		request.setAttribute("student", studentDAO.getStudent(studId));

		//Obtain the RequestDispatcher from the request object. The pathname to the resource is viewShawl.jsp
		RequestDispatcher req = request.getRequestDispatcher("viewStudent.jsp");
		

		//Dispatch the request to another resource using forward() methods of the RequestDispatcher
		req.forward(request, response);
	}

	

}
