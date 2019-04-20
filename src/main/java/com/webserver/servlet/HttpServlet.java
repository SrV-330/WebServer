package com.webserver.servlet;

import java.io.File;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public abstract class HttpServlet {
	public abstract void service(HttpRequest req,HttpResponse resp);
	
	public void forward(String path,
			HttpRequest req,
			HttpResponse resp) throws Exception{
		File file =new File("./webapps"+path);
		resp.setEntity(file);
		
	}

}
