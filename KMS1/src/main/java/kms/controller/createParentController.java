package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kms.dao.parentDAO;
import kms.model.parent;

import java.io.IOException;

/**
 * Servlet implementation class createParentController
 */
@WebServlet("/createParentController")
public class createParentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createParentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		parent p = new parent();

		// retrieve input from html
		p.setParentName(request.getParameter("name"));
		p.setParentEmail(request.getParameter("email"));
		p.setParentPass(request.getParameter("password"));
		p.setParentPhone(request.getParameter("phone"));
		

		parentDAO.addParent(p);

		// set attribute to a servlet request. Set the attribute name to shawls and call
		// getAllShawls() from ShawlDAO class
		request.setAttribute("parents", parentDAO.getAllParent());

		// Obtain the RequestDispatcher from the request object. The pathname to the
		// resource is listShawl.jsp
		RequestDispatcher req = request.getRequestDispatcher("login.jsp");

		// Dispatch the request to another resource using forward() methods of the
		// RequestDispatcher
		req.forward(request, response);
	}

}
