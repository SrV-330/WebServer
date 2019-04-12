package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.webserver.http.HttpRequest;

public class ClientHandler  implements Runnable{
	
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	
	
	public void run() {
		
		try {
			
			System.err.println("ClientHandler:handler start executing...");
			
			HttpRequest request=new HttpRequest(socket);
//			InputStream in= socket.getInputStream();
//			int d=-1;
//			while((d=in.read())!=-1){
//				System.out.print((char)d);
//			}
			
			
			
			
			
			System.err.println("Clienthandler:handler execute complete");
			
		} catch (Exception e) {
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
