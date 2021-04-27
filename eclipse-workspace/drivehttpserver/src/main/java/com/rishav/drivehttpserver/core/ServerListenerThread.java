/*package com.rishav.drivehttpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread extends Thread{
	
	private static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
	
	private int port;
	private String webroot;
	private ServerSocket serverSocket;
	private HashMap<String, HttpHandler> contextMap;
	
	public ServerListenerThread(int port, String webroot) throws IOException {
		this.port = port;
		this.webroot = webroot;
		this.serverSocket = new ServerSocket(this.port);
		this.contextMap = new HashMap<String, HttpHandler>();
	}
	
	public void createContext(String address, HttpHandler httphandler ) {
		contextMap.put(address, httphandler);
	}

	@Override
	public void run() {
		try {
			while (serverSocket.isBound() && !serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
			
				LOGGER.info(" * Connection accepted: " + socket.getInetAddress());
				
				HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(contextMap, socket, webroot);
				workerThread.start();
				
				
			}
			
			//serverSocket.close();
			
		} catch (IOException e) {
			LOGGER.error("Problem with setting socket", e);
		}
		finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} 
				catch (IOException e) {
				}
			}
		}
	}
}*/
