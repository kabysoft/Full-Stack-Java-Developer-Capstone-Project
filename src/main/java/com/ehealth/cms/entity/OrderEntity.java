package com.ehealth.cms.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="HMS_USER_ORDERS")
@Setter
@Getter
public class OrderEntity extends BaseEntity {
	
	@Column(name="TOTAL")
	private BigDecimal total;

	@Column(name="NAME",length = 225)
	private String name;
	
	@Column(name="EMAIL",length = 225)
	private String email;
	
	@Column(name="MOBILE_NO",length = 225)
	private String phoneNo;
	
	@Column(name="ADDRESS1",length = 225)
	private String address1;
	
	@Column(name="ADDRESS2",length = 225)
	private String address2;
	
	@Column(name="CITY",length = 225)
	private String city;
	
	@Column(name="STATE",length = 225)
	private String state;
	
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="QUANTITY",length = 225)
	private int quantity;
	
	@Column(name="ORDER_ID")
	private long orderid;
	
	@ManyToOne
	@JoinColumn(name="Medicine",nullable = false)
	private MedicineEntity medicine;
	
	private String year;
	private String month;
	@Transient
	private Date startDate;
	@Transient
	private Date endDate;
	
	@Column(name="OrderDate")
	@Temporal(TemporalType.DATE)
	private Date orderDate;


	
}
