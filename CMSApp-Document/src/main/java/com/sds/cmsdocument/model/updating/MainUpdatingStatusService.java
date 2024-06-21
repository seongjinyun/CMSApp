package com.sds.cmsdocument.model.updating;

import com.sds.cmsdocument.domain.DocumentVersion;

public interface MainUpdatingStatusService {

	public void changeStatusOne(DocumentVersion documentVersion);

	public void changeStatusRejectedToDraft(DocumentVersion documentVersion);			
}
		
	
