function toggleMouseLeave() {
	if ($(this).find('.submenu').is(':visible')) {
		$(this).find('.submenu').stop(true, true).slideUp(200);
	}
}
$(function(){
	//下拉展开收缩
	$('.Toggle .toggle-title').click(function(){
		$('.Toggle').off('mouseleave');
		if ($(this).next('.submenu').is(':hidden')) {
			$(this).next('.submenu').stop(true, true).slideDown(200);
		}
		setTimeout(function(){
			$('.Toggle').on('mouseleave', toggleMouseLeave);
		});
	});
	
    //点击下拉菜单收缩
	$('.Toggle .submenu a').on('click', function(){
		if ($(this).parents('.submenu').is(':visible')) {
			$(this).parents('.submenu').stop(true, true).slideUp(200);
		}
	});
    
    //选择系统
    $('.sys-toggle .submenu a').on('click', function(){
    	if($(this).find('.pic').hasClass('cate-icon-zh')||$(this).find('.pic').hasClass('cate-icon-yh')||$(this).find('.pic').hasClass('cate-icon-bx')){
    		return;
    	}else{
    		$('.sys-toggle .toggle-title .text').text($(this).find('.title').text());
    	}
        
    });
	$('.sys-toggle .toggle-title .text').text($('.sys-toggle .submenu .nav-item-selected').eq(0).find('.title').text());

	//固定左侧
	$('#navbarSide').on('click', '#fixedSide', function() {
		if ($('.navbar-static-side').is(":animated")) {
			return;
		}
		if($('.navbar-default').hasClass('navbar-default-mini')){
			$('.fixedSide').attr('title','收起');
			$('.navbar-static-side').stop(true, true).animate({ width: '180px' }, 400, 'swing').removeClass('navbar-default-mini');
			$('.sidebar-action .title').stop(true, true).animate({ width: '130px' }, 400, 'swing');
			$('.page-wrapper').stop(true, true).animate({ left: '180px' }, 400, 'swing');
		}else{
			$('.fixedSide').attr('title','展开');
			$('.navbar-static-side').stop(true, true).animate({ width: '50px' }, 400, 'swing').addClass('navbar-default-mini');
			$('.sidebar-action .title').stop(true, true).animate({ width: 0 }, 400, 'swing');
			$('.page-wrapper').stop(true, true).animate({ left: '50px' }, 400, 'swing');
			$('.bui-side-menu li ul').slideUp(400);
			setTimeout(function() {
				$('.bui-menu-item').addClass('bui-menu-item-collapsed');
			}, 400);
		}	
	});

	//顶部搜索
	$("#top_search_value").focus(function() {
		$('.search-box').addClass('active').width(200);
		var value = $("#top_search_value").attr('data-value');
		$("#top_search_value").val(value).select();
	});

	$("#top_search_value").blur(function() {
		$('.search-box').removeClass('active').width(80);
		var value = $("#top_search_value").val();
		$("#top_search_value").val('').attr('data-value',value);
	});
	
	
	//左侧导航bui-menu-item-collapsed
	$('#side_menu').on('click', '.bui-side-menu li > a', function(event) {
		if($('.navbar-default').hasClass('navbar-default-mini')){
			$('.fixedSide').attr('title','收起');
			$('.navbar-static-side').stop(true, true).animate({ width: '180px' }, 400, 'swing').removeClass('navbar-default-mini');
			$('.sidebar-action .title').stop(true, true).animate({ width: '130px' }, 400, 'swing');
			$('.page-wrapper').stop(true, true).animate({ left: '180px' }, 400, 'swing');
		}
		if ($(this).next('ul').length > 0 && !$(this).next('ul').is(':empty')) {
			event.preventDefault();
			var _this = $(this);
			if ($(this).next('ul').is(":animated")) {
				return;
			}
			if ($(this).parent().hasClass('bui-menu-item-collapsed')) {
				$(this).addClass('expand');
				//$(this).next('ul').stop().slideDown(400);
				$(this).next('ul').stop(true, true).animate({height: 'toggle', opacity: 'toggle'}, 400);
				setTimeout(function() {
					_this.parent().removeClass('bui-menu-item-collapsed');
					if (typeof setScroll == 'function') {
						setScroll();
					}
				}, 400);
			} else {
				$(this).removeClass('expand');
				$(this).next('ul').stop(true, true).slideUp(400);
				setTimeout(function() {
					_this.parent().addClass('bui-menu-item-collapsed');
					if (typeof setScroll == 'function') {
						setScroll();
					}
				}, 400);
			}
		} else if ($(this).parents('ul').length > 0) {
			$('.bui-side-menu li > a').removeClass('active');
			$(this).parents('ul').prev().addClass('active');
		}
	});
});