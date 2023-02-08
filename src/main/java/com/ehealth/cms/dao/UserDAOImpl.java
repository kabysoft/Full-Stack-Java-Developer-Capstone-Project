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

import com.ehealth.cms.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserDAOImpl implements UserDAOInt {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityManager entityManager;

	@Override
	public long add(UserEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " add() :" + entity.toString());
		Session session = entityManager.unwrap(Session.class);
		long pk = (long) session.save(entity);
		logger.info("end : " + this.getClass().getName() + " add() :" + entity.toString());
		return pk;
	}

	@Override
	public void delete(UserEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " delete() :" + entity.toString());
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		logger.info("End : " + this.getClass().getName() + " delete() :" + entity.toString());

	}

	@Override
	public UserEntity findBypk(long pk) {
		logger.info("Begin : " + this.getClass().getName() + " delete() :" + pk);
		Session session = entityManager.unwrap(Session.class);
		UserEntity entity = (UserEntity) session.get(UserEntity.class, pk);
		logger.info("End : " + this.getClass().getName() + " delete() :" + pk);
		return entity;
	}

	@Override
	public UserEntity findByLogin(String login) {
		logger.info("Begin : " + this.getClass().getName() + " findByUserName() :" + login);
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("login", login));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		logger.info("End : " + this.getClass().getName() + " findByUserName() :" + login);
		return entity;
	}

	@Override
	public void update(UserEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " update() :" + entity.toString());
		Session session = entityManager.unwrap(Session.class);
		session.merge(entity);
		logger.info("End : " + this.getClass().getName() + " update() :" + entity.toString());
	}

	@Override
	public List<UserEntity> list() {
		return list(0, 0);
	}

	@Override
	public List<UserEntity> list(int pageNo, int pageSize) {
		logger.info("Begin : " + this.getClass().getName() + " list() :" );
		Session session = entityManager.unwrap(Session.class);
		Query<UserEntity> query = session.createQuery("from UserEntity", UserEntity.class);
		List<UserEntity> list = query.getResultList();
		logger.info("End : " + this.getClass().getName() + " list() :" );
		return list;
	}

	@Override
	public List<UserEntity> search(UserEntity entity) {
		return search(entity, 0, 0);
	}

	@Override
	public List<UserEntity> search(UserEntity entity, int pageNo, int pageSize) {
		logger.info("Begin : " + this.getClass().getName() + " search() :"+entity.toString());
		Session session = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer("from UserEntity as u where 1=1 ");
		if (entity != null) {
			if (entity.getId() > 0) {
				hql.append("and u.id = " + entity.getId());
			}
			if (entity.getName() != null && entity.getName().length() > 0) {
				hql.append("and u.name like '%" + entity.getName() + "%'");
			}
			
			if (entity.getRoleName() != null && entity.getRoleName().length() > 0) {
				hql.append("and u.roleName like '%" + entity.getRoleName() + "%'");
			}
			if (entity.getEmail() != null && entity.getEmail().length() > 0) {
				hql.append("and u.email like '%" + entity.getEmail() + "%'");
			}

		}
		Query<UserEntity> query = session.createQuery(hql.toString(), UserEntity.class);
		if (pageNo > 0) {
			pageNo = (pageNo - 1) * pageSize;
			query.setFirstResult(pageNo);
			query.setMaxResults(pageSize);
		}
		List<UserEntity> list = query.getResultList();
		logger.info("End : " + this.getClass().getName() + " search() :"+entity.toString());
		return list;
	}

	@Override
	public UserEntity authentication(UserEntity entity) {
		logger.info("Begin : " + this.getClass().getName() + " authentication() :"+entity.toString());
		Session session = entityManager.unwrap(Session.class);
		Query<UserEntity> query = session.createQuery("from UserEntity where login=:login and password=:password",
				UserEntity.class);
		query.setParameter("login", entity.getLogin());
		query.setParameter("password", entity.getPassword());
		entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
		}
		logger.info("End : " + this.getClass().getName() + " authentication() :"+entity.toString());
		return entity;
	}
}
