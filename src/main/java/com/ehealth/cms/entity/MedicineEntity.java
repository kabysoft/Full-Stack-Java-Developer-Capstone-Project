package com.ehealth.cms.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMS_MEDICINE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicineEntity extends BaseEntity {
	
	@Column(name="NAME",length = 225)
	private String name;
	
	@Column(name="COMPANY_NAME",length = 225)
	private String companyName;
	
	@Column(name="Price")
	private BigDecimal price;
	
	@Column(name="QUANTITY",length = 225)
	private int quantity;
	
	@Column(name="EXPIRE_DATE")
	@Temporal(TemporalType.DATE)
	private Date expireDate;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "medicine")
	private Set<CartEntity> cart;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "medicine")
	private Set<OrderEntity> orders;

}
