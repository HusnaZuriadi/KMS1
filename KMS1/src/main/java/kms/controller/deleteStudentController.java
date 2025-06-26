package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kms.dao.studentDAO;
import kms.model.parent;
import kms.model.student;

import java.io.IOException;

/**
 * Servlet implementation class deleteStudentController
 */
@WebServlet("/deleteStudentController")
public class deleteStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteStudentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int studId = Integer.parseInt(request.getParameter("studId"));

		//call deleteShawl() from ShawlDAO class
		studentDAO.deleteStudent(studId);
		
	
		// get session and role
				HttpSession session = request.getSession(false);
				if (session == null || session.getAttribute("user") == null) {
					response.sendRedirect("login.jsp");
					return;
				}

				String role = (String) session.getAttribute("role");

				if ("parent".equalsIgnoreCase(role)) {
					parent p = (parent) session.getAttribute("user");
					int parentId = p.getParentId();
					request.setAttribute("students", studentDAO.getStudentsByParentId(parentId));
					
				} else if ("admin".equalsIgnoreCase(role)) {
					request.setAttribute("students", studentDAO.getAllStudents());
					
				} 
				request.setAttribute("msg", "Student successfully deleted.");
				RequestDispatcher rd = request.getRequestDispatcher("listStudent.jsp");
		        rd.forward(request, response);
			}
	

}
