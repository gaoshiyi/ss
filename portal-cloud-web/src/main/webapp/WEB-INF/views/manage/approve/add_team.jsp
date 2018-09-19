<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="form-horizontal">
	<div class="sup-content employee-top">
		<div class="row">
			<div class="control-group pull-left ml10">
				<div class="control-container pull-left box-w200">
					<div class="input-box">
						<input class="control-text input-full SearchInput" type="text" name="name" id="name" value="" placeholder="名称">
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<button class="button button-primary button-query" type="button" data-keyname="F">查询 (F)</button>
			</div>
		</div>
	</div>

	<div class="sub-content">
		<div class="control-role control-role-employee">
			<p>备选员工</p>
			<div class="clearfix">
				<div class="grid-content grid-content-height" id="gridTeam"></div>
			</div>
		</div>
	</div>
</div>

<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay'], function(Grid, Data, Overlay) {
	var Grid = Grid,
        Store = Data.Store,
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
			{title : '名称',dataIndex :'name', elCls: 'text-left', width:100, showTip: true},
			{ title: '分类',dataIndex: 'typeId', elCls: 'text-left', width: 150, showTip: true,renderer : Grid.Format.enumRenderer(typeSel) },
			{title : '所属集团或酒店',dataIndex :'belongId', elCls: 'text-left', width:150, showTip: true,renderer : Grid.Format.enumRenderer(grpHotelSel)}
		];
	var store = new Store({
		url:'<c:url value="/workflowapprover/teamList"/>',
		autoLoad: true,
		pageSize: 10
	}),
    grid = new Grid.Grid({
		render: '#gridTeam',
		columns: columns,
		width: '100%',
		//forceFit : true,
		idField: "id",
		store: store,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-number'
			}
		},
		listeners : {
			selectedchange:function(ev){
				var idx = selectedItems.indexOf(ev.item.id);
				if(ev.selected){
					if(idx < 0){
						selectedItems.push(ev.item.id);
						selectedItemNames.push(ev.item.name);
					}
    			 }else{
					 if(idx > -1){
    					 selectedItems.splice(idx,1);
    					 selectedItemNames.splice(idx,1);
    				 }
    			 }
			},
			itemrendered:function(ev){
				if(selectedItems.indexOf(ev.item.id) > -1){
					grid.setSelected(ev.item);
				}   
			}
		}
	});
	grid.render();
	gridClone = grid;
	//查询圈成员信息；
    $('.button-query').on('click',function(){
    	debugger
    	var nameVal = $("#name").val();
    	var params = {
   			name:nameVal
    	};
    	store.load(params);
    });
});
</script>