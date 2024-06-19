package com.sds.cmsapp.model.document;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class DocumentEditingServiceImpl implements DocumentEditingService{
	
    private Set<Integer> editingDocuments = new HashSet<>();
	
    public boolean isDocumentBeingEdited(int documentIdx) {
        return editingDocuments.contains(documentIdx);
    }

    public void addEditingDocument(int documentIdx) {
        editingDocuments.add(documentIdx);
    }

    public void removeEditingDocument(int documentIdx) {
        editingDocuments.remove(documentIdx);
    }
}
