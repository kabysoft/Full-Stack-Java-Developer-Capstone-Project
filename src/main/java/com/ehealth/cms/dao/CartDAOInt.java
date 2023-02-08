package com.ehealth.cms.dao;

import java.util.List;

import com.ehealth.cms.entity.CartEntity;

public interface CartDAOInt {

	public long add(CartEntity entity);
	
	public void delete(CartEntity entity);
	
	public CartEntity findBypk(long pk);
	
	public CartEntity findByName(String name);
	
	
	public void update(CartEntity entity);
	
	public List<CartEntity> list();
	
	public List<CartEntity>list(int pageNo,int pageSize);
	
	public List<CartEntity> search(CartEntity entity);
	
	public List<CartEntity> search(CartEntity entity,int pageNo,int pageSize);
	
	
}
