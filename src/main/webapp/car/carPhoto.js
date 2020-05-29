$(function() {
	//table
	$('#carPhotoGrid').datagrid({
		title : '车辆抓拍记录',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_photo_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"), 
			'lpn':$("#s-lpn").textbox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '城市',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val) {
				return val.substring(0,2) + "•" + val.substring(2);
			}
		}, {
			field : 'url',
			title : '图片地址',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter:function(value, row, index){
				   if(value != ""){
				      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: fixed;top:100px;'><img style='height:480px;width:800px;' src='"+value+"'/></div>";
				   }
				}
		}, {
			field : 'createName',
			title : '创建人',
			width : $(this).width() * 0.2,
			align : 'center'
		},{
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.2,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	  
	//query
	$("#btnQuery").bind("click",function(){
		$("#carPhotoGrid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"), 
			'lpn':$("#s-lpn").textbox("getValue"),
			'queryDateFrom': $('#queryDateFrom').textbox('getValue'),
			'queryDateTo':$('#queryDateTo').textbox('getValue')
		});
	});
	
	/*//clear
	$("#btnClear").bind("click",function(){
		$("#scarPhotoForm").form("clear");
	});*/
	//清空
	$("#btnClear").bind("click",function(){
		clearToolbar();
	}); 
});
function clearToolbar(){
	$('#toolbar').form('clear');
}
function display(id){
	document.getElementById("box"+id).style.display="block"; 
}
function disappear(id){
	document.getElementById("box"+id).style.display="none"; 
}