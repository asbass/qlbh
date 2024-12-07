package com.buoi2.ltw.dao;

import com.buoi2.ltw.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductDAO extends JpaRepository<Product, Integer>{

//	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
//	List<Product> findByPrice(double minPrice, double maxPrice);
	Page<Product> findByPriceBetween(double minPrice, double maxPrice,Pageable pageable);
//
//@Query("SELECT o FROM Product o WHERE o.name LIKE ?1")
//Page<Product> findByKeywords(String keywords, Pageable pageable);

Page<Product> findAllByNameLike(String keywords, Pageable pageable);
/*
	@Query("SELECT new Report(o.category, sum(o.price), count(o)) "
			+ " FROM Product o "
			+ " GROUP BY o.category"
			+ " ORDER BY sum(o.price) DESC")
	List<Report> getInventoryByCategory();
	*/

}
