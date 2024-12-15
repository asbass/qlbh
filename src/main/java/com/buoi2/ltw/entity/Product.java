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

	public Product() {
	}

	public Product(Integer id, String name, Double price, String image, Date createDate, Boolean available, String description, Integer quality, Category category, List<OrderDetail> orderDetails) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.createDate = createDate;
		this.available = available;
		this.description = description;
		this.quality = quality;
		this.category = category;
		this.orderDetails = orderDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
