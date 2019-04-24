package com.webserver.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.webserver.core.ServerContext;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public class RandomImageServlet extends HttpServlet{

	@Override
	public void service(HttpRequest req, HttpResponse resp) {
		System.out.println("RandomImageServlet: create code image...");
		
		BufferedImage img=null;
		img=new BufferedImage(ServerContext.IMAGE_WIDTH, ServerContext.IMAGE_HEIGTH, BufferedImage.TYPE_INT_RGB);
		Graphics g=img.getGraphics();
		g.setColor(new Color(200,200,255));
		g.fillRect(0, 0, ServerContext.IMAGE_WIDTH,  ServerContext.IMAGE_HEIGTH);
		
		Random rd=new Random();
		
		g.setFont(new Font("黑体",Font.BOLD,25));
		for(int i=0;i<4;i++){
			char c=ServerContext.randomChar();
			System.out.print(c);
			g.setColor(new Color(rd.nextInt(180), rd.nextInt(180), rd.nextInt(180)));
			g.drawString(c+"", i*15+3, 20);
		}
		System.out.println();
		for(int i=0;i<50;i++){
			int x=(int)(Math.random()*ServerContext.IMAGE_WIDTH);
			int y=(int)(Math.random()*ServerContext.IMAGE_HEIGTH);
			
			
			g.drawLine(x, y, x, y);
		}
		
		g.setColor(new Color(20, 20, 20));
		for(int i=0;i<6;i++){
			int x=(int)(Math.random()*ServerContext.IMAGE_WIDTH);
			int y=(int)(Math.random()*ServerContext.IMAGE_HEIGTH);
			int x1=(int)(Math.random()*ServerContext.IMAGE_WIDTH);
			int y1=(int)(Math.random()*ServerContext.IMAGE_HEIGTH);
			
			g.drawLine(x, y, x1, y1);
		}
		try(ByteArrayOutputStream baos=new ByteArrayOutputStream();){ 
			ImageIO.write(img, "jpg", baos);
			resp.setContentData(baos.toByteArray(), "jpg");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 	
		System.out.println("RandomImageServlet: create code image complete");
		
	}

}
