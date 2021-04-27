package com.rishav.drivehttpserver.drive.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rishav.drivehttpserver.drive.Location;
import com.rishav.drivehttpserver.drive.Ride;

public class LocationDao {
	public Location getLocation(int id) {
		try {
			ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Locations WHERE id=" + id);
            if(rs.next())
            {
                Location location = new Location(rs.getInt("id"), rs.getString("Address"), rs.getInt("Coordinates"));
                
                return location;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    return null;
	}

	private List<Location> extractLocationFromResultSet(ResultSet rs) throws SQLException {
		List<Location> locations = new ArrayList<Location>();
        while(rs.next())
        {
        	Location location =  new Location(rs.getInt("id"), rs.getString("Address"), rs.getInt("Coordinates"));
        	locations.add(location);
        }
        return locations;
	}
	
	public List<Location> getAllLocations() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Locations");
	        return extractLocationFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public boolean insertLocation(Location location) throws Exception {
	    try {
	    	String SqlLocation = ("INSERT INTO Locations(CarId, DriverId, Miles, Status, Price) Values (" + location.Address() + "," + location.Coordinates() + ");");
	    	MySQLAccess.getInstance().Write(SqlLocation);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean UpdateLocation(Location location) throws Exception {
	    try {
	    	String SqlLocation = ("UPDATE Locations SET (" + location.Address() + "," + location.Coordinates() + ");");
	    	MySQLAccess.getInstance().Write(SqlLocation);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean DeleteLocation(int id) throws Exception {
	    try {
	    	String SqlLocation = ("DELETE FROM Locations WHERE id=" + id + ";");
	    	MySQLAccess.getInstance().Write(SqlLocation);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
}
