package com.rishav.drivehttpserver.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rishav.drivehttpserver.drive.*;
import com.rishav.drivehttpserver.drive.Ride.Status;
import com.rishav.drivehttpserver.drive.dao.*;
import com.sun.net.httpserver.HttpExchange;

public class OrderHttpHandler implements com.sun.net.httpserver.HttpHandler{
	
	private static Logger LOGGER = LoggerFactory.getLogger(LoginSubmitHttpHandler.class);
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		// TODO Auto-generated method stub
		String requestParamValue=null; 
		HttpSession httpsession = new HttpSession();
		LOGGER.info("#" + httpExchange.toString());
		
		if (httpsession.checkSession(httpExchange) == true) {
		
		    if("GET".equals(httpExchange.getRequestMethod())) { 
		
		       requestParamValue = handleGetRequest(httpExchange);
		
		     /*}else if("POST".equals(httpExchange)) { 
		
		       requestParamValue = handlePostRequest(httpExchange);       */ 
		
		    }
		    handleResponse(httpExchange,requestParamValue);
		    
		} else {
			returnToLogin(httpExchange);
		}
		
	}
	
	private void returnToLogin(HttpExchange httpExchange) {
		httpExchange.getResponseHeaders().add("Location", "/login");
		try {
			httpExchange.sendResponseHeaders(302, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String handleGetRequest(HttpExchange httpExchange) {
		return httpExchange.
		getRequestURI()
		.toString();
	}
	
	private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
		OutputStream outputStream = httpExchange.getResponseBody();
		StringBuilder htmlBuilder = new StringBuilder();
		try {
			htmlBuilder.append("<html>")
				.append("<body>")
			    .append("<h1>")
			    .append("Hello ")
			    .append(requestParamValue)
			    .append("</h1>")
			    .append(html())
			    .append("</body>")
			    .append("</html>");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// encode HTML content 
		String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
		// this line is a must
		
		httpExchange.sendResponseHeaders(200, htmlResponse.length());
		outputStream.write(htmlBuilder.toString().getBytes());
		outputStream.flush();
		outputStream.close();
	}
	
	public String html() throws Exception {
		String html;
		
		html = getUserChoiceCarType();
		html += "\r\n";
		html += getUserChoiceCar();
		html += "\r\n";
		html += getStartLocation();
		html += getEndLocation();
		
		return html;
	}
	
	public String getUserChoiceCarType() {

		CarTypeDao dao = new CarTypeDao();
		List<String> cartypes = null;
		try {
			cartypes = dao.getCarTypes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String cartypestr = new String();
		cartypestr = "<p><label>Select Car Type&nbsp;</label><select id=\"myList\">";
		int c = 1;
		for(String i : cartypes) {
			cartypestr += "<option value=\"" + c + "\">" + i + "</option>";
			c++;
		}
		cartypestr += "</select></p>";
		
		return cartypestr;
	}
	
	public String getUserChoiceCar() {
		List<String> carlist = new ArrayList<String>();
		
		carlist.add("Cadillac Escalade");
		carlist.add("Mercedes GL class");
		carlist.add("Lexus LX");
		
		String carstr = new String();
		carstr = "<p><label>Select Car&nbsp;</label><select id=\"myList\">";
		int c = 1;
		for(String i : carlist) {
			carstr += "<option value=\"" + c + "\">" + i + "</option>";
			c++;
		}
		carstr += "</select></p>";
		
		return carstr;
	}
	
	public Car getUserChoiceCar(String carType) {
		try {
			CarDao carDao = new CarDao();
			List<Car> carlist = carDao.getCars(carType);
			
			System.out.println("");
			int c = 1;
			for (Car car : carlist) {
				System.out.println(c + ". " + car.Description());
				c++;
			}
			System.out.print("Input: ");
			Scanner Obj = new Scanner(System.in);
			int UserChoice = Obj.nextInt();
			
			return carlist.get(UserChoice - 1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getStartLocation() throws Exception {
		LocationDao locationdao = new LocationDao();
		List<Location> locations = null;
		locations = locationdao.getAllLocations();
		
		String locationstr = new String();
		locationstr = "<p><label>Select Start Location&nbsp;</label><select id=\"myList\">";
		int c = 1;
		for(Location i : locations) {
			locationstr += "<option value=\"" + c + "\">" + i.Address() + "</option>";
			c++;
		}
		locationstr += "</select></p>";
		
		return locationstr;
	}
	
	public String getEndLocation() throws Exception {
		LocationDao locationdao = new LocationDao();
		List<Location> locations = null;
		locations = locationdao.getAllLocations();
		
		String locationstr = new String();
		locationstr = "<p><label>Select End Location&nbsp;</label><select id=\"myList\">";
		int c = 1;
		for(Location i : locations) {
			locationstr += "<option value=\"" + c + "\">" + i.Address() + "</option>";
			c++;
		}
		locationstr += "</select></p>";
		
		return locationstr;
	}
	
	public int getMiles(int start, int end, boolean roundtrip) {
		if (roundtrip == true) {
			return 2 * Math.abs(end - start);
		}
		else {
			return Math.abs(end - start);
		}	
	}
	public boolean getRoundTrip() {
		System.out.println("Are you taking a round trip? Yes or No");
		Scanner myObj4 = new Scanner(System.in);
		String RoundTrip = myObj4.nextLine();
				
		if (RoundTrip.equalsIgnoreCase("Yes")) {
			return true;
		}
		return false;
	}
	public double getTimeHours(int Miles) {
		double Time = Math.round((Miles / 30.0) * 100 ) / 100.0 ;
		
		return Time;
	}
	public double getTimeMins(double TimeHours) {
		TimeHours *= 60;
		return (int) TimeHours;
	}
	public double getCost(String Car, int Miles) throws Exception {
		//Finds the cost of the ride and returns it
		double cost = 0;
		
		CarDao cardao = new CarDao();
		
		System.out.println(Car);
		
		double price = cardao.getDoubleValue("Price", "Name", Car) * (Miles);
		cost = (Math.round(price * 100 )/ 100.0);
		
		return cost;
	}
	public void finalPurchase(int miles, double timeHours, double timeMins, double cost, Car car, int start, int end) throws Exception {
		System.out.println("You are going " + miles + " miles. It will take " + timeHours + " hours or " + timeMins + " minutes.");
		System.out.println("");
		System.out.println("Your Final Cost is " + cost + "$ ");
				
		System.out.println("Type 1 to confirm your purchase. Type 2 to cancel the purchase.");
				
		Scanner myObj5 = new Scanner(System.in);
		int ConfirmPurchase = myObj5.nextInt();
		
		if (ConfirmPurchase == 1) {
			RideDao ridedao = new RideDao();
			
			Ride.Status status = Status.Created;
			Ride newride = (new Ride(car.Id() , 2 , miles , status , cost, start, end));
			
			ridedao.insertRide(newride);
			
		}
		else {
			System.out.println("Purchase Canceled.");
		}
	}
}
