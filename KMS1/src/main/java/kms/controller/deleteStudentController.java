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
		
		//set attribute to a servlet request. Set the attribute name to shawls and call getAllShawls() from ShawlDAO class
		// ✅ Get session to access parentId
				HttpSession session = request.getSession(false);
				if (session == null || session.getAttribute("parentId") == null) {
					response.sendRedirect("login.jsp"); // or your login page
					return;
				}

				int parentId = (Integer) session.getAttribute("parentId");
	      request.setAttribute("students", studentDAO.getStudentsByParentId(parentId));
		//Obtain the RequestDispatcher from the request object. The pathname to the resource is listShawl.jsp
		RequestDispatcher req = request.getRequestDispatcher("listStudent.jsp");

		//Dispatch the request to another resource using forward() methods of the RequestDispatcher
		req.forward(request, response);
	}

	

}
