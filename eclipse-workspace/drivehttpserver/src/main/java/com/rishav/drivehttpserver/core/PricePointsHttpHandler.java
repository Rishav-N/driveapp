package com.rishav.drivehttpserver.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.rishav.drivehttpserver.drive.dao.*;
import com.sun.net.httpserver.HttpExchange;
import com.rishav.drivehttpserver.drive.*;


public class PricePointsHttpHandler implements com.sun.net.httpserver.HttpHandler{
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		// TODO Auto-generated method stub
		String requestParamValue=null; 
		
		    if("GET".equals(httpExchange.getRequestMethod())) { 
		
		       requestParamValue = handleGetRequest(httpExchange);
		
		     /*}else if("POST".equals(httpExchange)) { 
		
		       requestParamValue = handlePostRequest(httpExchange);       */ 
		
		    }
		    handleResponse(httpExchange,requestParamValue);
		
	}
	
	private String handleGetRequest(HttpExchange httpExchange) {
		return httpExchange.
		getRequestURI()
		.toString()
		.split("\\?")[1]
		.split("=")[1];
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
			    .append(pricePoints())
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
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		outputStream.close();
	}
	
	public String pricePoints() throws Exception {
		CarDao cardao = new CarDao();
		List<Car> pricepoints = cardao.getAllCars();
		String pp = new String();
		pp += "<head>";
		pp += "<title>Price Points</title>";
		pp += "</head>";
		pp += "<body>";
		pp += "<blockquote>";
		for (Car i : pricepoints) {
			pp += "<p>" + i.Name() + " : " + i.Price() + "$ per mile : " + i.TimesBought() + "</p>";
		}
		pp += "</blockquote>";
		pp += "</body>";
		
		return pp;
	}
}
