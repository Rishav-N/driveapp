package com.rishav.drivehttpserver.drive;
import java.io.Serializable;

public class Car implements Serializable{
	String Name;
	int Seats;
	double Price;
	String Type;
	boolean Used;
	int TimesBought;
	int _id;
	
	public Car (int seats, String name, double price, int timesbought, String type) {
		Name = name;
		Seats = seats;
		Price = price;
		Type = type;
		TimesBought = timesbought;
	}
	public Car (int id, int seats, String name, double price, int timesbought, String type) {
		Name = name;
		Seats = seats;
		Price = price;
		Type = type;
		TimesBought = timesbought;
		_id = id;
	}
	public String Description() {
		return Type + " : " + Name + " : " + Seats + " seats : " + Price + "$ per mile";
	}
	public String Name() {
		return Name;
	}
	public double Price() {
		return Price;
	}
	public String Type() {
		return Type;
	}
	public int Seats() {
		return Seats;
	}
	public int TimesBought() {
		return TimesBought;
	}
	public void AddTimeBought() {
		TimesBought ++;
	}
	public int Id() {
		return _id;
	}
}
