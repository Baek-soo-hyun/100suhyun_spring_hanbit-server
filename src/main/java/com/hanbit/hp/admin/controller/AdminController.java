package com.hanbit.hp.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

// preFixed 되는 path이고
@RequestMapping("/admin")
public class AdminController {

	// preFixed 된 path 뒤에 다음 path명이 붙으면 아래 함수가 실행됨
	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("cssName", "main");
		model.addAttribute("jsName", "main");
		return "admin/index";
	}
	
	@RequestMapping("/{menuId}")
	public String main(Model model, @PathVariable("menuId") String menuId) {
		model.addAttribute("menuId", menuId);
		model.addAttribute("cssName", menuId);
		model.addAttribute("jsName", menuId);
		return "admin/" + menuId;
	}
}
