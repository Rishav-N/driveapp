package com.rishav.drivehttpserver.drive.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarTypeDao {
	private List<String> extractCarTypesFromResultSet(ResultSet rs) throws SQLException {
		List<String> carsTypes = new ArrayList<String>();
        while(rs.next())
        {
            String CarTypes =  rs.getString("Types");
            carsTypes.add(CarTypes);
        }
        return carsTypes;
	}
	
	public List<String> getCarTypes() throws Exception {
		 try {
		        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM CarType");
		        return extractCarTypesFromResultSet(rs);
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		    return null;
	}
}
