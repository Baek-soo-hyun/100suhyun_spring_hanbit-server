require([
	"common",
], function() {
	var common = require("common");
	
	var currentStore = {};
	
	var handler = function(section, jqElement) {
		if (section === ".admin-list") {
			loadList();
		}
		else if (section === ".admin-add") {
			$("#add-store_name").val("");
			$("#add-store_img").val("");
			$(".btn-admin-file").text("파일 선택");
			currentStore = {};
			$("#btn-txt-category").text("카테고리 선택");
			$("#btn-txt-location").text("지역 선택");
			
			$.ajax({
				url: "/admin/api/category/list",
				success: function(list) {
					var itemsHTML = "";
					
					for (var i=0; i<list.length; i++) {
						var item = list[i];
						
						itemsHTML += "<li><a href='#' item-id='";
						itemsHTML += item.category_id + "'>";
						itemsHTML += item.category_name;
						itemsHTML += "</a></li>";
					}
					
					$("#add-category").html(itemsHTML);
					
					$("#add-category a").on("click", function(event) {
						event.preventDefault();
						
						var categoryName = $(this).text();
						$("#btn-txt-category").text(categoryName);
						
						var categoryId = $(this).attr("item-id");
						currentStore.categoryId = categoryId;
					});
				},
			});
			
			$.ajax({
				url: "/admin/api/location/list",
				success: function(list) {
					var itemsHTML = "";
					
					for (var i=0; i<list.length; i++) {
						var item = list[i];
						
						itemsHTML += "<li><a href='#' item-id='";
						itemsHTML += item.location_id + "'>";
						itemsHTML += item.location_name;
						itemsHTML += "</a></li>";
					}
					
					$("#add-location").html(itemsHTML);
					
					$("#add-location a").on("click", function(event) {
						event.preventDefault();
						
						var locationName = $(this).text();
						$("#btn-txt-location").text(locationName);
						
						var locationId = $(this).attr("item-id");
						currentStore.locationId = locationId;
					});
				},
			});
		}
	};
	
	function loadList() {
		$.ajax({
			url: "/admin/api/store/list",
			success: function(list) {
				var itemHTML = "";
				
				for (var i=0; i<list.length; i++) {
					var item = list[i];
					
					itemHTML += "<tr category-id='" + item.store_id + "'>";
					itemHTML += "<td>" + (i+1) + "</td>";
					itemHTML += "<td>" + item.category_name + "</td>";
					itemHTML += "<td>" + item.location_name + "</td>";
					itemHTML += "<td>" + item.store_name + "</td>";
					itemHTML += "<td>" + item.store_score + "</td>";
					itemHTML += "</tr>";
				}
				
				$(".admin-list table>tbody").html(itemHTML);
				$(".admin-list table>tbody>tr").on("click", function() {
					// common.showSection(".admin-update", $(this), handler);
				});
			},
		});
	}
	
	$(".btn-admin-save").on("click", function() {
		var storeName = $("#add-store_name").val().trim();
		var storeImg = $("#add-store_img").val();
		
		if (storeName === "") {
			alert("맛집명을 입력하세요.");
			$("#add-store_name").focus();
			return;
		}
		else if (storeImg === "") {
			alert("대표이미지를 선택하세요.");
			return;
		}
		else if (!currentStore.categoryId) {
			alert("카테고리를 선택하세요.");
			return;
		}
		else if (!currentStore.locationId) {
			alert("지역을 선택하세요.");
			return;
		}
		
		var formData = new FormData();
		formData.append("storeName", storeName);
		formData.append("categoryId", currentStore.categoryId);
		formData.append("locationId", currentStore.locationId);
		
		var files = $("#add-store_img")[0].files;
		
		formData.append("storeImg", files[0]);
		
		$.ajax({
			url: "/admin/api/store/add",
			method: "POST",
			data: formData,
			processData: false,
			contentType: false,
			success: function() {
				common.showSection(".admin-list", null, handler);
			},
			error: function() {
				alert("저장에 실패했습니다.");
			},
		})
	});
	
	common.initMgmt(handler);
});
