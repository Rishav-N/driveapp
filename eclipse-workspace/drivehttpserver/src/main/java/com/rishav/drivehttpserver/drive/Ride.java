package com.rishav.drivehttpserver.drive;

public class Ride {
	private int _miles;
	private Status _status;
	private double _price;
	private int _carID;
	private int _driverID;
	private int _startID;
	private int _endID;
	
	public enum Status {
		Created,
		Started,
		Stopped,
		Cancelled,
		Completed
	};
	
	public Ride( int carid, int driverid, int miles, Ride.Status status, double price, int startid, int endid) {
		_miles = miles;
		_status = status;
		_price = price;
		_carID = carid;
		_driverID = driverid;
		_startID = startid;
		_endID = endid;
	}
	
	public String Description() {
		return  "\"" + _carID + "\"," + "\"" + _driverID + "\"," + "\"" + _miles + "\"" + "\"" + _status + "\"," + "\"" + _price + "\"," + "\"" + _startID + "\"," + "\"" + _endID + "\"";
	}
	
	public int Miles() {
		return _miles;
	}
	public Status Status() {
		return _status;
	}
	public double Price() {
		return _price;
	}
	public int CarID() {
		return _carID;
	}
	public int DriverID() {
		return _driverID;
	}
	public int StartID() {
		return _startID;
	}
	public int EndID() {
		return _endID;
	}
	
}
