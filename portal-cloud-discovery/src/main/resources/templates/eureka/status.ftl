<#import "/spring.ftl" as spring />
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
  <head>
    <base href="<@spring.url basePath/>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Eureka</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="eureka/css/wro.css">

  </head>

  <body id="one">
    <#include "header.ftl">
    <div class="container-fluid xd-container">
      <#include "navbar.ftl">
      <h1>Eureka已注册应用</h1>
      <table id='instances' class="table table-striped table-hover">
        <thead>
          <tr><th>Application</th><th>AMIs</th><th>Availability Zones</th><th>Status</th></tr>
        </thead>
        <tbody>
          <#if apps?has_content>
            <#list apps as app>
              <tr>
                <td><b>${app.name}</b>(<a style="cursor: pointer;" onclick="restartAllInstance(this)">重启所有实例</a>)</td>
                <td>
                  <#list app.amiCounts as amiCount>
                    <b>${amiCount.key}</b> (${amiCount.value})<#if amiCount_has_next>,</#if>
                  </#list>
                </td>
                <td>
                  <#list app.zoneCounts as zoneCount>
                    <b>${zoneCount.key}</b> (${zoneCount.value})<#if zoneCount_has_next>,</#if>
                  </#list>
                </td>
                <td>
                  <#list app.instanceInfos as instanceInfo>
                    <#if instanceInfo.isNotUp>
                      <font color=red size=+1><b>
                    </#if>
                    <b>${instanceInfo.status}</b> (${instanceInfo.instances?size}) -
                    <#if instanceInfo.isNotUp>
                      </b></font>
                    </#if>
                    <#list instanceInfo.instances as instance>
                      <#if instance.isHref>
                        <a href="${instance.url}" target="_blank">${instance.id}</a>（<a class="restartInstance" appUrl="${instance.url}" style="cursor: pointer;" onclick="restartInstance(this)">重启</a>）
                      <#else>
                        ${instance.id}
                      </#if><#if instance_has_next>,</#if>
                    </#list>
                  </#list>
                </td>
              </tr>
            </#list>
          <#else>
            <tr><td colspan="4">No instances available</td></tr>
          </#if>

        </tbody>
      </table>

      <h1>General Info</h1>

      <table id='generalInfo' class="table table-striped table-hover">
        <thead>
          <tr><th>Name</th><th>Value</th></tr>
        </thead>
        <tbody>
          <#list statusInfo.generalStats?keys as stat>
            <tr>
              <td>${stat}</td><td>${statusInfo.generalStats[stat]!""}</td>
            </tr>
          </#list>
          <#list statusInfo.applicationStats?keys as stat>
            <tr>
              <td>${stat}</td><td>${statusInfo.applicationStats[stat]!""}</td>
            </tr>
          </#list>
        </tbody>
      </table>

      <h1>Instance Info</h1>

      <table id='instanceInfo' class="table table-striped table-hover">
        <thead>
          <tr><th>Name</th><th>Value</th></tr>
        <thead>
        <tbody>
          <#list instanceInfo?keys as key>
            <tr>
              <td>${key}</td><td>${instanceInfo[key]!""}</td>
            </tr>
          </#list>
        </tbody>
      </table>
    </div>
    <script type="text/javascript" src="eureka/js/wro.js" ></script>
    <script type="text/javascript">
       $(document).ready(function() {
         $('table.stripeable tr:odd').addClass('odd');
         $('table.stripeable tr:even').addClass('even');
       });

       function restartInstance(target) {
           if (window.confirm("是否确认重启实例？")) {
               doRestartInstance($(target).attr("appUrl"));
           }
       }

       function doRestartInstance(appUrl) {
           $.get("/app/restart", {appUrl: appUrl}, function (result) {
               if (result.success == true) {
                   alert(appUrl + "执行重启！");
               } else {
                   alert(appUrl + "无法执行重启！"+result.failMessage);
               }
           });
       }

       function restartAllInstance(target){
           if (window.confirm("是否确认重启所有实例？生产环境请慎重！")) {
               $(target).parents("tr").find("a.restartInstance").each(function () {
                   doRestartInstance($(this).attr("appUrl"));
               })
           }
       }


    </script>
  </body>
</html>
