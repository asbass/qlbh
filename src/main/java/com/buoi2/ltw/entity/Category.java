package com.buoi2.ltw.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


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
	@PrePersist
	public void generateUUID() {
		if (this.id == null || this.id.isEmpty()) {
			this.id = UUID.randomUUID().toString();
		}
	}
}
