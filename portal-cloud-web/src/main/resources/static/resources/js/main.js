$(function(){
	//下拉展开收缩
	$('.Toggle').click(function(event){
		event.stopPropagation();
		$('.Toggle .submenu').not($(this).find('.submenu')).slideUp(200);
		$(this).find('.submenu').slideToggle(200);
	});
	
	//点击空白处下拉收缩
	$('body').on("click", function(){
		$('.Toggle .submenu').slideUp(200);
	});
	
	//左侧鼠标移进移出
	$('#navbarSide').hover(function(){
		if ($(this).hasClass('navbar-default-mini')) {
			$('#navbarSide').stop().animate({ width: '150px' }, 400);
			//$('#sidebar li a').stop().animate({ width: '130px' }, 400);
		}
	},function(){
		if ($(this).hasClass('navbar-default-mini')) {
			$('#navbarSide').stop().animate({ width: '50px' }, 400);
			//$('#sidebar li a').stop().animate({ width: '50px' }, 400);
		}
	});
	
	//顶部搜索
	$('.search-box').hover(function(){
		$(this).addClass('active').width(250);
		$("#top_search_value").focus().select();
	}, function(){
		$(this).removeClass('active').width(50);
		$("#top_search_value").blur();
	});
	
	//左侧导航高度控制
	$(window).bind("load resize", function() {
        $('#sidebar').css('height', $(window).height()-100);
    });
});