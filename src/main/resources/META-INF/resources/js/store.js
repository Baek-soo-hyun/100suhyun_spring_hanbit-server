require([
    "common",
], function() {
	var common = require("common");
	
	var handler = function(section, jqElement) {
		if (section === ".admin-add") {
			$(".btn-admin-file").text("파일선택");
			
			$.ajax({
				url: "/admin/api/category/list",
				success: function(list) {
					var itemHTML = "";
					
					for (var i=0; i<list.length; i++) {
						var item = list[i];
						
						itemHTML += "<li><a href='#'>";
						itemHTML += item.category_name;
						itemHTML += "</a></li>"
					}
					
					$("#add-category").html(itemHTML);
					
					$("#add-category a").on("click", function(event) {
						// preventDefault() => 태그가 기본으로 가지고 있는 이벤트 속성을 무시
						// stop~~ => 하위 태그로의 이벤트의 전파를 방지
						// <a> 태그의 페이지 이동 이벤트를 막아주기 위해 사용
						event.preventDefault();
						
						var categoryName = $(this).text();
						$("#btn-txt-category").text(categoryName);
					});
				},
			});
			
			$.ajax({
				url: "/admin/api/location/list",
				success: function(list) {
					var itemHTML = "";
					
					for (var i=0; i<list.length; i++) {
						var item = list[i];
						
						itemHTML += "<li><a href='#'>";
						itemHTML += item.location_name;
						itemHTML += "</a></li>"
					}
					
					$("#add-location").html(itemHTML);
					
					$("#add-location a").on("click", function(event) {
					
						event.preventDefault();
						
						var locationName = $(this).text();
						$("#btn-txt-location").text(locationName);
					});
				},
			});	
		}
	};

	common.initMgmt(handler);
	
});