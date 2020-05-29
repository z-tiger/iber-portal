<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件上传</title>
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="plupload/queue/css/jquery.plupload.queue.css" type="text/css"></link>
	<script type="text/javascript" src="plupload/plupload.js"></script>
	<script type="text/javascript" src="plupload/plupload.html4.js"></script>
	<script type="text/javascript" src="plupload/plupload.html5.js"></script>
	<script type="text/javascript" src="plupload/plupload.flash.js"></script>
	<script type="text/javascript" src="plupload/zh_CN.js"></script>
    <script type="text/javascript" src="plupload/queue/jquery.plupload.queue.js"></script>
  <body style="padding: 0;margin: 0;">
    <div id="uploader">&nbsp;</div>
<script type="text/javascript">
var files = [];
var errors = [];
var type = 'file';
var chunk = eval('${param.chunk}');
var max_file_size = '20mb';
var filters = {title : "图片", extensions : "png,jpg,bmp,jpeg,gif"};//zip,doc,docx,xls,xlsx,ppt,pptx,
$("#uploader").pluploadQueue($.extend({
	runtimes : 'flash,html4,html5',
	url : 'insurance_uploader_upload.do',
	max_file_size : max_file_size,
	file_data_name:'file',
	unique_names:true,
	filters : [filters],
	flash_swf_url : '<%=request.getContextPath()%>/plupload/plupload.flash.swf',
	init:{
		FileUploaded:function(uploader,file,response){
			var msg = response.response;
			 msg = msg.replace("<pre>", "")
			 msg = msg.replace("</pre>", "")
			if(msg){
				var rs = $.parseJSON(msg);
				if(rs.status){
					files.push(file.name+"::"+rs.fileUrl+"::"+rs.fileSize);
				}else{
					errors.push(file.name);
				}
			}
		},
		UploadComplete:function(uploader,fs){
			var e= errors.length ? ",失败"+errors.length+"个("+errors.join("、")+")。" : "。";
// 			alert("上传完成！共"+fs.length+"个。成功"+files.length+e);
			target.window("close");
		}
	}
},(chunk ? {chunk_size:'1mb'} : {})));
</script>
  </body>
</html>
