/*package com.rishav.drivehttpserver.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rishav.drivehttpserver.config.Configuration;

public class HttpConnectionWorkerThread extends Thread {
	
	private static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
	
	private Socket socket;
	private String webroot;

	
	public HttpConnectionWorkerThread(HashMap<String, HttpHandler> contextMap, Socket socket, String webroot) {
		this.socket = socket;
		this.webroot = webroot;
		this.contextMap = contextMap;
	}
	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String fileRequested = null;
			String input = in.readLine();
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase();
			fileRequested = parse.nextToken().toLowerCase();
			
			//inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			String html = null;
			
			LOGGER.debug(fileRequested);
			LOGGER.debug(method);
			
			HttpHandler httphandler = contextMap.get(fileRequested);
			
			if(httphandler != null) {
				html = httphandler.getResponseBody();
			} else {
				File tmpDir = new File(webroot + fileRequested);
				boolean exists = tmpDir.exists();
				if(exists) {
					html = new String(Files.readAllBytes(Paths.get(webroot + fileRequested)));
				} else {
					html = "<html><head><title>404</title></head><body><h1>File not found</h1></body></html>";
				}
			}
			
			final String CRLF = "\n\r";
			
			String response = 
					"HTTP/1.1 200 OK" + CRLF + //Status Line : HTTP_VERSION RESPONCE_CODE RESPONSE_MESSAGE
					"Content-Lenth: " + html.getBytes().length + CRLF + //HEADER
						CRLF + 
						html +
						CRLF + CRLF;
			
			outputStream.write(response.getBytes());
			
			LOGGER.info(" * Connection Proccessing Finished.");
		}
		catch (IOException e){
			e.printStackTrace();
			LOGGER.error("Problem with communcation", e);
		}
		finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}*/
