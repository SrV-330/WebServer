package com.webserver.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.webserver.exception.EmptyRequestException;
import com.webserver.http.HttpContext;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class ClientHandler  implements Runnable{
	
	
	private Socket socket;
	public ClientHandler(Socket socket) {
		this.socket = socket;
		
		
	}

	
	
	public void run() {
		
		try {
			
			System.err.println("ClientHandler:handler start executing...");
			
			HttpRequest request=new HttpRequest(socket);
			
			HttpResponse response=null;
			
			String path=request.getUrl();
			File file=new File("webapps"+path);
			
			if(file.exists()&&file.isFile()){
				System.out.println("ClientHandler:resource found");
				
				
				response=new HttpResponse.Builder(socket)
						.setEntity(file)
						
						.builder();
				
				
				
				
			}else{
				System.out.println("ClientHandler:resource not found");
				file=new File("webapps/root/404.html");
				response=new HttpResponse.Builder(socket)
						.setStatucode(404)
						.setStatusReason("NOT FOUND")
						.setEntity(file)
						.builder();
				
			}
			response.flush();
			
			
			
			System.err.println("Clienthandler:handler execute complete");
			
		}catch(EmptyRequestException e){
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(socket!=null){
					socket.close();
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}

}
