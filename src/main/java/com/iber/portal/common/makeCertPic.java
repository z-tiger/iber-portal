package com.iber.portal.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * @author ruanpeng 生成验证码图片
 */
public class makeCertPic {

	// 验证码图片中可以出现的字符集，可根据需要修改
	private char mapTable[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8',
			'9' };

	/**
	 * 功能:生成彩色验证码图片 参数width为生成图片的宽度,参数height为生成图片的高度,参数os为页面的输出流
	 */
	public String getCertPic(int width, int height, OutputStream os) {

		if (width <= 0)
			width = 55;
		if (height <= 0)
			height = 18;
		
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(new Color(0xf0f0f0));
		g.fillRect(0, 0, width, height);

		// 画边框
//		g.setColor(new Color(0x0172ff));
//		g.drawRect(0, 0, width - 1, height - 1);

		// 取随机产生的认证码
		String strEnsure = "";

		// 4代表4位验证码,如果要生成更多位的认证码,则加大数值
		for (int i = 0; i < 4; ++i) {
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}

		// 将认证码显示到图像中,如果要生成更多位的认证码,增加drawString语句
		//g.setColor(new Color(0x0172ff));
		g.setColor(Color.RED);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		String str = strEnsure.substring(0, 1);
		g.drawString(str, 3, 13);
		str = strEnsure.substring(1, 2);
		g.drawString(str, 13, 13);
		str = strEnsure.substring(2, 3);
		g.drawString(str, 23, 13);
		str = strEnsure.substring(3, 4);
		g.drawString(str, 33, 13);

		// 随机产生10个干扰点
//		Random rand = new Random();
//		for (int i = 0; i < 10; i++) {
//			int x = rand.nextInt(width);
//			int y = rand.nextInt(height);
//			g.drawOval(x, y, 1, 1);
//		}

		// 释放图形上下文
		g.dispose();

		try {
			// 输出图像到页面
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			return "";
		}
		
		return strEnsure;
		
	}

}