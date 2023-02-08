package com.ehealth.cms.ctl;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.ehealth.cms.constant.EMSView;

@Controller
public class WelcomeCtl implements EMSView {

	
	@GetMapping(path={WELCOME_CTL,"/"})
	public String display(Model model) {
		return WELCOME_VIEW;
	}

	@GetMapping(path = ABOUTUS_CTL)
	public String aboutUs(Model model) {
		return ABOUTUS_VIEW;
	}

	@GetMapping(path = CONTACT_CTL)
	public String contactUs(Model model) {
		return CONTACTUS_VIEW;
	}

	

}
