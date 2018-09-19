<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.sstc.hmis.portal.common.base.PropertyUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<% 
   	String profileActive = PropertyUtil.PROFILE_ACTIVE;
	pageContext.setAttribute("ac",PropertyUtil.PROFILE_ACTIVE);
	String commonResUrl = PropertyUtil.COMMON_RESOURCES_URL;
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>晟世天成</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<jsp:include page="common/header-2.0.0.jsp"></jsp:include>
<link href="<c:url value="/resources/css/index.css"></c:url>" rel="stylesheet">
<link href="<c:url value="/resources/css/work-order.css"></c:url>" rel="stylesheet">
<link href="<c:url value="/resources/css/notification.css"></c:url>" rel="stylesheet">
</head>

<body>
<div class="wrapper" id="wrapper">
	<!-- nav-left start -->
	<nav class="navbar-default navbar-static-side" id="navbarSide">
		<div class="sidebar-action">
			<span class="title">系统导航</span>
			<span class="icon icon-toleft" id="fixedSide" title="收起"></span>
		</div>
		<div class="sidebar-collapse">
			<div class="side-menu" id="side_menu"></div>
		</div>
	</nav>
	<!-- nav-left end -->
	<input type="hidden" id="profile" value="<%=profileActive %>">
	<!-- nav-top start -->
	
	<nav class="navbar-top navbar-top-<%=profileActive %>" id="nav">
		<div class="menu-toggle sys-toggle cate-list Toggle">
			<div class="cut-icon-menu toggle-title clearfix">
				<c:forEach items="${sessionScope.PERM_SYS_LIST }" var="sys" varStatus="status" >
					<c:if test="${status.first }">
						<span class="text">${sys.name }</span>
					</c:if>
				</c:forEach>
				<span class="icon"></span>
			</div>
			<div class="submenu">
				<ul id="J_Nav">
					<c:forEach items="${sessionScope.PERM_SYS_LIST }" var="sys" varStatus="status" >
						<c:if test="${sys.id ne '3489542adcc941f096dfd9073dacfe98' }">
							<li class="nav-item ${status.first? 'nav-item-selected':'' }" data-id="${sys.id }">
								<a href="javascript:;">
									<p class="pic ${sys.iconClass }"></p>
									<p class="title">${sys.name }</p>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="menu-toggle list-toggle Toggle clearfix">
			<div class="toggle-title clearfix"><span class="icon icon-l"></span><span class="text">我的菜单</span><span class="icon"></span></div>
			<div class="submenu" style="min-width: 150px;">
				<ul id="J_Menu"></ul>
			</div>
		</div>
		<div class="menu-toggle report-toggle Toggle clearfix">
			<div class="toggle-title clearfix"><span class="icon icon-l"></span><span class="text">我的报表</span><span class="icon"></span></div>
			<div class="submenu box-w190">
				<ul id="J_Report"></ul>
			</div>
		</div>

		<div class="bard-right pull-right">
			<div class="search-box pull-left">
				<input class="control-text" id="top_search_value" type="text" name="" placeholder="">
				<span class="btn-search" id="top_search_btn">搜索</span>
			</div>
			<div class="shortcut-items pull-left">
				<div class="menu-toggle user-box layoutX Toggle pull-left">
					<div class="toggle-title clearfix">
						<p class="pic header">
							<img src="<c:url value="${sessionScope.SESSION_USER.avatar }"></c:url>">
						</p>
						<div class="sort">
							<span class="name" title="${sessionScope.SESSION_USER.familyName }${sessionScope.SESSION_USER.name }">
								${sessionScope.SESSION_USER.familyName }${sessionScope.SESSION_USER.name }
							</span>
						</div>
						<span class="icon"></span>
					</div>
					<div class="submenu personal-submenu">
						<ul>
							<li><a id="Personal" href="javascript:void(0)" title="个人信息"><span class="person-icon person-icon1"></span><span class="title">个人信息</span></a></li>
							<li><a id="Password" href="javascript:void(0)" title="修改密码"><span class="person-icon person-icon2"></span><span class="title">修改密码</span></a></li>
							<li><a href="logout" title="注销登录"><span class="person-icon person-icon3"></span><span class="title">注销登录</span></a></li>
							<!-- <span class="action">您好，<a href="logout">退出</a></span> -->
						</ul>
					</div>
				</div>
				<div class="menu-toggle class-box layoutX Toggle pull-left">
					<div class="toggle-title clearfix">
						<div class="sort">
							<span class="name" id="currentShift"></span>
						</div>
						<span class="icon"></span>
					</div>
				</div>
				<div class="menu-toggle time-box pull-left">
					<div class="toggle-title clearfix">
						<div class="sort">
							<span class="time letter-text" id="bizDate">
								<fmt:formatDate pattern="yyyy/MM/dd" value="${bizDate}" />
							</span>
						</div>
					</div>
				</div>
				<div class="menu-toggle hotel-toggle Toggle clearfix">
					<div class="toggle-title toggle-hotel clearfix" title="${hotel.name }">
						
						<c:forEach items="${sessionScope.SESSION_HOTELS }" var="hotel" >
							<input type="hidden" id="sessionHotelId" value="${sessionScope.SESSION_USER.hotelId }">
							<input type="hidden" id="hotelId" value="${hotel.id }">
							<c:if test="${hotel.id == sessionScope.SESSION_USER.hotelId }">
								<span class="text">${hotel.name }</span>
							</c:if>
						</c:forEach>
						<span class="icon"></span>
					</div>
					<div class="submenu">
						<ul>
							<c:forEach items="${sessionScope.SESSION_HOTELS }" var="hotel" >
								<li><a href="javascript:switchWorkHotel('${hotel.id }');" title="${hotel.name }">${hotel.name }</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="menu-toggle lang-toggle Toggle clearfix">
					<div class="toggle-title toggle-hotel clearfix">
						<span class="text">
							<c:choose>
								<c:when test="${sessionScope.SESSION_LOCALE eq 'en'}">
									<span class="lang-icon lang-en"></span>
								</c:when>
								<c:otherwise>
									<span class="lang-icon lang-zh"></span>
								</c:otherwise>
							</c:choose>
						</span>
						<span class="icon"></span>
					</div>
					<div class="submenu">
						<ul>
							<li><a href="javascript:switchLocale('zh');"><span class="lang-icon lang-zh"></span><span class="title">中文</span></a></li>
							<li><a href="javascript:switchLocale('en');"><span class="lang-icon lang-en"></span><span class="title">英文</span></a></li>
						</ul>
					</div>
				</div>
				<div class="menu-toggle menu-links clearfix">
					<div class="toggle-title toggle-cut-icon clearfix">
						<a class="cut-icon cut-icon-phone" href="<c:url value="common/download"/>" data-id="dw" data-title="下载" title="下载"></a>
					</div>
				</div>
				<div class="menu-toggle tips-toggle Toggle clearfix">
					<div class="toggle-title toggle-cut-icon clearfix">
						<span class="cut-icon cut-icon-tips"></span>
						<!-- <span class="number-text">7</span> -->
					</div>
				</div>
				<div class="menu-toggle Toggle clearfix help-content">
					<div class="toggle-title toggle-cut-icon clearfix">
						<span class="cut-icon cut-icon-help"></span>
					</div>
					<div class="submenu">
						<ul>
							<li><a href="javascript:void(0)" title="使用手册"><span class="help-icon help-icon1"></span><span class="title">使用手册</span></a></li>
							<li><a href="javascript:void(0)" title="问题反馈" id="btnOrder"><span class="help-icon help-icon2"></span><span class="title">问题反馈</span></a></li>
							<li><a href="javascript:void(0)" title="在线客服"><span class="help-icon help-icon3"></span><span class="title">在线客服</span></a></li>
							<li><a class="help-toggle version" href="<%=commonResUrl%>/html/version.html" data-id="version" data-title="版本更新日志" title="版本信息"><span class="help-icon help-icon4"></span><span class="title">版本信息</span></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</nav>
	<!-- nav-top end -->
	
	<div class="page-wrapper" id="page_wrapper">
		<!-- main start -->
		<div class="content-main" id="content_main"></div>
		<!-- main end -->
	</div>
