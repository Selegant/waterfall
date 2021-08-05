package org.jeecg.modules.datasources.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.dto.ModelFolderDTO;
import org.jeecg.modules.datasources.model.WaterfallFolder;
import org.jeecg.modules.datasources.model.WaterfallModel;

import java.util.List;

public interface IModelManagementService {

    IPage<WaterfallFolder> queryListWithParentId(Integer parentId, int pageSize, int pageNo);

    List<WaterfallFolder> queryListWithParentId(Integer parentId);

    List<ModelFolderDTO> queryList();

    void saveFolder(WaterfallFolder waterfallFolder);

    void updateFolderWithConditionById(WaterfallFolder delFolder);

    Boolean exsitFolderById(Integer id);

    Boolean exsitFolderByParentIdAndFolderName(Integer parentId, String folderName);

    Boolean exsitModelByFolderIdAndModelName(Integer folderId, String modelName);

    Boolean exsitModelFieldByModelIdAndFieldName(Integer modelId, String fieldName);

    void saveDataModule(DataModuleDTO dataModuleDTO);

    IPage<WaterfallModel> queryDataModulePage(Page<WaterfallModel> page, WaterfallModel waterfallModel);

    void updateModuleWithConditionById(DataModuleDTO dataModuleDTO);

    DataModuleDTO queryDataMoudle(Integer id);

    DataModuleDTO ddlToModel(DataModuleDTO dto);

    void modelPublish(Integer dbId, Integer modelId);
}
