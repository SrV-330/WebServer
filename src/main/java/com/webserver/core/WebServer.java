package com.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class WebServer {
	
	private ServerSocket server;
	
	private ThreadPoolExecutor executor=
			new ThreadPoolExecutor(8, 32, 15, TimeUnit.SECONDS, 
					new LinkedBlockingQueue<Runnable>(64));
	public WebServer(){
		
		try {
			System.err.println("starting service...");
			server=new ServerSocket(8088);
			System.err.println("server startup");
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void start(){
		
		try {
			while(true){
				System.err.println("wait client connecting...");
				Socket socket=server.accept();
				executor.execute(new ClientHandler(socket));
				System.err.println("a client connected");
			}
			
			
			
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void main(String[] args) {
		
		WebServer server=new WebServer();
		server.start();
	}
	
	

}
