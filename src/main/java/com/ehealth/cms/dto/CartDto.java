package com.ehealth.cms.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.CartEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDto extends BaseDto {

	private long medicineId;
	private long userId;
	private BigDecimal price;
	private int quantity;
	private BigDecimal finalPrice;
	
	
	@Override
	public BaseEntity getEntity() {
		CartEntity entity=new CartEntity();
		entity.setId(id);
		entity.setMedicineId(medicineId);
		entity.setUserId(userId);
		entity.setPrice(price);
		entity.setQuantity(quantity);
		entity.setFinalPrice(finalPrice);
		entity.setCreatedBy(createdBy);
		entity.setModifiedBy(modifiedBy);
		entity.setCreatedDatetime(createdDatetime);
		entity.setModifiedDatetime(modifiedDatetime);
		return entity;
	}
	@Override
	public void populate(BaseEntity bEnt) {
		CartEntity entity=(CartEntity)bEnt;
		id=entity.getId();
		medicineId=entity.getMedicineId();
		userId=entity.getUserId();
		price=entity.getPrice();
		quantity=entity.getQuantity();
		finalPrice=entity.getFinalPrice();
		createdBy=entity.getCreatedBy();
		modifiedBy=entity.getModifiedBy();
		createdDatetime=entity.getCreatedDatetime();
		modifiedDatetime=entity.getModifiedDatetime();
		
	}
	
	
}
