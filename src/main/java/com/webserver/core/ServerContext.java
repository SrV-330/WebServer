package com.webserver.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.webserver.servlet.HttpServlet;


public class ServerContext {
	
	public static int IMAGE_WIDTH=65;
	public static int IMAGE_HEIGTH=25;
	private static char[] chars="Aa0".toCharArray();
	public static char randomChar(){
		
		int n=(int)(Math.random()*62);
		
		char c=(char) ((int)chars[n/26]+(n%26));
		return c;
		
	}
	private static Map<String,HttpServlet> servletMapping=new HashMap<>();
	static {
		initServlet();
	}
	private static void initServlet(){
		try {
			SAXReader reader=new SAXReader();
			
			Document doc=reader.read(new File("conf/servlets.xml"));
			Element root=doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> servletEles=root.elements("servlet");
			for(Element servletEle:servletEles){
				String path=servletEle.attributeValue("path");
				String className=servletEle.attributeValue("className");
				
				Class<?> clazz=Class.forName(className);
				HttpServlet servlet=(HttpServlet) clazz.newInstance();
				servletMapping.put(path, servlet);
			}
			System.out.println(servletMapping);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public static HttpServlet getServlet(String path){
		return servletMapping.get(path);
	}

}
