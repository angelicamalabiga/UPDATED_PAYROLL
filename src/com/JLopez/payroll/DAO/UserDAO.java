package com.JLopez.payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.sql.Statement;

import com.JLopez.payroll.database.DatabaseConnection;
import com.JLopez.payroll.model.User;

public class UserDAO {
	
public int create(User user) {
	
		
		String sql = """
				INSERT INTO users 
					(username, first_name, last_name, middle_name, password, email, 
					role, birth_date, gender, nationality, house_no, barangay, city, province,
					zip_code, contact_no, emergency_no1, emergency_no2, position, 
					joining_date, status, basic_salary, photo, created_at, updated_at ) 
				VALUES 
					(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";
		
		int generatedId = -1; 
		
		 try (Connection conn = DatabaseConnection.getInstance().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			

			 ps.setString(1, user.getUsername());
	            ps.setString(2, user.getFirstName());
	            ps.setString(3, user.getLastName());
	            ps.setString(4, user.getMiddleName());
	            ps.setString(5, user.getPassword());
	            ps.setString(6, user.getEmail());
	            ps.setString(7, user.getRole());
	            
	            // LocalDate → SQL Date
	            if (user.getBirthDate() != null)
	                ps.setDate(8, Date.valueOf(user.getBirthDate()));
	            else
	                ps.setNull(8, java.sql.Types.DATE);
	            
	            ps.setString(9, user.getGender());
	            ps.setString(10, user.getNationality());
	            ps.setString(11, user.getHouseNo());
	            ps.setString(12, user.getBarangay());
	            ps.setString(13, user.getCity());
	            ps.setString(14, user.getProvince());
	            ps.setString(15, user.getZipCode());
	            ps.setString(16, user.getContactNo());
	            ps.setString(17, user.getEmergencyNo1());
	            ps.setString(18, user.getEmergencyNo2());
	            ps.setString(19, user.getPosition());
	            
	           //joiningdate
	            if (user.getJoiningDate() != null)
	                ps.setDate(20, Date.valueOf(user.getJoiningDate()));
	            else
	                ps.setNull(20, java.sql.Types.DATE);
	            
	            //created_at
	            if (user.getCreatedAt() != null)
	                ps.setDate(24, Date.valueOf(user.getCreatedAt()));
	            else
	                ps.setDate(24, new Date(System.currentTimeMillis()));
	            //updated_at
	            if (user.getUpdatedAt() != null)
	                ps.setTimestamp(25, java.sql.Timestamp.valueOf(user.getUpdatedAt()));
	            else
	                ps.setTimestamp(25, new java.sql.Timestamp(System.currentTimeMillis()));

          
            
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                // Retrieve the auto-generated key
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        user.setId(generatedId);
                    }
                }
            }

            	
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		 return generatedId;
	}
	
	// NEW METHOD - Login using Employee ID
      public User getUserById(int userId) {
          String sql = """
            SELECT 
                id, email, username, first_name, last_name, role, status
            FROM users
            WHERE id = ?
            """;

    User user = null;
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));  
                user.setBasicSalary(rs.getDouble("basic_salary"));
                
                Date birth = rs.getDate("birth_date");
                if (birth != null) user.setBirthDate(birth.toLocalDate());
                
                Date joinDate = rs.getDate("joining_date");
                if (joinDate != null) user.setJoiningDate(joinDate.toLocalDate());

            }
            
            return user;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
		
	}
      
      
      
   // LOGIN METHOD
      public User getUserByIdAndPassword(String userIdOrUsername, String password) {
    	    String sqlById = "SELECT * FROM users WHERE id = ? AND password = ?";
    	    String sqlByUsername = "SELECT * FROM users WHERE username = ? AND password = ?";
    	    User user = null;

    	    try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
    	        PreparedStatement ps;

    	        try {
    	            int id = Integer.parseInt(userIdOrUsername);
    	            ps = conn.prepareStatement(sqlById);
    	            ps.setInt(1, id);
    	            ps.setString(2, password);
    	        } catch (NumberFormatException e) {
    	            ps = conn.prepareStatement(sqlByUsername);
    	            ps.setString(1, userIdOrUsername);
    	            ps.setString(2, password);
    	        }

    	        try (ResultSet rs = ps.executeQuery()) {
    	            if (rs.next()) {
    	                user = new User();
    	                user.setId(rs.getInt("id"));
    	                user.setEmail(rs.getString("email"));
    	                user.setUsername(rs.getString("username"));
    	                user.setFirstName(rs.getString("first_name"));
    	                user.setMiddleName(rs.getString("middle_name"));
    	                user.setLastName(rs.getString("last_name"));
    	                user.setRole(rs.getString("role"));
    	                user.setStatus(rs.getString("status"));
    	                user.setBasicSalary(rs.getDouble("basic_salary"));

    	                java.sql.Date birth = rs.getDate("birth_date");
    	                if (birth != null) user.setBirthDate(birth.toLocalDate());
    	                java.sql.Date join = rs.getDate("joining_date");
    	                if (join != null) user.setJoiningDate(join.toLocalDate());
    	            }
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }

    	    return user;
    	}
}
