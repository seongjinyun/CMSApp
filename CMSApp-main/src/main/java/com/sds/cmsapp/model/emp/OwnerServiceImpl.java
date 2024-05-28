package com.sds.cmsapp.model.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Owner;
import com.sds.cmsapp.exception.OwnerException;

@Service
public class OwnerServiceImpl implements OwnerService {

	@Autowired
	private OwnerDAO ownerDAO;
	
	@Override
	public void update(Owner owner) throws OwnerException {
		// TODO Auto-generated method stub
		ownerDAO.update(owner);
	}

}
