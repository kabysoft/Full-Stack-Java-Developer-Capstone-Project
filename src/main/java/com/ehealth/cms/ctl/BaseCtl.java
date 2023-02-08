package com.ehealth.cms.ctl;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;

import com.ehealth.cms.dto.UserDto;
import com.ehealth.cms.entity.BaseEntity;
import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.util.DataUtility;
import com.ehealth.cms.util.DataValidator;

@MappedSuperclass
public class BaseCtl {
	
	protected BaseEntity populateDTO(BaseEntity dto, HttpServletRequest request) {
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;
		UserEntity userbean = (UserEntity) request.getSession().getAttribute("user");
		if (userbean == null) {
			createdBy = "root";
			modifiedBy = "root";
		} else {

			modifiedBy = userbean.getLogin();

			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		return dto;
	}

}
