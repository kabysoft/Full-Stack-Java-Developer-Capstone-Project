package com.ehealth.cms.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealth.cms.dao.MedicineDAOInt;
import com.ehealth.cms.entity.MedicineEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicineServiceImpl  implements MedicineService {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MedicineDAOInt dao;
	
	

	@Override
	@Transactional
	public long add(MedicineEntity entity) throws DuplicateRecordException, ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" add() :" +entity.toString());
		long pk=0;
		try {
		  pk = dao.add(entity);
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to create the Medicine record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" add() :");
		return pk;
	}

	@Override
	@Transactional
	public void delete(MedicineEntity entity) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" delete() :" +entity.getId());
		try {
			 dao.delete(entity);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to Delete the Medicine record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" delete() :" +entity.getId());
	}

	@Override
	@Transactional
	public MedicineEntity findBypk(long pk) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" findBypk() :" +pk);
		MedicineEntity entity=null;
		try {
			entity=dao.findBypk(pk);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the Medicine by id record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" findBypk() :" +pk);
		return entity;
	}

	@Override
	@Transactional
	public MedicineEntity findByName(String name) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" findByName() :" +name);
		MedicineEntity entity=null;
		try {
			entity=dao.findByName(name);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the Medicine by login record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" findByName() :" +name);
		return entity;
	}

	@Override
	@Transactional
	public void update(MedicineEntity entity) throws DuplicateRecordException, ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" update() :" +entity.toString());
		try {
		 dao.update(entity);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to Medicine the Medicine record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" update() :");
		
	}


	@Override
	@Transactional
	public List<MedicineEntity> list(int pageNo, int pageSize) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" list() :");
		List<MedicineEntity> list=null;
		try {
		 list=dao.list(pageNo, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to getAllMedicine the  record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" list() :");
		return list;
	}

	@Override
	@Transactional
	public List<MedicineEntity> search(MedicineEntity entity) throws ApplicationException {
		return search(entity, 0, 0);
	}

	@Override
	@Transactional
	public List<MedicineEntity> search(MedicineEntity entity, int pageNo, int pageSize) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" search() :"+entity.toString());
		List<MedicineEntity> list=null;
		try {
		 list=dao.search(entity, pageNo, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to getAllMedicine the  record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" search() :"+entity.toString());
		return list;
	}


	@Override
	@Transactional
	public List<MedicineEntity> list() throws ApplicationException {
		return list(0, 0);
	}

	
	
	

}
