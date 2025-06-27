package kms.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.*;

public class teacherDAO {
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	// insert teacher
	public static void addTeacher(teacher teach, String teacherType) {
		try {
			// call getConnection() method
			con = ConnectionManager.getConnection();

			// 3. create statement
			sql = "INSERT INTO Teacher(teacherName, teacherEmail, teacherPass, teacherPhone, teacherRole, teacherType, adminId) VALUES (?, ?, ?, ?, ?, ?, ?)";

			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // ✅ Fix here

			// get values from student object and set parameter values
			ps.setString(1, teach.getTeacherName());
			ps.setString(2, teach.getTeacherEmail());
			ps.setString(3, teach.getTeacherPass());
			ps.setString(4, teach.getTeacherPhone());
			ps.setString(5, teach.getTeacherRole());
			ps.setString(6, teach.getTeacherType());

			if (teach.getAdminId() != null) {
				ps.setInt(7, teach.getAdminId());
			} else {
				ps.setNull(7, java.sql.Types.INTEGER); // ✅ safe for null
			}

			// 4. execute query
			ps.executeUpdate();

			PreparedStatement psGetId = con.prepareStatement("SELECT teacherId FROM teacher WHERE teacherEmail = ?");
			psGetId.setString(1, teach.getTeacherEmail());
			ResultSet rsId = psGetId.executeQuery();
			if (rsId.next()) {
				int teacherId = rsId.getInt("teacherId");
				teach.setTeacherId(teacherId);
			}
			
			if ("Admin".equalsIgnoreCase(teach.getTeacherRole())) {
			    teacherType = "FullTime";
			}


			// Also insert into FullTime or PartTime table
			if ("FullTime".equalsIgnoreCase(teacherType) && teach instanceof fullTime ft) {
				sql = "INSERT INTO FullTime (teacherId, salary) VALUES (?, ?)";
				try (PreparedStatement psFT = con.prepareStatement(sql)) {
					psFT.setInt(1, ft.getTeacherId());
					psFT.setNull(2, Types.DOUBLE);
					psFT.executeUpdate();
				}
			} else if ("PartTime".equalsIgnoreCase(teacherType) && teach instanceof partTime pt) {
				sql = "INSERT INTO PartTime (teacherId, contract) VALUES (?, ?)";
				try (PreparedStatement psPT = con.prepareStatement(sql)) {
					psPT.setInt(1, pt.getTeacherId());
					psPT.setNull(2, Types.INTEGER);
					psPT.executeUpdate();
				}
			}

			System.out.println("Teach actual class: " + teach.getClass().getName());
			System.out.println("Is instanceof fullTime: " + (teach instanceof fullTime));
			System.out.println("Is instanceof partTime: " + (teach instanceof partTime));
			System.out.println("teacherType: " + teach.getTeacherType());

			// 5. close connection
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// update teacher by id
	public static void updateTeacher(teacher teach) {
		// complete the code here
		try {
			// call getConnection() method
			con = ConnectionManager.getConnection();
			
			if(teach.getTeacherPhoto()!= null) {
				
				sql = "UPDATE teacher SET teacherName=?,teacherEmail=?,teacherPass=?,teacherPhone=?,  teacherRole=?, teacherType=?, adminId=?, teacherPhoto=?  WHERE teacherId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, teach.getTeacherName());
				ps.setString(2, teach.getTeacherEmail());
				ps.setString(3, teach.getTeacherPass());
				ps.setString(4, teach.getTeacherPhone());
				ps.setString(5, teach.getTeacherRole());
				ps.setString(6, teach.getTeacherType());
				
				if (teach.getAdminId() != null && teach.getAdminId() != 0) {
				    ps.setInt(7, teach.getAdminId());
				} else {
				    ps.setNull(7, Types.INTEGER); // Elakkan FK error
				}
				
				ps.setBytes(8, teach.getTeacherPhoto());
				ps.setInt(9, teach.getTeacherId());
			} else {

			// 3. create statement
			sql = "UPDATE teacher SET teacherName=?,teacherEmail=?,teacherPass=?,teacherPhone=?,  teacherRole=?, teacherType=?, adminId=?  WHERE teacherId=?";
			ps = con.prepareStatement(sql);

			// get values from teacher object and set parameter values
			ps.setString(1, teach.getTeacherName());
			ps.setString(2, teach.getTeacherEmail());
			ps.setString(3, teach.getTeacherPass());
			ps.setString(4, teach.getTeacherPhone());
			ps.setString(5, teach.getTeacherRole());
			ps.setString(6, teach.getTeacherType());
			
			if (teach.getAdminId() != null && teach.getAdminId() != 0) {
			    ps.setInt(7, teach.getAdminId());
			} else {
			    ps.setNull(7, Types.INTEGER); // Elakkan FK error
			}
			
			ps.setInt(8, teach.getTeacherId());
			}

			// 4. execute query
			ps.executeUpdate();

		
			// Tambahan untuk update subclass FullTime atau PartTime
			if ("FullTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof fullTime ft) {
				sql = "UPDATE FullTime SET salary = ? WHERE teacherId = ?";
				try (PreparedStatement psFT = con.prepareStatement(sql)) {
					if (ft.getSalary() != null) {
						psFT.setDouble(1, ft.getSalary());
					} else {
						psFT.setNull(1, Types.DOUBLE);
					}
					psFT.setInt(2, ft.getTeacherId());
					psFT.executeUpdate();
				}
			} else if ("PartTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof partTime pt) {
				sql = "UPDATE PartTime SET contract = ? WHERE teacherId = ?";
				try (PreparedStatement psPT = con.prepareStatement(sql)) {
					if (pt.getContract() != null) {
						psPT.setInt(1, pt.getContract());
					} else {
						psPT.setNull(1, Types.INTEGER);
					}
					psPT.setInt(2, pt.getTeacherId());
					psPT.executeUpdate();
				}
			}

			// 5. close connection
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// delete teacher
	public static void deleteTeacher(int teacherId) {
		// complete the code here
		try {
			// call getConnection() method
			con = ConnectionManager.getConnection();

			// Delete from subclass first
			String deleteFullTime = "DELETE FROM FullTime WHERE teacherId=?";
			ps = con.prepareStatement(deleteFullTime); // ✅

			ps.setInt(1, teacherId);
			ps.executeUpdate();

			String deletePartTime = "DELETE FROM PartTime WHERE teacherId=?";
			ps = con.prepareStatement(deletePartTime); // ✅
			ps.setInt(1, teacherId);
			ps.executeUpdate();

			// 3. create statement
			String sql = "DELETE FROM teacher WHERE teacherId=?";
			ps = con.prepareStatement(sql);

			// set parameter value
			ps.setInt(1, teacherId);

			// 4. execute query
			ps.executeUpdate();

			// 5. close connection
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// get teacher by id
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
	                fullTime ft = new fullTime();

	                // Ambil salary dari table FullTime
	                PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM FullTime WHERE teacherId = ?");
	                ps2.setInt(1, teacherId);
	                ResultSet rs2 = ps2.executeQuery();
	                if (rs2.next()) {
	                    ft.setSalary(rs2.getDouble("salary"));
	                }
	                
	                // Set semua common fields
	                ft.setTeacherId(rs.getInt("teacherId"));
	                ft.setTeacherName(rs.getString("teacherName"));
	                ft.setTeacherEmail(rs.getString("teacherEmail"));
	                ft.setTeacherPass(rs.getString("teacherPass"));
	                ft.setTeacherPhone(rs.getString("teacherPhone"));
	                ft.setTeacherRole(rs.getString("teacherRole"));
	                ft.setTeacherType(rs.getString("teacherType"));
	                ft.setAdminId(rs.getInt("adminId"));
	                Blob blobPhoto = rs.getBlob("teacherPhoto");
	                if (blobPhoto != null) {
	                    ft.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
	                }

	                teach = ft;

	            } else if ("PartTime".equalsIgnoreCase(teacherType)) {
	                partTime pt = new partTime();

	                // Ambil contract dari table PartTime
	                PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM PartTime WHERE teacherId = ?");
	                ps2.setInt(1, teacherId);
	                ResultSet rs2 = ps2.executeQuery();
	                if (rs2.next()) {
	                    pt.setContract(rs2.getInt("contract"));
	                }
	                
	                // Set semua common fields
	                pt.setTeacherId(rs.getInt("teacherId"));
	                pt.setTeacherName(rs.getString("teacherName"));
	                pt.setTeacherEmail(rs.getString("teacherEmail"));
	                pt.setTeacherPass(rs.getString("teacherPass"));
	                pt.setTeacherPhone(rs.getString("teacherPhone"));
	                pt.setTeacherRole(rs.getString("teacherRole"));
	                pt.setTeacherType(rs.getString("teacherType"));
	                pt.setAdminId(rs.getInt("adminId"));
	                Blob blobPhoto = rs.getBlob("teacherPhoto");
	                if (blobPhoto != null) {
	                    pt.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
	                }


	                teach = pt;

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
				Blob blobPhoto = rs.getBlob("teacherPhoto");
				if (blobPhoto != null) {
				    teach.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teach;
	}

	// get all teacher
	public static List<teacher> getAllTeacher() {

		List<teacher> teachers = new ArrayList<teacher>();
		// complete the code here
		try {
			// call getConnection() method
			con = ConnectionManager.getConnection();

			// 3. create statement
			sql = "SELECT * FROM teacher ORDER BY teacherId";
			ps = con.prepareStatement(sql);

			// 4. execute query
			rs = ps.executeQuery();

			// process ResultSet
			while (rs.next()) {

				String teacherType = rs.getString("teacherType");
				teacher teach = new teacher();

				if ("FullTime".equalsIgnoreCase(teacherType)) {

					teach = new fullTime();

					PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM FullTime WHERE teacherId=?");
					ps2.setInt(1, rs.getInt("teacherId"));
					ResultSet rs2 = ps2.executeQuery();

					if (rs2.next()) {
						((fullTime) teach).setSalary(rs2.getDouble("salary"));
					}

				} else if ("PartTime".equalsIgnoreCase(teacherType)) {

					teach = new partTime();

					PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM PartTime WHERE teacherId=?");
					ps2.setInt(1, rs.getInt("teacherId"));
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
				Blob blobPhoto = rs.getBlob("teacherPhoto");
				if (blobPhoto != null) {
				    teach.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
				}
				teachers.add(teach);

			}

			// 5. close connection
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return teachers;
	}

	// get all admin
	public static List<teacher> getAllAdmins() {

		List<teacher> admins = new ArrayList<teacher>();

		try {// call getConnection() method
			con = ConnectionManager.getConnection();

			sql = "SELECT teacherId, teacherName FROM teacher WHERE teacherRole = 'Admin'";

			ps = con.prepareStatement(sql);

			// 4. execute query
			rs = ps.executeQuery();

			// process ResultSet
			while (rs.next()) {
				teacher admin = new teacher();
				admin.setTeacherId(rs.getInt("teacherId"));
				admin.setTeacherName(rs.getString("teacherName"));
				admins.add(admin);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return admins;

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
				int teacherId = rs.getInt("teacherId");
				teach = getTeacher(teacherId); // auto detect fullTime / partTime
				
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teach;
	}

}
