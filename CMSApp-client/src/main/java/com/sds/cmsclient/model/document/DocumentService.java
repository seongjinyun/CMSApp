package com.sds.cmsclient.model.document;

import java.util.List;

import com.sds.cmsclient.domain.Folder;

public interface DocumentService {
    public List<Folder> topFolderSelect(int projectIdx);
//    public int subFolderSelect(int folderIdx);
}
