package kms.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kms.connection.ConnectionManager;
import kms.model.*;

public class teacherDAO {
	
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;
	
	//insert teacher
	public static void addTeacher(teacher teach){
		try {
			//call getConnection() method
			con = ConnectionManager.getConnection();
			
			//3. create statement
			sql = "INSERT INTO Teacher(teacherName, teacherEmail, teacherPass, teacherPhone, teacherRole, teacherType, adminId) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // ✅ Fix here

			
			//get values from student object and set parameter values
			ps.setString(1, teach.getTeacherName());
			ps.setString(2, teach.getTeacherEmail());
			ps.setString(3, teach.getTeacherPass());
			ps.setString(4, teach.getTeacherPhone());
			ps.setString(5, teach.getTeacherRole());
			ps.setString(6, teach.getTeacherType());
			ps.setInt(7, teach.getAdminId());

			//4. execute query
			ps.executeUpdate();
			
			// set generated ID
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int generatedId = generatedKeys.getInt(1);
	            teach.setTeacherId(generatedId); 
	        }
			
			// Also insert into FullTime or PartTime table
			if ("FullTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof fullTime) {
				fullTime ft = (fullTime) teach;
				
				 sql = "INSERT INTO fullTime(teacherId, salary) VALUES (?, ?)";
			        ps = con.prepareStatement(sql);
			        ps.setInt(1, ft.getTeacherId());
			        ps.setNull(2, java.sql.Types.DOUBLE);
			        ps.executeUpdate();
				
			} 
			
			else if ("PartTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof partTime) {
				
				partTime pt = (partTime) teach;
				
				 sql = "INSERT INTO partTime(teacherId, contract) VALUES (?, ?)";
			        ps = con.prepareStatement(sql);
			        ps.setInt(1, pt.getTeacherId());
			        ps.setNull(2, java.sql.Types.INTEGER);
			        ps.executeUpdate();
			}
			
			System.out.println("Teacher Type: " + teach.getTeacherType());
			System.out.println("Is instance of fullTime? " + (teach instanceof fullTime));
			System.out.println("Is instance of partTime? " + (teach instanceof partTime));
			System.out.println("FullTime salary: " + ((teach instanceof fullTime) ? ((fullTime)teach).getSalary() : "N/A"));
			System.out.println("PartTime contract: " + ((teach instanceof partTime) ? ((partTime)teach).getContract() : "N/A"));

			//5. close connection
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		//update teacher by id
		public static void updateTeacher(teacher teach){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement
				sql = "UPDATE teacher SET teacherName=?,teacherEmail=?,teacherPass=?,teacherPhone=?,  teacherRole=?, teacherType=?, adminId=?  WHERE teacherId=?";
				ps = con.prepareStatement(sql);

				//get values from teacher object and set parameter values
				ps.setString(1, teach.getTeacherName());
				ps.setString(2, teach.getTeacherEmail());
				ps.setString(3, teach.getTeacherPass());
				ps.setString(4, teach.getTeacherPhone());
				ps.setString(5, teach.getTeacherRole());
				ps.setString(6, teach.getTeacherType());
				ps.setInt(7, teach.getAdminId());
				ps.setInt(8, teach.getTeacherId());

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}
		}


