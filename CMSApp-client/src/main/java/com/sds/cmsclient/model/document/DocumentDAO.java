package com.sds.cmsclient.model.document;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsclient.domain.Folder;

@Mapper
public interface DocumentDAO {
	//public List folderAllSelect(Map map);
    public List<Folder> topFolderSelect(int projectIdx);
//    public int subFolderSelect(int folderIdx);
}
