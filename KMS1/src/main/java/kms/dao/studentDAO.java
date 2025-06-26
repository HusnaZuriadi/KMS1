package kms.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.student;

public class studentDAO {
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;
	
	//insert student
	public static void addStudent(student stud){
		try {
			//call getConnection() method
			con = ConnectionManager.getConnection();
			
			//3. create statement
			sql = "INSERT INTO student(studName, studAge, studGender, studAddress, studBirthDate, parentId, studPhoto, studBirthCert) VALUES (?, ?, ?, ?, ?, ?,?,?)";
			ps = con.prepareStatement(sql);
			
			//get values from student object and set parameter values
			ps.setString(1, stud.getStudName());
			ps.setInt(2, stud.getStudAge());
			ps.setString(3, stud.getStudGender());
			ps.setString(4, stud.getStudAddress());
			ps.setDate(5, stud.getStudBirthDate());
			ps.setInt(6, stud.getParentId());
			ps.setBytes(7, stud.getStudPhoto());
			ps.setBytes(8, stud.getStudBirthCert());
			//4. execute query
			ps.executeUpdate();
			//5. close connection
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
		
		//update student by id
		public static void updateStudent(student stud){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement
				if (stud.getStudPhoto() != null && stud.getStudBirthCert() != null) {
		            // Update semua termasuk gambar
		            sql = "UPDATE student SET studName=?, studAge=?, studGender=?, studAddress=?, studBirthDate=?, parentId=?, studPhoto=?, studBirthCert=? WHERE studId=?";
		            ps = con.prepareStatement(sql);
		            ps.setString(1, stud.getStudName());
		            ps.setInt(2, stud.getStudAge());
		            ps.setString(3, stud.getStudGender());
		            ps.setString(4, stud.getStudAddress());
		            ps.setDate(5, stud.getStudBirthDate());
		            ps.setInt(6, stud.getParentId());
		            ps.setBytes(7, stud.getStudPhoto());
		            ps.setBytes(8, stud.getStudBirthCert());
		            ps.setInt(9, stud.getStudId());
		        } else {
		            // Update tanpa gambar
		            sql = "UPDATE student SET studName=?, studAge=?, studGender=?, studAddress=?, studBirthDate=?, parentId=? WHERE studId=?";
		            ps = con.prepareStatement(sql);
		            ps.setString(1, stud.getStudName());
		            ps.setInt(2, stud.getStudAge());
		            ps.setString(3, stud.getStudGender());
		            ps.setString(4, stud.getStudAddress());
		            ps.setDate(5, stud.getStudBirthDate());
		            ps.setInt(6, stud.getParentId());
		            ps.setInt(7, stud.getStudId());
		        }

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}
		}


		//delete shawl
		public static void deleteStudent(int id){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement
				String sql = "DELETE FROM student WHERE studId=?";
				ps = con.prepareStatement(sql);

				//set parameter value
				ps.setInt(1, id);

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}	

	   }
		
		//get student by id
		public static student getStudent(int studId) {
			
			student stud = new student();
			
			try {
				
				//call getConnection() method
				con = ConnectionManager.getConnection();
				
				//3. create statement 
				sql = "SELECT * FROM student WHERE studId = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, studId);
				
				//4. execute query
				rs = ps.executeQuery();
				
				//process ResultSet
				if (rs.next()) {
					stud.setStudId(rs.getInt("studId"));
					stud.setStudName(rs.getString("studName"));
					stud.setStudAge(rs.getInt("studAge"));
					stud.setStudGender(rs.getString("studGender"));
					stud.setStudAddress(rs.getString("studAddress"));
					stud.setStudBirthDate(rs.getDate("studBirthDate"));
					stud.setParentId(rs.getInt("parentId"));
					Blob blobPhoto = rs.getBlob("studPhoto");
					if (blobPhoto != null) {
					    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
					}

					Blob blobBirthCert = rs.getBlob("studBirthCert");
					if (blobBirthCert != null) {
					    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
					}


				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return stud;
		}
		
		//get all student
		public static List<student> getAllStudents(){
			
			List<student> students = new ArrayList<student>();
			//complete the code here
			try {
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement 
				sql = "SELECT * FROM student";
				ps = con.prepareStatement(sql);

				//4. execute query
				rs = ps.executeQuery();

				//process ResultSet
				while(rs.next()) {		
					student stud = new student();
					stud.setStudId(rs.getInt("studId"));
					stud.setStudName(rs.getString("studName"));
					stud.setStudAge(rs.getInt("studAge"));
					stud.setStudGender(rs.getString("studGender"));
					stud.setStudAddress(rs.getString("studAddress"));
					stud.setStudBirthDate(rs.getDate("studBirthDate"));
					stud.setParentId(rs.getInt("parentId"));
					Blob blobPhoto = rs.getBlob("studPhoto");
					if (blobPhoto != null) {
					    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
					}

					Blob blobBirthCert = rs.getBlob("studBirthCert");
					if (blobBirthCert != null) {
					    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
					}
					students.add(stud);


				}

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}

			return students;
		}	
		
		//list by parentId
		
		public static List<student> getStudentsByParentId(int parentId) {
			List<student> students = new ArrayList<>();
			try {
				con = ConnectionManager.getConnection();
				sql = "SELECT * FROM student WHERE parentId = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, parentId);
				rs = ps.executeQuery();

				while (rs.next()) {
					student stud = new student();
					stud.setStudId(rs.getInt("studId"));
					stud.setStudName(rs.getString("studName"));
					stud.setStudAge(rs.getInt("studAge"));
					stud.setStudGender(rs.getString("studGender"));
					stud.setStudAddress(rs.getString("studAddress"));
					stud.setStudBirthDate(rs.getDate("studBirthDate"));
					stud.setParentId(rs.getInt("parentId"));
					Blob blobPhoto = rs.getBlob("studPhoto");
					if (blobPhoto != null) {
					    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
					}

					Blob blobBirthCert = rs.getBlob("studBirthCert");
					if (blobBirthCert != null) {
					    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
					}

					students.add(stud);
					
					
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return students;
		}


}