		//delete teacher
		public static void deleteTeacher(int teacherId){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();
				
				// Delete from subclass first
				String deleteFullTime = "DELETE FROM fullTime WHERE teacherId=?";
				ps = con.prepareStatement(deleteFullTime); // ✅

				ps.setInt(1, teacherId);
				ps.executeUpdate();

				String deletePartTime = "DELETE FROM partTime WHERE teacherId=?";
				ps = con.prepareStatement(deletePartTime); // ✅
				ps.setInt(1, teacherId);
				ps.executeUpdate();


				//3. create statement
				String sql = "DELETE FROM teacher WHERE teacherId=?";
				ps = con.prepareStatement(sql);

				//set parameter value
				ps.setInt(1, teacherId);

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}	

	   }
		
		//get teacher by id
		public static teacher getTeacher(int teacherId) {
			teacher teach = new teacher();
			try {
				con = ConnectionManager.getConnection();
				sql = "SELECT * FROM teacher WHERE teacherId = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, teacherId);
				rs = ps.executeQuery();

				if (rs.next()) {
					
					String teacherType = rs.getString("teacherType");

					if ("FullTime".equalsIgnoreCase(teacherType)) {
						teach = new fullTime();
						sql = "SELECT * FROM fullTime WHERE teacherId=?";
						PreparedStatement ps2 = con.prepareStatement(sql);
						ps2.setInt(1, teacherId);
						ResultSet rs2 = ps2.executeQuery();
						if (rs2.next()) {
							((fullTime) teach).setSalary(rs2.getDouble("salary"));
						}
					} else if ("PartTime".equalsIgnoreCase(teacherType)) {
						teach = new partTime();
						sql = "SELECT * FROM parttime WHERE teacherId=?";
						PreparedStatement ps2 = con.prepareStatement(sql);
						ps2.setInt(1, teacherId);
						ResultSet rs2 = ps2.executeQuery();
						if (rs2.next()) {
							((partTime) teach).setContract(rs2.getInt("contract"));
						}
					} else {
						teach = new teacher();
					}
					
					teach.setTeacherId(rs.getInt("teacherId"));
		            teach.setTeacherName(rs.getString("teacherName"));
		            teach.setTeacherEmail(rs.getString("teacherEmail"));
		            teach.setTeacherPass(rs.getString("teacherPass"));
		            teach.setTeacherPhone(rs.getString("teacherPhone"));
		            teach.setTeacherRole(rs.getString("teacherRole"));
		            teach.setTeacherType(rs.getString("teacherType"));
		            teach.setAdminId(rs.getInt("adminId"));
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return teach;
		}
		
		//get all teacher
		public static List<teacher> getAllTeacher(){
			
			List<teacher> teachers = new ArrayList<teacher>();
			//complete the code here
			try {
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement 
				sql = "SELECT * FROM teacher ORDER BY teacherId";
				ps = con.prepareStatement(sql);

				//4. execute query
				rs = ps.executeQuery();

				//process ResultSet
				while(rs.next()) {	
					
					String teacherType = rs.getString("teacherType");
					teacher teach = new teacher();
					
					if ("FullTime".equalsIgnoreCase(teacherType)) {
						
						teach = new fullTime();
						
						PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM fullTime WHERE teacherId=?");
						ps2.setInt(1, rs.getInt("teacherId"));
						ResultSet rs2 = ps2.executeQuery();
						
						if (rs2.next()) {
							((fullTime) teach).setSalary(rs2.getDouble("salary"));
						}
						
					    }
					    else if ("PartTime".equalsIgnoreCase(teacherType)) {
					    	
						teach = new partTime();
						
						PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM partTime WHERE teacherId=?");
						ps2.setInt(1, rs.getInt("teacherId"));
						ResultSet rs2 = ps2.executeQuery();
						
						if (rs2.next()) {
							((partTime) teach).setContract(rs2.getInt("contract"));
						}
					    }
					    else {
						teach = new teacher();
					}
					
					 teach.setTeacherId(rs.getInt("teacherId"));
					 teach.setTeacherName(rs.getString("teacherName"));
					 teach.setTeacherEmail(rs.getString("teacherEmail"));
					 teach.setTeacherPass(rs.getString("teacherPass"));
					 teach.setTeacherPhone(rs.getString("teacherPhone"));
					 teach.setTeacherRole(rs.getString("teacherRole"));
					 teach.setTeacherType(rs.getString("teacherType"));
					 teach.setAdminId(rs.getInt("adminId"));
					 teachers.add(teach);


				}

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}

			return teachers;
		}	
		
		
		// validate login
		public static teacher validate(String email, String password) {
		    teacher teach = null;
		    try {
		        con = ConnectionManager.getConnection();
		        sql = "SELECT * FROM teacher WHERE teacherEmail = ? AND teacherPass = ?";
		        ps = con.prepareStatement(sql);
		        ps.setString(1, email);
		        ps.setString(2, password);
		        rs = ps.executeQuery();

		        if (rs.next()) {
		            teach = new teacher();
		            teach.setTeacherId(rs.getInt("teacherId"));
		            teach.setTeacherName(rs.getString("teacherName"));
		            teach.setTeacherEmail(rs.getString("teacherEmail"));
		            teach.setTeacherPass(rs.getString("teacherPass"));
		            teach.setTeacherPhone(rs.getString("teacherPhone"));
		            teach.setTeacherRole(rs.getString("teacherRole"));
		            teach.setTeacherType(rs.getString("teacherType"));
		            teach.setAdminId(rs.getInt("adminId"));
		        }

		        con.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return teach;
		}

}


