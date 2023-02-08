package com.ehealth.cms.constant;

public interface EMSView {
	
	//To Access Controller
	static final String LOGIN_CTL = "/login";
	static final String SIGNUP_CTL = "/signUp";
	static final String WELCOME_CTL = "/welcome";
	
	static final String MEDICINE_CTL = "/ctl/medicine";
	static final String MEDICINE_LIST_CTL = "/ctl/medicine/search";
	
	static final String USER_CTL = "/ctl/user";
	static final String USER_LIST_CTL = "/ctl/user/search";
	
	static final String CART_CTL = "/ctl/cart";
	static final String CART_LIST_CTL = "/ctl/cart/search";
	
	static final String ABOUTUS_CTL = "/aboutUs";
	static final String CONTACT_CTL = "/contactUs";
	
	//To Access View
	static final String LOGIN_VIEW = "login";
	static final String WELCOME_VIEW = "welcome";
	static final String SIGNUP_VIEW = "signUp";
	
	static final String ABOUTUS_VIEW = "aboutUs";
	static final String CONTACTUS_VIEW = "contactUs";
	
	static final String  MEDICINE_VIEW = "medicine";
	static final String  MEDICINE_LIST_VIEW = "medicineList";
	
	static final String  USER_VIEW = "user";
	static final String  USER_LIST_VIEW = "userList";
	
	static final String  CART_VIEW = "cart";
	static final String  CART_LIST_VIEW = "cartList";
	
	

}
