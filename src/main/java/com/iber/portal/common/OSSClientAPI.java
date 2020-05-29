package com.iber.portal.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;


public class OSSClientAPI {

	private String endpoint;
	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;
	private String dns;
	
	public OSSClientAPI(String endpoint, String accessKeyId,
			String accessKeySecret, String bucketName,  String dns) {
		super();
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.bucketName = bucketName;
		this.dns = dns;
	}

	public String putObject( String newFileUrl, String filePath, String key , String contentType) throws Exception{
		key=key+newFileUrl;
//		OSSClient client = new OSSClient(endpoint, "", accessKeySecret);
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	    File file = new File(filePath);
	    InputStream content;
		content = new FileInputStream(file);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(contentType);
		meta.setContentLength(file.length());
		
		PutObjectResult result = client.putObject(bucketName, key, content, meta);
		System.out.println(result.getETag());
		return dns+ key;
	}
	
	public String putObject(String newFileUrl, InputStream is, String key) throws Exception{
		key=key+newFileUrl;
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ObjectMetadata meta = new ObjectMetadata();
		client.putObject(bucketName, key, is, meta);
		return dns+ key;
	}
	
	public void listObjects(String bucketName) {
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	    ObjectListing listing = client.listObjects(bucketName);
	    for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
	        System.out.println(objectSummary.getKey());
	    }
	}
}
