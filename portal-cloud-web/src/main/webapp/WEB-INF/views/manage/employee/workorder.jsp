<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 我的工单 -->
<div class="order-content">
	<div class="form-horizontal">
		<div class="clearfix">
			<div class="control-group pull-left">
				<label class="control-label control-extent">日期：</label>
				<div class="control-container control-container-extent">
					<div class="pull-left box-w110">
						<div class="input-box has-btn-dialog">
							<input class="control-text input-full calendar" type="text" readonly>
							<button class="btn-dialog btn-calendar" type="button"></button>
						</div>
					</div>
					<span class="wave pull-left">~</span>
					<div class="pull-left box-w110">
						<div class="input-box has-btn-dialog">
							<input class="control-text input-full calendar" type="text" readonly>
							<button class="btn-dialog btn-calendar" type="button"></button>
						</div>
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<label class="control-label control-label-auto">状态：</label>
				<div class="control-container pull-left">
					<div class="box-w120">
						<select class="choose">
							<option value="1">未处理</option>
							<option value="2">处理中</option>
						</select>
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<label class="control-label control-label-auto">类型：</label>
				<div class="control-container pull-left">
					<div class="box-w100">
						<select class="choose chosen-align-right">
							<option value="1">故障</option>
							<option value="2">需求</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="control-group pull-left">
				<label class="control-label control-extent">CaseNo/内容：</label>
				<div class="control-container control-container-extent box-auto">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="" name="">
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<button class="button button-primary button-query" type="button" data-keyname="F" id="btnQuery">查询 (F)</button>
			</div>
		</div>
		<hr>
		<div class="control-group">
			<button class="pull-left button button-primary" id="btnOrderadd" type="button" data-keyname="N">新建 (N)</button>
		</div>
		<div class="gird-content grid-order-height mt10" id="gridOrder"></div>
	</div>
</div>
<script>
	$(function () {
	    chooseConfig();
	});
	BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tab', 'bui/picker', 'bui/list', 'bui/select','bui/toolbar'], function(Grid, Data, Overlay, Form, Tab, Picker, List, Select,Toolbar) {
		var borrowForm = new Form.HForm({
			srcNode: '.form-horizontal',
			autoRender: true
		});
		var GridOrder = Grid,
        StoreOrder = Data.Store,
        columnsOrder = [
			{ title: '日期',dataIndex: 'a', elCls: 'text-center', width: 100, showTip: true },
			{ title: 'CaseNo.',dataIndex: 'b', elCls: 'text-center', width: 130, showTip: true, renderer : function(value){
					return '<a class="text-primary text-underline btnQuestions" href="javascript:;">'+value+'</a>'
				}
			},
			{ title: '类型',dataIndex: 'c', elCls: 'text-center', width: 70, showTip: true },
			{ title: '内容',dataIndex: 'd', elCls: 'text-left', width: 150, showTip: true },
			{ title: '状态',dataIndex: 'e', elCls: 'text-left', width: 70, showTip: true },
			{ title: '处理人',dataIndex: 'f', elCls: 'text-left', width: 80, showTip: true },
			{ title: '联系方式',dataIndex: 'g', elCls: 'text-center', width: 110, showTip: true },
		],
        dataOrder = [
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
			{ a: '2017/10/19', b: '2017101900001', c: '故障', d: '换房失败', e: '未处理', f: '李大伟', g: '1867788999' },
		];
	    var storeOrder = new StoreOrder({
	        data: dataOrder,
	        autoLoad: true,
	        pageSize: 5
	    }),
		gridOrder = new GridOrder.Grid({
			render: '#gridOrder',
			columns: columnsOrder,
			width: '100%',
			//forceFit : true,
			//innerBorder: false, //单元格左右之间是否出现边框
			store: storeOrder,
			emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
			plugins: [ Grid.Plugins.ColumnResize],
			bbar:{
				pagingBar: {
					xclass: 'pagingbar-number'
				}
			},
			listeners : {
				aftershow: function(ev) {
					
				}
			}
		});
	    gridOrder.render();

	    /* 新建工单 ================================================================================= */
		var dialogOrderadd;
		dialogOrderaddInit = function(type) {
			dialogOrderadd = new Overlay.Dialog({
				title: '新建工单',
				width: 755,
				height: 450,
				zIndex: 1080,
				closeAction:'destroy',
				loader: {
					url: '<c:url value="employee/orderadd" />',
		            autoLoad: false, //不自动加载
		            lazyLoad: false,
		            failure: function(response, params) { // 错误调用
		                // 关闭弹出窗口
		                dialogOrderadd.close();
		                BUI.Message.Alert('<spring:message code="EO035"/>', 'error');
		            },
		            callback: function() {
						
		            }
				},
				buttons: [{
					text: '提交 (K)',
					elCls: 'button button-primary key-K',
					handler: function() {
						this.close();
					}
				},{
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
			dialogOrderadd.show();
		}
		$('#btnOrderadd').on('click', function(){
			dialogOrderaddInit();
			dialogOrderadd.get('loader').load();
		});
		/*  ================================================================================= */

		/* 工单信息—提问 ================================================================================= */
		var dialogQuestions;
		dialogQuestionsInit = function(type) {
			dialogQuestions = new Overlay.Dialog({
				title: '工单信息—提问',
				width: 630,
				height: 630,
				zIndex: 1080,
				closeAction:'destroy',
				loader: {
					url: '<c:url value="employee/questions" />',
		            autoLoad: false, //不自动加载
		            lazyLoad: false,
		            failure: function(response, params) { // 错误调用
		                // 关闭弹出窗口
		                dialogQuestions.close();
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
			dialogQuestions.show();
		}
		$('#gridOrder').on('click','.btnQuestions', function(){
			dialogQuestionsInit();
			dialogQuestions.get('loader').load();
		});
		/*  ================================================================================= */
	})
</script>