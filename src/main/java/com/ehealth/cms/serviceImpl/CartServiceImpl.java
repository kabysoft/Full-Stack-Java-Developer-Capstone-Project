package com.ehealth.cms.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealth.cms.dao.CartDAOInt;
import com.ehealth.cms.entity.CartEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl  implements CartService {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CartDAOInt dao;
	
	

	@Override
	@Transactional
	public long add(CartEntity entity) throws DuplicateRecordException, ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" add() :" +entity.toString());
		long pk=0;
		try {
		  pk = dao.add(entity);
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to create the Cart record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" add() :");
		return pk;
	}

	@Override
	@Transactional
	public void delete(CartEntity entity) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" delete() :" +entity.getId());
		try {
			 dao.delete(entity);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to Delete the Cart record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" delete() :" +entity.getId());
	}

	@Override
	@Transactional
	public CartEntity findBypk(long pk) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" findBypk() :" +pk);
		CartEntity entity=null;
		try {
			entity=dao.findBypk(pk);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the Cart by id record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" findBypk() :" +pk);
		return entity;
	}

	@Override
	@Transactional
	public CartEntity findByName(String name) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" findByName() :" +name);
		CartEntity entity=null;
		try {
			entity=dao.findByName(name);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the Cart by login record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" findByName() :" +name);
		return entity;
	}

	@Override
	@Transactional
	public void update(CartEntity entity) throws DuplicateRecordException, ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" update() :" +entity.toString());
		try {
		 dao.update(entity);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to Cart the Cart record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" update() :");
		
	}


	@Override
	@Transactional
	public List<CartEntity> list(int pageNo, int pageSize) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" list() :");
		List<CartEntity> list=null;
		try {
		 list=dao.list(pageNo, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to getAllCart the  record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" list() :");
		return list;
	}

	@Override
	@Transactional
	public List<CartEntity> search(CartEntity entity) throws ApplicationException {
		return search(entity, 0, 0);
	}

	@Override
	@Transactional
	public List<CartEntity> search(CartEntity entity, int pageNo, int pageSize) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" search() :"+entity.toString());
		List<CartEntity> list=null;
		try {
		 list=dao.search(entity, pageNo, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to getAllCart the  record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" search() :"+entity.toString());
		return list;
	}


	@Override
	@Transactional
	public List<CartEntity> list() throws ApplicationException {
		return list(0, 0);
	}

	
	
	

}
