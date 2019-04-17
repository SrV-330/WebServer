package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
	
	
	private Map<String,Header> headers=new HashMap<>();
	
	private int statucCode=200;
	
	private String statusReason="OK";
	
	private File entity;
	
	private Socket socket;
	
	private OutputStream out;
	
	
	
	public int getStatucCode() {
		return statucCode;
	}



	public void setStatucCode(int statucCode) {
		this.statucCode = statucCode;
	}



	public String getStatusReason() {
		return statusReason;
	}



	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}



	public File getEntity() {
		return entity;
	}



	public void setEntity(File entity) throws Exception {
		this.entity = entity;
		
		String s=entity.getName().toLowerCase();
		s=s.substring(s.lastIndexOf(".")+1);
		s=HttpContext.getExt(s);
		addHeader("Content-Type: "+s);
		addHeader("Content-Length: "+entity.length()+"");
	}


	
	



	public HttpResponse(Socket socket) {
		try {
			this.socket = socket;
			this.out=this.socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addHeader(String line) throws Exception{
		Header h=new Header(line);
		headers.put(h.getHeader(), h);
	}

	public void flush(){
		try {
			
			sendStatusLine();
			
			sendHeaders();
			
			sendContent();
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private void sendStatusLine() throws Exception{
		
		System.err.println("HttpResponse: send status line...");
		String line ="HTTP/1.1 "+statucCode+" "+statusReason;
		
		out.write(line.getBytes("ISO8859-1"));
		out.write('\r');
		out.write('\n');
		System.err.println("HttpResponse: send status line complete");
	}
	private void sendHeaders() throws Exception{
		System.err.println("HttpResponse: send headers...");
		
		
		for(Header header:headers.values()){
			out.write(header.toBytes());
		}
		
		
		out.write('\r');
		out.write('\n');
		System.err.println("HttpResponse: send headers complete");
	}
	private void sendContent() throws Exception{
		System.err.println("HttpResponse: send content...");
		if(entity!=null){
			try(FileInputStream fis=new FileInputStream(entity);){
				int len=-1;
				
				byte[] data=new byte[1024*10];
				
				while((len=fis.read(data))!=-1){
					out.write(data, 0, len);
				}
			} catch (Exception e) {
				
				throw e;
			}
		}
		System.err.println("HttpResponse: send content complete");
	}
	
	public static class Builder{
		private HttpResponse httpResponse;
		public Builder(Socket socket){
			this.httpResponse=new HttpResponse(socket);
		}
		public Builder addHeader(String line) throws Exception{
			httpResponse.addHeader(line);
			
			return this;
		}
		public Builder setStatucode(int code) throws Exception{
			httpResponse.setStatucCode(code);
			
			return this;
		} 
		public Builder setEntity(File file) throws Exception{
			httpResponse.setEntity(file);
			
			return this;
		} 
		public Builder setStatusReason(String reason) throws Exception{
			httpResponse.setStatusReason(reason);
			
			return this;
		} 
		
		public HttpResponse builder(){
			return httpResponse;
		}
		
	}
	

}
