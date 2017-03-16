require([
	"common",
], function() {
	var common = require("common");
	
	var currentStore = {};
	var rowsPerPage = 5;
	var pagesPerPaging = 5;
	var currentPage = 1;
	
	function getCode(codeType, sectionType) {
		$.ajax({
			url: "/admin/api/" + codeType + "/list",
			success: function(list) {
				var itemsHTML = "";
				
				for (var i=0; i<list.length; i++) {
					var item = list[i];
					
					itemsHTML += "<li><a href='#' item-id='";
					itemsHTML += item[codeType + "_id"] + "'>";
					itemsHTML += item[codeType + "_name"];
					itemsHTML += "</a></li>";
				}
				
				$("#" + sectionType + "-" + codeType).html(itemsHTML);
				
				$("#" + sectionType + "-" + codeType + " a").on("click", function(event) {
					event.preventDefault();
					
					var codeName = $(this).text();
					$("#btn-txt-" + sectionType + "-" + codeType).text(codeName);
					
					var codeId = $(this).attr("item-id");
					currentStore[codeType + "Id"] = codeId;
				});
			},
		});
	}
	
	var handler = function(section, jqElement) {
		if (section === ".admin-list") {
			loadList(currentPage);
		}
		else if (section === ".admin-add") {
			$("#add-store_name").val("");
			$("#add-store_img").val("");
			$(".btn-admin-file").text("파일 선택");
			currentStore = {};
			$("#btn-txt-add-category").text("카테고리 선택");
			$("#btn-txt-add-location").text("지역 선택");
			
			getCode("category", "add");
			getCode("location", "add");
		}
		else if (section === ".admin-update") {
			getCode("category", "upt");
			getCode("location", "upt");
			
			var storeId = jqElement.attr("store-id");
			
			$.ajax({
				url: "/admin/api/store/" + storeId,
				success: function(store) {
					$("#upt-store_id").val(store.store_id);
					$("#upt-store_name").val(store.store_name);
					
					$("#upt-store_img").val("");
					$(".btn-admin-file").html("<img src='" + store.store_img +
							"?ts=" + Date.now() + "'>");
					
					currentStore = {
						categoryId: store.category_id,
						locationId: store.location_id,
					};
					$("#btn-txt-upt-category").text(store.category_name);
					$("#btn-txt-upt-location").text(store.location_name);
				},
			});
		}
	};
	
	function loadList(page) {
		currentPage = page;
		
		$.ajax({
			url: "/admin/api/store/list",
			data: {
				page: page,
				rowsPerPage: rowsPerPage,
			},
			success: function(result) {
				var list = result.list;
				var count = result.count;
				
				var itemHTML = "";
				var storeNo = (currentPage - 1) * rowsPerPage;
				
				for (var i=0; i<list.length; i++) {
					var item = list[i];
					
					itemHTML += "<tr store-id='" + item.store_id + "'>";
					itemHTML += "<td>" + (++storeNo) + "</td>";
					itemHTML += "<td>" + item.category_name + "</td>";
					itemHTML += "<td>" + item.location_name + "</td>";
					itemHTML += "<td>" + item.store_name + "</td>";
					itemHTML += "<td>" + item.store_score + "</td>";
					itemHTML += "</tr>";
				}
				
				$(".admin-list table>tbody").html(itemHTML);
				$(".admin-list table>tbody>tr").on("click", function() {
					common.showSection(".admin-update", $(this), handler);
				});
				
				// for Paging
				var firstPage = 1;
				var lastPage = parseInt(count / rowsPerPage)
					+ (count % rowsPerPage === 0 ? 0 : 1);
				
				var pagingHTML = "";
				
				pagingHTML += "<li page='" + firstPage + "'>";
				pagingHTML += "<a href='#'><i class='fa fa-fw fa-fast-backward'></i></a></li>";
				
				var startPage = parseInt((currentPage-1) / pagesPerPaging)
					* pagesPerPaging + 1;
				var endPage = Math.min(startPage + (pagesPerPaging - 1), lastPage);
				
				if (startPage > 1) {
					pagingHTML += "<li page='" + (startPage - 1) + "'>";
					pagingHTML += "<a href='#'><i class='fa fa-fw fa-step-backward'></i></a></li>";
				}
				
				for (var i=startPage; i<=endPage; i++) {
					pagingHTML += "<li page='" + i + "'";
					
					if (i === currentPage) {
						pagingHTML += " class='active'";
					}
					
					pagingHTML += "><a href='#'>" + i + "</a></li>";
				}
				
				if (endPage < lastPage) {
					pagingHTML += "<li page='" + (endPage + 1) + "'>";
					pagingHTML += "<a href='#'><i class='fa fa-fw fa-step-forward'></i></a></li>";
				}
				
				pagingHTML += "<li page='" + lastPage + "'>";
				pagingHTML += "<a href='#'><i class='fa fa-fw fa-fast-forward'></i></a></li>";
				
				$(".admin-paging").html(pagingHTML);
				$(".admin-paging>li>a").on("click", function(event) {
					event.preventDefault();
					
					var page = parseInt($(this).parent("li").attr("page"));
					
					loadList(page);
				});
			},
		});
	}
	
	$(".btn-admin-update").on("click", function() {
		var storeId = $("#upt-store_id").val();
		var storeName = $("#upt-store_name").val().trim();
		var storeImg = $("#upt-store_img").val();

		if (storeName === "") {
			alert("맛집명을 입력하세요.");
			$("#upt-store_name").focus();
			return;
		}
		
		var formData = new FormData();
		formData.append("storeName", storeName);
		formData.append("categoryId", currentStore.categoryId);
		formData.append("locationId", currentStore.locationId);
		
		if (storeImg !== "") {
			var files = $("#upt-store_img")[0].files;
			
			formData.append("storeImg", files[0]);
		}
		
		$.ajax({
			url: "/admin/api/store/" + storeId,
			method: "POST",
			data: formData,
			processData: false,
			contentType: false,
			success: function() {
				common.showSection(".admin-list", null, handler);
			},
			error: function() {
				alert("수정에 실패했습니다.");
			},
		});
	});
	
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
		});
	});
	
	common.initMgmt(handler);
});
