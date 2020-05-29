$(function() {
			//查询链接
			$(document).keydown(function(event){
				if(event.keyCode===13){
                    loadData();
				}
			});

			//查询链接
			$("#btnQuery").bind("click",loadData);

            //加载数据
            function loadData() {
                var method = $('#method').val();
                var memberId = $('#memberId').val();
                var start = $('#queryDateFrom').textbox('getValue');
                if (start) start = new Date(start).getTime();
                var end = $('#queryDateTo').textbox('getValue');
                if (end) end = new Date(end).getTime();

                $('#dataGrid').datagrid('load', {
                    method: method,
                    memberId: memberId,
                    start: start,
                    end: end
                });
            }

			$('#dataGrid').datagrid( {
				title : '接口异常管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dataListXExceptionLog.do',
				pageSize : 30,
				pageList : [50,30,10],
				idField : 'id',
				columns : [ [
				{field:'memberid',title:'会员id',align:'center',width : $(this).width() * 0.1},
					{field:'method',title:'接口',align:'center',width : $(this).width() * 0.2},
                    {field:'createtime',title:'创建时间',align:'center',sortable:true,width : $(this).width() * 0.2,
                        formatter: function (value) {
                            if (value) {
                                var d = new Date();
                                d.setTime(value);
                                return d.toLocaleString()
                            }
                        }},
                    {field:'param',title:'参数',align:'center',width : $(this).width() * 0.2},
                    {field:'exceptioncontent',title:'异常内容',align:'center',width : $(this).width() * 0.3}
				] ],
				pagination : true,
				rownumbers : true
			});

	//清空
	$("#clearQuery").bind("click",function(){
		$('#queryDateFrom').textbox('setValue', '');
		$('#queryDateTo').textbox('setValue', '');
        $('#method').val('');
        $('#memberId').val('');
	});
});
		