package com.buoi2.ltw.entity;

import java.io.Serializable;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
@SuppressWarnings("serial")
@NamedQuery(
		name = "findByKeyword",
		query = "SELECT c FROM Category c WHERE c.name LIKE ?1"
)
@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable{
	@Id
	String id;
	String name;
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Product> products;
}
