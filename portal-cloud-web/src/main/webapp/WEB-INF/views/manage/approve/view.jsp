<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="grid-content grid-content-height" id="gridView">
<input type="hidden" id="wfPrcfId" value="${wfPrcfId}">
</div>

<script>
var docType = '${type}';
BUI.use(['bui/grid', 'bui/data'], function(Grid, Data) {
	var columnsPer = {name: true, account: true, department: false, position: false, orgInfo: true, title: false, category: false, hotel: false}; //['姓名','账号','组织'];
	var columnsOrg = {name: false, account: false, department: true, position: true, orgInfo: true, title: false, category: false, hotel: false}; //['部门','职位','组织'];
	var columnsTeam = {name: false, account: false, department: false, position: false, orgInfo: false, title: true, category: true, hotel: true}; //['名称','分类','所属集团或酒店'];
	var columnsType = docType == '01' ? columnsPer : (docType == '02' ? columnsOrg : columnsTeam);
	/* Grid */
	var Grid = Grid,
        Store = Data.Store,
		wfPrcfIdObj = {'register': '注册审批流程模板', 'wardProcess': '退房查房流程模板'},
		typeObj = {'01': '按人员', '02': '按职位','04':'按工作圈'},
	    typeSel = {
	    		<c:forEach items="${typeSel}" var ="val">
		          "${val.key}":"${val.value}",
		        </c:forEach>	
		},
		grpHotelSel = {
				<c:forEach items="${groupHotelList}" var ="val">
		          "${val.id}":"${val.name}",
		        </c:forEach>
	    },
        columns = [
			{ title: '姓名',dataIndex: 'name', elCls: 'text-center', width: 70, showTip: true, visible: columnsType.name},
			{ title: '账号',dataIndex: 'account', elCls: 'text-center', width: 70, showTip: true, visible: columnsType.account},
			{ title: '部门',dataIndex: 'department', elCls: 'text-center', width: 70, showTip: true, visible: columnsType.department},
			{ title: '职位',dataIndex: 'position', elCls: 'text-center', width: 70, showTip: true, visible: columnsType.position},
			{ title: '组织',dataIndex: 'orgInfo', elCls: 'text-left', width: 150, showTip: true, visible: columnsType.orgInfo},
			{ title: '名称',dataIndex: 'title', elCls: 'text-center', width: 90, showTip: true, visible: columnsType.title},
			{ title: '分类',dataIndex: 'category', elCls: 'text-center', width: 90, showTip: true, visible: columnsType.category,renderer : Grid.Format.enumRenderer(typeSel)},
			{ title: '所属集团或酒店',dataIndex: 'hotelId', elCls: 'text-left', width: 150, showTip: true, visible: columnsType.hotel,renderer : Grid.Format.enumRenderer(grpHotelSel)}
		];
    var store = new Store({
    	url: '<c:url value="/workflowapprover/detailList"/>',
    	params:{wfPrfId:'${wfPrfId}',type:'${type}',documentNo:'${documentNo}'},
        autoLoad: true,
        pageSize: 10
    }),
	grid = new Grid.Grid({
		render: '#gridView',
		columns: columns,
		idField : 'id',
		width: '100%',
		//forceFit : true,
		//innerBorder: false, //单元格左右之间是否出现边框
		store: store,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-number'
			}
		}

	});
    grid.render();
});
</script>
