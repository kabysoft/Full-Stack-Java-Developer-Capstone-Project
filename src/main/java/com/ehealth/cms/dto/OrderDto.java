package com.ehealth.cms.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.OrderEntity;
import com.ehealth.cms.util.DataUtility;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OrderDto extends BaseDto {
	
	private BigDecimal finaltotal;

	private String name;
	
	private String email;
	
	private String phoneNo;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	private long userId;
	
	private int quantity;
	
	private String orderid;
	
	private String year;
	private String month;
	
	private String startDate;
	private String endDate;


	@Override
	public BaseEntity getEntity() {
		OrderEntity dto=new OrderEntity();
		dto.setId(id);
		dto.setOrderid(DataUtility.getLong(orderid));
		dto.setName(name);
		dto.setEmail(email);
		dto.setPhoneNo(phoneNo);
		dto.setAddress1(address1);
		dto.setAddress2(address2);
		dto.setCity(city);
		dto.setStartDate(DataUtility.getDate(startDate));
		dto.setEndDate(DataUtility.getDate(endDate));
		dto.setYear(year);
		dto.setMonth(month);
		dto.setState(state);
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		dto.setCreatedDatetime(createdDatetime);
		dto.setModifiedDatetime(modifiedDatetime);
		return dto;
	}

	@Override
	public void populate(BaseEntity bEnt) {
		OrderEntity dto=(OrderEntity)bEnt;
		id=dto.getId();
		orderid=String.valueOf(dto.getOrderid());
		name=dto.getName();
		email=dto.getEmail();
		phoneNo=dto.getPhoneNo();
		address1=dto.getAddress1();
		address2=dto.getAddress2();
		month=dto.getMonth();
		year=dto.getMonth();
		startDate=DataUtility.getDateString(dto.getStartDate());
		endDate=DataUtility.getDateString(dto.getEndDate());
		city=dto.getCity();
		state=dto.getState();
		createdBy=dto.getCreatedBy();
		modifiedBy=dto.getModifiedBy();
		createdDatetime=dto.getCreatedDatetime();
		modifiedDatetime=dto.getModifiedDatetime();
		
	}

	

	
}
