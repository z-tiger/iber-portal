package com.iber.portal.common;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.iber.portal.exception.ServiceException;


public class FileUtil {
	
	/**
	 * 下载文件 
	 * @param filePath 文件路径
	 * @param response
	 * @throws ServiceException
	 */
	public static void fileDownload(String filePath, HttpServletResponse response) throws ServiceException{
		try {
			File file = new File(filePath);
			if(!file.exists()){
				response.sendError(404, "文件不存在");
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[1024];
			int len = 0;
			
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
			OutputStream out = response.getOutputStream();
			while((len = br.read(buf)) >0){
				out.write(buf,0,len);
			}
			out.flush();
			out.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * 下载文件
	 * @param fileName  文件名称
	 * @param filePath 文件路径
	 * @param response  
	 * @throws ServiceException
	 */
	public static void fileDownload(String fileName,String filePath, HttpServletResponse response) throws ServiceException{
		try {
			File file = new File(filePath);
			if(!file.exists()){
				response.sendError(404, "文件不存在");
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[1024];
			int len = 0;
			
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName); 
			OutputStream out = response.getOutputStream();
			while((len = br.read(buf)) >0){
				out.write(buf,0,len);
			}
			out.flush();
			out.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	
	/**
	 * 上传文件
	 * @param basePath 文件基本路径
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public static HashMap<String, String> fileUpload(String basePath, HttpServletRequest request) throws ServiceException{
		HashMap<String, String> formHashMap = new HashMap<String, String>();
		try {
			//使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
             //3解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8"); 
          //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //保存表单中的其他信息
                    formHashMap.put(name, value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    formHashMap.put("fileName", filename);
                    
                    String fileNamePrx = filename.substring(filename.lastIndexOf("."));
                    String newFileName = DateTimeUtil.getCurrDateTime("yyyyMMddHHmmss") + System.currentTimeMillis() + fileNamePrx;
                    formHashMap.put("newFileName", newFileName);
                    
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(basePath + "\\" + newFileName);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                }
            }
            return formHashMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
