package com.ehealth.cms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ehealth.cms.entity.MedicineEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MedicineDAOImpl implements MedicineDAOInt {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityManager entityManager;

	@Override
	public long add(MedicineEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " add() :" + entity.toString());
		Session session = entityManager.unwrap(Session.class);
		long pk = (long) session.save(entity);
		logger.info("end : " + this.getClass().getName() + " add() :" + entity.toString());
		return pk;
	}

	@Override
	public void delete(MedicineEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " delete() :" + entity.toString());
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		logger.info("End : " + this.getClass().getName() + " delete() :" + entity.toString());

	}

	@Override
	public MedicineEntity findBypk(long pk) {
		logger.info("Begin : " + this.getClass().getName() + " delete() :" + pk);
		Session session = entityManager.unwrap(Session.class);
		MedicineEntity entity = (MedicineEntity) session.get(MedicineEntity.class, pk);
		logger.info("End : " + this.getClass().getName() + " delete() :" + pk);
		return entity;
	}

	@Override
	public MedicineEntity findByName(String name) {
		logger.info("Begin : " + this.getClass().getName() + " findByMedicineName() :" + name);
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(MedicineEntity.class);
		criteria.add(Restrictions.eq("name", name));
		MedicineEntity entity = (MedicineEntity) criteria.uniqueResult();
		logger.info("End : " + this.getClass().getName() + " findByMedicineName() :" + name);
		return entity;
	}

	@Override
	public void update(MedicineEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " update() :" + entity.toString());
		Session session = entityManager.unwrap(Session.class);
		session.merge(entity);
		logger.info("End : " + this.getClass().getName() + " update() :" + entity.toString());
	}

	@Override
	public List<MedicineEntity> list() {
		return list(0, 0);
	}

	@Override
	public List<MedicineEntity> list(int pageNo, int pageSize) {
		logger.info("Begin : " + this.getClass().getName() + " list() :" );
		Session session = entityManager.unwrap(Session.class);
		Query<MedicineEntity> query = session.createQuery("from MedicineEntity", MedicineEntity.class);
		List<MedicineEntity> list = query.getResultList();
		logger.info("End : " + this.getClass().getName() + " list() :" );
		return list;
	}

	@Override
	public List<MedicineEntity> search(MedicineEntity entity) {
		return search(entity, 0, 0);
	}

	@Override
	public List<MedicineEntity> search(MedicineEntity entity, int pageNo, int pageSize) {
		logger.info("Begin : " + this.getClass().getName() + " search() :"+entity.toString());
		Session session = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer("from MedicineEntity as u where 1=1 ");
		if (entity != null) {
			if (entity.getId() > 0) {
				hql.append("and u.id = " + entity.getId());
			}
			if (entity.getName() != null && entity.getName().length() > 0) {
				hql.append("and u.name like '%" + entity.getName() + "%'");
			}
			if (entity.getCompanyName() != null && entity.getCompanyName().length() > 0) {
				hql.append("and u.companyName like '%" + entity.getCompanyName() + "%'");
			}

		}
		Query<MedicineEntity> query = session.createQuery(hql.toString(), MedicineEntity.class);
		if (pageNo > 0) {
			pageNo = (pageNo - 1) * pageSize;
			query.setFirstResult(pageNo);
			query.setMaxResults(pageSize);
		}
		List<MedicineEntity> list = query.getResultList();
		logger.info("End : " + this.getClass().getName() + " search() :"+entity.toString());
		return list;
	}


}
