package com.ehealth.cms.dao;

import java.util.List;

import com.ehealth.cms.entity.MedicineEntity;

public interface MedicineDAOInt {

	public long add(MedicineEntity entity);
	
	public void delete(MedicineEntity entity);
	
	public MedicineEntity findBypk(long pk);
	
	public MedicineEntity findByName(String name);
	
	public void update(MedicineEntity entity);
	
	public List<MedicineEntity> list();
	
	public List<MedicineEntity>list(int pageNo,int pageSize);
	
	public List<MedicineEntity> search(MedicineEntity entity);
	
	public List<MedicineEntity> search(MedicineEntity entity,int pageNo,int pageSize);
	
	
}
