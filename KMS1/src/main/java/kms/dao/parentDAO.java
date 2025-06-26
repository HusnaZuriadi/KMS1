package kms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kms.connection.ConnectionManager;
import kms.model.*;

public class parentDAO {
	
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;
	
	//insert student
	public static void addParent(parent p){
		try {
			//call getConnection() method
			con = ConnectionManager.getConnection();
			
			//3. create statement
			sql = "INSERT INTO parent( parentName, parentEmail, parentPass, parentPhone) VALUES ( ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			
			//get values from student object and set parameter values
			ps.setString(1, p.getParentName());
			ps.setString(2, p.getParentEmail());
			ps.setString(3, p.getParentPass());
			ps.setString(4, p.getParentPhone());
			
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
		public static void updateParent(parent p){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement
				sql = "UPDATE parent SET parentName=?,parentEmail=?,parentPass=?,parentPhone=? WHERE parentId=?";
				ps = con.prepareStatement(sql);

				//get values from parent object and set parameter values
				ps.setString(1, p.getParentName());
				ps.setString(2, p.getParentEmail());
				ps.setString(3, p.getParentPass());
				ps.setString(4, p.getParentPhone());
				ps.setInt(5, p.getParentId());

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}
		}


		//delete shawl
		public static void deleteParent(int parentId){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement
				String sql = "DELETE FROM p WHERE id=?";
				ps = con.prepareStatement(sql);

				//set parameter value
				ps.setInt(1, parentId);

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}	

	   }
		
		//get parent by id
		public static parent getParent(int parentId) {
			parent p = new parent();
			try {
				con = ConnectionManager.getConnection();
				sql = "SELECT * FROM parent WHERE parentId = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, parentId);
				rs = ps.executeQuery();

				if (rs.next()) {
					
			            p.setParentId(rs.getInt("parentId"));
			            p.setParentName(rs.getString("name"));
			            p.setParentEmail(rs.getString("email"));
			            p.setParentPass(rs.getString("password"));
			            p.setParentPhone(rs.getString("phone"));
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return p;
		}
		
		//get all student
		public static List<parent> getAllParent(){
			
			List<parent> parents = new ArrayList<parent>();
			//complete the code here
			try {
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement 
				sql = "SELECT * FROM parent ORDER BY parentId";
				ps = con.prepareStatement(sql);

				//4. execute query
				rs = ps.executeQuery();

				//process ResultSet
				while(rs.next()) {		
					parent p = new parent();
			            p.setParentId(rs.getInt("parentId"));
			            p.setParentName(rs.getString("name"));
			            p.setParentEmail(rs.getString("email"));
			            p.setParentPass(rs.getString("password"));
			            p.setParentPhone(rs.getString("phone"));


				}

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}

			return parents;
		}
		
		public static parent validate(String email, String password) {
		    parent p = null;
		    try {
		        con = ConnectionManager.getConnection();
		        sql = "SELECT * FROM parent WHERE parentEmail = ? AND parentPass = ?";
		        ps = con.prepareStatement(sql);
		        ps.setString(1, email);
		        ps.setString(2, password);
		        rs = ps.executeQuery();

		        if (rs.next()) {
		        	 p = new parent();
		             p.setParentId(rs.getInt("parentId"));
		             p.setParentName(rs.getString("parentName"));
		             p.setParentEmail(rs.getString("parentEmail"));
		             p.setParentPass(rs.getString("parentPass"));
		             p.setParentPhone(rs.getString("parentPhone"));
		        }

		        con.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return p;
		}

}