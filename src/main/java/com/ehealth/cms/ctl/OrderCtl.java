package com.ehealth.cms.ctl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.ehealth.cms.dto.OrderDto;
import com.ehealth.cms.entity.CartEntity;
import com.ehealth.cms.entity.MedicineEntity;
import com.ehealth.cms.entity.OrderEntity;
import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;
import com.ehealth.cms.serviceImpl.CartService;
import com.ehealth.cms.serviceImpl.MedicineService;
import com.ehealth.cms.serviceImpl.OrderServiceInt;
import com.ehealth.cms.util.DataUtility;


@Controller
@RequestMapping("/ctl/order")
public class OrderCtl extends BaseCtl implements EMSMessages, EMSView {

	@Autowired
	private OrderServiceInt service;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MedicineService medicineService;

	@ModelAttribute
	public void preload(Model model) {
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("January".toUpperCase(), "January".toUpperCase());
		map2.put("February".toUpperCase(), "February".toUpperCase());
		map2.put("March".toUpperCase(), "March".toUpperCase());
		map2.put("April".toUpperCase(), "April".toUpperCase());
		map2.put("May".toUpperCase(), "May".toUpperCase());
		map2.put("June".toUpperCase(), "June".toUpperCase());
		map2.put("July".toUpperCase(), "July".toUpperCase());
		map2.put("August".toUpperCase(), "August".toUpperCase());
		map2.put("September".toUpperCase(), "September".toUpperCase());
		map2.put("October".toUpperCase(), "October".toUpperCase());
		map2.put("November".toUpperCase(), "November".toUpperCase());
		map2.put("December".toUpperCase(), "December".toUpperCase());
		
		model.addAttribute("monthList", map2);
		
	}

