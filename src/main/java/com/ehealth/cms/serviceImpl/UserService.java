package com.ehealth.cms.serviceImpl;

import java.util.List;

import com.ehealth.cms.entity.UserEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;



public interface UserService {

	public long add(UserEntity entity) throws DuplicateRecordException, ApplicationException;

	public void delete(UserEntity entity) throws ApplicationException;

	public UserEntity findBypk(long pk) throws ApplicationException;

	public UserEntity findByLogin(String userName) throws ApplicationException;

	public void update(UserEntity entity) throws DuplicateRecordException, ApplicationException;

	public List<UserEntity> list() throws ApplicationException;

	public List<UserEntity> list(int pageNo, int pageSize) throws ApplicationException;

	public List<UserEntity> search(UserEntity entity) throws ApplicationException;

	public List<UserEntity> search(UserEntity entity, int pageNo, int pageSize) throws ApplicationException;

	public UserEntity authentication(UserEntity entity) throws ApplicationException;

	public boolean changePassword(Long id, String oldPassword, String newPassword);

	public boolean forgetPassword(String login);

	public long register(UserEntity entity) throws DuplicateRecordException, ApplicationException;

}
