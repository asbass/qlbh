package com.buoi2.ltw.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(nullable = false) // name không được null
	String name;

	String image;

	@Column(nullable = false) // price không được null
	Double price;

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate", nullable = false) // Createdate không được null
	Date createDate = new Date();

	@Column(nullable = false, columnDefinition = "tinyint(1) default 1") // giá trị mặc định là true
	Boolean available = true;

	// Thêm trường description và quality
	@Column(length = 500) // description có thể dài hơn, bạn có thể điều chỉnh length nếu cần
			String description;
	// quality có độ dài tối đa là 50 ký tự
	Integer quality;

	@ManyToOne
	@JoinColumn(name = "categoryid")
	Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;
}
