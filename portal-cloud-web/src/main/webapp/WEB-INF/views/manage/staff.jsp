<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<title>角色管理</title>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<link href="<c:url value="/resources/css/management.css"></c:url>" rel="stylesheet">
</head>

<body>
<div class="layout clearfix">
	<div class="content">
		<h1 class="title mb10">员工管理</h2>
		<!-- filter-box start -->
		<div class="panel filter-box">
			<div class="panel-body">
				<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
					<div class="row">
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">组织：</label>
								<div class="control-container box-w160 pull-left">
									<select class="chosen-select-no-single input-full" data-placeholder="选择组织">
										<option value="">选择组织</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">状态：</label>
								<div class="control-container box-w160 pull-left">
									<select class="chosen-select-no-single input-full" data-placeholder="选择状态">
										<option value="">选择状态</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group">
								<div class="control-container">
									<input class="control-text input-large" type="text" name="" value="" placeholder="中文名/外文名/电话/邮箱/工号">
									<button class="button button-primary" type="button">查 询(F)</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- filter-box end -->

		<!-- grid-top-bar start -->
		<div class="grid-top-bar clearfix">
			<div class="action-pop pull-left">
				<button class="button button-primary button-alike" id="btnAdd" type="button" data-keyname="N">新 建(N)</button>
				<button class="button button-primary button-pda" id="btnStratery"type="button">批量设置登录策略</button>
				<button class="button button-primary button-pda ml10" id="btnDel" type="button" data-keyname="D">批量删除(D)</button>
			</div>
			<div class="action-pup pull-right">
				<button class="btn-icon btn-icon-small btn-icon-up" type="button" title="上移"></button>
				<button class="btn-icon btn-icon-small btn-icon-down" type="button" title="下移"></button>
				<button class="btn-icon btn-icon-small btn-icon-import" type="button" title="导入"></button>
				<button class="btn-icon btn-icon-small btn-icon-export" type="button" title="导出"></button>
				<button class="btn-icon btn-icon-small btn-icon-export-column" type="button" title="列导出"></button>
				<button class="btn-icon btn-icon-small btn-icon-edit" type="button" title="列权限"></button>
				<button class="btn-icon btn-icon-small btn-icon-authority" type="button" title="列调整"></button>
			</div>
		</div>
		<!-- grid-top-bar end -->

		<!-- grid start -->
		<div class="grid-content" id="grid"></div>
		<!-- grid end -->
	</div>
</div>

