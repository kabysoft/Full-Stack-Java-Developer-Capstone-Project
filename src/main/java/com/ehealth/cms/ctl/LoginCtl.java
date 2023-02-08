package com.ehealth.cms.ctl;

import java.util.HashMap;

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

import com.ehealth.cms.constant.EMSMessages;
import com.ehealth.cms.constant.EMSView;
import com.ehealth.cms.constant.RoleConst;
import com.ehealth.cms.dto.ChangePasswordForm;
import com.ehealth.cms.dto.ForgetPasswordForm;
import com.ehealth.cms.dto.LoginDto;
import com.ehealth.cms.dto.MyProfileForm;
import com.ehealth.cms.dto.UserDto;
import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;
import com.ehealth.cms.serviceImpl.UserService;
import com.ehealth.cms.util.DataUtility;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginCtl extends BaseCtl implements EMSMessages, EMSView {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	protected static final String OP_SIGNIN = "SignIn";
	protected static final String OP_SIGNUP = "SignUp";
	protected static final String OP_LOGOUT = "Logout";

	@Autowired
	private UserService service;

	@GetMapping(path = LOGIN_CTL)
	public String display(@ModelAttribute("form") LoginDto dto, HttpSession session, Model model) {
		logger.info("Begin: " + this.getClass().getName() + "display method");
		if (session.getAttribute("user") != null) {
			session.invalidate();
			model.addAttribute(KEY_MSG_SUCCESS, MSG_LOGOUT);
		}
		logger.info("End: " + this.getClass().getName() + "display method");
		return LOGIN_VIEW;
	}

	@ModelAttribute
	public void preload(Model model) {
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("MALE", "MALE");
		map2.put("FEMALE", "FEMALE");
		model.addAttribute("gender", map2);
	}

	@PostMapping(path = LOGIN_CTL)
	public String submit(@RequestParam String operation, HttpSession session,
			@Valid @ModelAttribute("form") LoginDto dto, BindingResult result, Model model) {
		logger.info("Begin: " + this.getClass().getName() + "submit method");
		System.out.println("In dopost  LoginCtl");

		if (OP_SIGNUP.equalsIgnoreCase(dto.getOperation())) {
			return "redirect:".concat(SIGNUP_CTL);
		}

		if (result.hasErrors()) {
			logger.info(result.toString());
			return LOGIN_VIEW;
		}

		UserEntity user;
		try {
			user = service.authentication((UserEntity) dto.getEntity());
			if (user != null) {
				System.out.println(user.toString());
				session.setAttribute("user", user);
				return "redirect:".concat(WELCOME_CTL);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		model.addAttribute(KEY_MSG_ERROR, MSG_INVALID_LOGIN_PASSWORD);
		logger.info("End: " + this.getClass().getName() + "submit method");
		return WELCOME_VIEW;
	}

	@GetMapping(path = SIGNUP_CTL)
	public String display(@ModelAttribute("form") UserDto dto, Model model) {
		logger.info("Begin: " + this.getClass().getName() + "display SignUp method");
		logger.info("End: " + this.getClass().getName() + "display SignUp method");
		return SIGNUP_VIEW;
	}

	@PostMapping(path = SIGNUP_CTL)
	public String submit(@RequestParam String operation, @Valid @ModelAttribute("form") UserDto dto,
			BindingResult bindingResult, Model model, HttpServletRequest request) {

		logger.info("Begin: " + this.getClass().getName() + "submit SignUp method");

		if (OP_RESET.equalsIgnoreCase(dto.getOperation())) {
			return "redirect:".concat(SIGNUP_CTL);
		}

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			return SIGNUP_VIEW;
		}

		try {
			if (OP_SAVE.equalsIgnoreCase(dto.getOperation())) {
				dto.setRoleName(RoleConst.USER_ROLE);
				service.register((UserEntity) populateDTO(dto.getEntity(), request));
				model.addAttribute(KEY_MSG_SUCCESS, MSG_USER_REGISTRATION);
				return "signUp";
			}
		} catch (DuplicateRecordException e) {
			model.addAttribute("error", e.getMessage());
			return SIGNUP_VIEW;
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		logger.info("End: " + this.getClass().getName() + "submit SignUp method");
		return SIGNUP_VIEW;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String displayProfile(HttpSession session, @ModelAttribute("form") MyProfileForm form, Model model) {
		UserEntity entity = (UserEntity) session.getAttribute("user");
		form.populate(entity);
		System.out.println("/Myprofile");
		return "myprofile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String submitProfile(HttpSession session, @ModelAttribute("form") @Valid MyProfileForm form,
			BindingResult bindingResult, @RequestParam(required = false) String operation, Model model) {

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:/profile";
		}

		if (bindingResult.hasErrors()) {
			return "myprofile";
		}
		try {
			UserEntity entity = (UserEntity) session.getAttribute("user");
			entity = service.findBypk(entity.getId());
			entity.setName(form.getName());
			entity.setLogin(form.getLogin());
			entity.setEmail(form.getEmail());
			entity.setPhoneNo(form.getPhoneNo());
			service.update(entity);
		} catch (DuplicateRecordException e) {

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("success", "Profile Update successfully");

		return "myprofile";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String displayChangePassword(@ModelAttribute("form") ChangePasswordForm form, Model model) {
		return "changePassword";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String submitChangePassword(HttpSession session, @ModelAttribute("form") @Valid ChangePasswordForm form,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "changePassword";
		}
		try {
			if (form.getNewPassword().equalsIgnoreCase(form.getConfirmPassword())) {

				UserEntity dto = (UserEntity) session.getAttribute("user");
				dto = service.findBypk(dto.getId());
				if (service.changePassword(dto.getId(), form.getOldPassword(), form.getNewPassword())) {
					model.addAttribute("success", "Password changed Successfully");
				} else {
					model.addAttribute("error", "Old Passowors Does not Matched");
				}
			} else {
				model.addAttribute("error", "New Password and confirm password does not matched");
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "changePassword";
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
	public String display(@ModelAttribute("form") ForgetPasswordForm form, HttpSession session, Model model) {

		System.out.println("In doget LoginCtl forgetpassword");

		return "forgetPassword";

	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public String display(@ModelAttribute("form") @Valid ForgetPasswordForm form, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "forgetPassword";
		}
		try {
			UserEntity dto;
			dto = service.findByLogin(form.getLogin());
			if (dto == null) {
				model.addAttribute("error", "Login Id does not exist");
			}
			if (dto != null) {
				service.forgetPassword(form.getLogin());
				model.addAttribute("success", "Password has been sent to your registered Email ID!!");
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "forgetPassword";
	}

}
