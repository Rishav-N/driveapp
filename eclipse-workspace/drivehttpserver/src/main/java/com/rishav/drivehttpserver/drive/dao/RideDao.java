package com.rishav.drivehttpserver.drive.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mysql.cj.xdevapi.Statement;
import com.rishav.drivehttpserver.drive.Car;

import com.rishav.drivehttpserver.drive.Ride;

public class RideDao {
	public Ride getRide(int id) {
		try {
			ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Ride WHERE id=" + id);
            if(rs.next())
            {
                Ride ride = new Ride( rs.getInt("CarId"), rs.getInt("DriverId"), rs.getInt("Miles"), Ride.Status.valueOf(rs.getString("Status")),  rs.getDouble("Price"),  rs.getInt("StartId"), rs.getInt("EndId"));
                
                return ride;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    return null;
	}

	private List<Ride> extractRideFromResultSet(ResultSet rs) throws SQLException {
		List<Ride> rides = new ArrayList<Ride>();
        while(rs.next())
        {
            Ride ride =  new Ride( rs.getInt("CarId"), rs.getInt("DriverId"), rs.getInt("Miles"), Ride.Status.valueOf(rs.getString("Status")),  rs.getDouble("Price"), rs.getInt("StartId"), rs.getInt("EndId"));
            rides.add(ride);
        }
        return rides;
	}
	
	public List<Ride> getAllRides() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Ride");
	        return extractRideFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public boolean insertRide(Ride ride) throws Exception {
	    try {
	    	String SqlRide = ("INSERT INTO Ride(CarId, DriverId, Miles, Status, Price, StartId, EndId) Values (" + ride.CarID() + "," + ride.DriverID() + "," +  ride.Miles() + ",'" +  ride.Status() + "'," + ride.Price() + "," + ride.StartID() + "," + ride.EndID() + ");");
	    	System.out.println(SqlRide);
	    	MySQLAccess.getInstance().Write(SqlRide);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean UpdateRide(Ride ride) throws Exception {
	    try {
	    	String SqlRide = ("UPDATE Ride SET (" + ride.CarID() + "," + ride.DriverID() + "," +  ride.Miles() + "," +  ride.Status() + "," + ride.Price() + "," + ride.StartID() + "," + ride.EndID() + ");");
	    	MySQLAccess.getInstance().Write(SqlRide);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean DeleteRide(int id) throws Exception {
	    try {
	    	String SqlRide = ("DELETE FROM Ride WHERE id=" + id + ";");
	    	MySQLAccess.getInstance().Write(SqlRide);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
}