<!-- 新建 Dialog start  -->
<div class="hide" id="contentEmployee">
	<div id="tab"></div>
	<div id="panel">
		<div class="hide">
			<!-- 基本资料 start -->
			<div class="tab-content">
				<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="">
					<div class="row control-indent control-employee">
						<div class="span18">
							<div class="row">
								<div class="control-group span6">
									<label class="control-label control-extent">帐号<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">默认密码<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="password" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">收银帐号：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">收银密码：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="password" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">中文姓名<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">外文姓名：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">手机号码<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">性别<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<label class="radio"><input type="radio" name="gender" value="0" checked>男</label>
										<label class="radio ml10"><input type="radio" name="gender" value="1">女</label>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">&nbsp;</label>
									<div class="control-container control-container-extent">&nbsp;</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">所属酒店<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择所属酒店">
											<option value="">选择所属酒店</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">所属部门<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择所属部门">
											<option value="">选择所属部门</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">所属职位<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择所属职位">
											<option value="">选择所属职位</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">类型<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择类型">
											<option value="">选择类型</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">工号<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">联系电话<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">邮箱地址<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">限制登录：</label>
									<div class="control-container control-container-extent">
										<label class="checkbox"><input type="checkbox" name="" value="1"></label>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">停用：</label>
									<div class="control-container control-container-extent">
										<label class="checkbox"><input type="checkbox" name="" value="1"></label>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">证件类型<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择证件类型">
											<option value="">选择证件类型</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span12">
									<label class="control-label control-extent">证件号码<s>*</s>：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">过期时间：</label>
									<div class="control-container control-container-extent">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" type="text" name="" value="">
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">出生日期：</label>
									<div class="control-container control-container-extent">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" type="text" name="" value="">
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">学历：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择学历">
											<option value="">选择学历</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">国家：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择国家">
											<option value="">选择国家</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">省/州：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择省/州">
											<option value="">选择省/州</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">市：</label>
									<div class="control-container control-container-extent">
										<select class="chosen-select-no-single input-full" data-placeholder="选择市">
											<option value="">选择市</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
										</select>
									</div>
								</div>
								<div class="control-group span18">
									<label class="control-label control-extent">详细地址：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">联系人1：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">联系电话：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">&nbsp;</label>
									<div class="control-container control-container-extent">&nbsp;</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">联系人2：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">联系电话：</label>
									<div class="control-container control-container-extent">
										<div class="input-box">
											<input class="control-text input-full" type="text" name="" value="">
										</div>
									</div>
								</div>
								<div class="control-group span18">
									<label class="control-label control-extent">描述：</label>
									<div class="control-container control-container-extent control-container-area">
										<div class="input-box">
											<textarea class="control-text input-full" name=""></textarea>
										</div>
									</div>
								</div>

							</div>
						</div>
						<div class="span5 ml20">
							<p><img class="header" src="../../resources/updata/header.jpg" alt="header"></p>
							<p><span class="auxiliary-text h6">仅支持JPG、JPEG、GIF、PNG图片文件，且文件小于200k</span></p>
						</div>
					</div>
				</form>
			</div>
			<!-- 基本资料 end -->
		</div>
		<div class="hide">
			<!-- 工作酒店 start -->
			<div class="tab-content">
				<div class="row control-indent control-employee control-employee-hotel form-horizontal">
					<div class="side side-left ml10">
						<div class="tree-list tree-noicon" id="treeList"></div>
					</div>
					<div class="side side-right ml10">
						<div class="grid-content grid-content-border" id="gridTree"></div>
					</div>
				</div>
			</div>
			<!-- 工作酒店 end -->
		</div>
		<div class="hide">
			<!-- 用户角色 start -->
			<div class="tab-content">
				<div class="row control-employee control-role-employee form-horizontal">
					<div class="side side-left ml10">
						<div class="bui-tab-panel bui-tab nav-tabs">
							<div class="tab-panel-inner">
								<ul><li class="bui-tab-panel-item bui-tab-item bui-tab-panel-item-selected"><span class="bui-tab-item-text">备选角色</span></li></ul>
							</div>
						</div>
						<div class="tab-content clearfix">
							<div class="grid-content grid-content-border" id="gridSelect"></div>
						</div>
					</div>
					<div class="side side-right">
						<div class="bui-tab-panel bui-tab nav-tabs">
							<div class="tab-panel-inner">
								<ul><li class="bui-tab-panel-item bui-tab-item bui-tab-panel-item-selected"><span class="bui-tab-item-text">已选角色</span></li></ul>
							</div>
						</div>
						<div class="tab-content clearfix">
							<div class="grid-content grid-content-border" id="gridSelected"></div>
						</div>
					</div>
					<div class="action">
						<button class="button button-primary button-own" id="toRight" type="button">移入 ></button>
						<button class="button button-primary button-own" id="toLeft" type="button">< 移出</button>
					</div>
				</div>
			</div>
			<!-- 用户角色 end -->
		</div>
		<div class="hide">
			<!-- 数据权限 start -->
			<div class="tab-content">
				<div class="row control-indent control-employee control-employee-jurisd form-horizontal">
					<div class="side side-left ml10">
						<div class="tree-list tree-noicon" id="treeListJurisd"></div>
					</div>
					<div class="side side-right ml10"></div>
				</div>
			</div>
			<!-- 数据权限 end -->
		</div>
	</div>
</div>
<!-- 新建 Dialog end  -->

