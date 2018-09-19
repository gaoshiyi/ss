//地区对象
function areaSelect(countryName, stateName, cityName, areaUrl, countryLable, stateLable, cityLable){
	//初始化下拉框的名称
	this.countryName = countryName;
	this.stateName = stateName;
	this.cityName = cityName;
    var areaUrl = areaUrl ? areaUrl : 'http://lp.taobao.com/go/rgn/citydistrictdata.php';
	var areaData = '';
	//选择的值
	this.countryVal = '';
	this.stateVal = '' ;
	this.cityVal = '';
	
	//私有成员
	var a = this;
	var sl_country = "select[id='" + countryName + "']";
	var sl_state = "select[id='" + stateName + "']";
	var sl_city = "select[id='" + cityName + "']";

	//初始化地区，绑定相关事件
	this.init = function(countryId, stateId, cityId){
		if(countryId == undefined || stateId == undefined || cityId == undefined){
			a.countryVal = '' ;
			a.stateVal = '' ;
			a.cityVal = '' ;
		} else {
			a.countryVal = countryId ;
			a.stateVal = stateId ;
			a.cityVal = cityId ;
		}
		
		//初始化默认下拉
		defaultRender();
		//初始绑定国家
		bindArea(1);
		bindArea(2);
		bindArea(3);
		registerEvent();
	}
	
	//注册事件
	var registerEvent=function(){
		//将绑定事件,请求查询绑定数据,进行联动
		$(sl_country).change(function(){
			clearRender(3);
			clearRender(2);
			a.countryVal = this.value;
			if (a.countryVal != '') {
				bindArea(2);
			}
		});
		//省/州
		$(sl_state).change(function(){
			clearRender(3);
			a.stateVal = this.value;
			if (a.stateVal != '') {
				bindArea(3);
			}
		});
		//市
		$(sl_city).change(function(){
			a.cityVal = this.value;
		});
	}
	
	//清除下拉呈现
	var clearRender = function(level){
		getCurrentSL(level).empty();
		switch(level){
			case 1:
				countryLable ? getCurrentSL(level).prepend("<option value=''>" + countryLable + "</option>") : getCurrentSL(level).prepend("<option value=''>选择国家</option>");
				getCurrentSL(level).trigger('chosen:updated');
				a.stateVal = '';
				a.cityVal = '';
				break;
			case 2:
				stateLable ? getCurrentSL(level).prepend("<option value=''>" + stateLable + "</option>") : getCurrentSL(level).prepend("<option value=''>选择省/州</option>");
				getCurrentSL(level).trigger('chosen:updated');
				a.stateVal = '';
				a.cityVal = '';
				break;
			case 3:
				cityLable ? getCurrentSL(level).prepend("<option value=''>" + cityLable + "</option>") : getCurrentSL(level).prepend("<option value=''>选择市</option>");
				getCurrentSL(level).trigger('chosen:updated');
				a.cityVal = '';
				break;
			default:
				break;
		}
	}
	
	//得到当前下拉选项控件
	var getCurrentSL = function(level){
		var current_sl = a;
		var sl_name = "";
		switch(level){
			case 1:
				sl_name = sl_country;
				break;
			case 2:
				sl_name = sl_state;
				break;
			case 3:
				sl_name = sl_city;
				break;
			default:
				break;
		}
		return $(sl_name);
	}
	
	//默认呈现
	var defaultRender=function(){
		$(sl_country).empty();
		$(sl_state).empty();
		$(sl_city).empty();
		countryLable ? $(sl_country).prepend("<option value=''>" + countryLable + "</option>") : $(sl_country).prepend("<option value=''>选择国家</option>");
		stateLable ? $(sl_state).prepend("<option value=''>" + stateLable + "</option>") : $(sl_state).prepend("<option value=''>选择省/州</option>");
		cityLable ? $(sl_city).prepend("<option value=''>" + cityLable + "</option>") : $(sl_city).prepend("<option value=''>选择市</option>");
	}
	
	//请求查询地区数据,并绑定下拉控件
	var bindArea = function(level){
		//处理默认绑定
		var selected_id = '';
		var area_id, type,parent_id;
		switch(level){
			case 1:
				area_id = '';
				level = 1;
				selected_id = a.countryVal;
				type = 0;
				break;
			case 2:
				area_id = a.countryVal;
				selected_id = a.stateVal;
				type = 1;
				break;
			case 3:
				area_id = a.stateVal;
				selected_id = a.cityVal;
				parent_id = a.countryVal;
				type = 2;
				break;
			default:
				area_id = '';
				level = 1;
				selected_id = a.provinceVal;
				break;
		}

		//校验是否需要请求下级菜单,不是一级菜单,且查询为请选择的，将不填充下拉选项
		if(level != 1 && area_id == ''){
			return;
		}
		//填充
		$.ajax({
			type: "GET",
			url: areaUrl,
			dataType: "json",
			data: { areaId: area_id, type: type , parentId: parent_id},
			cache: false,
			success: function(dataResult){
				if(dataResult.status === 200){
					var areaLength = dataResult.result.length;
					if(areaLength > 0){
						a.areaData = dataResult.result;
						for(var i = 0; i < areaLength; i++) {
							var selected = "";
							var idCode = dataResult.result[i].id.split('+')[1];
							if(selected_id == idCode){
								selected = "selected";
							}
							var optionRender = "<option value='" + dataResult.result[i].id + "' " + selected + ">" + dataResult.result[i].name + "</option>";
							//判断填充哪个级别
							getCurrentSL(level).append(optionRender);
						}
						getCurrentSL(level).trigger('chosen:updated');
					}
				}
			},
			error: function(err){   
				alert("网络传输异常，无法获取地区信息");
			} 
		});
	}
}