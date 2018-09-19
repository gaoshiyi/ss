<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>指定审批人</title>
   <jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
   <link href="<c:url value="/resources/css/management.css"></c:url>" rel="stylesheet">
<style>
.approver-box .row {
	margin-left: -15px;
}
.approver-box .control-item {
	margin-left: 25px;
}
.approver-box .ccontrol-extent {
	width: 120px;
}
.approver-box .box-w200 {
	width: 200px;
}

</style>
</head>

<body>
<div class="layout clearfix">
	<div class="content">
		<div class="panel filter-box approver-box">
			<div class="panel-body">
				<form  id="wfform" class="form-horizontal" method="post" action="<c:url value='/wfapprover/saveWfAuditDetail'/>" autocomplete="off" data-plus-as-tab="true">
					<div class="grid-enwrap grid-enwrap-diff">
						<div class="row">
							<div class="control-item">
								<div class="control-group">
									<label class="control-label control-label-auto">选择模板：</label>
									<div class="control-container box-w200 pull-left">
										<select id="wfPrcfId" name="wfPrcfId" class="choose" data-placeholder="选择模板">
											<option value="">选择模板</option>
											<c:forEach items="${wfdataList}" var="wfdata">
											  <option value="${wfdata.key}">${wfdata.name}</option>
											</c:forEach>										
										</select>
									</div>
								</div>
							</div>
							<div class="control-item">
								<div class="control-group">
									<label class="control-label ccontrol-extent">任务节点/名称：</label>
									<div class="control-container box-w200 pull-left">
										<select id="wfTaskKey" name="wfTaskKey" class="choose" data-placeholder="选择任务节点/名称">
											<option value="">选择任务节点/名称</option>
										</select>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="control-item">
								<div class="control-group">
									<label class="control-label control-label-auto">审批策略：</label>
									<div class="control-container box-w200 pull-left">
										<select id="type" name="type" class="choose" data-placeholder="选择审批策略">
											<option value="">选择审批策略</option>
											<c:forEach items="${auditTypeList}" var="item">
											  <option value="${item.key}">${item.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="control-item">
								<div class="control-group">
									<label class="control-label ccontrol-extent">审批人/职位ID：</label>
									<div class="control-container box-w200 pull-left">
										<div class="input-box has-btn-dialog">
											<input id="auditId" type="hidden" name="auditId" value="">
											<input id="auditName" class="control-text input-full" readonly="readonly" type="text" name="auditName" value="" placeholder="审批人/职位ID">
											<button class="btn-dialog" id="btn_choose" type="button"></button>								    
										</div>
									</div>
								</div>
							</div>
							<div class="control-item">
								<div class="control-group">
									<div class="control-container">
										<button class="button button-primary button-query" type="button" data-keyname="K" id="btn_ok">确定 (K)</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>
<!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
<div id="content" class="hidden" style="visibility:hidden;padding:0;margin:0;">
	<div class="form-horizontal clearfix">	
		<div class="control-container pull-left box-w200">
			<div class="input-box">
				<input class="control-text input-full" id="txt_query" type="text" name="" value="" placeholder="名称">
			</div>
		</div>
	    <button class="button button-primary button-query ml20 pull-left" type="button" data-keyname="F" id="btn_grid_query">查询 (F)</button>
    </div>
   	<div class="grid-content grid-content-height mt20" id="grid">
 	</div>
</div>

<script>
$(function () {
    chooseConfig();
});
</script>
<script>

var selectedItems = [];
var selectedItemNames = [];

var isauto = false;

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/tab','bui/mask'], function(Grid, Data, Overlay, Form, Tree, Tab) {
	var form = new Form.HForm({
	      srcNode : '#wfform'
	    });
    form.render();
    
    var Grid = Grid,
    Store = Data.Store,
    columns = [
      /* {title : '序号',dataIndex :'seq', width:50, showTip: true}, */
      {title : '名称',dataIndex :'name', elCls: 'text-left', width:100, showTip: true},
      {title : '组织',dataIndex :'orgPath', elCls: 'text-left', width:300, showTip: true},
    ],
    store = new Store({
        url : '<c:url value="/wfapprover/queryWorkTeamOrEmployee" />',
        autoLoad:false, //自动加载数据
        params: { //配置初始请求的参数
        },
        pageSize: 10,	// 配置分页数目
        listeners: {
	       	exception : function(ev){
	       		Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'});
	       	}
        }
      }),
    grid = new Grid.Grid({
      width: '100%',
      idField:"id",
      render:'#grid',
      columns : columns,
      store: store,
      // 底部工具栏
      bbar:{
    	  pagingBar: {
				xclass: 'pagingbar-number'
			}
      },
      listeners : {
    	  selectedchange:function(e){
    		  if(isauto){
    			 isauto = false; 
    		  }
    		  else{
    			 if(e.selected){
    				 selectedItems.push(e.item.id);
    				 selectedItemNames.push(e.item.name);
    			 }
    			 else{
    				 var idx = selectedItems.indexOf(e.item.id);
    				 if(idx > -1){
    					 selectedItems.splice(idx,1);
    					 selectedItemNames.splice(idx,1);
    				 }
    			 }
    		  }
    		  
    		  //alert(e.selected);
    	  },
    	  itemrendered:function(e){
    		  if(selectedItems.indexOf(e.item.id)>-1){
    			  isauto = true;
        		  grid.setSelected(e.item);
    		  }    		  
    		  //alert("itemrendered:function");
    	  }//,
    	  //itemsshow:function(e){
    		  //alert("itemrendered:function");
    	  //}
      },
      emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
      plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize]
    });

  grid.render();
    
    dialog = new Overlay.Dialog({
        title:'选择人员',
        width:790,
        height:460,
        contentId:'content',
        buttons: [{
			text: '确定 (K)',
			elCls: 'button button-primary key-K',
			handler: function() {
				$("#auditId").val(selectedItems.join(","));
	    		$("#auditName").val(selectedItemNames.join(","));
	    		$("#auditName").attr("title",selectedItemNames.join(","))
				this.close();
			}
		}, {
			text: '关闭 (Esc)',
			elCls: 'button key-Esc',
			handler: function() {
				this.close();
			}
		}]
      });
    
    $("#btn_choose").on("click",function(){
    	
    	var type = $("#type").val();
    	if(type==""){
    		Message.Alert({msg:'请先选择审批策略！', icon:'warning'});
    	}
    	else{
    		var title;
    		if(type=="01")
    			title = "选择人员";
    		else if(type=="04")
    			title = "选择工作圈";
    		store.load({type:type,pageIndex:0,start:0})
    		dialog.set("title",title);
    		dialog.show();
    	}   	
    })
    
    $("#btn_grid_query").on("click",function(){
    	var querykey = $("#txt_query").val();
    	if(querykey.trim().length>0){
    		store.load({name:querykey,pageIndex:0,start:0});
    	}
    	else{
    		store.load({name:null,pageIndex:0,start:0});
    	}
    })
    
    function validatForm(){
    	var wfPrcfId = $("#wfPrcfId").val();
    	if(wfPrcfId == ""){
    		Message.Alert({msg:'请选择模板！', icon:'warning'});
    		return false;
    	}
    	var wfTaskKey = $("#wfTaskKey").val();
    	if(wfTaskKey == ""){
    		Message.Alert({msg:'请选择任务节点！', icon:'warning'});
    		return false;
    	}
    	var type = $("#type").val();
    	if(type == ""){
    		Message.Alert({msg:'请选择审批策略！', icon:'warning'});
    		return false;
    	}
    	var type = $("#auditId").val();
    	if(type==""){
    		Message.Alert({msg:'审批人/职位ID不能为空！', icon:'warning'});
    		return false;
    	}
    	return true;
    }
    
    $("#btn_ok").on("click",function(){
    	var isValid = validatForm();
    	if(isValid){
    		form.ajaxSubmit({
    			success : function(data, textStatus){
    				Message.Alert({msg:'保存成功！', icon:'info'});
    			}
    		});
    		}
    })
 }
)
$(function () {
	    
	$("#wfPrcfId").on("change",function(p_sender){
		var wfPrcfId = $("#wfPrcfId").val();
		if(wfPrcfId == ""){
			$("#wfTaskKey").html("<option value=\"\">选择任务节点/名称</option>");
    		$("#wfTaskKey").trigger('chosen:updated');
    		return;
		}
		var url = "<c:url value='/wfapprover/getTaskidsByprocfid' />";
		$.ajax({
			url:url,
			type:"post",
			data:{wfPrcfId:wfPrcfId},
			dataType:"json",
		    success:function(data, textStatus){
		    	var html = ["<option value=\"\">选择任务节点/名称</option>"];
		    	if(data.status == 200){
		    		for(var i=0;i<data.result.length;i++){
		    			html.push("<option value=\""+data.result[i].key+"\">"+data.result[i].name+"</option>");
		    		}
		    		$("#wfTaskKey").html(html.join("\r\n"));
		    		$("#wfTaskKey").trigger('chosen:updated');
		    	}
		    	else
		    	{
		    		alert("Status:"+data.status+", "+data.message);
		    	}
		    }
		});
	});
	
	$("#type").on("change",function(){
		$("#auditId").val("");
		$("#auditName").val("");
		selectedItems.splice(0,selectedItems.length);
		selectedItemNames.splice(0,selectedItemNames.length);
	});
});
</script>
</body>
</html>