<!-- 用户登录策略 Dialog start  -->
<div class="hide" id="contentStratery">
	<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
		<div class="row control-indent control-employee control-stratery">
			<div class="control-group span12">
				<label class="control-label control-extent">登录酒店：</label>
				<div class="control-container control-container-extent">
					<select class="chosen-select-no-single input-full" data-placeholder="选择登录酒店">
						<option value="">选择登录酒店</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
					</select>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">开始日期：</label>
				<div class="control-container control-container-extent">
					<div class="input-box has-btn-dialog">
						<input class="control-text input-full calendar" type="text" name="" value="">
						<button class="btn-dialog btn-calendar" type="button"></button>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">结束日期：</label>
				<div class="control-container control-container-extent">
					<div class="input-box has-btn-dialog">
						<input class="control-text input-full calendar" type="text" name="" value="">
						<button class="btn-dialog btn-calendar" type="button"></button>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">开始时间：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full calendar-time clockpicker" type="text" name="" value="" readonly>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">结束时间：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full calendar-time clockpicker" type="text" name="" value="" readonly>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">开始时间：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full calendar-time clockpicker" type="text" name="" value="" readonly>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">结束时间：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full calendar-time clockpicker" type="text" name="" value="" readonly>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">开始时间：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full calendar-time clockpicker" type="text" name="" value="" readonly>
					</div>
				</div>
			</div>
			<div class="control-group span6">
				<label class="control-label control-extent">结束时间：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full calendar-time clockpicker" type="text" name="" value="" readonly>
					</div>
				</div>
			</div>
			<div class="control-group span12">
				<div class="control-container control-container-checkbox">
					<label class="checkbox"><input type="checkbox" name="" value="1">周一</label>
					<label class="checkbox"><input type="checkbox" name="" value="2">周二</label>
					<label class="checkbox"><input type="checkbox" name="" value="3">周三</label>
					<label class="checkbox"><input type="checkbox" name="" value="4">周四</label>
					<label class="checkbox"><input type="checkbox" name="" value="5">周五</label>
					<label class="checkbox"><input type="checkbox" name="" value="6">周六</label>
					<label class="checkbox"><input type="checkbox" name="" value="7">周日</label>
					<label class="checkbox"><input type="checkbox" name="" value="pc">PC</label>
					<label class="checkbox"><input type="checkbox" name="" value="mobile">手机</label>
				</div>
			</div>
			<div class="control-group span12">
				<label class="control-label control-extent">允许登录<s>*</s>：</label>
				<div class="control-container control-container-extent">
					<label class="radio"><input type="radio" name="allowLogin" value="1" checked>可以</label>
					<label class="radio ml10"><input type="radio" name="allowLogin" value="0">不可以</label>
					<label class="checkbox ml20"><input type="checkbox" name="" value="1">停用</label>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 用户登录策略 Dialog end  -->


<script>
$('.clockpicker').clockpicker({
	autoclose: true,
	'default': 'now'
});
</script>
<script>
var config = {
	'.chosen-select': {},
	'.chosen-select-deselect': {
		allow_single_deselect: true
	},
	'.chosen-select-no-single': {
		disable_search_threshold: 10
	},
	'.chosen-select-no-results': {
		no_results_text: '哎呀，没有发现！'
	},
	'.chosen-select-width': {
		width: "95%"
	}
}
$(function () {
	for (var selector in config) {
		$('.panel-body').find(selector).chosen(config[selector]);
	}
});
</script>

