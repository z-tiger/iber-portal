//package com.iber.portal.common;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPReply;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class FavFTPUtil {
//	private final static Logger logger = LoggerFactory
//			.getLogger(FavFTPUtil.class);
//
//	// private final static String HOSTNAME = "ibgoing.com";
//	// private final static String USERNAME = "ftp_pile";
//	// private final static String PASSWORD = "lKCZhIePNZT76cxb";
//
//	/**
//	 * 上传文件（可供Action/Controller层使用）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param fileName
//	 *            上传到FTP服务器后的文件名称
//	 * @param inputStream
//	 *            输入文件流
//	 * @return
//	 */
//	public static String uploadFile(String hostname, String username,
//			String password, String pathname, String fileName,
//			InputStream inputStream) throws Exception {
//		logger.debug("上传文件" + fileName + "到ftp服务器");
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.setControlEncoding("UTF-8");
//		try {
//			// 连接FTP服务器
//			// ftpClient.connect(HOSTNAME, 80);
//			ftpClient.connect(hostname);
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			// 是否成功登录FTP服务器
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				logger.info("上传文件" + fileName + "到ftp服务器失败---> ftp服务器登录失败");
//				return "";
//			}
//			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//			ftpClient.makeDirectory(pathname);
//			ftpClient.changeWorkingDirectory(pathname);
//			ftpClient.storeFile(fileName, inputStream);
//			inputStream.close();
//			ftpClient.logout();
//			logger.debug("上传文件" + fileName + "到ftp成功");
//		} catch (Exception e) {
//			logger.info("ftp上传文件" + fileName + "失败 -->异常信息： " + e.getMessage());
//			throw e;
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.disconnect();
//				} catch (IOException e) {
//					e.printStackTrace();
//					logger.info("ftp客户端连接关闭异常 -->异常信息： " + e.getMessage());
//				}
//			}
//		}
//		return "ftp://" + hostname + "/" + pathname + "/" + fileName;
//	}
//
//	/**
//	 * 上传文件（可供Action/Controller层使用）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param fileName
//	 *            上传到FTP服务器后的文件名称
//	 * @param inputStream
//	 *            输入文件流
//	 * @return
//	 */
//	public static boolean uploadFile(String hostname, Integer port,
//			String username, String password, String pathname, String fileName,
//			InputStream inputStream) {
//		boolean flag = false;
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.setControlEncoding("UTF-8");
//		try {
//			// 连接FTP服务器
//			logger.info("连接FTP服务器");
//			// ftpClient.connect(HOSTNAME, 80);
//			ftpClient.connect(hostname, port);
//			// 登录FTP服务器
//			logger.info("登录FTP服务器");
//			ftpClient.login(username, password);
//			// 是否成功登录FTP服务器
//			logger.info("是否成功登录FTP服务器");
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//			logger.info("开始上传文件");
//			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//			ftpClient.makeDirectory(pathname);
//			ftpClient.changeWorkingDirectory(pathname);
//			ftpClient.storeFile(fileName, inputStream);
//			inputStream.close();
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.disconnect();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}
//
//	/**
//	 * 上传文件（可对文件进行重命名）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param filename
//	 *            上传到FTP服务器后的文件名称
//	 * @param originfilename
//	 *            待上传文件的名称（绝对地址）
//	 * @return
//	 */
//	public static String uploadFileFromProduction(String hostname,
//			String username, String password, String pathname, String filename,
//			String originfilename) {
//		String flag = "";
//		try {
//			InputStream inputStream = new FileInputStream(new File(
//					originfilename));
//			flag = uploadFile(hostname, username, password, pathname, filename,
//					inputStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//
//	/**
//	 * 上传文件（不可以进行文件的重命名操作）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param originfilename
//	 *            待上传文件的名称（绝对地址）
//	 * @return
//	 */
//	public static String uploadFileFromProduction(String hostname,
//			String username, String password, String pathname,
//			String originfilename) {
//		String flag = "";
//		try {
//			String fileName = new File(originfilename).getName();
//			InputStream inputStream = new FileInputStream(new File(
//					originfilename));
//			flag = uploadFile(hostname, username, password, pathname, fileName,
//					inputStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//
//	/**
//	 * 删除文件
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param filename
//	 *            要删除的文件名称
//	 * @return
//	 */
//	public static boolean deleteFile(String hostname, int port,
//			String username, String password, String pathname, String filename) {
//		boolean flag = false;
//		FTPClient ftpClient = new FTPClient();
//		try {
//			// 连接FTP服务器
//			if (port > 0) {
//				ftpClient.connect(hostname, port);
//			} else {
//				ftpClient.connect(hostname);
//			}
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			// 验证FTP服务器是否登录成功
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//			// 切换FTP目录
//			ftpClient.changeWorkingDirectory(pathname);
//			ftpClient.dele(filename);
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.logout();
//				} catch (IOException e) {
//
//				}
//			}
//		}
//		return flag;
//	}
//
//	/**
//	 * 下载文件
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器文件目录
//	 * @param filename
//	 *            文件名称
//	 * @param localpath
//	 *            下载后的文件路径
//	 * @return
//	 */
//	public static boolean downloadFile(String hostname, int port,
//			String username, String password, String pathname, String filename,
//			String localpath) {
//		boolean flag = false;
//		FTPClient ftpClient = new FTPClient();
//		try {
//			// 连接FTP服务器
//			if (port > 0) {
//				ftpClient.connect(hostname, port);
//			} else {
//				ftpClient.connect(hostname);
//			}
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			// 验证FTP服务器是否登录成功
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//			// 切换FTP目录
//			ftpClient.changeWorkingDirectory(pathname);
//			FTPFile[] ftpFiles = ftpClient.listFiles();
//			for (FTPFile file : ftpFiles) {
//				if (filename.equalsIgnoreCase(file.getName())) {
//					File localFile = new File(localpath + "/" + file.getName());
//					OutputStream os = new FileOutputStream(localFile);
//					ftpClient.retrieveFile(file.getName(), os);
//					os.close();
//				}
//			}
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.logout();
//				} catch (IOException e) {
//
//				}
//			}
//		}
//		return flag;
//	}
//}
