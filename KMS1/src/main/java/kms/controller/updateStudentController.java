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
import java.io.InputStream;
import java.sql.Date;

/**
 * Servlet implementation class updateStudentController
 */
@MultipartConfig
@WebServlet("/updateStudentController")
public class updateStudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	
    public updateStudentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get id from input parameter
				int studId = Integer.parseInt(request.getParameter("studId"));

				//set attribute to a servlet request. Set the attribute name to shawl and call getShawl() from ShawlDAO class
				request.setAttribute("student", studentDAO.getStudent(studId));

				//Obtain the RequestDispatcher from the request object. The pathname to the resource is updateShawl.jsp
				RequestDispatcher req = request.getRequestDispatcher("updateStudent.jsp");

				//Dispatch the request to another resource using forward() methods of the RequestDispatcher
				req.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//create student object
				student stud = new student();

		        // Retrieve basic parameters
				// SAFELY PARSE studId
				String studIdStr = request.getParameter("studId");
				int studId = 0;
				if (studIdStr != null && !studIdStr.isEmpty()) {
				    studId = Integer.parseInt(studIdStr);
				}
				stud.setStudId(studId);
				
		        stud.setStudName(request.getParameter("name"));
		        stud.setStudGender(request.getParameter("gender"));
		        
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
		        stud.setStudAddress(request.getParameter("address"));

		        // Date conversion
		        String dobStr = request.getParameter("dob");
		        if (dobStr != null && !dobStr.isEmpty()) {
		            stud.setStudBirthDate(Date.valueOf(dobStr));
		        }
		        
		        System.out.println("DEBUG - Name: " + stud.getStudName());
		        System.out.println("DEBUG - Age: " + stud.getStudAge());
		        System.out.println("DEBUG - Gender: " + stud.getStudGender());
		        System.out.println("DEBUG - Stud ID: " + stud.getStudId());



		        // Get parentId from session
		        HttpSession session = request.getSession(false);
		        String role = (String) session.getAttribute("role");
		        int parentId = 0;
		        
		        if ("parent".equals(role) && session.getAttribute("parentId") != null) {
		            Object parentIdObj = session.getAttribute("parentId");
		            if (parentIdObj instanceof Integer) {
		                parentId = (Integer) parentIdObj;
		            } else if (parentIdObj instanceof String) {
		                parentId = Integer.parseInt((String) parentIdObj);
		            }
		            stud.setParentId(parentId);
		        }

		        // Handle file uploads
		        Part photoPart = request.getPart("photo");
		        Part certPart = request.getPart("birthCert");

		        if (photoPart != null && photoPart.getSize() > 0) {
		            InputStream photoStream = photoPart.getInputStream();
		            byte[] photoBytes = photoStream.readAllBytes();
		            stud.setStudPhoto(photoBytes);
		        }
		        else {
		            // Kalau takde gambar baru, ambil gambar lama dari DB
		            byte[] existing = studentDAO.getStudent(stud.getStudId()).getStudPhoto();
		            stud.setStudPhoto(existing);
		        }


		        if (certPart != null && certPart.getSize() > 0) {
		            InputStream certStream = certPart.getInputStream();
		            byte[] certBytes = certStream.readAllBytes();
		            stud.setStudBirthCert(certBytes);
		        }
		        else {
		            // Kalau takde gambar baru, ambil gambar lama dari DB
		        	 byte[] existing = studentDAO.getStudent(stud.getStudId()).getStudBirthCert();
			            stud.setStudBirthCert(existing);
		        }


		        // Update the student in the database
		        studentDAO.updateStudent(stud);
		        
		        if ("admin".equals(role)) {
		            request.setAttribute("students", studentDAO.getAllStudents());
		        } else if ("parent".equals(role)) {
		            request.setAttribute("students", studentDAO.getStudentsByParentId(parentId));
		        }


				//Obtain the RequestDispatcher from the request object. The pathname to the resource is listShawl.jsp
				RequestDispatcher req = request.getRequestDispatcher("listStudent.jsp");

				//Dispatch the request to another resource using forward() methods of the RequestDispatcher
				req.forward(request, response);

	}

}
