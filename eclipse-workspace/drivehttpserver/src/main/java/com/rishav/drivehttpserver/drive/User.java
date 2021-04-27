package com.rishav.drivehttpserver.drive;

public class User {
	private String username;
	private String email;
	private String password;
	private boolean driver;
	
	public User(String username, String email, boolean driver, String password) {
		this.username = username;
		this.email = email;
		this.driver = driver;
		this.password = password;
	}
	
	public String Username() {
		return username;
	}
	public String Email() {
		return email;
	}
	public boolean Driver() {
		return driver;
	}
	public String Password() {
		return password;
	}
}
