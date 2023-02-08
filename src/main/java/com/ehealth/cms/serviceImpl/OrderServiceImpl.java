package com.ehealth.cms.serviceImpl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehealth.cms.dao.OrderDAOInt;
import com.ehealth.cms.entity.OrderEntity;
import com.ehealth.cms.exception.DuplicateRecordException;



@Service
public class OrderServiceImpl implements OrderServiceInt {

	private static Logger log = Logger.getLogger(OrderServiceImpl.class.getName());

	@Autowired
	private OrderDAOInt dao;

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Override
	@Transactional
	public long add(OrderEntity entity) throws DuplicateRecordException {
		log.info("OrderServiceImpl Add method start");
		long pk = dao.add(entity);
		log.info("OrderServiceImpl Add method end");
		return pk;
	}

	@Override
	@Transactional
	public void delete(OrderEntity entity) {
		log.info("OrderServiceImpl Delete method start");
		dao.delete(entity);
		log.info("OrderServiceImpl Delete method end");

	}

	@Override
	@Transactional
	public OrderEntity findBypk(long pk) {
		log.info("OrderServiceImpl findBypk method start");
		OrderEntity entity = dao.findBypk(pk);
		log.info("OrderServiceImpl findBypk method end");
		return entity;
	}

	@Override
	@Transactional
	public OrderEntity findByName(String name) {
		log.info("OrderServiceImpl findByOrderName method start");
		OrderEntity entity = dao.findByName(name);
		log.info("OrderServiceImpl findByOrderName method end");
		return entity;
	}

	@Override
	@Transactional
	public void update(OrderEntity entity) throws DuplicateRecordException {
		log.info("OrderServiceImpl update method start");
		dao.update(entity);
		log.info("OrderServiceImpl update method end");
	}

	@Override
	@Transactional
	public List<OrderEntity> list() {
		log.info("OrderServiceImpl list method start");
		List<OrderEntity> list = dao.list();
		log.info("OrderServiceImpl list method end");
		return list;
	}

	@Override
	@Transactional
	public List<OrderEntity> list(int pageNo, int pageSize) {
		log.info("OrderServiceImpl list method start");
		List<OrderEntity> list = dao.list(pageNo, pageSize);
		log.info("OrderServiceImpl list method end");
		return list;
	}

	@Override
	@Transactional
	public List<OrderEntity> search(OrderEntity entity) {
		log.info("OrderServiceImpl search method start");
		List<OrderEntity> list = dao.search(entity);
		log.info("OrderServiceImpl search method end");
		return list;
	}

	@Override
	@Transactional
	public List<OrderEntity> search(OrderEntity entity, int pageNo, int pageSize) {
		log.info("OrderServiceImpl search method start");
		List<OrderEntity> list = dao.search(entity, pageNo, pageSize);
		log.info("OrderServiceImpl search method end");
		return list;
	}
	

}
