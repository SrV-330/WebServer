package com.webserver.servlet;

import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class UpdateServlet extends HttpServlet{

	@Override
	public void service(HttpRequest req, HttpResponse resp) {
		
		try {
			System.err.println("UpdateService:start execute update...");
			String userName=req.getParameter("userName");
			String oldPwd=req.getParameter("oldPwd");
			String newPwd=req.getParameter("newPwd");
			
			
			if(updateUsers(userName, oldPwd,newPwd)){
				forward(req.getRequestURI()+"_success.html",req,resp);
			}else{
				forward(req.getRequestURI()+"_fail.html",req,resp);
			}
			System.err.println("UpdateService:execute login complete");
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	private boolean updateUsers(String name,String pwd,String newPwd){
		if(name==null||name.trim().length()==0) return false;
		
		if(pwd==null||pwd.trim().length()==0) return false;
		
		if(newPwd==null||newPwd.trim().length()==0) return false;
		
		try(RandomAccessFile raf=new RandomAccessFile("user.dat", "rw");){
			byte[] b=new byte[32];
			for(long i=0;i<raf.length()/100;i++){
				raf.seek(i*100);
				raf.read(b);
				String userName=new String(b,"utf-8").trim();
				raf.read(b);
				String userPwd=new String(b,"utf-8").trim();
				if(userName.equals(name)&&userPwd.equals(pwd)){
					raf.seek(raf.getFilePointer()-32);
					raf.write(Arrays.copyOf(newPwd.getBytes("utf-8"),32));
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
