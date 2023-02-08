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

import com.ehealth.cms.entity.CartEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CartDAOImpl implements CartDAOInt {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityManager entityManager;

	@Override
	public long add(CartEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " add() :" + entity.toString());
		Session session = entityManager.unwrap(Session.class);
		long pk = (long) session.save(entity);
		logger.info("end : " + this.getClass().getName() + " add() :" + entity.toString());
		return pk;
	}

	@Override
	public void delete(CartEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " delete() :" + entity.toString());
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		logger.info("End : " + this.getClass().getName() + " delete() :" + entity.toString());

	}

	@Override
	public CartEntity findBypk(long pk) {
		logger.info("Begin : " + this.getClass().getName() + " delete() :" + pk);
		Session session = entityManager.unwrap(Session.class);
		CartEntity entity = (CartEntity) session.get(CartEntity.class, pk);
		logger.info("End : " + this.getClass().getName() + " delete() :" + pk);
		return entity;
	}

	@Override
	public CartEntity findByName(String name) {
		logger.info("Begin : " + this.getClass().getName() + " findByCartName() :" + name);
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(CartEntity.class);
		criteria.add(Restrictions.eq("name", name));
		CartEntity entity = (CartEntity) criteria.uniqueResult();
		logger.info("End : " + this.getClass().getName() + " findByCartName() :" + name);
		return entity;
	}

	@Override
	public void update(CartEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " update() :" + entity.toString());
		Session session = entityManager.unwrap(Session.class);
		session.merge(entity);
		logger.info("End : " + this.getClass().getName() + " update() :" + entity.toString());
	}

	@Override
	public List<CartEntity> list() {
		return list(0, 0);
	}

	@Override
	public List<CartEntity> list(int pageNo, int pageSize) {
		logger.info("Begin : " + this.getClass().getName() + " list() :" );
		Session session = entityManager.unwrap(Session.class);
		Query<CartEntity> query = session.createQuery("from CartEntity", CartEntity.class);
		List<CartEntity> list = query.getResultList();
		logger.info("End : " + this.getClass().getName() + " list() :" );
		return list;
	}

	@Override
	public List<CartEntity> search(CartEntity entity) {
		return search(entity, 0, 0);
	}

	@Override
	public List<CartEntity> search(CartEntity entity, int pageNo, int pageSize) {
		logger.info("Begin : " + this.getClass().getName() + " search() :"+entity.toString());
		Session session = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer("from CartEntity as u where 1=1 ");
		if (entity != null) {
			if (entity.getId() > 0) {
				hql.append("and u.id = " + entity.getId());
			}
			
			if (entity.getUserId() > 0) {
				hql.append("and u.userId = " + entity.getUserId());
			}
			
			if (entity.getMedicineId() > 0) {
				hql.append("and u.medicineId = " + entity.getMedicineId());
			}
			
			/*
			 * if (entity.getName() != null && entity.getName().length() > 0) {
			 * hql.append("and u.name like '%" + entity.getName() + "%'"); } if
			 * (entity.getCompanyName() != null && entity.getCompanyName().length() > 0) {
			 * hql.append("and u.companyName like '%" + entity.getCompanyName() + "%'"); }
			 */

		}
		Query<CartEntity> query = session.createQuery(hql.toString(), CartEntity.class);
		if (pageNo > 0) {
			pageNo = (pageNo - 1) * pageSize;
			query.setFirstResult(pageNo);
			query.setMaxResults(pageSize);
		}
		List<CartEntity> list = query.getResultList();
		logger.info("End : " + this.getClass().getName() + " search() :"+entity.toString());
		return list;
	}


}
