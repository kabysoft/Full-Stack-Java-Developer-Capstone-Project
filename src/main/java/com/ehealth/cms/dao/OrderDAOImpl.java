package com.ehealth.cms.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ehealth.cms.entity.OrderEntity;
import com.ehealth.cms.util.DataUtility;



@Repository
public class OrderDAOImpl implements OrderDAOInt {

	private static Logger log = Logger.getLogger(OrderDAOImpl.class.getName());

	@Autowired
	private EntityManager entityManager;

	@Override
	public long add(OrderEntity entity) {
		log.info("OrderDAOImpl Add method Start");
		Session session = entityManager.unwrap(Session.class);
		long pk = (long) session.save(entity);
		log.info("OrderDAOImpl Add method End");
		return pk;
	}

	@Override
	public void delete(OrderEntity entity) {
		log.info("OrderDAOImpl Delete method Start");
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		log.info("OrderDAOImpl Delete method End");

	}

	@Override
	public OrderEntity findBypk(long pk) {
		log.info("OrderDAOImpl FindByPk method Start");
		Session session = entityManager.unwrap(Session.class);
		OrderEntity entity = (OrderEntity) session.get(OrderEntity.class, pk);
		log.info("OrderDAOImpl FindByPk method End");
		return entity;
	}

	@Override
	public OrderEntity findByName(String name) {
		log.info("OrderDAOImpl FindByLogin method Start");
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(OrderEntity.class);
		criteria.add(Restrictions.eq("name", name));
		OrderEntity entity = (OrderEntity) criteria.uniqueResult();
		log.info("OrderDAOImpl FindByLogin method End");
		return entity;
	}

	@Override
	public void update(OrderEntity entity) {
		log.info("OrderDAOImpl Update method Start");
		Session session = entityManager.unwrap(Session.class);
		session.merge(entity);
		log.info("OrderDAOImpl update method End");
	}

	@Override
	public List<OrderEntity> list() {
		return list(0, 0);
	}

	@Override
	public List<OrderEntity> list(int pageNo, int pageSize) {
		log.info("OrderDAOImpl List method Start");
		Session session = entityManager.unwrap(Session.class);
		Query<OrderEntity> query = session.createQuery("from OrderEntity", OrderEntity.class);
		List<OrderEntity> list = query.getResultList();
		log.info("OrderDAOImpl List method End");
		return list;
	}

	@Override
	public List<OrderEntity> search(OrderEntity entity) {
		return search(entity, 0, 0);
	}

	@Override
	public List<OrderEntity> search(OrderEntity entity, int pageNo, int pageSize) {
		log.info("OrderDAOImpl Search method Start");
		Session session = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer("from OrderEntity as u where 1=1 ");
		if (entity != null) {
			if (entity.getId() > 0) {
				hql.append("and u.id = " + entity.getId());
			}
			if (entity.getOrderid() > 0) {
				hql.append("and u.orderid = " + entity.getOrderid());
			}
			
			if (entity.getUserId() > 0) {
				hql.append("and u.userId = " + entity.getUserId());
			}
			if (entity.getMonth() != null && entity.getMonth().length() > 0) {
				hql.append("and u.month like '%" + entity.getMonth() + "%'");
			}
			if (entity.getYear() != null && entity.getYear().length() > 0) {
				hql.append("and u.year like '%" + entity.getYear() + "%'");
			}
			
			if (entity.getStartDate() != null && entity.getEndDate()!=null) {
				hql.append("and  u.orderDate between '"+DataUtility.getDateString1(entity.getStartDate())+"' and '"+DataUtility.getDateString1(entity.getEndDate())+"'");
			}
			

		}
		Query<OrderEntity> query = session.createQuery(hql.toString(), OrderEntity.class);
		if (pageNo > 0) {
			pageNo = (pageNo - 1) * pageSize;
			query.setFirstResult(pageNo);
			query.setMaxResults(pageSize);
		}
		List<OrderEntity> list = query.getResultList();
		log.info("OrderDAOImpl Search method End");
		return list;
	}

	

}
