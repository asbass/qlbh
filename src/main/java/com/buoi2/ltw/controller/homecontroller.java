package com.buoi2.ltw.controller;

import java.util.List;
import java.util.Optional;


import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.entity.Category;
import com.buoi2.ltw.entity.Product;
import com.buoi2.ltw.service.SessionService;
import com.buoi2.ltw.utils.CartItems;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


// chua lam

@Controller
public class homecontroller {
	@Autowired
	CategoryDAO dao;
	@Autowired
	ProductDAO t;
	@Autowired
	ProductDAO daos;
	@Autowired
	SessionService session;
	@Autowired
	HttpSession ss;

	@RequestMapping("/index")
	public String index2(Model model, @RequestParam("p") Optional<Integer> p) {
		Category item = new Category();
		model.addAttribute("item", item);
		List<Category> items = dao.findAll();
		model.addAttribute("items", items);
		if (ss.getAttribute("cart") == null) {
			ss.setAttribute("cart", new CartItems());
		}
		model.addAttribute("cartNumber", ((CartItems) ss.getAttribute("cart")).getTotal());
		model.addAttribute("user", ss.getAttribute("user"));

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = t.findAll(pageable);
		model.addAttribute("page", page);
		return "site/index";
	}

	@RequestMapping("/detail")
	public String detailshop(Model model, @RequestParam("p") Optional<Integer> p) {
		Category item = new Category();
		model.addAttribute("item", item);
		List<Category> items = dao.findAll();
		model.addAttribute("items", items);

		return "site/detail";
	}

	@RequestMapping("edit/{id}")
	public String edit(Model model, @PathVariable("id") int id, @RequestParam("p") Optional<Integer> p) {
		Product item = daos.findById(id).get();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = t.findAll(pageable);
		model.addAttribute("page", page);
		model.addAttribute("cartNumber", ((CartItems) ss.getAttribute("cart")).getTotal());
		model.addAttribute("user", ss.getAttribute("user"));

		List<Category> items = dao.findAll();
		model.addAttribute("items", items);
		return "site/detail";
	}

	@RequestMapping("/checkout")
	public String checkout(Model model) {
		Category item = new Category();
		model.addAttribute("item", item);
		List<Category> items = dao.findAll();
		model.addAttribute("items", items);
		return "site/checkout";
	}

	@RequestMapping("/contact")
	public String contact(Model model) {
		Category item = new Category();
		model.addAttribute("item", item);
		List<Category> items = dao.findAll();
		model.addAttribute("items", items);
		return "site/contact";
	}

	@RequestMapping("/shop")
	public String shop(Model model, @RequestParam("p") Optional<Integer> p) {
		Category item = new Category();
		model.addAttribute("item", item);
		List<Category> items = dao.findAll();
		model.addAttribute("items", items);
		model.addAttribute("user", ss.getAttribute("user"));
		model.addAttribute("cartNumber", ((CartItems) ss.getAttribute("cart")).getTotal());

		Pageable pageable = PageRequest.of(p.orElse(0), 9);
		Page<Product> page = t.findAll(pageable);
		model.addAttribute("page", page);
		return "site/shop";
	}

	@RequestMapping("/shop/price")
	public String sho2p(Model model, @RequestParam("p") Optional<Integer> p, @RequestParam("min") double min,
			@RequestParam("max") double max) {
		Category item = new Category();
		model.addAttribute("item", item);
		List<Category> items = dao.findAll();
		model.addAttribute("items", items);
		Pageable pageable = PageRequest.of(p.orElse(0), 9);
		Page<Product> page = t.findByPriceBetween(min, max, pageable);
		model.addAttribute("page", page);
		return "site/shop";
	}

	@RequestMapping("search")
	public String search(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		String keywords = kw.orElse(session.get("keywords", ""));
		session.set("keywords", keywords);
		Pageable pageable = PageRequest.of(p.orElse(0), 9);
		// Page<Product> page = dao.findByKeywords("%"+keywords+"%", pageable);
		Page<Product> page = daos.findAllByNameLike("%" + keywords + "%", pageable);
		model.addAttribute("page", page);
		return "site/shop";
	}

	@RequestMapping("/regester")
	public String regester(Model model) {
		return "site/shop";
	}
}
