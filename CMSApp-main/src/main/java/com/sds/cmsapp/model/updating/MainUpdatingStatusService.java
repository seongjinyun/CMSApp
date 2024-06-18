package com.sds.cmsapp.model.updating;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.MasterCode;

public interface MainUpdatingStatusService {

		public void changeStatus(Document document, Emp emp, MasterCode masterCode, String comments);
			
}
		
	
