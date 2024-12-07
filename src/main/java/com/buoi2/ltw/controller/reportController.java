package com.buoi2.ltw.controller;

import java.util.List;
import java.util.Optional;

import com.buoi2.ltw.dao.OrderDAO;
import com.buoi2.ltw.entity.Report;
import com.buoi2.ltw.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//
//
//@Controller
//@RequestMapping("admin/report")
//public class reportController {
//
//	@Autowired
//	private  OrderDetailDAO orderDetailDAO;
//	@Autowired
//	SessionService session;
//	@RequestMapping("index")
//	public String search(Model model,@RequestParam("p") Optional<Integer> p) {
//		Pageable pageable = PageRequest.of(p.orElse(0), 5);
//		List<Report> page = orderDetailDAO.getInventoryByoder();
//		model.addAttribute("page", page);
//		return "admin/report/list";
//	}
////	@RequestMapping("edit/{id}")
////	public String edit(Model model ) {
////		OrderDetail item = daos.findByOrder();
////		model.addAttribute("item", item);
////		return "admin/report/list";
////	}
//}
