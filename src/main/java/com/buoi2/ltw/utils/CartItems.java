package com.buoi2.ltw.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.buoi2.ltw.entity.Product;
import org.springframework.stereotype.Component;


import lombok.Getter;

@Getter
@Component
public class CartItems {

	int total = 0;
	List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
	
	public void addItems(Product product, int quantity){
		boolean check = true;
		for(int i = 0; i < items.size(); i++) {
//			if(String.valueOf(product.getId()).equals(String.valueOf(((Product)items.get(i).get("product")).getId()))) {
//				items.get(i).put("quantity", quantity + Integer.valueOf(items.get(i).get("quantity").toString()));
//				check = false;
//			}
			if(product.getId() == ((Product)items.get(i).get("product")).getId()) {
				items.get(i).put("quantity", quantity + Integer.valueOf(items.get(i).get("quantity").toString()));
				check = false;
			}
		}
		if(check) {
			HashMap m = new HashMap();
			m.put("product", product);
			m.put("quantity", quantity);
			m.put("price", product.getPrice() * quantity);
			items.add(m);
		}
	
	}
	
	public void modifyQuantity(Product p, int quantity) {
		for(int i = 0; i < items.size(); i++) {
			if(String.valueOf(p.getId()).equals(String.valueOf(((Product)items.get(i).get("product")).getId()))) {
				items.get(i).put("quantity", quantity);
			}
		}
	}
	
	public int getTotal(){
		this.total = 0;
		for(HashMap h: items) {
			this.total += Integer.valueOf(h.get("quantity").toString());
		}
		return this.total;
	}
	
	public int getTotal2(){
		this.total = 0;
		for(HashMap h: items) {
			this.total += Integer.valueOf(h.get("quantity").toString()) * Double.valueOf(((Product)h.get("product")).getPrice().toString());
		}
		return this.total;
	}
	
	public void removeItem(String id) {
		for(int i = 0; i < items.size(); i++) {
			if(id.equals(String.valueOf(((Product)items.get(i).get("product")).getId()))) {
				items.remove(i);
			}
		}
	}
	
}
