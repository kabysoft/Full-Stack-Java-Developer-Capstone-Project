package com.ehealth.cms.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="EVS_CART")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartEntity extends BaseEntity {

	@Column(name="MEDICINE_ID")
	private long medicineId;
	
	@ManyToOne
	@JoinColumn(name = "medicine",nullable = false)
	private MedicineEntity medicine;
	
	@Column(name="USER_ID")
	private long userId;
	
	@ManyToOne
	@JoinColumn(name = "user",nullable = false)
	private UserEntity user;
	
	@Column(name="PRICE")
	private BigDecimal price;
	@Column(name="QUANTITY")
	private int quantity;
	@Column(name="FINAL_PRICE")
	private BigDecimal finalPrice;
	
	
}
