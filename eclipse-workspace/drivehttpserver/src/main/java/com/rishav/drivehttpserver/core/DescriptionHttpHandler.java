/*package com.rishav.drivehttpserver.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DescriptionHttpHandler implements com.sun.net.httpserver.HttpHandler {
	
	w
	public DescriptionHttpHandler(String webroot) {
		super(webroot);
	}
	
	public String getResponseBody(){
		try {
			return new String(Files.readAllBytes(Paths.get(webroot + "description.html")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}
}*/
