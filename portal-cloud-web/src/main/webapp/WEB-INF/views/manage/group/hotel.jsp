<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>酒店列表</title>
</head>
<body>


<!-- 酒店列表 start -->
<div class="tab-content panel-body pd0">
	<!-- filter-box start -->
	<div class="panel filter-box">
		<div class="panel-body">
			<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
				<div class="row">
					<div class="control-item">
						<div class="control-group ml20">
							<label class="control-label control-label-auto">国家：</label>
							<div class="control-container box-w130 pull-left">
								<select class="choose" data-placeholder="选择国家" id="sCountry">
									<option value="">选择国家</option>									
								</select>
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml20">
							<label class="control-label control-label-auto">省/州：</label>
							<div class="control-container box-w130 pull-left">
								<select class="choose" data-placeholder="选择省/州" id="sProvince">
									<option value="">选择省/州</option>									
								</select>
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml20">
							<label class="control-label control-label-auto">市：</label>
							<div class="control-container box-w130 pull-left">
								<select class="choose" data-placeholder="选择市" id="sCity">
									<option value="">选择市</option>									
								</select>
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml20">
							<label class="control-label control-label-auto">星级：</label>
							<div class="control-container box-w130 pull-left">
								<select class="choose" data-placeholder="选择星级" id="sLevel">
									<option value="">选择星级</option>
									<option value="5">五星</option>
									<option value="4">四星</option>
									<option value="3">三星</option>
									<option value="2">二星</option>
									<option value="1">一星</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-item">
						<div class="control-group ml20">
							<label class="control-label control-label-auto">状态：</label>
							<div class="control-container box-w130 pull-left">
								<select class="choose" data-placeholder="选择状态" id="sStatus">
									<option value="">选择状态</option>
									<option value="2">停用</option>
									<option value="0">正常</option>
								</select>
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml10">
							<div class="control-container">
								<input class="control-text input-wauto" type="text" name="" value="" placeholder="酒店编码/酒店名称/联系人" id="sCode">
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml10">
							<button class="button button-primary button-query" type="button" id="search" data-keyname="N">查询 (F)</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<hr>
	</div>
	<!-- filter-box end -->

	<!-- grid-top-bar start -->
	<div class="grid-top-bar clearfix">
		<div class="action-pop pull-left">
			<button class="button button-primary" type="button" id="openStatus">启 用</button>
			<button class="button" type="button" id="closeStatus">停 用</button>
		</div>
		<div class="pull-right">
			<button class="btn-icon btn-icon-small btn-icon-up" type="button" title="上移"></button>
			<button class="btn-icon btn-icon-small btn-icon-down" type="button" title="下移"></button>
			<button class="btn-icon btn-icon-small btn-icon-import" type="button" title="导入"></button>
			<button class="btn-icon btn-icon-small btn-icon-export" type="button" title="导出"></button>
			<!-- <button class="btn-icon btn-icon-small btn-icon-export-column" type="button" title="列导出"></button> -->
			<button class="btn-icon btn-icon-small btn-icon-edit" type="button" title="列权限"></button>
			<button class="btn-icon btn-icon-small btn-icon-authority" type="button" title="列调整"></button>
		</div>
	</div>
	<!-- grid-top-bar end -->

	<!-- grid start -->
	<div class="grid-content" id="grid"></div>
	<!-- grid end -->
</div>
<!-- 酒店列表 start -->
				
