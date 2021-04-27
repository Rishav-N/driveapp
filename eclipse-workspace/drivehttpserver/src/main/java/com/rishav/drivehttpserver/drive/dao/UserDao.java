package com.rishav.drivehttpserver.drive.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.xdevapi.Statement;
import com.rishav.drivehttpserver.core.LoginSubmitHttpHandler;
import com.rishav.drivehttpserver.drive.Car;

import com.rishav.drivehttpserver.drive.User;

public class UserDao {
	private static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
	
	public User getUser(int id) {
		try {
			ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM User WHERE id=" + id);
            if(rs.next())
            {
                User user = new User(rs.getString("Username"), rs.getString("Email"), rs.getBoolean("Driver"), rs.getString("Password"));
                
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    return null;
	}

	private List<User> extractUserFromResultSet(ResultSet rs) throws SQLException {
		List<User> users = new ArrayList<User>();
        while(rs.next())
        {
            User user =  new User(rs.getString("Username"), rs.getString("Email"), rs.getBoolean("Driver"), rs.getString("Password"));
            users.add(user);
        }
        return users;
	}
	
	 public User checkLogin(String email, String password) {
		 ResultSet rs = null;
		 try {
			
			rs = MySQLAccess.getInstance().Read("SELECT * FROM User WHERE email ='" + email + "' and password ='" + password + "'");
			
			if (rs.next()) {
			     User user = new User(rs.getString("Username"), rs.getString("Email"), rs.getBoolean("Driver"), rs.getString("Password"));
			     return user;
			 }
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		
		 return null;
	}
	
	public List<User> getAllUsers() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM User");
	        return extractUserFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}

	public boolean insertUser(User user) throws Exception {
	    try {
	    	String SqlUser = ("INSERT INTO Car(Username, Email, Driver) Values (" + user.Username() + user.Email() + user.Driver() + ");");
	    	MySQLAccess.getInstance().Write(SqlUser);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean UpdateUser(User user) throws Exception {
	    try {
	    	String SqlUser = ("UPDATE User SET (" + user.Username() + user.Email() + user.Driver() + ");");
	    	MySQLAccess.getInstance().Write(SqlUser);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean DeleteUser(int id) throws Exception {
	    try {
	    	String SqlUser = ("DELETE FROM User WHERE id=" + id + ";");
	    	MySQLAccess.getInstance().Write(SqlUser);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
}