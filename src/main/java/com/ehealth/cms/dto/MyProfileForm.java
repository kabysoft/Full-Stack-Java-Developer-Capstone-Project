package com.ehealth.cms.dto;
import javax.validation.constraints.NotEmpty;

import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProfileForm extends BaseDto {

	@NotEmpty(message = "First Name is required")
	private String name;
	
	@NotEmpty(message = "Email is required")
	private String email;
	
	@NotEmpty(message = "Login is required")
	private String login;
	
	@NotEmpty(message = "MobileNo is required")
	private String phoneNo;
	
	
	public BaseEntity getEntity() {
		UserEntity entity = new UserEntity();
		entity.setName(name);
		entity.setPhoneNo(phoneNo);
		entity.setEmail(email);
		entity.setLogin(login);
		return entity;
	}

	
	public void populate(BaseEntity bDto) {
		UserEntity entity = (UserEntity) bDto;
		name = entity.getName();
		phoneNo=entity.getPhoneNo();
		email=entity.getEmail();
		login=entity.getLogin();
	}

	

}
