package com.rishav.drivehttpserver.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rishav.drivehttpserver.DriveHttpServer;
import com.sun.net.httpserver.HttpExchange;

public class RootHttpHandler implements com.sun.net.httpserver.HttpHandler{
	private static Logger LOGGER = LoggerFactory.getLogger(RootHttpHandler.class);
	
	protected String webroot;
	
	public RootHttpHandler(String webroot) {
		this.webroot = webroot;
	}
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		// TODO Auto-generated method stub
		String requestParamValue=null; 
		if("GET".equals(httpExchange.getRequestMethod())) { 
			
		       requestParamValue = handleGetRequest(httpExchange);
		       LOGGER.info(requestParamValue);
		
		     /*}else if("POST".equals(httpExchange)) { 
		
		       requestParamValue = handlePostRequest(httpExchange);       */ 
		
		    }
		    handleResponse(httpExchange,requestParamValue);
	}
	
	public String getResponseBody(){
		try {
			return new String(Files.readAllBytes(Paths.get(webroot + "index.html")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
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
			    .append("<p>Hello</p>")
			    .append("</body>")
			    .append("</html>");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// encode HTML content 
		//String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
		// this line is a must
		String fileToGet;
		if(requestParamValue.equals("/"))
			fileToGet = webroot + "index.html";
		else 
			fileToGet = webroot + requestParamValue;
		
		//String htmlResponse = new String(Files.readAllBytes(Paths.get(fileToGet)));
		
		File file = new File(fileToGet);
		httpExchange.sendResponseHeaders(200, file.length());
		// TODO set the Content-Type header to image/gif 

		Files.copy(file.toPath(), outputStream);
		outputStream.close();
		
		//httpExchange.sendResponseHeaders(200, htmlResponse.length());
		//outputStream.write(htmlResponse.getBytes());
		//outputStream.flush();
		//outputStream.close();
	}
}
