package com.rishav.drivehttpserver.core;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;

import com.rishav.drivehttpserver.drive.User;

public class HttpSession {
	public void setSession(HttpExchange httpExchange, User user) {
		httpExchange.getResponseHeaders().add("Set-Cookie", "session=12345"); 
	}
	
	public Boolean checkSession(HttpExchange httpExchange) {
		List<String> sessionId = httpExchange.getRequestHeaders().get("Cookie");
		if(sessionId.get(0) == "12345") {
			return true;
		}
		
		return false;
	}
}