</div>
<div class="hide" id="classes">
	<ul class="classes" id="shiftList">
		
	</ul>
</div>

<div class="notification" v-if="need" :class="{'bottom': show, 'opacity': opacity}" id="notification" v-cloak>
	<div class="notification-group">
		<h2 class="notification-title">{{ title }}</h2>
		<div class="notification-content">
			<p>{{ sort }}</p>
			<a class="text-primary" href="javascript:;" @click="showView">点击查看详情</a>
		</div>
		<div class="notification-loseBtn icon-close" @click="close">x</div>
	</div>
</div>

<script src="resources/js/index.js"></script>

<script>
function setScroll(){
	$('#side_menu').slimScroll();
}

function switchLocale(locale){
	$.ajax({
		url:'<c:url value="/sys/locale/switch" />',
		data: {
			localeStr : locale
		},
		success:function(json){
			if(json.status == 200){
				location.reload();
			}
		}
	});
}

function switchWorkHotel(hotelId){
	$.ajax({
		url:'<c:url value="/sys/hotel/switch" />',
		data: {
			hotelId : hotelId
		},
		success:function(json){
			if(json.status == 200){
				location.reload();
			}
		}
	});
}

$(function () {
	
	/* 敬请期待 */
	$('#J_Nav .cate-icon-zh').parent().parent().prepend('<span>敬请期待</span>');
	$('#J_Nav .cate-icon-yh').parent().parent().prepend('<span>敬请期待</span>');
	$('#J_Nav .cate-icon-bx').parent().parent().prepend('<span>敬请期待</span>');
	
	$('#side_menu').slimScroll({
        height: '100%',
        color: '#cccccc',
        railOpacity: .9,
        alwaysVisible: !1
    });
	$(window).on('resize',setScroll);
	
	//我的菜单，我的报表
	$('#J_Menu, #J_Report').on('click', 'a', function(e) {
		e.preventDefault();
		var id = $(this).attr('data-id'),
			title = $(this).find('.title').html(),
			href = $(this).attr('href');
		if (href && href != '#' && href != 'javascript:;') {
			if (top.topManager) {
				//打开左侧菜单中配置过的页面
				top.topManager.openPage({
					id: id,
					title: title,
					href: href,
					reload: true
				});
			}
		}
	});
	
	//版本 
	$('.version').on('click', function(e) {
		e.preventDefault();
		var id = $(this).attr('data-id'),
			title = $(this).attr('data-title'),
			href = $(this).attr('href');
		if (href && href != '#' && href != 'javascript:;') {
			if (top.topManager) {
				//打开左侧菜单中配置过的页面
				top.topManager.openPage({
					id: id,
					title: title,
					href: href,
					reload: true
				});
			}
		}
	});
	
	/* 下载 */
	$('.menu-links').on('click', 'a', function(e) {
		e.preventDefault();
	});
	$('.menu-links').on('click', function(e) {
		e.preventDefault();
		var item = $(this).find('a');
		if (item.length) {
			var id = item.attr('data-id'),
				title = item.attr('data-title'),
				href = item.attr('href');
			if (href && href != '#' && href != 'javascript:;') {
				if (top.topManager) {
					//打开左侧菜单中配置过的页面
					top.topManager.openPage({
						id: id,
						title: title,
						href: href,
						reload: true
					});
				}
			}
		}
	});
});

