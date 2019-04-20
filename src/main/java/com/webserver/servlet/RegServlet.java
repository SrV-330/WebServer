package com.webserver.servlet;


import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class RegServlet extends HttpServlet{
	@Override
	public void service(HttpRequest req,HttpResponse resp){
		System.err.println("RegService:start execute register...");
		String userName=req.getParameter("userName");
		String userPwd=req.getParameter("userPwd");
		String nickName=req.getParameter("nickName");
		int age=Integer.parseInt(req.getParameter("age"));
		System.out.println("userName:"+userName);
		System.out.println("userPwd:"+userPwd);
		System.out.println("nickName:"+nickName);
		System.out.println("age:"+age);
		
		
		
		try(RandomAccessFile raf=new RandomAccessFile("user.dat", "rw");){
			byte[] b=new byte[32];
			while((raf.read(b)!=-1)){
				String s=new String(b,"utf-8").trim();
				if(s.equals(userName)){
					forward(req.getRequestURI()+"_fail.html",req,resp);
					return;
				}
				if(raf.getFilePointer()-32+100>=raf.length()) break;
				raf.seek(raf.getFilePointer()-32+100);
			}
			
			raf.seek(raf.length());
			
			b=userName.getBytes("utf-8");
			raf.write(Arrays.copyOf(b, 32));
			b=userPwd.getBytes("utf-8");
			raf.write(Arrays.copyOf(b, 32));
			b=nickName.getBytes("utf-8");
			raf.write(Arrays.copyOf(b, 32));
			raf.writeInt(age);
			forward(req.getRequestURI()+"_success.html",req,resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		showUsers();
		
		System.err.println("RegService:execute register complete");
	}
	
	private void showUsers(){
		try(RandomAccessFile raf=new RandomAccessFile("user.dat", "rw");){
			byte[] b=new byte[32];
			for(long i=0;i<raf.length();i+=100){
				raf.read(b);
				String userName=new String(b,"utf-8").trim();
				raf.read(b);
				String userPwd=new String(b,"utf-8").trim();
				raf.read(b);
				String nickName=new String(b,"utf-8").trim();
				
				int age=raf.readInt();
				
				System.out.println("userName:"+userName);
				System.out.println("userPwd:"+userPwd);
				System.out.println("nickName:"+nickName);
				System.out.println("age:"+age);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
