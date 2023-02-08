package com.ehealth.cms.dao;

import java.util.List;

import com.ehealth.cms.entity.OrderEntity;



public interface OrderDAOInt {

	public long add(OrderEntity entity);
	
	public void delete(OrderEntity entity);
	
	public OrderEntity findBypk(long pk);
	
	public OrderEntity findByName(String name);
	
	public void update(OrderEntity entity);
	
	public List<OrderEntity> list();
	
	public List<OrderEntity>list(int pageNo,int pageSize);
	
	public List<OrderEntity> search(OrderEntity entity);
	
	public List<OrderEntity> search(OrderEntity entity,int pageNo,int pageSize);
	
	
	
}