/* 系统升级通知 */
var notification = new Vue({
    el: '#notification',
    data: {
		noticeId:'',
    	need: false,
		opacity: true,
		show: false,
        title: '系统升级通知',
        sort: '您好！非常感谢使用YUDAO智慧酒店管理系统，针对近期用户需求以及BUG，我司将于XXXX年XX月XX日晚上XX点XX分做系统升级，系统升级约需XX分钟。'
    },
	created: function(){
		this.loadData();
	},
    methods: {
        loadData: function() {
            var _this = this;
            $.ajax({
                type : "GET",
                dataType : "json",
                url : '<c:url value="/sysMsgnotify/isPrompt" />',
                data : '',
                async : false,
                success : function(data) {
                    if (data.status == "200") {
                    	_this.noticeId = data.result.id;
                    	_this.title = data.result.title;
                        _this.sort = data.result.summary;
						_this.need = true;
						_this.opacity = true;
                    	setTimeout(function(){
    						_this.show = true;
                		},1000);
                    }
                }
            });
        },
		close: function() {
			var _this = this;
			this.opacity = false;
			setTimeout(function(){
				_this.need = false;
				_this.show = false
			}, 3000);
		},
		showView: function() {
			var id = this.noticeId;
			dialogNoticeViewInit(id);
			dialogNoticeView.get('loader').load();
		}
    }
});
</script>
<script>
BUI.use(['bui/overlay','bui/form','common/main'], function(Overlay,Form){
	/* 系统升级通知详情 ================================================================================= */
	window.dialogNoticeViewInit = function(idValue) {
		dialogNoticeView = new Overlay.Dialog({
			title: '详情',
			width: 630,
			height: 460,
			closeAction:'destroy',
			loader: {
	            url: '<c:url value="/sysMsgnotify/view" />',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            params : {id:idValue},
	            callback: function() {
	            	
	            }
	        },
			buttons: [{
				text: '确认接收 (K)',
				elCls: 'button button-primary key-K',
				handler: function() {
					var noticeId = $("#noticeId").val();
					var params = {noticeId : noticeId}
					dialogNoticeHandleInit();
					dialogNoticeHandle.get('loader').load(params);
					dialogNoticeView.close();
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogNoticeView.show();
	}
	/*  ================================================================================= */
	
	var dialogPassword,pwdForm;
	/* 班次选择 */
	var dialog;
	dialogInit = function(type) {
		dialog = new Overlay.Dialog({
			title: '班次选择',
			width: 446,
			height: 290,
			zIndex: 1082,
			closeAction:'destroy',
			contentId: 'classes',
			buttons: [{
				id: 'classesSubmit',
				text: '确定 (K)',
				elCls: 'button button-primary key-K',
				handler: function() {
					$('#classesSubmit').prop('disabled',true);
					var id = $('.classes li.active input:eq(0)').val()
					var code = $('.classes li.active input:eq(1)').val()
					var className = $('.classes').find('li.active').first().find('p').first().text();
					$.ajax({
						type : "POST",
						dataType : "json",
						url : '<c:url value="/sys/shift/switch" />',
						data : {id : id,
								code: code
							   },
						success : function(result) {
							if (result.status == 200) {
								$('.class-box .name').html(className);
								dialog.close();
							} else {
							    
							}
							$('#classesSubmit').prop('disabled',false);
						}
					});
				}
			}, {
				id: 'classsesClose',
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
				}
			}
		});
		dialog.show();
	}

	$('.class-box').on('click', function() {
		dialogInit();
	});

	$('body').on('click', '.classes li', function() {
		$('.classes li').removeClass('active');
		$(this).addClass('active');
	});
	
	/* 修改密码 ================================================================================= */
	
	dialogPasswordInit = function(type) {
		dialogPassword = new Overlay.Dialog({
			title: '修改密码',
			width: 400,
			height: 240,
			closeAction:'destroy',
			loader: {
				url: '<c:url value="employee/password" />',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            failure: function(response, params) { // 错误调用
	                // 关闭弹出窗口
	                BUI.Message.Alert('<spring:message code="EO035"/>', 'error');
	            },
	            callback: function() {
	            	var node = dialogPassword.get('el').find('form').eq(0);
					pwdForm = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
	            }
			},
			buttons: [{
				text: '确定 (K)',
				elCls: 'button button-primary key-K',
				handler: function() {
					if (pwdForm.isValid()) {
						if($("#newPwd").val() != $("#pwdConfirm").val()){
							BUI.Message.Alert('两次输入的新密码不一样，请重新输入', 'error');
							return;
						}
						pwdForm&&pwdForm.ajaxSubmit({
							success : function(data) {
								var code = data.status;
								if(code == '200'){
									dialogPassword.close();
									BUI.Message.Alert('修改成功，请重新登录', function(){
										window.location.href = "logout";
									},'success');
								}else{
									BUI.Message.Alert('原密码输入错误，请重新输入', 'error');
									return;
								}
							}
						});
					}
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogPassword.show();
	}
	

	$('#Password').on('click', function(){
		dialogPasswordInit();
		dialogPassword.get('loader').load();
	});

	/* 个人信息 ================================================================================= */
	var dialogPersonal;
	dialogPersonalInit = function(type) {
		dialogPersonal = new Overlay.Dialog({
			title: '个人信息',
			width: 600,
			height: 420,
			closeAction:'destroy',
			loader: {
				url: '<c:url value="employee/info" />',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            failure: function(response, params) { // 错误调用
	                BUI.Message.Alert('<spring:message code="EO035"/>', 'error');
	            },
	            callback: function() {
	            	var node = dialogPersonal.get('el').find('form').eq(0);
					formInfo = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
	            }
			},
			buttons: [{
				text: '确定 (K)',
				elCls: 'button button-primary key-K',
				handler: function() {
					if(formInfo.isValid()){
						formInfo&&formInfo.ajaxSubmit({
							success:function(data, status){
								BUI.Message.Alert('保存成功', 'success');
							},
							dataType:"json"
						});
					}
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogPersonal.show();
	}
	$('#Personal').on('click', function(){
		dialogPersonalInit();
		dialogPersonal.get('loader').load();
	});
	/*  ================================================================================= */
	
	/* 我的工单 ================================================================================= */
	var dialogOrder;
	dialogOrderInit = function(type) {
		dialogOrder = new Overlay.Dialog({
			title: '我的工单',
			width: 780,
			height: 460,
			closeAction:'destroy',
			loader: {
				url: '<c:url value="employee/workorder" />',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            failure: function(response, params) { // 错误调用
	                // 关闭弹出窗口
	                dialogOrder.close();
	                BUI.Message.Alert('<spring:message code="EO035"/>', 'error');
	            },
	            callback: function() {
					
	            }
			},
			buttons: [{
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogOrder.show();
	}
	$('#btnOrder').on('click', function(){
		dialogOrderInit();
		dialogOrder.get('loader').load();
	});
	/*  ================================================================================= */


//Tab标签的Config初始化
	var reportHtml = '<li><a href="{url}" title="{name}" data-id="{id}" title="{name}"><span class="del" title="移出我的报表"></span>{name}</a></li> ';
	var menuHtml = '<li><a href="{url}" data-id="{id}" title="{name}"><span class="del" title="移出我的菜单"></span><span class="title">{name}</span></a></li>';
	menuInit();
	//我的菜单初始化
	function myMenuInit(){
		$.ajax({
			url:'<c:url value="/staffMenu/my" />',
			success:function(data){
				var menu = data.result.menu;
				var report = data.result.report;
				$("#J_Menu").html(html(menu,0));
				$("#J_Report").html(html(report,1));
			}
		});
	}
	
	function myShiftInit(){
		$.ajax({
			url:'<c:url value="/sys/shift" />',
			success:function(data){
				var current = data.result.current;
				$("#currentShift").html(current.StrVal2);
				var currentId = current.id;
				var shift = data.result.shift;
				var html = "";
				for(var i = 0; i < shift.length; i++){
					var id = shift[i].id;
					var active = id==currentId? 'active':'';
					html += '<li class="' + active + '"><p>{StrVal2}</p><p>{StrVal3} - {StrVal4}</p>' +
					'<input type="hidden" name="id" value="{id}"><input type="hidden" name="code" value="{StrVal1}"></li>';
					html = html.format(shift[i]);
				}
				$("#shiftList").html(html);
			}
		});
	}
	
	function html(data,type){
		var html = ''
		if(typeof(data) != 'undefined'){
			for(var i = 0; i< data.length; i++){
				var menu = data[i];
				if(type == 1){
					html += reportHtml;
				}else{
					html += menuHtml;
				}
				html = html.format(menu);
			}
		}
		return html;
	}
	// 角色菜单初始化
	function menuInit(){
		$.ajax({
			url:'<c:url value="/sys/staff/menu" />',
			success:function(data){
				var status = data.status;
				if(status == 102){
					BUI.Message.Alert("根据安全策略，您需要修改默认密码后才能使用系统！",function(){
						dialogPasswordInit();
						dialogPassword.get('loader').load();
					},'info');
				}else if(status == 200){
					var result = data.result;
					var menu = result.menu;
					var home = result.homePage;
					var id,title,href;
					if(home != null){
						id = home.id;
						title = home.name;
						href = home.url;
					}
					var bizDate = result.bizDate;
					if(bizDate != ''){
						$("#bizDate").html(bizDate);
					}else{
						$("#bizDate").html('<span style="color:red;">无营业时间</span>');
					}
					new PageUtil.MainPage({
						homePage: {  //首页的信息
							id: id,
							title: title,
							href: href
						},
						setFavorite: { //设为首页、我的菜单、我的报表的Ajax地址,参数type=0为设为我的菜单和我的报表,type=1为设为首页
				            url: '<c:url value="/staffMenu/add" />',
				            param: '', //可以不传
				            success: function(result) {
				            	var collect = result.result.collected;
				            	if(collect == '0'){
					                var perm = result.result.perm;
					                if(perm != null){
						                var isReport = perm.isReport;
						                html = menuHtml;
						                var addId = "J_Menu";
						                if(isReport == 1){
						                	addId = "J_Report";
						                	html = reportHtml;
						                }
						                html = html.format(perm);
						                $("#"+addId).append(html)
					                }
				            	}else{
				            		var menuId = result.result.menuId;
				            		$('.submenu a[data-id="'+menuId+'"]').parent().remove();
				            	}
				            }
				        },
						modulesConfig : menu  //菜单
					});
					myMenuInit();
					myShiftInit();
				}
			}
		});
	}
	
});
</script>
</body>
</html>