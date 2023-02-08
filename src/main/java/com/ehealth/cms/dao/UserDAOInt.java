package com.ehealth.cms.dao;

import java.util.List;

import com.ehealth.cms.entity.UserEntity;

public interface UserDAOInt {

	public long add(UserEntity entity);
	
	public void delete(UserEntity entity);
	
	public UserEntity findBypk(long pk);
	
	public UserEntity findByLogin(String userName);
	
	public void update(UserEntity entity);
	
	public List<UserEntity> list();
	
	public List<UserEntity>list(int pageNo,int pageSize);
	
	public List<UserEntity> search(UserEntity entity);
	
	public List<UserEntity> search(UserEntity entity,int pageNo,int pageSize);
	
	public UserEntity authentication(UserEntity entity);
	
}
