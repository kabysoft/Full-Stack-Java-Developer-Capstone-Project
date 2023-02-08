package com.ehealth.cms.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMS_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {

	@Column(name = "NAME", length = 20)
	private String name;
	
	@Column(name = "LOGIN", length = 20)
	private String login;

	@Column(name = "PASSWORD", length = 20)
	private String password;
	
	@Column(name = "EMAIL", length = 50)
	private String email;
	
	@Column(name = "GENDER", length = 6)
	private String gender;
	
	@Column(name = "PHONE_NO", length = 10)
	private String phoneNo;
	
	@Column(name = "Address", length = 755)
	private String address;
	
	@Column(name = "Role_Name", length = 6)
	private String roleName;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	private Set<CartEntity> cart;
	
	
}
