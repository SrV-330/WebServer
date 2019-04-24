package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
	
	
	private Map<String,Header> headers=new HashMap<>();
	
	private int statucCode=200;
	
	private String statusReason="OK";
	
	private File entity;
	
	private byte[] data;
	
	private Socket socket;
	
	private OutputStream out;
	
	
	
	public int getStatucCode() {
		return statucCode;
	}



	public void setStatusCode(int statucCode) {
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
		this.data=null;
		
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
			
			e.printStackTrace();
		}

	}
	
	
	private void sendStatusLine() throws Exception{
		
		System.err.println("HttpResponse: send status line...");
		String line ="HTTP/1.1 "+statucCode+" "+statusReason;
		
		println(line);
		
		System.err.println("HttpResponse: send status line complete");
	}
	
	
	private void println(String line) throws Exception{
		out.write(line.getBytes("ISO8859-1"));
		out.write(HttpContext.CR);
		out.write(HttpContext.LF);
	}
	private void sendHeaders() throws Exception{
		System.err.println("HttpResponse: send headers...");
		
		
		for(Header header:headers.values()){
			out.write(header.toBytes());
		}
		
		
		println("");
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
		}else if(data!=null){
			out.write(data);
		}
		System.err.println("HttpResponse: send content complete");
	}

	public void setContentData(byte[] data,String ext) throws Exception {
		this.data = data;
		this.entity=null;
		addHeader("Content-Type: "+HttpContext.getExt(ext));
		addHeader("Content-Length: "+data.length+"");
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
			httpResponse.setStatusCode(code);
			
			return this;
		} 
		public Builder setEntity(File file) throws Exception{
			httpResponse.setEntity(file);
			
			return this;
		} 
		public Builder setContentData(byte[] data,String ContentType) throws Exception{
			httpResponse.setContentData(data,ContentType);
			
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
