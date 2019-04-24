package com.webserver.servlet;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.webserver.entity.User;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class ShowAllUserServlet extends HttpServlet {

	@Override
	public void service(HttpRequest req, HttpResponse resp) {
		
		System.out.println("ShowAllUserServlet: show users...");
		
		List<User> users=getAllUsers();
		TemplateEngine eng=new TemplateEngine();
		FileTemplateResolver resolver=new FileTemplateResolver();
		resolver.setCharacterEncoding("utf-8");
		eng.setTemplateResolver(resolver);
		Context context=new Context();
		context.setVariable("users", users);
		String html=eng.process("./webapps/myweb/showAllUser.html", context);
		
		try {
			resp.setContentData(html.getBytes("utf-8"), "html");
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		System.out.println("ShowAllUserServlet: show users complete");

	}
	private List<User> getAllUsers(){
		
		List<User> users=new ArrayList<User>();
		
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
				User user=new User(userName, userPwd, nickName, age);
				users.add(user);
				System.out.println("userName:"+userName);
				System.out.println("userPwd:"+userPwd);
				System.out.println("nickName:"+nickName);
				System.out.println("age:"+age);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return users;
		
	}

}
