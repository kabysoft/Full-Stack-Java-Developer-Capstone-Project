package com.ehealth.cms.dto;


import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.MedicineEntity;
import com.ehealth.cms.util.DataUtility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicineDto extends BaseDto {
	
	@NotEmpty(message = "Name is required")
	private String name;
	
	@NotEmpty(message = "Company Name is required")
	private String companyName;
	
	@NotEmpty(message = "Price is required")
	private String price;
	
	@NotEmpty(message = "Quantity is required")
	private String quantity;
	
	@NotEmpty(message = "Expire Date is required")
	private String expireDate;

	@Override
	public BaseEntity getEntity() {
		MedicineEntity entity=new MedicineEntity();
		entity.setId(id);
		entity.setName(name);
		entity.setCompanyName(companyName);
		entity.setPrice(BigDecimal.valueOf(DataUtility.getLong(price)));
		entity.setQuantity(DataUtility.getInt(quantity));
		entity.setExpireDate(DataUtility.getDate(expireDate));
		entity.setCreatedBy(createdBy);
		entity.setModifiedBy(modifiedBy);
		entity.setCreatedDatetime(createdDatetime);
		entity.setModifiedDatetime(modifiedDatetime);
		return entity;
	}

	@Override
	public void populate(BaseEntity bEnt) {
		MedicineEntity entity=(MedicineEntity)bEnt;
		id=entity.getId();
		name=entity.getName();
		companyName=entity.getCompanyName();
		price=String.valueOf(entity.getPrice());
		quantity=String.valueOf(entity.getQuantity());
		expireDate=DataUtility.getDateString(entity.getExpireDate());
		createdBy=entity.getCreatedBy();
		modifiedBy=entity.getModifiedBy();
		createdDatetime=entity.getCreatedDatetime();
		modifiedDatetime=entity.getModifiedDatetime();
	}
	
	
	

}
