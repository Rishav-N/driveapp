package com.rishav.drivehttpserver.core;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rishav.drivehttpserver.drive.User;
import com.rishav.drivehttpserver.drive.dao.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class LoginSubmitHttpHandler implements com.sun.net.httpserver.HttpHandler{
	 private static Logger LOGGER = LoggerFactory.getLogger(LoginSubmitHttpHandler.class);
	 
	 private static final long serialVersionUID = 1L;
	 
	 protected String webroot;
		
		public LoginSubmitHttpHandler(String webroot) {
			this.webroot = webroot;
		}
	 
	    /*protected String doPost(HttpServletRequest request, HttpServletResponse response, String name)
	            throws ServletException, IOException {
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	         
	        UserDao userDao = new UserDao();
	         
	        User user = userDao.checkLogin(email, password);
	        String destPage = "/";
	             
	        if (user != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("user", user);
	            destPage = "/";
	        } else {
	            String message = "Invalid email/password";
	            request.setAttribute("message", message);
	        }
	        
	        String rName = request.getParameter(name);
	        
	        LOGGER.info(user.Email() + user.Password());
	             
	        RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
	        dispatcher.forward(request, response); 
	        
	        return rName;
	    }*/
	    
	    public void handle(HttpExchange httpExchange) throws IOException {
			// TODO Auto-generated method stub
			User requestParamValue=null; 
			LOGGER.info(httpExchange.getRequestMethod().toString());
			
			    if("POST".equals(httpExchange.getRequestMethod())) { 
			
			       requestParamValue = handlePostRequest(httpExchange);
			
			    }
			    handleResponse(httpExchange,requestParamValue);
			
		}
		
		private String handleGetRequest(HttpExchange httpExchange) {
			LOGGER.info(httpExchange.getRequestURI().toString());
			return httpExchange.
			getRequestURI()
			.toString();
		}
		
		private User handlePostRequest(HttpExchange httpExchange) {
			LOGGER.info("##"+httpExchange.getRequestURI().toString());
			
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        BufferedReader br = new BufferedReader(isr);

	        int b;
	        StringBuilder buf = new StringBuilder();
	        try {
				
		    	String input;
		    	while ((input = br.readLine()) != null) {
		    		buf.append(input).append(" ");
		    	}
		    	
		    	br.close();
		        //isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        LOGGER.info(buf.toString().trim());
	        
	        Map<String, String> squery = null;
	        
	        try {
	        	squery = splitQuery(buf.toString().trim());
	        	LOGGER.info(squery.get("email"));
	        	LOGGER.info(squery.get("password"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        UserDao userdao = new UserDao();
	        
	        User user = userdao.checkLogin(squery.get("email"), squery.get("password"));
	            
	    	return user;
		}
		
		public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
		    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		    
		    String[] pairs = query.split("&");
		    for (String pair : pairs) {
		        int idx = pair.indexOf("=");
		        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		    }
		    return query_pairs;
		}
		
		private void handleResponse(HttpExchange httpExchange, User requestParamValue) throws IOException {
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
			
			LOGGER.info("#"+requestParamValue);
			
			if (requestParamValue != null) {
				
				HttpSession httpsession = new HttpSession();
				httpsession.setSession(httpExchange, requestParamValue);
				
				httpExchange.getResponseHeaders().add("Location", "/"); 
				
				//File file = new File(webroot + "login.jsp");
				
				// TODO set the Content-Type header to image/gif 
	
				//Files.copy(file.toPath(), outputStream);
				//outputStream.close();
				
				//httpExchange.sendResponseHeaders(200, htmlResponse.length());
				//outputStream.write(htmlResponse.getBytes());
				//outputStream.flush();
				//outputStream.close();
			} else {
				httpExchange.getResponseHeaders().add("Location", "/login"); 
			}
			
			httpExchange.sendResponseHeaders(302, 0);
		}
}
