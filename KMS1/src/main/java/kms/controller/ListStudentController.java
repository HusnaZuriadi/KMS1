package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kms.dao.studentDAO;
import kms.model.parent;

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
		 HttpSession session = request.getSession(false); //  Dapatkan session

		 if (session == null || session.getAttribute("user") == null) {
			    response.sendRedirect("login.jsp?msg=sessionExpired");
			    return;
			}

			String role = (String) session.getAttribute("role");

			if (!"parent".equalsIgnoreCase(role) && !"admin".equalsIgnoreCase(role)) {
			    response.sendRedirect("login.jsp?msg=unauthorized");
			    return;
			}


	        if ("parent".equalsIgnoreCase(role)) {
	            parent p = (parent) session.getAttribute("user");
	            int parentId = p.getParentId();
	            request.setAttribute("students", studentDAO.getStudentsByParentId(parentId));
	           
	        } 
	        
	        else if ("admin".equalsIgnoreCase(role)) {
	            request.setAttribute("students", studentDAO.getAllStudents());
	            
	        } 
	        RequestDispatcher rd = request.getRequestDispatcher("listStudent.jsp");
	        rd.forward(request, response);
	       
	    }
	}


