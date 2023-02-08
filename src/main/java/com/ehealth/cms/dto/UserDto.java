package com.ehealth.cms.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto extends BaseDto {

	@NotEmpty(message = "Name is required")
	private String name;
	@NotEmpty(message = "Login is required")
	private String login;
	@NotEmpty(message = "Password is required")
	private String password;
	@NotEmpty(message = "Email is required")
	private String email;
	@NotEmpty(message = "Gedner is required")
	private String gender;
	@NotEmpty(message = "PhoneNo is required")
	private String phoneNo;
	@NotEmpty(message = "Address is required")
	private String address;
	private String roleName;

	@Override
	public BaseEntity getEntity() {
		UserEntity entity=new UserEntity();
		entity.setId(id);
		entity.setName(name);
		entity.setLogin(login);
		entity.setPassword(password);
		entity.setEmail(email);
		entity.setGender(gender);
		entity.setPhoneNo(phoneNo);
		entity.setAddress(address);
		entity.setRoleName(roleName);
		entity.setCreatedBy(createdBy);
		entity.setModifiedBy(modifiedBy);
		entity.setCreatedDatetime(createdDatetime);
		entity.setModifiedDatetime(modifiedDatetime);
		return entity;
	}

	@Override
	public void populate(BaseEntity bEnt) {
		UserEntity entity=(UserEntity)bEnt;
		id=entity.getId();
		name=entity.getName();
		login=entity.getLogin();
		password=entity.getPassword();
		email=entity.getEmail();
		gender=entity.getGender();
		phoneNo=entity.getPhoneNo();
		address=entity.getAddress();
		roleName=entity.getRoleName();
		createdBy=entity.getCreatedBy();
		modifiedBy=entity.getModifiedBy();
		createdDatetime=entity.getCreatedDatetime();
		modifiedDatetime=entity.getModifiedDatetime();
		
	}
	
	
}
