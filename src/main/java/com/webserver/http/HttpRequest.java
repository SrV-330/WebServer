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
	
	private String requestURI;
	
	private String queryString;
	
	private HashMap<String,String> parameters= new HashMap<>();
	
	private InputStream in;
	
	private byte[] data;
	
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

	public Header getHeader(String name){
		return headers.get(name);
	}

	public HttpRequest(Socket socket) throws Exception {
		
		System.err.println("HttpRequest:start parsing request...");
		
			try {
				this.socket=socket;
				this.in=socket.getInputStream();
				parseRequestLine();
				parserURL();
				parseHanders();
				parseContent();
				
			} 
			catch(EmptyRequestException e){
				throw e;
			}
			catch (Exception e) {
				
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
	private void parserURL(){
		String[] data=url.split("\\?");
		requestURI=data[0];
		if(data.length>1){
			queryString=data[1];
			parserParameters(queryString);
		}else{
			requestURI=url;
		}
		System.out.println(parameters);
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
	private void parserParameters(String line){
		String[] kvs=line.split("&");
		for(String kv:kvs){
				
			String[] p=kv.split("=");
			if(p.length>1){
				parameters.put(p[0], p[1]);
			}else{
				parameters.put(p[0], null);
			}
			
		}
	}
	private void parseContent(){
		System.err.println("start parsing request content...");
		
		
		if(method.toLowerCase().equals("post")){
			method=method.toUpperCase();
			System.out.println("handler post");
			Header type=headers.get("Content-Type");
			if(type.getValue().equals("application/x-www-form-urlencoded")){
				Header length=headers.get("Content-Length");
				try{
					byte[] b=new byte[Integer.parseInt(length.getValue())];
					in.read(b);
					data=b;
					String s=new String(data,"ISO8859-1");
					System.out.println("form: "+s);
					parserParameters(s);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			
			
		}
		
		
		
		System.err.println("parse request content complete");
	}
	
	private String readLine() throws Exception{
		int c=-1;
		int c1=-1;
		StringBuilder sb=new StringBuilder("");
		while((c1=in.read())!=-1){
			if(c==HttpContext.CR&&c1==HttpContext.LF) break;
			sb.append((char)c1);
			c=c1;
		}
		
		String line=sb.toString().trim();
		return line;
	}

	public String getParameter(String name){
		return this.parameters.get(name);
	}
	
	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	

}
