package com.rishav.drivehttpserver.drive;

public class Location {
	int _id;
	String _address;
	int _coordinates;
	
	public Location (int id, String address, int coordinates) {
		_id = id;
		_address = address;
		_coordinates = coordinates;
	}
	public int Id() {
		return _id;
	}
	public String Address() {
		return _address;
	}
	public int Coordinates() {
		return _coordinates;
	}
}
