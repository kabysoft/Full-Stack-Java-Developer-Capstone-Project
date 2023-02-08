package com.ehealth.cms.dto;


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
public class LoginDto extends BaseDto {
	
	@NotEmpty(message = "Login is required")
	private String login;
	@NotEmpty(message = "Password is required")
	private String password;

	@Override
	public BaseEntity getEntity() {
		UserEntity entity=new UserEntity();
		entity.setLogin(login);
		entity.setPassword(password);
		return entity;
	}

	@Override
	public void populate(BaseEntity bEnt) {
		UserEntity entity=(UserEntity)bEnt;
		login=entity.getLogin();
		password=entity.getPassword();
		
	}
	
	
}
