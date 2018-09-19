<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 新建工单 -->
<div class="personal">
	<div class="form-horizontal">
		<div class="row">
			<div class="span5">
				<label class="control-label control-extent">姓名：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">zhangxiahu</span>
				</div>
			</div>
			<div class="span8">
				<label class="control-label control-extent">酒店：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">合肥北城世纪金源大饭店</span>
				</div>
			</div>
			<div class="span5">
				<label class="control-label control-extent">部门：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">财务部</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span5">
				<label class="control-label control-extent">电话：</label>
				<div class="control-container control-container-extent box-auto">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="187698765566" name="">
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span18">
				<label class="control-label control-extent">问题描述：</label>
				<div class="control-container control-container-extent" style="height: 115px;">
					<div class="input-box">
						<textarea class="control-text input-full area-remark" style="height: 100px;" name=""></textarea>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span18">
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
							<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
							<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
							<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
							<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
							<li><a class="file" href="">账户.txt</a><a class="btn btn-del" href="javascript:;">删除</a></li>
						</ul>
					</div>
					<span class="auxiliary-text h6">( 仅支持10M以内JPG，PDF，OFFICE，TXT格式文件，最多上传5个附件)</span>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$(function () {
	    /* 滚动条 */
		$('#queueContent').perfectScrollbar();

		$(window).on('resize', function() {
			setTimeout(function() {
				$('#queueContent').perfectScrollbar('update');
			}, 100);
		});
	    chooseConfig();
	});
</script>