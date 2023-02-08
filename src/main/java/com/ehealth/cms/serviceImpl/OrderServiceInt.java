package com.ehealth.cms.serviceImpl;

import java.util.List;

import com.ehealth.cms.entity.OrderEntity;
import com.ehealth.cms.exception.DuplicateRecordException;


public interface OrderServiceInt {

	public long add(OrderEntity entity) throws DuplicateRecordException;

	public void delete(OrderEntity entity);

	public OrderEntity findBypk(long pk);

	public OrderEntity findByName(String name);

	public void update(OrderEntity entity) throws DuplicateRecordException;

	public List<OrderEntity> list();

	public List<OrderEntity> list(int pageNo, int pageSize);

	public List<OrderEntity> search(OrderEntity entity);

	public List<OrderEntity> search(OrderEntity entity, int pageNo, int pageSize);
	

}
