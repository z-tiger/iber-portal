package com.iber.portal.common;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class Picture {
	// TODO Auto-generated constructor stub
	public static InputStream resizePNG(File file,
			int outputWidth, int outputHeight,String ipx) {
		try {
			BufferedImage bi2 = ImageIO.read(file);
			int newWidth;
			int newHeight;
			// 判断是否是等比缩放
			// 为等比缩放计算输出的图片宽度及高度
			double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth
					+ 0.1;
			double rate2 = ((double) bi2.getHeight(null))
					/ (double) outputHeight + 0.1;
			// 根据缩放比率大的进行缩放控制
			double rate = rate1 < rate2 ? rate1 : rate2;
			newWidth = (int) (((double) bi2.getWidth(null)) / rate);
			newHeight = (int) (((double) bi2.getHeight(null)) / rate);
			BufferedImage to = new BufferedImage(newWidth, newHeight,

			BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = to.createGraphics();

			to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth,
					newHeight,

					Transparency.TRANSLUCENT);

			g2d.dispose();

			g2d = to.createGraphics();

			Image from = bi2.getScaledInstance(newWidth, newHeight,
					BufferedImage.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();

			 ByteArrayOutputStream bs = new ByteArrayOutputStream();  
		        ImageOutputStream imOut; 
	            imOut = ImageIO.createImageOutputStream(bs); 
	            ImageIO.write(to, ipx,imOut); 
	            InputStream is= new ByteArrayInputStream(bs.toByteArray()); 
	            
			return is ;

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null ;
	}

	public static InputStream resizePngNoCompress(File file, String ipx) {
		try {
			BufferedImage imgBar = ImageIO.read(file);  
			ByteArrayOutputStream bs = new ByteArrayOutputStream();  
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);  
			ImageIO.write(imgBar, ipx, imOut);  
			InputStream is = new ByteArrayInputStream(bs.toByteArray());
			
			return is ;

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null ;
	}
	
}