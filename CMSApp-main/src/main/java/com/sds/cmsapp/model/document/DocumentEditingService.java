package com.sds.cmsapp.model.document;

public interface DocumentEditingService {
    public boolean isDocumentBeingEdited(int documentIdx);


    public void addEditingDocument(int documentIdx);
    

    public void removeEditingDocument(int documentIdx);
    
}
