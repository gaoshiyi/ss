<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 工单信息-提问 -->
<div class="personal questions">
	<div class="form-horizontal">
		<div class="clearfix">
			<div class="control-group pull-left">
				<label class="control-label control-extent">CaseNo：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">2007101700002</span>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<span class="control-form-text input-full nowrap text-success">处理中</span>
			</div>
			<div class="control-group pull-right">
				<button class="button button-primary button-query pull-left" type="button">关闭</button>
				<button class="button button-primary button-query pull-left ml10" type="button">恢复</button>
			</div>
		</div>
		<div class="clearfix">
			<div class="control-group pull-left">
				<label class="control-label control-extent">日期：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">2017/10/17</span>
				</div>
			</div>
			<div class="control-group pull-right">
				<a class="control-form-text text-primary text-underline" href="javascript:void(0)">表扬</a>
				<a class="control-form-text text-primary text-underline ml10" href="javascript:void(0)">投诉</a>
			</div>
		</div>
		<hr>
		<div class="tab-remark" id="tab"></div>
		<div class="mt10" id="panel">
			<div>
				<form class="form-horizontal" method="post" action="" autocomplete="off" data-plus-as-tab="true">
					<ul class="info-content" id="infoContent">
						<li>
							<div class="clearfix">
								<span class="pull-left">2017/10/17 15:14</span>
								<span class="pull-left ml30">zhangxiaohu</span>
							</div>
							<p class="message">你们的系统能发导弹么？</p>
							<div class="hide link-content">
								<a class="text-primary text-underline" href="javascript:void(0)">编辑</a>
								<a class="text-primary text-underline ml10" href="javascript:void(0)">删除</a>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left">2017/10/17 15:14</span>
								<span class="pull-left ml30">李大伟</span>
							</div>
							<p class="message">不能！</p>
							<div class="clearfix link-content">
								<a class="text-primary text-underline" href="javascript:void(0)">编辑</a>
								<a class="text-primary text-underline ml10" href="javascript:void(0)">删除</a>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left">2017/10/17 15:14</span>
								<span class="pull-left ml30">zhangxiaohu</span>
							</div>
							<p class="message">你们的系统能发导弹么？</p>
							<div class="hide link-content">
								<a class="text-primary text-underline" href="javascript:void(0)">编辑</a>
								<a class="text-primary text-underline ml10" href="javascript:void(0)">删除</a>
							</div>
						</li>
					</ul>
					<div class="clearfix control-container">
						<div class="input-box">
							<textarea class="area input-full area-add" name="" rows="" cols=""></textarea>
						</div>
						<div class="control-group pull-right mt10 mb10">
							<button class="button button-primary button-query pull-left" type="button">追加</button>
						</div>
					</div>
					<hr>
					<div class="clearfix">
						<label class="control-label control-extent">附件管理：</label>
						<div class="control-container control-container-extent">
							<div class="bui-uploader defaultTheme" aria-disabled="false" aria-pressed="false">
								<div class="bui-uploader-htmlButton bui-uploader-button pull-right" aria-disabled="false" aria-pressed="false"><a href="javascript:void(0);" class="bui-uploader-button-wrap"><span class="bui-uploader-button-text">上传附件</span><div class="file-input-wrapper"><input type="file" name="fileData" hidefocus="true" class="file-input" multiple="multiple"></div></a>
								</div>
								<div class="bui-queue bui-simple-list" aria-disabled="false" aria-pressed="false">
									<ul>
										
									</ul>
								</div>
							</div>
							<div class="bui-queue" id="queueContent">
								<ul>
									<li><a class="file" href="">主页文字.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
									<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
									<li><a class="file" href="">主页文字.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
									<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
								</ul>
							</div>
							<span class="auxiliary-text h6">( 仅支持10M以内JPG，PDF，OFFICE，TXT格式文件，最多上传5个附件)</span>
						</div>
					</div>
				</form>
			</div>
			<div class="hide">
				<div class="clearfix data-content" id="dataContent">
					<ul class="data-content">
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
						<li>
							<div class="clearfix">
								<span class="pull-left control-form-text">2017/10/17 15:14</span>
								<span class="pull-left ml30 text-name control-form-text nowrap">李大伟</span>
								<span class="pull-left ml30 text-data control-form-text nowrap">更新内容</span>
								<span class="pull-left ml30 text-file control-form-text nowrap">什么公司能发射导弹.doc</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function scrollbarUpdate (){
		$('#queueContent').perfectScrollbar('update');
		$('#infoContent').perfectScrollbar('update');
		$('#dataContent').perfectScrollbar('update');
	}
	$(function () {
	    /* 滚动条 */
		$('#queueContent').perfectScrollbar();
		$('#infoContent').perfectScrollbar();
		$('#dataContent').perfectScrollbar();
		scrollbarUpdate();
	});
</script>
<script>
BUI.use(['bui/tab', 'bui/picker', 'bui/list'], function(Tab, Picker, List) {
    /* Tab */
	var tab = new Tab.TabPanel({
		render: '#tab',
		elCls: 'nav-tabs',
		panelContainer: '#panel', //如果内部有容器，那么会跟标签项一一对应，如果没有会自动生成
		autoRender: true,
		children: [{
			title: '过程',
			value: '1',
			selected: true,
			/*loader: {
				url: '../dialog/data/text.php',
			}*/
		}, {
			title: '日志',
			value: '2',
		}]
	});
	tab.on('selectedchange',function(ev) {
      	scrollbarUpdate();
    });
});
</script>