<script type="text/javascript">
$(function () {
	chooseConfig();
});

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	/* 酒店列表 ================================================================================= */
	/* Grid */
	var Grid = Grid,
	       Store = Data.Store,
	       columns = [
			{ title: '状态',dataIndex: 'status', width: 60, renderer : Grid.Format.enumRenderer({
					'2':'停用',
					'0':'正常'
				})
			},
			{ title: '酒店编码',dataIndex: 'code', elCls: 'text-left', width: 100, showTip: true },
			{ title: '酒店名称',dataIndex: 'name', elCls: 'text-left', width: 150, showTip: true },
			{ title: '星级',dataIndex: 'level', elCls: 'text-center', width: 80, showTip: true, renderer : Grid.Format.enumRenderer({
				'1':'一星',
				'2':'二星',
				'3':'三星',
				'4':'四星',
				'5':'五星'
			})},
			{ title: '联系人',dataIndex: 'contacter', elCls: 'text-left', width: 100, showTip: true },
			{ title: '联系电话',dataIndex: 'contactNo', width: 150, showTip: true },
			{ title: '邮箱',dataIndex: 'email', width: 150, showTip: true }
		];
	     
	   var store = new Store({
		    url : '<c:url value="/group/findHotelList"/>',
			params : {
				groupId : '${parentId}'
			},
	       autoLoad: true,
			pageSize: 10,
			listeners : {
		       	exception : function(ev){
		       		Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'});
		       	}
	        }
	   }),
	   
	grid = new Grid.Grid({
		render: '#grid',
		columns: columns,
		width: '100%',
		//forceFit : true,
		store: store,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-number'
			}
		}
	});
	   grid.render();
	   
	   //搜索
	   $("#search").bind("click",function(){
			
			var sCountry=$("#sCountry").val();
			var sProvince=$("#sProvince").val();
			var sCity=$("#sCity").val();
			
			var level=$("#sLevel").val();
			var status=$("#sStatus").val();
			var code=$("#sCode").val().replace(/(^\s*)|(\s*$)/g, "");
			
			var countryId='';
			var countryCode='';
			if(sCountry != ''){
				var strs= new Array();
				strs=sCountry.split("+");
				countryCode=strs[0];
				countryId=strs[1];
			}
			
			var provinceId='';
			var provinceCode='';
			if(sProvince != ''){
				var strs= new Array();
				strs=sProvince.split("+");
				provinceCode=strs[0];
				provinceId=strs[1];
			}
			
			var cityId='';
			var cityCode='';
			if(sCity != ''){
				var strs= new Array();
				strs=sCity.split("+");
				cityCode=strs[0];
				cityId=strs[1];
			}
			
			store.load({
				countryId:countryId,
				countryCode:countryCode,
				provinceId:provinceId,
				provinceCode:provinceCode,
				cityId:cityId,
				cityCode:cityCode,
				level:level,
				status:status,
				code:code,
				name:code,
				contacter:code
			})
		})
		
		$("#openStatus").bind("click",function(){
			var selections = grid.getSelection();
			var ids = "";
			for (var i = 0; i < selections.length; i++) {
				ids += selections[i].id + ",";
			}
			
			if(ids == ""){
				Message.Alert({msg:'请选择需要启用的酒店！'});
				
			}else{
				BUI.Message.Confirm('确认启用吗？',function(){
					$.ajax({
						url : '<c:url value="/group/batchUpdateStatus"/>',
						data : {
							ids : ids,
							status:0
						},
						dateType : 'html',
						success : function(data) {
							if (data > 0) {
								store.load();
							}
						}
					});
	               },'question');
				
			}
		})
		
		$("#closeStatus").bind("click",function(){
			
			var selections = grid.getSelection();
			var ids = "";
			for (var i = 0; i < selections.length; i++) {
				ids += selections[i].id + ",";
			}
			
			if(ids == ""){
				Message.Alert({msg:'请选择需要停用的酒店！'});
			}else{
				BUI.Message.Confirm('确认停用吗？',function(){
					$.ajax({
						url : '<c:url value="/group/batchUpdateStatus"/>',
						data : {
							ids : ids,
							status:2
						},
						dateType : 'html',
						success : function(data) {
							if (data > 0) {
								store.load();
							}
						}
					});
	               },'question');
				
			}
		})
		
});
/*  ================================================================================= */

</script>

<script type="text/javascript">

</script>

<!-- areaSelect -->
<script src="<c:url value="/resources/js/areaSelect.js"></c:url>"></script>
<script>
    var areaSelect = new areaSelect('sCountry', 'sProvince','sCity','<c:url value="/common/getAreaData"/>','选择国家');
	//默认查询：001国家编码；0001省州编码；HF市编码
	areaSelect.init();
</script> 
</body>
</html>