package com.education.system.common.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.education.system.common.entity.Result;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/imageCode")
public class ImageCodeContorller {
	//获取颜色
	Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	@RequestMapping(value = "/showCode", method = RequestMethod.GET)
	public void outputImage(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		//设置页面不缓存
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-control","no-cache");
		response.setDateHeader("Expires",0);
		
		//在内存中创建图像
		int width = 60, height = 20;
		
		BufferedImage  image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		
		// 获取图形上下文,就相当于在一张图片上面形成一张画板，在上面进行涂涂写写
		Graphics g = image.getGraphics();
		
		// 生成随机类
		Random random = new Random();
		
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		//填充颜色
		g.fillRect(0, 0, width, height);
		// 设定字体,it is Times New Roman and Font is plain type size 18
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		//set the random line on the code in order to security ,shade the code
		g.setColor(getRandColor(160, 200));
		
		//100 time line,the angle is random product
		for (int i = 0; i < 100; i++) {
			int x = random.nextInt(width);
			
			int y = random.nextInt(height);
			
			int xl = random.nextInt(12);
			
			int yl = random.nextInt(12);
			
			//invoke function that realize draw line
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码(4位数字)
				String sRand = "";
				for (int i = 0; i < 4; i++) {
					//product 0~9 number translation to String data type
					String rand = String.valueOf(random.nextInt(10));
					//String buffer concat function 
					sRand += rand;
					// 将认证码显示到图象中
					g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
					g.drawString(rand, 13 * i + 6, 16);
				}
		
				// 将认证码存入SESSION
				session.setAttribute("code", sRand);
				// 图象生效
				g.dispose();
					
				@SuppressWarnings("restriction")
				JPEGImageEncoder encoder =null;
				// 输出图象到页面
				try {
					
					ImageIO.write(image, "JPEG", response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
	
	/** 获取验证码 */
	/**
	 * 这个方法使用的是post方法。
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	public Result getRandCode(HttpSession session) {
		//design a entity class to load the return data
		Result result = new Result();
		Object obj = session.getAttribute("code");
		result.setObj(obj);
		System.out.println("开始将结果传回js函数进行判断");
		return result;
	}
	
	Date d = new Date();
}


