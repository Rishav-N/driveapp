package com.rishav.drivehttpserver.core;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
 
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rishav.drivehttpserver.drive.User;
import com.rishav.drivehttpserver.drive.dao.*;
import com.sun.net.httpserver.HttpExchange;

public class LoginHttpHandler implements com.sun.net.httpserver.HttpHandler{
	 private static Logger LOGGER = LoggerFactory.getLogger(LoginHttpHandler.class);
	 
	 private static final long serialVersionUID = 1L;
	 
	 protected String webroot;
		
		public LoginHttpHandler(String webroot) {
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
			String requestParamValue=null; 
			
			    if("GET".equals(httpExchange.getRequestMethod())) { 
			
			       requestParamValue = handleGetRequest(httpExchange);
			
			     }else if("POST".equals(httpExchange.getRequestMethod())) { 
			
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
		
		private String handlePostRequest(HttpExchange httpExchange) {
			LOGGER.info(httpExchange.getRequestURI().toString());
			return httpExchange.getRequestURI().toString();
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
			
			File file = new File(webroot + "login.jsp");
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
