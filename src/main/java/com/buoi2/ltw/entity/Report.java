package com.buoi2.ltw.entity;

import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
	@Id
	Serializable group;
	Double sum;
	Long count;
}
