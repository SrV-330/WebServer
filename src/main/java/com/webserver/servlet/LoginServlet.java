package com.webserver.servlet;


import java.io.RandomAccessFile;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class LoginServlet extends HttpServlet{
	@Override
	public void service(HttpRequest req,HttpResponse resp){
		try {
			System.err.println("RegService:start execute login...");
			String userName=req.getParameter("userName");
			String userPwd=req.getParameter("userPwd");
			
			
			if(vaildUsers(userName, userPwd)){
				forward(req.getRequestURI()+"_success.html",req,resp);
				
				
			}else{
				forward(req.getRequestURI()+"_fail.html",req,resp);
			}
			System.err.println("RegService:execute login complete");
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}

	private boolean vaildUsers(String name,String pwd){
		if(name==null||name.trim().length()==0) return false;
		
		if(pwd==null||pwd.trim().length()==0) return false;
		
		try(RandomAccessFile raf=new RandomAccessFile("user.dat", "rw");){
			byte[] b=new byte[32];
			for(long i=0;i<raf.length();i+=100){
				raf.read(b);
				String userName=new String(b,"utf-8").trim();
				raf.read(b);
				String userPwd=new String(b,"utf-8").trim();
				if(userName.equals(name)&&userPwd.equals(pwd)){
					return true;
				}
				
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
