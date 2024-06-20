package com.sds.cmsdocument.model.updating;

import com.sds.cmsdocument.domain.Document;
import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.MasterCode;

public interface MainUpdatingStatusService {

		public void changeStatus(Document document, Emp emp, MasterCode masterCode, String comments);
			
}
		
	
