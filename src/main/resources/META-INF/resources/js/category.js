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
			var categoryId = jqElement.attr("category-id");
			$.ajax({
				url: "/admin/api/category/" + categoryId,
				success: function(item) {
					$("#upt-category_id").val(item.category_id);
					$("#upt-category_name").val(item.category_name);
				},
			});
		}
	};
	
	function loadList() {
		$.ajax({
			url: "/admin/api/category/list",
			success: function(list) {
				var itemHTML = "";
				
				for (var i=0; i<list.length; i++) {
					var item = list[i];
					
					itemHTML += "<tr category-id='" + item.category_id + "'>";
					itemHTML += "<td>" + (i+1) + "</td>";
					itemHTML += "<td>" + item.category_name + "</td>";
					itemHTML += "<td>" + item.category_id + "</td>";
					itemHTML += "<td>" + item.stores + "</td>";
					itemHTML += "</tr>";
				}
				
				$(".admin-list table>tbody").html(itemHTML);
				$(".admin-list table>tbody>tr").on("click", function() {
					common.showSection(".admin-update", $(this), handler);
				});	
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
	
	$(".btn-admin-update").on("click", function() {
		var categoryName = $("#upt-category_name").val().trim();
		
		if (categoryName === "") {
			alert("카테고리명을 입력하세요.");
			$("#upt-category_name").focus();
			return;
		}
		
		var categoryId = $("#upt-category_id").val();
		
		$.ajax({
			url: "/admin/api/category/" + categoryId,
			method: "PUT",
			data: {
				categoryName: categoryName,
			},
			success: function() {
				common.showSection(".admin-list", null, handler);
				
			},
			error: function() {
				alert("수정에 실패했습니다.");
			},
		});
	});
	
	$(".btn-admin-delete").on("click", function() {
		var categoryId = $("#upt-category_id").val();
		
		$.ajax({
			url: "/admin/api/category/" + categoryId,
			method: "DELETE",
			success: function() {
				common.showSection(".admin-list", null, handler);
				
			},
			error: function() {
				alert("삭제에 실패했습니다.");
			},
		});
	});
	
	common.initMgmt(handler);
});