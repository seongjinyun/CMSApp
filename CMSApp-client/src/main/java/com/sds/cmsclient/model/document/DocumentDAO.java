package com.sds.cmsclient.model.document;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsclient.domain.Folder;

@Mapper
public interface DocumentDAO {
    public List<Folder> folderAllSelect(int projectIdx);
    public Integer subFolderSelect(int folderIdx);
}
