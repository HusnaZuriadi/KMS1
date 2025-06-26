package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import kms.dao.*;
import kms.model.*;

/**
 * Servlet implementation class createStudentController
 */
@WebServlet("/createStudentController")
@MultipartConfig(maxFileSize = 16177215) // optional: limit file size
public class createStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createStudentController() {
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

		System.out.println("DEBUG - Age parameter: " + request.getParameter("age"));

		student stud = new student();

		// Basic fields
		stud.setStudName(request.getParameter("name"));
		stud.setStudGender(request.getParameter("gender"));
		stud.setStudAddress(request.getParameter("address"));

		// Safe parsing for age
		String ageStr = request.getParameter("age");
		int age = 0;
		try {
			if (ageStr != null && !ageStr.isEmpty()) {
				age = Integer.parseInt(ageStr);
			}
		} catch (NumberFormatException e) {
			System.out.println("DEBUG - Invalid or missing age value: " + ageStr);
		}
		stud.setStudAge(age);

		// Safe DOB parsing
		String dobStr = request.getParameter("dob");
		Date dob = null;
		if (dobStr != null && !dobStr.isEmpty()) {
			try {
				dob = Date.valueOf(dobStr);
			} catch (IllegalArgumentException e) {
				System.out.println("DEBUG - Invalid DOB: " + dobStr);
			}
		}
		stud.setStudBirthDate(dob);

		// Parent ID from session
		int parentId = (int) request.getSession().getAttribute("parentId");
		stud.setParentId(parentId);

		// File uploads
		Part photoPart = request.getPart("photo");
		Part certPart = request.getPart("birthCert");

		if (photoPart != null && photoPart.getSize() > 0) {
			stud.setStudPhoto(readPartAsBytes(photoPart));
		}

		if (certPart != null && certPart.getSize() > 0) {
			stud.setStudBirthCert(readPartAsBytes(certPart));
		}

		studentDAO.addStudent(stud);
		response.sendRedirect("listStudent.jsp");
	}

	private byte[] readPartAsBytes(Part part) throws IOException {
		InputStream is = part.getInputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}
}
