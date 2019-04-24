package com.webserver.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import com.webserver.exception.EmptyRequestException;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.servlet.HttpServlet;

public class ClientHandler  implements Runnable{
	
	
	private Socket socket;
	public ClientHandler(Socket socket) {
		this.socket = socket;
		
		
	}

	
	
	public void run() {
		
		try {
			
			System.err.println("ClientHandler:handler start executing...");
			
			HttpRequest request=new HttpRequest(socket);
			
			HttpResponse response=new HttpResponse.Builder(socket).builder();
			
			String path=request.getRequestURI();
			HttpServlet servlet=ServerContext.getServlet(path);
			if(servlet!=null){
				
				servlet.service(request,response);
			}else{
				File file=new File("webapps"+path);
				
				if(file.exists()&&file.isFile()){
					System.out.println("ClientHandler:resource found");
					
					
					response.setEntity(file);
					
					
					
				}else{
					System.out.println("ClientHandler:resource not found");
					file=new File("webapps/root/404.html");
					response.setStatusCode(404);
					response.setStatusReason("NOT FOUND");
					response.setEntity(file);
					
				}
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
