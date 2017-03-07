require([
    "common",
], function() {
	var common = require("common");
	
	var handler = function(section, jqElement) {
		if (section === ".admin-list") {
			loadList();
		}
		else if (section === ".admin-add") {
			$("#add-category_name").val("");
			$("#add-category_name").focus();
		}
		else if (section === ".admin-update") {
			
		}
	};
	
	function loadList() {
		$.ajax({
			url: "/admin/api/category/list",
			success: function(list) {
				var itemHTML = "";
				
				for (var i=0; i<list.length; i++) {
					var item = list[i];
					
					itemHTML += "<tr>";
					itemHTML += "<td>" + (i+1) + "</td>";
					itemHTML += "<td>" + item.category_name + "</td>";
					itemHTML += "<td>" + item.category_id + "</td>";
					itemHTML += "<td>" + item.stores + "</td>";
					itemHTML += "</tr>";
				}
				
				$(".admin-list table>tbody").html(itemHTML);
			},
		})
	}
	
	$(".btn-admin-save").on("click", function() {
		var categoryName = $("#add-category_name").val().trim();
		
		if (categoryName === "") {
			alert("카테고리명을 입력하세요.");
			$("#add-category_name").focus();
			return;
		}
		
		$.ajax({
			url: "/admin/api/category/add",
			data: {
				categoryName: categoryName,
			},
			success: function() {
				common.showSection(".admin-list", null, handler);
				
			},
			error: function() {
				alert("저장에 실패했습니다.");
			},
		});
	});
	
	common.initMgmt(handler);
});