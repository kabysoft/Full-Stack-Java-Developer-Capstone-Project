package com.ehealth.cms.serviceImpl;

import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ehealth.cms.dao.UserDAOInt;
import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;
import com.ehealth.cms.util.EmailBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl  implements UserService {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDAOInt dao;
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Override
	@Transactional
	public long add(UserEntity entity) throws DuplicateRecordException, ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" add() :" +entity.toString());
		long pk=0;
		try {
		  pk = dao.add(entity);
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to create the user record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" add() :");
		return pk;
	}

	@Override
	@Transactional
	public void delete(UserEntity entity) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" delete() :" +entity.getId());
		try {
			 dao.delete(entity);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to Delete the user record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" delete() :" +entity.getId());
	}

	@Override
	@Transactional
	public UserEntity findBypk(long pk) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" findBypk() :" +pk);
		UserEntity entity=null;
		try {
			entity=dao.findBypk(pk);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the user by id record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" findBypk() :" +pk);
		return entity;
	}

	@Override
	@Transactional
	public UserEntity findByLogin(String login) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" findByLogin() :" +login);
		UserEntity entity=null;
		try {
			entity=dao.findByLogin(login);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the user by login record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" findByLogin() :" +login);
		return entity;
	}

	@Override
	@Transactional
	public void update(UserEntity entity) throws DuplicateRecordException, ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" update() :" +entity.toString());
		try {
		 dao.update(entity);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to user the user record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" update() :");
		
	}



	@Override
	@Transactional
	public UserEntity authentication(UserEntity entity) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" authentication() :" +entity.toString());
		UserEntity UserEntity=null;
		try {
			UserEntity=dao.authentication(entity);
		} catch (Exception e) {
				log.error(e.getMessage());
			throw new ApplicationException("not able to find  the user by login record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" authentication() :" +entity.toString());
		return UserEntity;
	}

	@Override
	@Transactional
	public List<UserEntity> list(int pageNo, int pageSize) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" list() :");
		List<UserEntity> list=null;
		try {
		 list=dao.list(pageNo, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to getAllUser the  record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" list() :");
		return list;
	}

	@Override
	@Transactional
	public List<UserEntity> search(UserEntity entity) throws ApplicationException {
		return search(entity, 0, 0);
	}

	@Override
	@Transactional
	public List<UserEntity> search(UserEntity entity, int pageNo, int pageSize) throws ApplicationException {
		logger.info("Begin : "+this.getClass().getName()+" search() :"+entity.toString());
		List<UserEntity> list=null;
		try {
		 list=dao.search(entity, pageNo, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("not able to getAllUser the  record into db : "+e.getMessage());
		}
		logger.info("End : "+this.getClass().getName()+" search() :"+entity.toString());
		return list;
	}

	@Override
	@Transactional
	public boolean changePassword(Long id, String oldPassword, String newPassword) {
		logger.info("Begin : "+this.getClass().getName()+" changePassword() :");
		UserEntity dtoExist;
		try {
			dtoExist = findBypk(id);
			if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
				dtoExist.setPassword(newPassword);
				dao.update(dtoExist);
				return true;
			} else {
				return false;
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("End : "+this.getClass().getName()+" changePassword() :");
		return false;
	}

	@Override
	@Transactional
	public boolean forgetPassword(String login) {
		logger.info("Begin : "+this.getClass().getName()+" forgetPassword() :");
		UserEntity dtoExist = dao.findByLogin(login);

		if (dtoExist != null) {

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("firstName", dtoExist.getName());
			map.put("login", dtoExist.getLogin());
			map.put("password", dtoExist.getPassword());

			String message = EmailBuilder.getForgetPasswordMessage(map);

			MimeMessage msg = mailSender.createMimeMessage();

			try {
				MimeMessageHelper helper = new MimeMessageHelper(msg);
				helper.setTo(dtoExist.getEmail());
				helper.setSubject("E-Health Care Forget Password ");
				helper.setText(message, true);
				mailSender.send(msg);
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
		logger.info("End : "+this.getClass().getName()+" forgetPassword() :");
		return false;
	}

	@Override
	@Transactional
	public long register(UserEntity entity) throws DuplicateRecordException, ApplicationException {
		return add(entity);
	}

	@Override
	@Transactional
	public List<UserEntity> list() throws ApplicationException {
		return list(0, 0);
	}

	
	
	

}