<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, Tab) {
    /* Grid */
	var Grid = Grid,
        Store = Data.Store,
        columns = [
			{ title: '操作',dataIndex: 'id', width: 150, renderer : function(value){ 
					return '<a class="grid-icon grid-icon-edit btn-edit" href="javascript:;" onclick="editRow(\''+value+'\');" title="编辑"></a>\
					<span class="text-gray-light">/</span>\
					<a class="grid-icon grid-icon-del btn-del" href="javascript:;" onclick="delRow(\''+value+'\');" title="删除"></a>\
					<span class="text-gray-light">/</span>\
					<a class="grid-icon grid-icon-lock btn-lock" href="javascript:;" onclick="lock(\''+value+'\');" title="锁定"></a>\
					<span class="text-gray-light">/</span>\
					<a class="grid-icon grid-icon-reset btn-reset" href="javascript:;" onclick="resetPwd(\''+value+'\');" title="重置密码"></a>\
					<span class="text-gray-light">/</span>\
					<a class="grid-icon grid-icon-strategy btn-strategy" href="javascript:;" onclick="loginStratery(\''+value+'\');" title="登录策略"></a>\
					<span class="text-gray-light">/</span>\
					<a class="grid-icon grid-icon-link btn-link" href="javascript:;" onclick="roleLink(\''+value+'\');" title="关联角色"></a>'
				}
			},
			{ title: '中文姓名',dataIndex: 'b', elCls: 'text-left', width: 100 },
			{ title: '外文姓名',dataIndex: 'c', elCls: 'text-left', width: 100 },
			{ title: '工号',dataIndex: 'd', elCls: 'text-left', width: 100 },
			{ title: '电话',dataIndex: 'e', elCls: 'text-left', width: 100 },
			{ title: '状态',dataIndex: 'f', elCls: 'text-left', width: 80 },
			{ title: '归属组织',dataIndex: 'g', elCls: 'text-left', width: 200 }
		],
        data = [
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' },
			{id:'1', b: '张强 ', c: 'bj_Jason', d: '10024567415', e: '13512457788', f: '正常', g: '世纪金源集团/北京世纪金源/财务部/会计' }
		];
    var store = new Store({
        data: data,
        autoLoad: true,
		pageSize: 10
    }),
	grid = new Grid.Grid({
		render: '#grid',
		columns: columns,
		width: '100%',
		//forceFit : true,
		store: store,
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-defined'
			}
		}
	});
    grid.render();

	//行编辑
	window.editRow = function(id) {
		dialogEmployeeInit(id);
		dialogEmployee.set('title','修改员工信息');
		dialogEmployee.get('loader').load({id:id});
	}

	//行删除
	window.delRow = function(id) {
		
	}

	//行锁定
	window.lock = function(id) {
		
	}

	//行重置密码
	window.resetPwd = function(id) {
		
	}

	//行用户登录策略
	window.loginStratery = function(ids) {
		dialogStrateryInit(ids)
	}

	//行关联角色
	window.roleLink = function(id) {
		
	}

	//添加员工
	window.userAdd = function(id) {
		dialogEmployeeInit(id);
		dialogEmployee.show();
		dialogEmployee.get('loader').load({id:id});
	}

	var tab = new Tab.TabPanel({
		render: '#tab',
		elCls: 'nav-tabs',
		panelContainer: '#panel', //如果内部有容器，那么会跟标签项一一对应，如果没有会自动生成
		autoRender: true,
		children: [{
			title: '基本资料',
			value: '1',
			selected: true,
		},{
			title: '工作酒店',
			value: '2',
			/*loader: {
				url: '',
				params: {}
			}*/
		},{
			title: '用户角色',
			value: '3',
			/*loader: {
				url: '',
				params: {}
			}*/
		},{
			title: '数据权限',
			value: '4',
			/*loader: {
				url: '',
				params: {}
			}*/
		}]
	});
	
	/* 新建 Dialog */
	var dialogEmployee;
	function dialogEmployeeInit(id) {
		dialogEmployee = new Overlay.Dialog({
			title: '新建员工信息',
			width: 960,
			height: 690,
			contentId: 'contentEmployee',
			closeAction:'destroy',
			buttons: [{
				text: '保存(S)',
				elCls: 'button button-primary button-alike',
				handler: function() {
					this.close();
				}
			}, {
				text: '关闭(ESC)',
				elCls: 'button button-alike',
				handler: function() {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					this.close();
				}
			}],
			listeners: {
				afterRenderUI: function(e) {
					for (var selector in config) {
						$('.bui-dialog').find(selector).chosen(config[selector]);
					}
					node = dialogEmployee.get('el').find('form').eq(0); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
				},
				closeclick: function(e) {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					//return false;
				}
			}
		});
		dialogEmployee.show()
	}
	$('#btnAdd').on('click', function() {
		dialogEmployeeInit();
	});


	/* 用户登录策略 Dialog */
	var dialogStratery;
	function dialogStrateryInit(ids) {
		dialogStratery = new Overlay.Dialog({
			title: '用户登录策略设置',
			width: 560,
			height: 390,
			contentId: 'contentStratery',
			closeAction:'destroy',
			buttons: [{
				text: '保存(S)',
				elCls: 'button button-primary button-alike',
				handler: function() {
					this.close();
				}
			}, {
				text: '关闭(ESC)',
				elCls: 'button button-alike',
				handler: function() {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					this.close();
				}
			}],
			listeners: {
				afterRenderUI: function(e) {
					for (var selector in config) {
						$('.bui-dialog').find(selector).chosen(config[selector]);
					}
					node = dialogStratery.get('el').find('form').eq(0); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
				},
				closeclick: function(e) {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					//return false;
				}
			}
		});
		dialogStratery.show();
	}
	$('#btnStratery').on('click', function() {
		var selects = grid.getSelection();
		var ids = '';
		if(selects.length <= 0){
 			Message.Alert({msg:'请选择设置项'});
			return;
 		}
 		for(var i=0; i<selects.length; i++){
 			ids += selects[i].id+",";
 		}
		dialogStrateryInit(ids);
	});

	/* 工作酒店 ================================================================================= */
	//树节点数据，
	//text : 文本，
	//id : 节点的id,
	//leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
	//expanded ： 是否默认展开
	//checked : 节点是否默认选中
	var data = [
		{text: '世纪金源集团',id: '1',expanded: true,children: [
			{text: '北京世纪金源', id: '11',expanded: true, children: [
				{text: '市场部', id: '111',expanded: true, children: [
					{text: '销售', id: '1111', checked: true},
					{text: '市场', id: '1112'}
				]},
				{text: '人事部', id: '112',expanded: true, children: [
					{text: '主管', id: '1121'},
					{text: '专员', id: '1122'}
				]}
			]}
		]}, 
		{text: 'CRM', id: '2',children: [
			{text: '21',id: '21',children: [
				{text: '211',id: '211',children: [
					{text: '2111',id: '2111'},
					{text: '2112',id: '2112'}
				]},
				{text: '212',id: '212',children: [
					{text: '2121',id: '2121'},
					{text: '2121',id: '2121'},
				]}
			]}
		]}
	];
	var tree = new Tree.TreeList({
		render: '#treeList',
		nodes: data,
		checkType: 'onlyLeaf', //checkType:勾选模式，提供了4中，all,onlyLeaf,none,custom
		showLine: true //显示连接线
	});
	tree.render();
	tree.on('checkedchange', function(ev) {
		var node = ev.node;
		var pText1 = node.parent ? node.parent.text : '';
		var pText2 = node.parent.parent ? node.parent.parent.text : '';
		var pText3 = node.parent.parent ? node.parent.parent.parent.text : '';
		var group = pText3 + '/' + pText2 + '/' + pText1 + '/' + ev.node.text;
		if (node.checked) { //勾选上
			storeTree.add({
				id: node.id,
				b: group,
			});
		} else { //取消勾选
			storeTree.remove(ev.node.id, function(obj1,obj2){
				return obj1 == obj2.id;
			});
		}
	});
	
	/* GridTree */
	var GridTree = Grid,
        StoreTree = Data.Store,
        columnsTree = [
			{ title: '操作',dataIndex: 'id', width: 40, renderer : function(value){ 
					return '<a class="grid-icon grid-icon-del btn-del" href="javascript:;" onclick="delRow(\''+value+'\',this);" title="删除"></a>'
				}
			},
			{ title: '组织',dataIndex: 'b', elCls: 'text-left', width: 300 },
		],
        dataTree = [
			{ id: '1111', b: '世纪金源集团/北京世纪金源/市场部/销售' },
		];
    var storeTree = new Store({
        data: dataTree,
        autoLoad: true,
    }),
	gridTree = new Grid.Grid({
		render: '#gridTree',
		columns: columnsTree,
		width: '100%',
		//forceFit : true,
		store: storeTree,
		plugins: [Grid.Plugins.ColumnResize]
	});
    gridTree.render();
	
	//删除工作酒店
	window.delRow = function(id,obj) {
		var index = $(obj).parents('tr').index() - 1;
		var item = gridTree.getItemAt(index);
		var node = tree.findNode(id);
		storeTree.remove(item);
		tree.setNodeChecked(node,false);
	}
	
	window.formateScrillBar = function(obj) {
		var headH = obj.get('el').find('.bui-grid-header .bui-grid-table').height();
		var bodyH = obj.get('el').find('.bui-grid-body .bui-grid-table').height();
		var bodyMaxHeight = parseInt(obj.get('el').find('.bui-grid-body').css('maxHeight'));
		var scrollbarWidth = obj.get('el').find('.bui-grid-body')[0].offsetWidth - obj.get('el').find('.bui-grid-body')[0].clientWidth;
		if (bodyH > bodyMaxHeight) {
			obj.get('el').find('.bui-grid-hd-empty').show();
			obj.get('el').find('.bui-grid-hd.bui-grid-hd-empty').width(scrollbarWidth);
		}
	}

	/* GridSelect */
	var GridSelect = Grid,
        StoreSelect = Data.Store,
        columnsSelect = [
			{ title: '名称',dataIndex: 'b', elCls: 'text-left', width: '40%' },
			{ title: '所属集团或酒店',dataIndex: 'c', elCls: 'text-left', width: '60%' }
		],
        dataSelect = [
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' },
			{ b: '超级管理员 ', c: '北京世纪金源' }
		];
    var storeSelect = new Store({
        data: dataSelect,
        autoLoad: true,
    }),
	gridSelect = new Grid.Grid({
		render: '#gridSelect',
		columns: columnsSelect,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelect,
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize],
		listeners: {
			aftershow: function(ev){
				formateScrillBar(gridSelect);
			}
		},
	});
    gridSelect.render();
	
	/* 用户角色 ================================================================================= */
	/* GridSelected */
	var GridSelected = Grid,
        StoreSelected = Data.Store,
        columnsSelected = [
			{ title: '名称',dataIndex: 'b', elCls: 'text-left', width: '40%' },
			{ title: '所属集团或酒店',dataIndex: 'c', elCls: 'text-left', width: '60%' }
		],
        dataSelected = [
			{ b: '超级管理员 ', c: '北京世纪金源' },
		];
    var storeSelected = new Store({
        data: dataSelected,
        autoLoad: true,
    }),
	gridSelected = new Grid.Grid({
		render: '#gridSelected',
		columns: columnsSelected,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelected,
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize],
		listeners: {
			aftershow: function(ev){
				formateScrillBar(gridSelected);
			}
		},
	});
    gridSelected.render();
	
	$('body').on('click', '#toRight', function() {
		var selections1 = gridSelect.getSelection();
		gridSelect.removeItems(selections1);
		gridSelected.addItems(selections1);
		formateScrillBar(gridSelect);
		formateScrillBar(gridSelected);
	});
	$('body').on('click', '#toLeft', function() {
		var selections2 = gridSelected.getSelection();
		gridSelected.removeItems(selections2);
		gridSelect.addItems(selections2);
		formateScrillBar(gridSelect);
		formateScrillBar(gridSelected);
	});
	
	/* 数据权限 ================================================================================= */
	//树节点数据，
	//text : 文本，
	//id : 节点的id,
	//leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
	//expanded ： 是否默认展开
	//checked : 节点是否默认选中
	var dataJurisd = [
		{text: 'PMS',id: '1',expanded: true,children: [
			{text: '预定', id: '11',expanded: true, children: [
				{text: '散客预定', id: '111',expanded: true, children: [
					{text: '新建散客预定', id: '1111', checked: true},
					{text: '复制散客预定', id: '1112'}
				]},
				{text: '团队预订', id: '112',expanded: true, children: [
					{text: '新建团队预定', id: '1121'},
					{text: '复制团队预定', id: '1122'}
				]}
			]}
		]}, 
		{text: 'CRM', id: '2',children: [
			{text: '21',id: '21',children: [
				{text: '211',id: '211',children: [
					{text: '2111',id: '2111'},
					{text: '2112',id: '2112'}
				]},
				{text: '212',id: '212',children: [
					{text: '2121',id: '2121'},
					{text: '2121',id: '2121'},
				]}
			]}
		]}
	];
	var treeJurisd = new Tree.TreeList({
		render: '#treeListJurisd',
		nodes: dataJurisd,
		showLine: true //显示连接线
	});
	treeJurisd.render();
});
</script>
</body>
</html>