	@GetMapping
	public String display(@RequestParam(required = false) Long id, Long pId, @ModelAttribute("Dto") OrderDto Dto,
			HttpSession session, Model model) {
		if (Dto.getId() > 0) {
			OrderEntity bean = service.findBypk(id);
			Dto.populate(bean);
		}
		try {
		UserEntity uDto=(UserEntity)session.getAttribute("user");
		Dto.setName(uDto.getName());
		Dto.setEmail(uDto.getEmail());
		Dto.setPhoneNo(uDto.getPhoneNo());
		CartEntity cDto=new CartEntity();
		cDto.setUser(uDto);
		
			model.addAttribute("cList", cartService.search(cDto));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return "order";
	}

	@PostMapping
	public String submit(@ModelAttribute("Dto") OrderDto Dto, BindingResult bindingResult,
			HttpSession session, Model model,HttpServletRequest request) {

		if (OP_RESET.equalsIgnoreCase(Dto.getOperation())) {
			return "redirect:/ctl/order";
		}
		
		if (OP_PAYMENT.equalsIgnoreCase(Dto.getOperation())) {
			OrderEntity bean = (OrderEntity) Dto.getEntity();
			session.setAttribute("orders", bean);
			return "payment";
		}
		
		try {
			if (OP_PLACE_ORDER.equalsIgnoreCase(Dto.getOperation())) {
				OrderEntity bean = (OrderEntity) session.getAttribute("orders");
				UserEntity uDto=(UserEntity)session.getAttribute("user");
				CartEntity cDto=new CartEntity();
				cDto.setUser(uDto);
				List<CartEntity> cList= cartService.search(cDto);
				System.out.println("Cart List Size==="+cList.size());
				
				Iterator<CartEntity> it=cList.iterator();
				model.addAttribute("cartList",cList);
			
				while (it.hasNext()) {
					System.out.println("In loop");
					OrderEntity ent=new OrderEntity();
					CartEntity cartDTO = (CartEntity) it.next();
					ent.setName(bean.getName());
					ent.setAddress1(bean.getAddress1());
					ent.setAddress2(bean.getAddress2());
					ent.setCity(bean.getCity());
					ent.setEmail(bean.getEmail());
					ent.setPhoneNo(bean.getPhoneNo());
					ent.setState(bean.getState());;
					ent.setOrderid(DataUtility.getRandom());
					ent.setMedicine(cartDTO.getMedicine());
					ent.setUserId(uDto.getId());
					ent.setQuantity(cartDTO.getQuantity());
					ent.setTotal(BigDecimal.valueOf(cartDTO.getQuantity()).multiply(cartDTO.getPrice()));
					model.addAttribute("orderDetail",ent);
					ent.setOrderDate(new Date());
					ent.setYear(String.valueOf(DataUtility.getYear(DataUtility.getDateString1(new Date()))));
					ent.setMonth(DataUtility.getMonth(DataUtility.getDateString1(new Date())).name());					
					service.add((OrderEntity)populateDTO(ent, request));
					MedicineEntity mEnt=cartDTO.getMedicine();
					mEnt.setQuantity(mEnt.getQuantity()-cartDTO.getQuantity());
					medicineService.update(mEnt);
					cartService.delete(cartDTO);
				}
				return "success";
			}
		} catch (DuplicateRecordException e) {
			model.addAttribute("error", e.getMessage());
			return "order";
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchList(@ModelAttribute("Dto") OrderDto Dto,
			@RequestParam(required = false) String operation, Long vid, HttpSession session, Model model) {

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:/ctl/order/search";
		}

		int pageNo = Dto.getPageNo();
		int pageSize = Dto.getPageSize();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		} else if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/order";
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;
		pageSize = (pageSize < 1) ? 10 : pageSize;

		if (OP_DELETE.equals(operation)) {
			pageNo = 1;
			if (Dto.getIds() != null) {
				for (long id : Dto.getIds()) {
					OrderEntity dto = new OrderEntity();
					dto.setId(id);
					service.delete(dto);
				}
				model.addAttribute("success", "Deleted Successfully!!!");
			} else {
				model.addAttribute("error", "Select at least one record");
			}
		}
		OrderEntity dto = (OrderEntity) Dto.getEntity();

		UserEntity uDto = (UserEntity) session.getAttribute("user");
		if(uDto.getRoleName()=="user") {
			dto.setUserId(uDto.getId());
		}
		

		List<OrderEntity> list = service.search(dto, pageNo, pageSize);
		List<OrderEntity> totallist = service.search(dto);
		model.addAttribute("list", list);

		if (list.size() == 0 && !OP_DELETE.equalsIgnoreCase(operation)) {
			model.addAttribute("error", "Record not found");
		}

		int listsize = list.size();
		int total = totallist.size();
		int pageNoPageSize = pageNo * pageSize;

		Dto.setPageNo(pageNo);
		Dto.setPageSize(pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("listsize", listsize);
		model.addAttribute("total", total);
		model.addAttribute("pagenosize", pageNoPageSize);
		model.addAttribute("Dto", Dto);
		return "orderList";
	}
	
	@RequestMapping(value = "/search/month", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchListMonth(@ModelAttribute("Dto") OrderDto Dto,
			@RequestParam(required = false) String operation, Long vid, HttpSession session, Model model) {

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:/ctl/order/search/month";
		}

		int pageNo = Dto.getPageNo();
		int pageSize = Dto.getPageSize();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		} else if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/order";
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;
		pageSize = (pageSize < 1) ? 10 : pageSize;

		if (OP_DELETE.equals(operation)) {
			pageNo = 1;
			if (Dto.getIds() != null) {
				for (long id : Dto.getIds()) {
					OrderEntity dto = new OrderEntity();
					dto.setId(id);
					service.delete(dto);
				}
				model.addAttribute("success", "Deleted Successfully!!!");
			} else {
				model.addAttribute("error", "Select at least one record");
			}
		}
		OrderEntity dto = (OrderEntity) Dto.getEntity();

		UserEntity uDto = (UserEntity) session.getAttribute("user");
		if(uDto.getRoleName()=="user") {
			dto.setUserId(uDto.getId());
		}
		

		List<OrderEntity> list = service.search(dto, pageNo, pageSize);
		List<OrderEntity> totallist = service.search(dto);
		model.addAttribute("list", list);

		if (list.size() == 0 && !OP_DELETE.equalsIgnoreCase(operation)) {
			model.addAttribute("error", "Record not found");
		}

		int listsize = list.size();
		int total = totallist.size();
		int pageNoPageSize = pageNo * pageSize;

		Dto.setPageNo(pageNo);
		Dto.setPageSize(pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("listsize", listsize);
		model.addAttribute("total", total);
		model.addAttribute("pagenosize", pageNoPageSize);
		model.addAttribute("Dto", Dto);
		return "orderMonthList";
	}
	
	@RequestMapping(value = "/search/year", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchListYear(@ModelAttribute("Dto") OrderDto Dto,
			@RequestParam(required = false) String operation, Long vid, HttpSession session, Model model) {

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:/ctl/order/search/year";
		}

		int pageNo = Dto.getPageNo();
		int pageSize = Dto.getPageSize();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		} else if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/order";
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;
		pageSize = (pageSize < 1) ? 10 : pageSize;

		if (OP_DELETE.equals(operation)) {
			pageNo = 1;
			if (Dto.getIds() != null) {
				for (long id : Dto.getIds()) {
					OrderEntity dto = new OrderEntity();
					dto.setId(id);
					service.delete(dto);
				}
				model.addAttribute("success", "Deleted Successfully!!!");
			} else {
				model.addAttribute("error", "Select at least one record");
			}
		}
		OrderEntity dto = (OrderEntity) Dto.getEntity();

		UserEntity uDto = (UserEntity) session.getAttribute("user");
		if(uDto.getRoleName()=="user") {
			dto.setUserId(uDto.getId());
		}
		

		List<OrderEntity> list = service.search(dto, pageNo, pageSize);
		List<OrderEntity> totallist = service.search(dto);
		model.addAttribute("list", list);

		if (list.size() == 0 && !OP_DELETE.equalsIgnoreCase(operation)) {
			model.addAttribute("error", "Record not found");
		}

		int listsize = list.size();
		int total = totallist.size();
		int pageNoPageSize = pageNo * pageSize;

		Dto.setPageNo(pageNo);
		Dto.setPageSize(pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("listsize", listsize);
		model.addAttribute("total", total);
		model.addAttribute("pagenosize", pageNoPageSize);
		model.addAttribute("Dto", Dto);
		return "orderYearList";
	}
	


}
