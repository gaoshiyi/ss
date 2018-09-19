<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- 新建 职位列表 Dialog start  -->
	<form class="form-horizontal" method="post" action="<c:url value="/post/savePost"/>" autocomplete="off" data-plus-as-tab="true">
		<input type="hidden" name="roleIds" value="" id="roleIds"/>
		<input type="hidden" name="deptId" value="" id="deptId"/>
		<div class="row-fluid control-indent control-battalion control-jobs control-jobs-dialog">
			<div class="control-group span10">
				<div>
					<label class="control-label control-extent"><s>*</s>名称：</label>
					<div class="control-container control-container-extent">
						<div class="input-box">
							<input class="control-text input-full" type="text" name="postEnglishName" value="" data-rules="{required:true}" autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			<div class="control-group span10">
				<div class="ml10">
					<label class="control-label control-extent"><s>*</s>职级：</label>
					<input type="hidden" name="name" id="name" />
					<div class="control-container control-container-extent">
						<select class="choose" data-placeholder="选择名称" name="postNameId" id="postNameId" data-rules="{required:true}">
							<option value="">选择职级</option>
							<c:forEach items="${typeSel}" var ="val">
				          		<option value="${val.key}">${val.value}</option>
				        	</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid control-indent control-battalion control-jobs control-jobs-dialog">
			<div class="control-group span10">
				<label class="control-label control-extent"><s>*</s>所属部门：</label>
				<div class="control-container control-container-extent">
					<div class="input-box has-btn-dialog">
						<input id="hideDpt2" type="hidden" name="" value="">
						<input class="control-text input-full" id="showDpt2" type="text" name="" value="" placeholder="选择所属部门" readonly data-rules="{required:true}">
						<button class="btn-dialog btn-treelist" id="btnDept2" type="button"></button>
					</div>
				</div>
			</div>
			<div class="control-group span10">
				<div class="ml10">
					<label class="control-label control-extent">默认工作圈：</label>
					<div class="control-container control-container-extent">
						<select class="choose" data-placeholder="选择默认工作圈" name="defaultTeam">
							<option value="">选择默认工作圈</option>
							<c:forEach items="${workTeamList }" var="workTeam">
								<option value="${workTeam.id }">${workTeam.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid control-indent control-battalion control-jobs control-jobs-dialog">
			<div class="control-group span20">
				<label class="control-label control-extent">默认角色：</label>
				<div class="control-container control-container-extent control-container-roles">
					<div class="sup-content employee-top">
						<div class="row">
							<div class="control-group pull-left bar-search ml10">
								<div class="control-container pull-left">
									<input class="control-text input-large search-text" type="text" name="" placeholder="请输入角色名" id="selectRoleName">
								</div>
							</div>
							<div class="control-group pull-left ml20">
								<button class="button button-primary button-query" id="selectRoleBtn" type="button" data-keyname="N">查询 (F)</button>
							</div>
						</div>
					</div>

					<div class="sub-content">
						<div class="row control-role control-role-employee">
							<div class="side side-left ml10">
								<p>未选择角色</p>
								<div class="clearfix">
									<div class="grid-content grid-content-border" id="gridSelect"></div>
								</div>
							</div>
							<div class="side side-right">
								<p>已选择角色</p>
								<div class="clearfix">
									<div class="grid-content grid-content-border" id="gridSelected"></div>
								</div>
							</div>
							<div class="action">
								<button class="button button-primary" id="toRight" type="button" onclick="roleInner()">&nbsp;移入 >&nbsp;</button>
								<button class="button button-primary" id="toLeft" type="button" onclick="roleOuter()">&nbsp;< 移出&nbsp;</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
<!-- 新建 职位列表 Dialog end  -->
<script>
$(function () {
	chooseConfig();
});
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	/* GridSelect */
	var GridSelect = Grid,
        StoreSelect = Data.Store,
        columnsSelect = [
            { title: 'id',dataIndex: 'id', visible : false},
			{ title: '角色名',dataIndex: 'name', elCls: 'text-left', width: '25%', showTip: true },
			{ title: '所属酒店',dataIndex: 'belongName', elCls: 'text-left', width: '50%', showTip: true }
		];
    var storeSelect = new StoreSelect({
    	url : '<c:url value="/post/roleList"/>',
        autoLoad: true,
		params:{
			belongId:findHotelId(orgId) // 所属酒店或集团id
		}
    }),
	gridSelect = new Grid.Grid({
		render: '#gridSelect',
		columns: columnsSelect,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelect,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection,Grid.Plugins.ColumnResize]
	});
    gridSelect.render();

	$("#selectRoleBtn").click(function(){
		var selections = gridSelected.getItems();
		var idvalue = "";
		for (var i = 0; i < selections.length; i++) {
			idvalue += selections[i].id + ",";
		} 
  		var roleName = $("#selectRoleName").val();
  		var params = {
  				roleName:roleName,
  				belongId:'${belongId}',
  				postIds:idvalue
  		};
  		storeSelect.load(params);
  	})
    
	/* GridSelected */
	var GridSelected = Grid,
        StoreSelected = Data.Store,
        columnsSelected = [
			{ title: 'id',dataIndex: 'id', visible : false},
			{ title: '角色名',dataIndex: 'name',sortable:false, elCls: 'text-left', width: '25%', showTip: true },
			{ title: '所属酒店',dataIndex: 'belongName',sortable:false, elCls: 'text-left', width: '50%', showTip: true }
		];
    var storeSelected = new StoreSelected({
        //data: dataSelected,
        autoLoad: true
    }),
	gridSelected = new Grid.Grid({
		render: '#gridSelected',
		columns: columnsSelected,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelected,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection,Grid.Plugins.ColumnResize]
	});
    gridSelected.render();
    
    $('body').on('click', '#toRight', function() {
		var selections1 = gridSelect.getSelection();
		gridSelect.removeItems(selections1);
		gridSelected.addItems(selections1);
		gridSelect.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected2 = gridSelected;
	});
	$('body').on('click', '#toLeft', function() {
		var selections2 = gridSelected.getSelection();
		gridSelected.removeItems(selections2);
		gridSelect.addItems(selections2);
		gridSelect.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected2 = gridSelected;
	});
	
	gridSelected2 = gridSelected;
	gridSelect2 = gridSelect;
	/*部门树*/
	var hotelId = orgId;
	if(nodeType != 0 && nodeType != 1){
		hotelId = findHotelId(orgId);
	}
	var storeDpt = new Data.TreeStore({
        url : '<c:url value="/post/treeOrg"/>',
        autoLoad : true,
        params:{'hotelId': hotelId,
        		'nodeType': 1,
        		'disableLevel': 1}
      }),
    treeDpt = new Tree.TreeList({
	      store : storeDpt,
	      elCls: 'tree-noicon',
	      checkType: 'none'
    });
  	var pickerDpt = new TreePicker({
		autoAlign: false,
		align: {
			node: '#showDpt2',
  			points: ['bl', 'tl'],
  			offset: [0, 1]
		},
		trigger : '#btnDept2',
		textField : '#showDpt2',  
		valueField : '#hideDpt2', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
		width: $('#showDpt2').outerWidth(),  //指定宽度
		children : [treeDpt] //配置picker内的列表
    });
  	pickerDpt.render();
  	
  	//var treeItemDpt;
  	treeDpt.on('itemclick',function(ev){
  		treeItemDpt = ev.item;
	});
  	treeDpt.on('selectedchange',function(ev){
  		treeItemDpt = ev.item;
  		var node = loopHotel(treeItemDpt);
  		var belongId = node.id;
  		//storeSelect.load({belongId:belongId});
	});
  	
	function loopHotel(item){
		//var orgPath = item.text;
		
		var parent = item.parent;
		if(parent != null && parent.text != '' && (parent.nodeType == 0 || parent.nodeType == 1)){
			return parent;
		}else{
			return loopHotel(parent);
		}
	}
	
});

</script>