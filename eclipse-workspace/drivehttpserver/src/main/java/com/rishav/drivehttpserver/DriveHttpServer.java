package com.rishav.drivehttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import com.rishav.drivehttpserver.config.Configuration;
import com.rishav.drivehttpserver.config.ConfigurationManager;
import com.rishav.drivehttpserver.core.*;

import com.rishav.drivehttpserver.drive.dao.MySQLAccess;

public class DriveHttpServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DriveHttpServer.class);
	
	public static void main(String[] args) {
		LOGGER.info("Server Starting...");
		
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
		
		LOGGER.info("Using Port: " + conf.getPort());
		LOGGER.info("Using Webroot: " + conf.getWebroot());
		
		//ServerListenerThread serverlistenerthread;
		try {
			MySQLAccess dao = new MySQLAccess();
	        dao.openDataBase();
	        
	        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
	        
			/*serverlistenerthread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
			serverlistenerthread.createContext("/", new RootHttpHandler(conf.getWebroot()));
			serverlistenerthread.createContext("/order", new OrderHttpHandler(conf.getWebroot()));
			serverlistenerthread.createContext("/pricepoints", new PricePointsHttpHandler(conf.getWebroot()));
			serverlistenerthread.createContext("/description", new DescriptionHttpHandler(conf.getWebroot()));
			serverlistenerthread.start();*/
	        
	        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", conf.getPort()), 0);
	        server.createContext("/", new RootHttpHandler(conf.getWebroot()));
	        server.createContext("/order", new OrderHttpHandler());
	        server.createContext("/pricepoints", new PricePointsHttpHandler());
	        server.createContext("/login", new LoginHttpHandler(conf.getWebroot()));
	        server.createContext("/loginSubmit", new LoginSubmitHttpHandler(conf.getWebroot()));
	        server.setExecutor(threadPoolExecutor);
	        server.start();
	        LOGGER.info(" Server started on port 8080");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
