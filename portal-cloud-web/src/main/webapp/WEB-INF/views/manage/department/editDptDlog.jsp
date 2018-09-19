<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
										
	<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action='<c:url value="/department/saveOrUpdateDepartmentInfo"/>' autocomplete="off" data-plus-as-tab="true">
		<input type="hidden" name="id" value="${department.id }">
		<input type="hidden" name="grpId" value="${sessionScope.SESSION_USER.grpId }">
		
		<div class="row control-indent control-battalion control-dpt">
			<div class="control-group span10">
				<label class="control-label control-extent"><s>*</s>名称：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="name" value="${department.name }" data-rules="{required:true}">
					</div>
				</div>
			</div>
			<div class="control-group span10">
				<label class="control-label control-extent"><s>*</s>编码：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<c:choose>
							<c:when test="${empty department.id }">
								<input class="control-text input-full" type="text" name="code" value="${department.code }" data-rules="{required:true}">
							</c:when>
							<c:otherwise>
								<input class="control-text input-full" type="text" value="${department.code }" data-rules="{required:true}" disabled="disabled">
								<input type="hidden" name="code" value="${department.code }">
							</c:otherwise>
						</c:choose>
						
						
						
					</div>
				</div>
			</div>
			<div class="control-group span10">
				<label class="control-label control-extent">外文名称：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="englishName" value="${department.englishName }" >
					</div>
				</div>
			</div>
			
			<div class="control-group span10">
				<input type="hidden" value="${orgId }" id="orgId">
				<label class="control-label control-extent"><s>*</s>所属酒店：</label>
				<div class="control-container control-container-extent">
					<select class="choose-s" id="sHotel" data-maxheight="180px" name="hotelIdSelect" onchange="hotelChange()" data-placeholder="选择所属酒店"  data-rules="{required:true}" <c:if test="${staffSize > 0}">disabled</c:if>>
						<c:forEach var="hotel" items="${hotelList }">
							<c:choose>
								<c:when test="${department.hotelId != null }">
		                             <option value="${hotel.id }"  <c:if test="${department.hotelId eq hotel.id }">selected</c:if> >${hotel.name }</option>
								</c:when>
								<c:otherwise>
		                             <option value="${hotel.id }"  <c:if test="${orgId eq hotel.id }">selected</c:if> >${hotel.name }</option>
								</c:otherwise>
							</c:choose>
						
                  		</c:forEach>
					</select>
					<c:choose>
						<c:when test="${empty department.id}">
							<input type="hidden" name="hotelId" value="${orgId }" id="hotelIdx"/>
						</c:when>
						<c:otherwise>
							<input type="hidden" name="hotelId" value="${department.hotelId }" id="hotelIdx"/>
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
			
			<div class="control-group span10">
				<label class="control-label control-extent">上级部门：</label>
				<div class="control-container control-container-extent">
					<div class="input-box has-btn-dialog">
						<input id="hideDept" type="hidden" name="pid" value="${department.pid}">
						<input class="control-text input-full" id="showDept" type="text" value="${department.pname}" placeholder="选择上级部门" readonly <c:if test="${staffSize > 0}">disabled</c:if>>
						<button class="btn-dialog btn-treelist" id="btnDept" type="button" <c:if test="${staffSize > 0}">disabled</c:if>></button>
					</div>
				</div>
			</div>
			
			<div class="control-group span10">
				<label class="control-label control-extent">默认工作圈：</label>
				<div class="control-container control-container-extent">
					<select class="choose-s chosen-align-top" data-maxheight="140px" data-placeholder="选择默认工作圈" name="workTeamId" id="sTeam">
						<option value="">选择默认工作圈</option>
						<c:forEach items="${workTeamList }" var="workTeam">
							<option value="${workTeam.id }">${workTeam.name }</option>
						</c:forEach>
					</select>
					
					<script type="text/javascript">
						$("#sTeam").val("${department.workTeamId}")
					</script>
				</div>
			</div>
		</div>
	</form>
	
<script>
$(function () {
	chooseConfig();
});
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	var hotelId = '${department.hotelId}'
	if(hotelId == ''){
		if(nodeType != 0 || nodeType != 1){
			hotelId = findHotelId(orgId);
		}
	}
	var storeDept = new Data.TreeStore({
        url : '<c:url value="/post/treeOrg"/>',
        /* orgLevel = 1 只查部门树 */
        params:{orgLevel:1,hotelId:hotelId},
        autoLoad : true,
        listeners: {
        	load: function(ev) {
        		var node = storeDept.findNode('${department.id}');
        		if(node != null){
	        		storeDept.remove(node);
        		}
        	}
        }
      }),
    treeDept = new Tree.TreeList({
	      store : storeDept,
	      elCls: 'tree-noicon',
	      checkType: 'none'
    });
  	var pickerDept = new TreePicker({
  		autoAlign: false,
  		align: {
  			node: '#showDept',
  			points: ['bl', 'tl'],
  			offset: [0, 1]
  		},
  		trigger : '#btnDept',
	    textField : '#showDept',  
	    valueField : '#hideDept', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	    width: 294,  //指定宽度
	    maxHeight: 150,
	    children : [treeDept] //配置picker内的列表
    });
  	pickerDept.render();
  	var treeItem;
  	
  	treeDept.on('itemclick',function(ev){
  		treeItem = ev.item;
	});
  	
  	
  	
  	$("#sHotel").on("change",function(){
  		var id = $(this).val();
  		storeDept.load({orgLevel:1,hotelId:id});
		$('#hideDept').val('');
		$('#showDept').val('');
		$('#theJob').val('');
		$('#theJob').trigger('chosen:updated');
  	});
  	
  	window.hotelChange = function(){
  		var hotelId = $("#sHotel").val();
  		$("#hotelIdx").val(hotelId);
  	}
});
</script>