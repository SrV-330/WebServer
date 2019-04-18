package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class RegServlet {
	
	public void service(HttpRequest req,HttpResponse resp){
		System.err.println("RegService:start excute register...");
		String userName=req.getParameter("userName");
		String userPwd=req.getParameter("userPwd");
		String nickName=req.getParameter("nickName");
		int age=Integer.parseInt(req.getParameter("age"));
		System.out.println("userName:"+userName);
		System.out.println("userPwd:"+userPwd);
		System.out.println("nickName:"+nickName);
		System.out.println("age:"+age);
		
		
		
		try(RandomAccessFile raf=new RandomAccessFile("user.dat", "rw");){
			raf.seek(raf.length());
			byte[] b=new byte[32];
			b=userName.getBytes("utf-8");
			raf.write(Arrays.copyOf(b, 32));
			b=userPwd.getBytes("utf-8");
			raf.write(Arrays.copyOf(b, 32));
			b=nickName.getBytes("utf-8");
			raf.write(Arrays.copyOf(b, 32));
			raf.writeInt(age);
			resp.setEntity(new File("webapps"+req.getRequestURI()+"_success.html"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.err.println("RegService:excute register complete");
	}

}
