package com.iber.portal.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipInputStream; 

public class EctractZip {

	 public static void main(String[] args) {  
	        EctractZip z = new EctractZip();  
	        ArrayList<String> a = z.Ectract("D:\\11.zip", "D:\\"+CharacterUtils.getRandomString2(10)+"\\"); // 返回解压缩出来的文件列表  
	        for(String s : a){  
	            System.out.println(s);  
	        }  
	    }  
	  
	    /** 
	     * 解压缩 
	     * @param sZipPathFile 要解压的文件 
	     * @param sDestPath 解压到某文件夹 
	     * @return 
	     */  
	    @SuppressWarnings("unchecked")  
	    public static ArrayList Ectract(String sZipPathFile, String sDestPath) {  
	    	System.setProperty("sun.zip.encoding", System.getProperty("sun.zip.encoding"));
	        ArrayList<String> allFileName = new ArrayList<String>();  
	        try {  
	            // 先指定压缩档的位置和档名，建立FileInputStream对象  
	            FileInputStream fins = new FileInputStream(sZipPathFile);  
	            // 将fins传入ZipInputStream中  
	            ZipInputStream zins = new ZipInputStream(fins);  
	            ZipEntry ze = null;  
	            byte[] ch = new byte[256];  
	            while ((ze = zins.getNextEntry()) != null) {  
	                File zfile = new File(sDestPath + ze.getName());  
	                File fpath = new File(zfile.getParentFile().getPath());  
	                if (ze.isDirectory()) {  
	                    if (!zfile.exists())  
	                        zfile.mkdirs();  
	                    zins.closeEntry();  
	                } else {  
	                    if (!fpath.exists())  
	                        fpath.mkdirs();  
	                    FileOutputStream fouts = new FileOutputStream(zfile);  
	                    int i;  
	                    allFileName.add(zfile.getAbsolutePath());  
	                    while ((i = zins.read(ch)) != -1)  
	                        fouts.write(ch, 0, i);  
	                    zins.closeEntry();  
	                    fouts.close();  
	                }  
	            }  
	            fins.close();  
	            zins.close();  
	        } catch (Exception e) {  
	            System.err.println("Extract error:" + e.getMessage());  
	        }  
	        return allFileName;  
	    }  
	    
	    
	    /** 
	     * <p> 
	     * 解压压缩包 
	     * </p> 
	     *  
	     * @param zipFilePath 压缩文件路径 
	     * @param destDir 压缩包释放目录 
	     * @throws Exception 
	     */  
	    public static void unZip(String zipFilePath, String destDir) throws Exception {  
	    	org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(zipFilePath, CHINESE_CHARSET);  
	        Enumeration<org.apache.tools.zip.ZipEntry> emu = zipFile.getEntries();  
	        BufferedInputStream bis;  
	        FileOutputStream fos;  
	        BufferedOutputStream bos;  
	        File file, parentFile;  
	        org.apache.tools.zip.ZipEntry entry ;  
	        byte[] cache = new byte[CACHE_SIZE];  
	        while (emu.hasMoreElements()) {  
	            entry = (org.apache.tools.zip.ZipEntry) emu.nextElement();  
	            if (entry.isDirectory()) {  
	                new File(destDir + entry.getName()).mkdirs();  
	                continue;  
	            }  
	            bis = new BufferedInputStream(zipFile.getInputStream(entry));  
	            file = new File(destDir + entry.getName());  
	            parentFile = file.getParentFile();  
	            if (parentFile != null && (!parentFile.exists())) {  
	                parentFile.mkdirs();  
	            }  
	            fos = new FileOutputStream(file);  
	            bos = new BufferedOutputStream(fos, CACHE_SIZE);  
	            int nRead = 0;  
	            while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {  
	                fos.write(cache, 0, nRead);  
	            }  
	            bos.flush();  
	            bos.close();  
	            fos.close();  
	            bis.close();  
	        }  
	        zipFile.close();  
	    }  
	    
	    /** 
	     * 使用GBK编码可以避免压缩中文文件名乱码 
	     */  
	    private static final String CHINESE_CHARSET = "GBK";  
	      
	    /** 
	     * 文件读取缓冲区大小 
	     */  
	    private static final int CACHE_SIZE = 1024; 
}
