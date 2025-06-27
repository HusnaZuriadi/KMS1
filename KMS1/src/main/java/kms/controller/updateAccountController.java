package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kms.dao.parentDAO;
import kms.dao.teacherDAO;
import kms.model.fullTime;
import kms.model.parent;
import kms.model.partTime;
import kms.model.teacher;

import java.io.IOException;
import java.io.InputStream;

/**
 * Servlet implementation class UpdateAccountController
 */
@WebServlet("/UpdateAccountController")
@MultipartConfig(maxFileSize = 16177215) // to support photo upload
public class UpdateAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAccountController() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();
	        String role = request.getParameter("role");
	        int id = Integer.parseInt(request.getParameter("id"));
		
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        Part photoPart = request.getPart("photo"); // same name for both form input
		InputStream photoInput = (photoPart != null && photoPart.getSize() > 0) ? photoPart.getInputStream() : null;
        

        try {
            if ("parent".equalsIgnoreCase(role)) {
            	
            	parent existing = parentDAO.getParent(id);
            	
                parent p = new parent();
                p.setParentId(id);
                
                p.setParentName(name != null && !name.isEmpty() ? name : existing.getParentName());
				p.setParentEmail(email != null && !email.isEmpty() ? email : existing.getParentEmail());
				p.setParentPhone(phone != null && !phone.isEmpty() ? phone : existing.getParentPhone());
				p.setParentPass(existing.getParentPass());

				if (photoInput != null) {
					p.setParentPhoto(photoInput.readAllBytes());
				} else {
					p.setParentPhoto(existing.getParentPhoto());
				}

				
                parentDAO.updateParent(p);
                session.setAttribute("user", p);
                
            } else if ("teacher".equalsIgnoreCase(role) || "admin".equalsIgnoreCase(role)) {
            	
            	teacher existing = teacherDAO.getTeacher(id);
            	
                teacher t;
        
  

                if ("FullTime".equalsIgnoreCase(existing.getTeacherType())) {
                	fullTime ft = new fullTime();

                	String salaryStr = request.getParameter("salary");
                	if (salaryStr != null && !salaryStr.isEmpty()) {
                		ft.setSalary(Double.parseDouble(salaryStr)); // ✅ ambil dari form
                	} else {
                		ft.setSalary(null); // or existing.getSalary() if you want to keep old value
                	}

                	t = ft;

                } else if ("PartTime".equalsIgnoreCase(existing.getTeacherType())) {
                	partTime pt = new partTime();

                	String contractStr = request.getParameter("contract");
                	if (contractStr != null && !contractStr.isEmpty()) {
                		pt.setContract(Integer.parseInt(contractStr)); // ✅ ambil dari form
                	} else {
                		pt.setContract(null);
                	}

                	t = pt;

                } else {
                	t = new teacher();
                }

                
                t.setTeacherId(id);
                t.setTeacherName(name != null && !name.isEmpty() ? name : existing.getTeacherName());
				t.setTeacherEmail(email != null && !email.isEmpty() ? email : existing.getTeacherEmail());
				t.setTeacherPhone(phone != null && !phone.isEmpty() ? phone : existing.getTeacherPhone());
				t.setTeacherPass(existing.getTeacherPass()); // pastikan password tak null
				t.setTeacherRole(existing.getTeacherRole());
				t.setTeacherType(existing.getTeacherType());
				t.setAdminId(existing.getAdminId());
				
				if (photoInput != null) {
					t.setTeacherPhoto(photoInput.readAllBytes());
				} else {
					t.setTeacherPhoto(existing.getTeacherPhoto());
				}
  
                teacherDAO.updateTeacher(t);
                
                teacher updated = teacherDAO.getTeacher(t.getTeacherId());

                if ("FullTime".equalsIgnoreCase(updated.getTeacherType()) && updated instanceof fullTime) {
                    session.setAttribute("user", (fullTime) updated);
                } else if ("PartTime".equalsIgnoreCase(updated.getTeacherType()) && updated instanceof partTime) {
                    session.setAttribute("user", (partTime) updated);
                } else {
                    session.setAttribute("user", updated);
                }

                session.setAttribute("role", updated.getTeacherRole()); // optional, for safety
            }

            request.getRequestDispatcher("viewAccount.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
	}

	}


