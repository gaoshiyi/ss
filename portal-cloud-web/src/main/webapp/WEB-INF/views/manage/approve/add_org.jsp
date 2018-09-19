<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="form-horizontal">
	<!-- 下拉框选项的酒店ID -->
	<input type="hidden" value="${loginHotelVal}" id="selectOrgHotelId">
	<div class="sup-content employee-top">
		<div class="row">
			<div class="control-group pull-left ml10">
				<div class="control-container pull-left box-w160">
					<div class="input-box has-btn-dialog">
						<input id="hideOpt" type="hidden" name="" value="">
						<input class="control-text input-full" id="showOpt" type="text" name="" value="" placeholder="选择部门" readonly>
						<button class="btn-dialog btn-treelist" id="btnOpt" type="button"></button>
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml10">
				<div class="control-container pull-left box-w160">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="positionName" id="positionName" value="" placeholder="职位名称">
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<button class="button button-primary button-query" data-keyname="F" type="button">查询 (F)</button>
			</div>
		</div>
	</div>

	<div class="sub-content">
		<div class="row control-role control-role-employee">
			<div class="side side-left ml10">
				<p>选择职位</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridLeft"></div>
				</div>
			</div>
			<div class="side side-right">
				<p>已选择</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridRight"></div>
				</div>
			</div>
			<div class="action">
				<button class="button button-primary" id="toRight" type="button">&nbsp;移入 >&nbsp;</button>
				<button class="button button-primary" id="toLeft" type="button">&nbsp;< 移出&nbsp;</button>
			</div>
		</div>
	</div>
</div>

<script>
var orgId = hiddenHtlId;
var nodeType = 1;
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/tree', 'bui/extensions/treepicker'], function(Grid, Data, Overlay, Tree, TreePicker) {
	/*组织树*/
	var storeTreeOrg = new Data.TreeStore({
		url : '<c:url value="/employee/treeOrgTab"/>',
		params : {
			'nodeTypeVal' : -1
		},
		autoLoad: true
	}),
    treeOrg = new Tree.TreeList({
		store : storeTreeOrg,
		elCls: 'tree-noicon',
		checkType: 'none'
    });
  	var picker = new TreePicker({
		autoAlign: false,
		align: {
			node: '#showOpt',
  			points: ['bl', 'tl'],
  			offset: [0, 1]
		},
		trigger : '#btnOpt',
		textField : '#showOpt',
		valueField : '#hideOpt', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
		width: 162,  //指定宽度
		children : [treeOrg] //配置picker内的列表
    });
	function loopParent(item,nodeType){
		if(nodeType > 1){
			var parent = item.parent;
			nodeType = parent.nodeType;
			return loopParent(parent,nodeType);
		}else{
			return item.id;
		}
	}
  	picker.render();
	var treeItem;
	treeOrg.on('itemclick',function(ev){
  		treeItem = ev.item;
  		orgId = treeItem.id;
  		nodeType = treeItem.nodeType;
  		var hotelId = loopParent(treeItem,nodeType);
  		$("#selectOrgHotelId").val(hotelId);
	});
  	storeTreeOrg.on('load',function(){
		var value = '${loginHotelVal}';
		picker.setSelectedValue(value);
    });

	/* GridLeft */
	var Grid = Grid,
        Store = Data.Store,
        columnsLeft = [
			{ title: '职位名称',dataIndex: 'englishName', elCls: 'text-center', width: 70, showTip: true },
			{ title: '组织',dataIndex: 'organization', elCls: 'text-left', width: 150, showTip: true }
		];
    var storeLeft = new Store({
        url:'<c:url value="/post/list"/>',
        params:{orgId : '${loginHotelVal}',nodeType : 1},
        autoLoad: true
    }),
	gridLeft = new Grid.Grid({
		render: '#gridLeft',
		columns: columnsLeft,
		width: '100%',
		//forceFit : true,
		store: storeLeft,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridLeft.render();

	/* GridRight */
	var Grid = Grid,
        Store = Data.Store,
        columnsRight = [
			{ title: '职位名称',dataIndex: 'englishName', elCls: 'text-center', width: 70, showTip: true },
			{ title: '组织',dataIndex: 'organization', elCls: 'text-left', width: 150, showTip: true }
		],
        dataRight = [
			
		];
    var storeRight = new Store({
        data: dataRight,
        autoLoad: true
    }),
	gridRight = new Grid.Grid({
		render: '#gridRight',
		columns: columnsRight,
		width: '100%',
		//forceFit : true,
		store: storeRight,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridRight.render();

	gridClone = gridRight;
	// 查询
    $(".button-query").click(function(){
		var positionName = $("#positionName").val();
		storeLeft.load({
			'orgId':orgId,  // 部门id
           	'nodeType':nodeType,
			'condition':positionName
		});
		
	})

	$('#toRight').on('click', function() {
		var left = gridLeft.getSelection();
		storeLeft.remove(left);
		storeRight.add(left);

		gridLeft.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridRight.get('el').find('.bui-grid-hd.checked').removeClass('checked');
	});
	$('#toLeft').on('click', function() {
		var right = gridRight.getSelection();
		storeRight.remove(right);
		storeLeft.add(right);

		gridLeft.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridRight.get('el').find('.bui-grid-hd.checked').removeClass('checked');
	});
});
</script>