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

public class CarDao {
	public Car getCar(int id) {
		try {
			ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car WHERE id=" + id);
            if(rs.next())
            {
                Car car = new Car(rs.getInt("Seats"), rs.getString("Name"), rs.getDouble("Price"), rs.getInt("TimesUsed"), rs.getString("Type"));
                
                return car;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    return null;
	}

	private List<Car> extractCarFromResultSet(ResultSet rs) throws SQLException {
		List<Car> cars = new ArrayList<Car>();
        while(rs.next())
        {
            Car car =  new Car(rs.getInt("id"), rs.getInt("Seats"), rs.getString("Name"), rs.getDouble("Price"), rs.getInt("TimesUsed"), rs.getString("Type"));
            cars.add(car);
        }
        return cars;
	}
	
	public List<Car> getAllCars() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car");
	        return extractCarFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public List<Car> getCars(String cartype) throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car where type='" + cartype + "'");
	        return extractCarFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	public List<Car> getSuvCars() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car where type='Suv'");
	        return extractCarFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public List<Car> getStandardCars() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car where type ='Standard'");
	        return extractCarFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public List<Car> getLuxuryCars() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car where type ='Luxury'");
	        return extractCarFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public List<Car> getMinivanCars() throws Exception {
	    try {
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT * FROM Car where type ='Minivan'");
	        return extractCarFromResultSet(rs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public boolean insertCar(Car car) throws Exception {
	    try {
	    	String SqlCar = ("INSERT INTO Car(Seats, Name, Price, TimesUsed, Type,) Values (" + car.Seats() + car.Name() + car.Price() + car.TimesBought() + car.Type() + ");");
	    	MySQLAccess.getInstance().Write(SqlCar);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean UpdateCar(Car car) throws Exception {
	    try {
	    	String SqlCar = ("UPDATE Car SET (" + car.Seats() + car.Name() + car.Price() + car.TimesBought() + car.Type() + ")");
	    	MySQLAccess.getInstance().Write(SqlCar);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean UpdateCarWhere(Car car, String column, String value) throws Exception {
	    try {
	    	String SqlCar = ("UPDATE Car SET (" + car.Seats() + car.Name() + car.Price() + car.TimesBought() + car.Type() + ") WHERE" + column + "='" + value + "';");
	    	MySQLAccess.getInstance().Write(SqlCar);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean DeleteCar(int id) throws Exception {
	    try {
	    	String SqlCar = ("DELETE FROM Car WHERE id=" + id + ";");
	    	MySQLAccess.getInstance().Write(SqlCar);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public boolean AddTimesUsed(Car car) throws Exception {
		try {
	    	String SqlCar = ("UPDATE Car SET (" + car.Seats() + car.Name() + car.Price() + (car.TimesBought() + 1) + car.Type() + ")");
	    	MySQLAccess.getInstance().Write(SqlCar);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public double getDoubleValue(String field1, String field2, String value) throws Exception {
		try {
			double dbValue = 0.0;
			System.out.println("SELECT " + field1 +  " FROM Car where" + field2 + "='" + value + "';");
	        ResultSet rs = MySQLAccess.getInstance().Read("SELECT " + field1 +  " FROM Car where " + field2 + "='" + value + "';");
	       
	        if(rs.next()) {
	        	dbValue = rs.getDouble(1);
            }
	        return dbValue;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return 0.0;
	}
}
