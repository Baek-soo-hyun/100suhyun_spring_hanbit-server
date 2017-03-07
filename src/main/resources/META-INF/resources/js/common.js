define([
    "bootstrap",
], function() {
	$(".admin-logo").on("click", function() {
		location.href = "/admin";
	});
	
	$(".admin-menu>ul>li").on("click", function() {
		var menu = $(this).attr("menu");
		location.href = "/admin/" + menu;
	});
	
	if (_ctx.menuId && _ctx.menuId !== "") {
		$(".admin-menu>ul>li[menu='" + _ctx.menuId + "']")
		.addClass("active");	
	}
	
	function showSection(section, jqElement, handler) {
		$("section").hide();
		$(section).show();
		
		handler(section, jqElement);
	}
	
	function initMgmt(handler) {
		$(".btn-admin-add").on("click", function() {
			showSection(".admin-add", $(this), handler);
		});
		
		$(".btn-admin-cancel").on("click", function() {
			showSection(".admin-list", null, handler);
		});
		
		$(".admin-list table>tbody>tr").on("click", function() {
			showSection(".admin-update", $(this), handler);
		});	
		
		showSection(".admin-list", null, handler);
	}
	
	// 밖에서도 부를 수 있게
	return {
		initMgmt: initMgmt,
		showSection: showSection,
	}
});