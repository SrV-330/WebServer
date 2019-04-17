package com.webserver.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpContext {
	
	private final static Map<String,String> mimeMapping=new HashMap<String, String>();
	static{
		initMimeMapping();
	}
	
	private static void initMimeMapping(){
		SAXReader reader=new SAXReader();
		
		try {
			Document doc=reader.read("conf/web.xml");
			Element root=doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> mapEles=root.elements("mime-mapping");
			for(Element mapEle:mapEles){
				String key=mapEle.elementText("extension").trim();
				String value=mapEle.elementText("mime-type").trim();
				mimeMapping.put(key, value);
			}
			System.out.println(mimeMapping.size());
			
			
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static String getExt(String key){
		return mimeMapping.get(key);
	}
	public static void main(String[] args) {
		
	}
	

}
