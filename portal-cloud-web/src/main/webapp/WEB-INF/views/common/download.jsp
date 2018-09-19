<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>下载</title>
<jsp:include page="header-2.0.0.jsp"></jsp:include>
<link href="<c:url value="/resources/css/download.css" />" rel="stylesheet">

</head>

<body>
<div class="wrapper">
	<div class="sestion clearfix">
		<div class="pic">
			<img src="<c:url value="/resources/images/download/dw_pic_mobile.png" />">
		</div>
		<div class="intro">
			<h1>手机端下载</h1>
			<h2>方便企业通过手机APP进行业务操作，查看数据报表</h2>
			<div class="sort clearfix">
				<div class="text">
					<h3>扫维二维码，即刻下载</h3>
					<h4>实时掌握最新状态</h4>
					<a class="button button-primary" href="https://file.empark.sstcsoft.com/app/lingling_empark.apk">Android 下载</a>
				</div>
				<img class="qrcode" src="https://file.empark.sstcsoft.com/app/lingling_empark.png" alt="二维码">
			</div>
		</div>
	</div>

	<div class="sestion clearfix">
		<div class="intro">
			<h1>浏览器下载</h1>
			<h2>可支持多种浏览器大屏观看实时动态</h2>
			<div class="sort clearfix">
				<div class="text">
					<h3>目前仅支持 Chrome 浏览器</h3>
					<h4><img src="<c:url value="/resources/images/download/dw_google.png" />">&nbsp;&nbsp;谷歌浏览器</h4>
					<a class="button button-primary" href="https://file.empark.sstcsoft.com/app/ChromeStandalone.exe">Chrome 下载</a>
				</div>
			</div>
		</div>
		<div class="pic">
			<img src="<c:url value="/resources/images/download/dw_pic_pc.png" />">
		</div>
	</div>
	
	<div class="sestion clearfix">
		<div class="pic">
			<img src="<c:url value="/resources/images/download/dw_pic_print.png" />">
		</div>
		<div class="intro">
			<h1>打印控件下载</h1>
			<h2>帮助用户快速出单，告别传统手写，高效且实用</h2>
			<div class="sort clearfix">
				<div class="text">
					<h3>未安装最新打印控件？建议安装</h3>
					<a class="button button-primary" href="https://file.empark.sstcsoft.com/app/CLodop_Setup.zip">打印控件下载</a>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>