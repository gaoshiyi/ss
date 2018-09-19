<%@page import="com.sstc.hmis.portal.common.base.PropertyUtil"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<% String commonResUrl = PropertyUtil.COMMON_RESOURCES_URL;
   String ctx = request.getContextPath();
%>
<link rel="stylesheet" href="<%=commonResUrl%>/bui/build/css/bs3/bui.css">
<link rel="stylesheet" href="<%=commonResUrl%>/bui/build/css/bs3/dpl.css">
<link rel="stylesheet" href="<%=commonResUrl%>/public/css/base.css">
<link rel="stylesheet" href="<%=commonResUrl%>/public/plugin/chosen/chosen.css">
<link rel="stylesheet" href="<%=commonResUrl%>/public/plugin/clockpicker/jquery-clockpicker.min.css">

<script src="<%=commonResUrl%>/jquery/jquery-1.8.1.min.js"></script>
<script src="<%=commonResUrl%>/bui/build/loader.js"></script>
<script src="<%=commonResUrl%>/public/js/public.js"></script>
<script src="<%=commonResUrl%>/public/js/keyCode.js"></script>
<script src="<%=commonResUrl%>/public/plugin/chosen/chosen.jquery.js"></script>
<script src="<%=commonResUrl%>/public/plugin/clockpicker/jquery-clockpicker.min.js"></script>
<script src="<%=commonResUrl%>/public/js/areaSelect.js"></script>
<script src="<%=commonResUrl%>/public/plugin/slimscroll/jquery.slimscroll.min.js"></script>


<input type="hidden"  value="${sessionScope.SESSION_USER.grpId }" id="hiddenGrpId">
<input type="hidden"  value="${sessionScope.SESSION_USER.hotelId }" id="hiddenHtlId">

<script type="text/javascript">
	document.domain = window.location.hostname.indexOf('sstcsoft') >= 0 ? 'sstcsoft.com' : window.location.hostname;
	var hiddenGrpId = $("#hiddenGrpId").val();
	var hiddenHtlId = $("#hiddenHtlId").val();
</script>
