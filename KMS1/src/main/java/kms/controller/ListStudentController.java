package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kms.dao.studentDAO;

import java.io.IOException;

/**
 * Servlet implementation class ListStudentController
 */
@WebServlet("/ListStudentController")
public class ListStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListStudentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stud
		int parentId = (int) request.getSession().getAttribute("parentId");
		
		request.setAttribute("students", studentDAO.getStudentsByParentId(parentId));
	


		//Obtain the RequestDispatcher from the request object. The pathname to the resource is listShawl.jsp
		RequestDispatcher req = request.getRequestDispatcher("listStudent.jsp");

		//Dispatch the request to another resource using forward() methods of the RequestDispatcher
		req.forward(request, response);
	}

	
	

}
