package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kms.connection.ConnectionManager;
import kms.dao.studentDAO;
import kms.model.student;

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
        int studId = Integer.parseInt(request.getParameter("studId"));
        String type = request.getParameter("type"); // either "photo" or "cert"


        try { 
        	student stud = studentDAO.getStudent(studId);
            byte[] fileData = null;
            String contentType = "application/octet-stream"; // fallback

            if ("photo".equalsIgnoreCase(type)) {
                fileData = stud.getStudPhoto();
                contentType = "image/jpeg"; // you can dynamically detect if needed
            } else if ("cert".equalsIgnoreCase(type)) {
                fileData = stud.getStudBirthCert();
                contentType = detectMimeType(fileData); // detect JPEG/PNG/PDF

                // Tetapkan nama fail ikut jenis
                String filename = "birthCert";
                if (contentType.equals("application/pdf")) {
                    filename += ".pdf";
                } else if (contentType.equals("image/jpeg")) {
                    filename += ".jpg";
                } else if (contentType.equals("image/png")) {
                    filename += ".png";
                }

                // Allow inline view
                response.setHeader("Content-Disposition", "inline; filename=" + filename);
            }

            if (fileData == null || fileData.length == 0) {
                if ("photo".equalsIgnoreCase(type)) {
                    response.sendRedirect("images/admin.jpg");
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
        } catch (Exception e) { 
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
}
