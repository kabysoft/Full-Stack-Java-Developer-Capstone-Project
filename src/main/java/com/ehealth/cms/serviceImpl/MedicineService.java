package com.ehealth.cms.serviceImpl;

import java.util.List;

import com.ehealth.cms.entity.MedicineEntity;
import com.ehealth.cms.exception.ApplicationException;
import com.ehealth.cms.exception.DuplicateRecordException;



public interface MedicineService {

	public long add(MedicineEntity entity) throws DuplicateRecordException, ApplicationException;

	public void delete(MedicineEntity entity) throws ApplicationException;

	public MedicineEntity findBypk(long pk) throws ApplicationException;

	public MedicineEntity findByName(String name) throws ApplicationException;

	public void update(MedicineEntity entity) throws DuplicateRecordException, ApplicationException;

	public List<MedicineEntity> list() throws ApplicationException;

	public List<MedicineEntity> list(int pageNo, int pageSize) throws ApplicationException;

	public List<MedicineEntity> search(MedicineEntity entity) throws ApplicationException;

	public List<MedicineEntity> search(MedicineEntity entity, int pageNo, int pageSize) throws ApplicationException;


}
