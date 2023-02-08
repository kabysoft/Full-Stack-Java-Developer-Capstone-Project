package com.ehealth.cms.dto;


import javax.validation.constraints.NotEmpty;

import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForgetPasswordForm extends BaseDto {

	@NotEmpty(message = "Login is required")
	private String login;

	@Override
	public BaseEntity getEntity() {
		UserEntity dto = new UserEntity();
		dto.setLogin(login);
		return dto;
	}

	@Override
	public void populate(BaseEntity bDto) {
		
	}

}
