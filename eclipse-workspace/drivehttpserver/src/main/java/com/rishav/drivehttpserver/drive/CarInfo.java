package com.rishav.drivehttpserver.drive;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rishav.drivehttpserver.drive.Car;
import com.rishav.drivehttpserver.drive.dao.MySQLAccess;

import com.rishav.drivehttpserver.drive.dao.*;

public class CarInfo implements Serializable {
	
	private MySQLAccess mysqlaccess;
	int Miles;
	HashMap<String , ArrayList <Car>> CarInfo;
	
	int location1 = 5;
	int location2 = 10;
	int location3 = 15;
	int location4 = 20;
	
	int PreviousLocation = 0;
	
	double Time;
	public CarInfo() {
		try {
			mysqlaccess = new MySQLAccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Car> GetSuv() throws Exception {
		CarDao dao = new CarDao();
		return dao.getSuvCars();
	}
	
	public List<Car> GetStandard() throws Exception {
		CarDao dao = new CarDao();
		List<Car> Standard = new ArrayList<Car>();
		Standard = dao.getStandardCars();
		
		return Standard;
	}
	
	public List<Car> GetLuxury() throws Exception {
		CarDao dao = new CarDao();
		List<Car> Luxury = new ArrayList<Car>();
		Luxury = dao.getLuxuryCars();
		
		return Luxury;
	}
	
	public List<Car> GetMinivan() throws Exception {
		CarDao dao = new CarDao();
		List<Car> Minivan = new ArrayList<Car>();
		Minivan = dao.getMinivanCars();
		
		return Minivan;
	}
	
	public int AddMiles(int location ) {
		switch (location) {
		//gets distance from the previous location to the new one
		case 1:
			Miles += Math.abs(location1 - PreviousLocation);
			PreviousLocation = location1;
			break;
		case 2:
			Miles += Math.abs(location2 - PreviousLocation);
			PreviousLocation = location2;
			break;
		case 3:
			Miles += Math.abs(location3 - PreviousLocation);
			PreviousLocation = location3;
			break;
		case 4:
			Miles += Math.abs(location4 - PreviousLocation);
			PreviousLocation = location4;
			break;
		}
		return Miles;
	}
	public int RoundTrip() {
		//Multiplies Miles by 2 to make it a round trip
		Miles *= 2;
		return Miles;
	}
	public double TimeHours() {
		//Finds the time in hours
		//distance/speed = time
		
		Time = Math.round((Miles / 30.0) * 100 ) / 100.0 ;
		//hours
		
		return Time;
	}
	public int TimeMins() {
		//Finds the Time in mins
		Time *= 60;
		return (int) Time;
	}
	public ArrayList<String> GetLocations() {
		//Returns all the locations
		ArrayList<String> locations = new ArrayList<String>();
		
		locations.add("Location 1");
		locations.add("Location 2");
		locations.add("Location 3");
		locations.add("Location 4");
		
		return locations;
	}
	public int Miles() {
		//returns Miles
		return Miles;
	}
	//come back to this
	public double Cost(String Car) throws Exception {
		//Finds the cost of the ride and returns it
		double cost = 0;
		
		CarDao cardao = new CarDao();
		
		double price = cardao.getDoubleValue("Price", "Name", Car) * (Miles);
		cost = (Math.round(price * 100 )/ 100.0);
		
		return cost;
	}
	public void ResetDefault() {
		//Resets these variables to 0
		Miles = 0;
		Time = 0;
		PreviousLocation = 0;
	}
	public void AddTimeBought(String car) {
		//Adds 1 to amount of times a car was bought
		
	}
	
	public String GetSuvValue(int index) {
		//Gets the Name of Suv
		return CarInfo.get("Suv").get(index).Name();
	}
	public String GetStandardValue(int index) {
		//Gets the Name of Standard
		return CarInfo.get("Standard").get(index).Name();
	}
	public String GetLuxuryValue(int index) {
		//Gets the Name of Luxury
		return CarInfo.get("Luxury").get(index).Name();
	}
	public String GetMinivanValue(int index) {
		//Gets the Name of Minivan
		return CarInfo.get("Minivan").get(index).Name();
	}
	private void writeResultSetCar(ResultSet resultSet, String car) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String Car = resultSet.getString(car);
            System.out.println(Car);
        }
    }
}
