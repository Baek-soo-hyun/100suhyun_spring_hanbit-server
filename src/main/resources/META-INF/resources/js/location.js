require([
    "common",
], function() {
	var common = require("common");
	
	var handler = function(section, jqElement) {
		if (section === ".admin-list") {
			loadList();
		}
		else if (section === ".admin-add") {
			$("#add-location_name").val("");
			$("#add-location_name").focus();
		}
		else if (section === ".admin-update") {
			var locationId = jqElement.attr("location-id");
			$.ajax({
				url: "/admin/api/location/" + locationId,
				success: function(item) {
					$("#upt-location_id").val(item.location_id);
					$("#upt-location_name").val(item.location_name);
				},
			});
		}
	};
	
	function loadList() {
		$.ajax({
			url: "/admin/api/location/list",
			success: function(list) {
				var itemHTML = "";
				
				for (var i=0; i<list.length; i++) {
					var item = list[i];
					
					itemHTML += "<tr location-id='" + item.location_id + "'>";
					itemHTML += "<td>" + (i+1) + "</td>";
					itemHTML += "<td>" + item.location_name + "</td>";
					itemHTML += "<td>" + item.location_id + "</td>";
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
		var locationName = $("#add-location_name").val().trim();
		
		if (locationName === "") {
			alert("카테고리명을 입력하세요.");
			$("#add-location_name").focus();
			return;
		}
		
		$.ajax({
			url: "/admin/api/location/add",
			data: {
				locationName: locationName,
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
		var locationName = $("#upt-location_name").val().trim();
		
		if (locationName === "") {
			alert("카테고리명을 입력하세요.");
			$("#upt-location_name").focus();
			return;
		}
		
		var locationId = $("#upt-location_id").val();
		
		$.ajax({
			url: "/admin/api/location/" + locationId,
			method: "PUT",
			data: {
				locationName: locationName,
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
		var locationId = $("#upt-location_id").val();
		
		$.ajax({
			url: "/admin/api/location/" + locationId,
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