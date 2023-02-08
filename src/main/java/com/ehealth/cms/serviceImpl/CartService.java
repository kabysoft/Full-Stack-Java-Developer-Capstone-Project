package com.ehealth.cms.serviceImpl;

import java.util.List;

import com.ehealth.cms.entity.CartEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;



public interface CartService {

	public long add(CartEntity entity) throws DuplicateRecordException, ApplicationException;

	public void delete(CartEntity entity) throws ApplicationException;

	public CartEntity findBypk(long pk) throws ApplicationException;

	public CartEntity findByName(String name) throws ApplicationException;

	public void update(CartEntity entity) throws DuplicateRecordException, ApplicationException;

	public List<CartEntity> list() throws ApplicationException;

	public List<CartEntity> list(int pageNo, int pageSize) throws ApplicationException;

	public List<CartEntity> search(CartEntity entity) throws ApplicationException;

	public List<CartEntity> search(CartEntity entity, int pageNo, int pageSize) throws ApplicationException;


}
