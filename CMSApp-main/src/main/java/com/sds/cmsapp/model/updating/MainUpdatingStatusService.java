package com.sds.cmsapp.model.updating;

import com.sds.cmsapp.domain.DocumentVersion;

public interface MainUpdatingStatusService {

		public void changeStatusOne(DocumentVersion documentVersion);
		
		public void changeStatusRejectedToDraft(DocumentVersion documentVersion);
			
}
		
	
