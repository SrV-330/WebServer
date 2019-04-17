package com.webserver.http;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import com.webserver.exception.EmptyRequestException;

public class HttpRequest {
	//请求方式
	private String method;
	//资源路径
	private String url;
	//协议版本
	private String protocol;
	
	private HashMap<String,Header> headers=new HashMap<String, Header>(); 
	
	private Socket socket;
	
	private InputStream in;
	
	public HttpRequest(){
		
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	
	
	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getProtocol() {
		return protocol;
	}

	public HttpRequest(Socket socket) {
		
		System.err.println("HttpRequest:start parsing request...");
		
			try {
				this.socket=socket;
				this.in=socket.getInputStream();
				parseRequestLine();
				parseHanders();
				parseContent();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		
		
		
		System.err.println("HttpRequest:parse request complete");
	}
	private void parseRequestLine() throws Exception{
		System.err.println("start parsing request line...");
		
		String line=readLine();
		if(line.length()==0) throw new EmptyRequestException("request line length = 0");
		String[] lines=line.split("\\s");
		if(lines.length!=3) throw new EmptyRequestException("empty request");
		
		for(int i=0;i<lines.length;i++){
			lines[i]=lines[i].trim();
			if(lines[i].length()==0){
				throw new EmptyRequestException("empty request");
				
			}
		}
		
		method=lines[0];
		url=lines[1];
		protocol=lines[2];
		
		
		
		System.out.println("Method: "+method);
		System.out.println("URL: "+url);
		System.out.println("Protocol: "+protocol);

		System.err.println("parse request line complete");
	}
	
	private void parseHanders() throws Exception{
		System.err.println("start parsing request handers...");
		
		String line="";
		while((line=readLine()).length()>0){
			Header header=new Header(line);
			headers.put(header.getHeader(),header);
		}
		for(Entry<String,Header> header:headers.entrySet()){
			System.out.println(header.getKey()+": "+header.getValue().getValue());
		}
		
		
		
		System.err.println("parse request handers complete");
	}
	private void parseContent(){
		System.err.println("start parsing request content...");
		
		
		
		
		System.err.println("parse request content complete");
	}
	
	private String readLine() throws Exception{
		int c=-1;
		int c1=-1;
		StringBuilder sb=new StringBuilder("");
		while((c1=in.read())!=-1){
			if(c=='\r'&&c1=='\n') break;
			sb.append((char)c1);
			c=c1;
		}
		
		String line=sb.toString().trim();
		return line;
	}

}
