package com.ehealth.cms.dto;

import javax.validation.constraints.NotEmpty;

import com.ehealth.cms.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm extends BaseDto  {

	@NotEmpty(message = "Old Password is required")
	private String oldPassword;
	
	@NotEmpty(message = "Confirm Password is required")
	private String confirmPassword;
	
	@NotEmpty(message = "New Passeword is required")
	private String newPassword;

	

	@Override
	public BaseEntity getEntity() {
		return null;
	}

	@Override
	public void populate(BaseEntity bDto) {
	}
	
}
