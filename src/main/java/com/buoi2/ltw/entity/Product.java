package com.buoi2.ltw.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@SuppressWarnings("serial")
//@NamedQuery(
//		name = "findByKeyword",
//		query = "SELECT p FROM Product p WHERE p.name LIKE ?1"
//)
@Data
@Entity
@Table(name = "Products")
public class Product  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String image;
	Double price;
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	Boolean available;
	@ManyToOne
	@JoinColumn(name = "categoryid")
	Category category;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;	
}
