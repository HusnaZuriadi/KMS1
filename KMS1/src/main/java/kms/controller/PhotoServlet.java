package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kms.connection.ConnectionManager;
import kms.dao.*;
import kms.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream; // ✅ This was missing
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PhotoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	 String role = request.getParameter("role");     // student, teacher, parent
         String type = request.getParameter("type");     // photo or cert
         String idStr = request.getParameter("id");
         
         if (role == null || type == null || idStr == null) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters.");
             return;
         }
         
         int id = Integer.parseInt(idStr);
         byte[] fileData = null;
         String contentType = "application/octet-stream";// fallback


         try {
             if (role.equalsIgnoreCase("student")) {
                 student stud = studentDAO.getStudent(id);
                 if ("photo".equalsIgnoreCase(type)) {
                     fileData = stud.getStudPhoto();
                     contentType = "image/jpeg";
            } 
                 else if ("cert".equalsIgnoreCase(type)) {
                fileData = stud.getStudBirthCert();
                contentType = detectMimeType(fileData); // detect JPEG/PNG/PDF
                
                String filename = "birthCert" + getExtension(contentType);
                response.setHeader("Content-Disposition", "inline; filename=" + filename);
            }
             
             } else if (role.equalsIgnoreCase("teacher")) {
                 teacher t = teacherDAO.getTeacher(id);
                 if ("photo".equalsIgnoreCase(type)) {
                     fileData = t.getTeacherPhoto();
                     contentType = "image/jpeg";
                 }
             } else if (role.equalsIgnoreCase("parent")) {
                 parent p = parentDAO.getParent(id);
                 if ("photo".equalsIgnoreCase(type)) {
                     fileData = p.getParentPhoto();
                     contentType = "image/jpeg";
                 }
             } else {
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role.");
                 return;
             }


         if (fileData == null || fileData.length == 0) {
             if ("photo".equalsIgnoreCase(type)) {
                 response.sendRedirect("images/default-user.png");
             } else {
                 response.setContentType("text/plain");
                 response.getWriter().write("No file found.");
             }
             return;
         }

            // Set headers
            response.setContentType(contentType);
            response.setContentLength(fileData.length);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

            // Write to output
            try (OutputStream out = response.getOutputStream()) {
                out.write(fileData);
            }
        } 
    catch (Exception e) { 
            throw new ServletException(e); 
        } 
}
    
    private String detectMimeType(byte[] fileData) {
        if (fileData == null || fileData.length < 4) {
            return "application/octet-stream";
        }

        // PDF signature: %PDF
        if (fileData[0] == 0x25 && fileData[1] == 0x50 && fileData[2] == 0x44 && fileData[3] == 0x46) {
            return "application/pdf";
        }

        // JPEG signature: FF D8 FF
        if ((fileData[0] & 0xFF) == 0xFF && (fileData[1] & 0xFF) == 0xD8 && (fileData[2] & 0xFF) == 0xFF) {
            return "image/jpeg";
        }

        // PNG signature: 89 50 4E 47
        if ((fileData[0] & 0xFF) == 0x89 && fileData[1] == 0x50 && fileData[2] == 0x4E && fileData[3] == 0x47) {
            return "image/png";
        }

        // Fallback
        return "application/octet-stream";
    }
    
    private String getExtension(String mimeType) {
        if (mimeType.equals("application/pdf")) return ".pdf";
        if (mimeType.equals("image/jpeg")) return ".jpg";
        if (mimeType.equals("image/png")) return ".png";
        return "";
    }
}
