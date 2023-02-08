package com.ehealth.cms.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehealth.cms.constant.EMSView;
import com.ehealth.cms.constant.EMSMessages;
import com.ehealth.cms.constant.RoleConst;
import com.ehealth.cms.dto.UserDto;
import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;
import com.ehealth.cms.serviceImpl.UserService;

@Controller
public class UserCtl extends BaseCtl implements EMSMessages, EMSView {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService service;

	@ModelAttribute
	public void preload(Model model) {

	}

	@GetMapping({ USER_CTL })
	public String display(@RequestParam(required = false) Long id, Long pId, @ModelAttribute("form") UserDto dto,
			HttpSession session, Model model) {
		logger.info("Begin: " + this.getClass().getName() + "display method");
		try {
			if (dto.getId() > 0) {
				UserEntity ent = service.findBypk(id);
				dto.populate(ent);
			}
		} catch (ApplicationException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return USER_VIEW;
		}
		logger.info("End: " + this.getClass().getName() + "display method");
		return USER_VIEW;
	}

	@PostMapping({ USER_CTL })
	public String submit(@Valid @ModelAttribute("form") UserDto dto, BindingResult bindingResult,
			HttpSession session, Model model, HttpServletRequest request) {
		logger.info("Begin: " + this.getClass().getName() + "submit method");

		if (OP_RESET.equalsIgnoreCase(dto.getOperation())) {
			return "redirect:".concat(USER_CTL);
		}

		try {
			if (OP_SAVE.equalsIgnoreCase(dto.getOperation())) {

				if (bindingResult.hasErrors()) {
					logger.info(bindingResult.toString());
					return USER_VIEW;
				}
				if (dto.getId() > 0) {
					service.update((UserEntity) populateDTO(dto.getEntity(), request));
					model.addAttribute(KEY_MSG_SUCCESS, MSG_UPDATE_SUCCESS);
				} else {
					service.add((UserEntity) populateDTO(dto.getEntity(), request));
					model.addAttribute(KEY_MSG_SUCCESS, MSG_ADD_SUCCESS);
				}
				return USER_VIEW;
			}
		} catch (DuplicateRecordException e) {
			model.addAttribute(KEY_MSG_ERROR, e.getMessage());
			return USER_VIEW;
		} catch (ApplicationException e) {
			e.printStackTrace();
			return USER_VIEW;
		}
		logger.info("End: " + this.getClass().getName() + "submit method");
		return "";
	}

	@RequestMapping(value = USER_LIST_CTL, method = { RequestMethod.GET, RequestMethod.POST })
	public String searchList(@ModelAttribute("form") UserDto dto, @RequestParam(required = false) String operation,
			HttpSession session, Model model) {

		logger.info("Begin: " + this.getClass().getName() + "searchList method");

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:".concat(USER_LIST_CTL);
		}

		int pageNo = dto.getPageNo();
		int pageSize = dto.getPageSize();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		} else if (OP_NEW.equals(operation)) {
			return "redirect:".concat(USER_CTL);
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;
		pageSize = (pageSize < 1) ? 10 : pageSize;

		try {

			if (OP_DELETE.equals(operation)) {
				pageNo = 1;
				if (dto.getIds() != null) {
					for (long id : dto.getIds()) {
						UserEntity deletedto = new UserEntity();
						deletedto.setId(id);
						service.delete(deletedto);
					}
					model.addAttribute(KEY_MSG_SUCCESS, MSG_DELETE_SUCCESS);
				} else {
					model.addAttribute(KEY_MSG_ERROR, MSG_SELECT_ONE_RECORD);
				}
			}
			
			UserEntity entity=(UserEntity) dto.getEntity();
			entity.setRoleName(RoleConst.USER_ROLE);
			List<UserEntity> list =  service.search(entity, pageNo, pageSize);
			List<UserEntity> totallist = service.search(entity);
			model.addAttribute("list", list);

			if (list.size() == 0 && !OP_DELETE.equalsIgnoreCase(operation)) {
				model.addAttribute(KEY_MSG_ERROR, MSG_RECORD_NOT_FOUND);
			}

			int listsize = list.size();
			int total = totallist.size();
			int pageNoPageSize = pageNo * pageSize;

			dto.setPageNo(pageNo);
			dto.setPageSize(pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("listsize", listsize);
			model.addAttribute("total", total);
			model.addAttribute("pagenosize", pageNoPageSize);
			model.addAttribute("form", dto);
		} catch (ApplicationException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return USER_LIST_VIEW;

		}
		logger.info("End: " + this.getClass().getName() + "searchList method");
		return USER_LIST_VIEW;
	}

}
