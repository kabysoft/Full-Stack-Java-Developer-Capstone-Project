package com.ehealth.cms.ctl;

	
import java.math.BigDecimal;
import java.util.Iterator;
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

import com.ehealth.cms.constant.EMSMessages;
import com.ehealth.cms.constant.EMSView;
import com.ehealth.cms.dto.CartDto;
import com.ehealth.cms.entity.CartEntity;
import com.ehealth.cms.entity.MedicineEntity;
import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;
import com.ehealth.cms.serviceImpl.CartService;
import com.ehealth.cms.serviceImpl.MedicineService;
import com.ehealth.cms.util.DataUtility;

@Controller
public class CartCtl extends BaseCtl implements EMSMessages, EMSView {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CartService service;

	@Autowired
	private MedicineService medicineService;

	@ModelAttribute
	public void preload(Model model) {

	}

	@GetMapping({ CART_CTL })
	public String display(@RequestParam(required = false) Long id, Long mId, @ModelAttribute("form") CartDto dto,
			HttpSession session, Model model) {
		logger.info("Begin: " + this.getClass().getName() + "display method");
		try {
			mId = DataUtility.getLong(String.valueOf(mId));
			if (mId > 0) {
				UserEntity userEnt = (UserEntity) session.getAttribute("user");
				CartEntity entity = (CartEntity) dto.getEntity();
				MedicineEntity mdEntity = medicineService.findBypk(mId);

				entity.setUserId(userEnt.getId());
				entity.setMedicineId(mId);
				if (service.search(entity).size() == 0) {
					entity.setUser(userEnt);
					entity.setMedicine(mdEntity);
					entity.setPrice(mdEntity.getPrice());
					entity.setQuantity(1);
					entity.setFinalPrice(mdEntity.getPrice());
					service.add(entity);
				} else {
					return "redirect:".concat(CART_LIST_CTL);
				}
			} else {
				return "redirect:".concat(MEDICINE_LIST_CTL);
			}
		} catch (ApplicationException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return "redirect:".concat(MEDICINE_LIST_CTL);
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
			return "redirect:".concat(MEDICINE_LIST_CTL);
		}
		logger.info("End: " + this.getClass().getName() + "display method");
		return "redirect:".concat(MEDICINE_LIST_CTL);
	}

	@PostMapping({ CART_CTL })
	public String submit(@Valid @ModelAttribute("form") CartDto dto, BindingResult bindingResult, HttpSession session,
			Model model, HttpServletRequest request) {
		logger.info("Begin: " + this.getClass().getName() + "submit method");

		if (OP_RESET.equalsIgnoreCase(dto.getOperation())) {
			return "redirect:".concat(CART_CTL);
		}

		try {
			if (OP_SAVE.equalsIgnoreCase(dto.getOperation())) {

				if (bindingResult.hasErrors()) {
					logger.info(bindingResult.toString());
					return CART_VIEW;
				}
				if (dto.getId() > 0) {
					service.update((CartEntity) populateDTO(dto.getEntity(), request));
					model.addAttribute(KEY_MSG_SUCCESS, MSG_UPDATE_SUCCESS);
				} else {
					service.add((CartEntity) populateDTO(dto.getEntity(), request));
					model.addAttribute(KEY_MSG_SUCCESS, MSG_ADD_SUCCESS);
				}
				return CART_VIEW;
			}
		} catch (DuplicateRecordException e) {
			model.addAttribute(KEY_MSG_ERROR, e.getMessage());
			return CART_VIEW;
		} catch (ApplicationException e) {
			e.printStackTrace();
			return CART_VIEW;
		}
		logger.info("End: " + this.getClass().getName() + "submit method");
		return "";
	}

	@RequestMapping(value = CART_LIST_CTL, method = { RequestMethod.GET, RequestMethod.POST })
	public String searchList(@ModelAttribute("form") CartDto dto, @RequestParam(required = false) String operation,
			Long cid,HttpSession session, Model model,HttpServletRequest request) {

		logger.info("Begin: " + this.getClass().getName() + "searchList method");

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:".concat(CART_LIST_CTL);
		}
		
		if("Checkout".equalsIgnoreCase(operation)) {
			return"redirect:/ctl/order";
		}

		int pageNo = dto.getPageNo();
		int pageSize = dto.getPageSize();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		} else if (OP_NEW.equals(operation)) {
			return "redirect:".concat(CART_CTL);
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;
		pageSize = (pageSize < 1) ? 10 : pageSize;

		try {

			if (OP_DELETE.equals(operation)) {
				pageNo = 1;
				if (dto.getIds() != null) {
					for (long id : dto.getIds()) {
						CartEntity deletedto = new CartEntity();
						deletedto.setId(id);
						service.delete(deletedto);
					}
					model.addAttribute(KEY_MSG_SUCCESS, MSG_DELETE_SUCCESS);
				} else {
					model.addAttribute(KEY_MSG_ERROR, MSG_SELECT_ONE_RECORD);
				}
			}
			UserEntity userEnt = (UserEntity) session.getAttribute("user");
			try {
				
				
				
				if("Update".equalsIgnoreCase(operation)) {
					CartEntity cDto=new CartEntity();
					cDto.setUser(userEnt);
					List cList=service.search(cDto);
					Iterator<CartEntity> cit=cList.iterator();
					int i=1;
					while (cit.hasNext()) {
						CartEntity cartDTO = (CartEntity) cit.next();
						int quant=DataUtility.getInt(request.getParameter("qunatity"+i++));
						cartDTO.setQuantity(quant);
						cartDTO.setFinalPrice(cartDTO.getPrice().multiply(BigDecimal.valueOf(cartDTO.getQuantity())));
							service.update(cartDTO);
					}
				}if(OP_DELETE.equalsIgnoreCase(operation)) {
					CartEntity deleteCart=service.findBypk(DataUtility.getLong(String.valueOf(cid)));
					service.delete(deleteCart);
					return"redirect:/ctl/cart/search";
				}
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			

			CartEntity entity = (CartEntity) dto.getEntity();
			entity.setUserId(userEnt.getId());
			List<CartEntity> list = service.search(entity, pageNo, pageSize);
			List<CartEntity> totallist = service.search(entity);
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
			return CART_LIST_VIEW;

		}
		logger.info("End: " + this.getClass().getName() + "searchList method");
		return CART_LIST_VIEW;
	}

}
