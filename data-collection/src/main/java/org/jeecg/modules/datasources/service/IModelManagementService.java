package org.jeecg.modules.datasources.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.dto.ModelFolderDTO;
import org.jeecg.modules.datasources.model.WaterfallFolder;
import org.jeecg.modules.datasources.model.WaterfallModel;

import java.util.List;
import java.util.Map;

public interface IModelManagementService {

    IPage<WaterfallFolder> queryListWithParentId(Integer parentId, int pageSize, int pageNo);

    List<WaterfallFolder> queryListWithParentId(Integer parentId);

    List<ModelFolderDTO> queryList();

    void saveFolder(WaterfallFolder waterfallFolder);

    void updateFolderWithConditionById(WaterfallFolder folder, boolean isDel);

    Boolean exsitFolderById(Integer id);

    Boolean exsitFolderByParentIdAndFolderNameExcludeId(Integer id, Integer parentId, String folderName);

    Boolean exsitModelByFolderIdAndModelNameExcludeId(Integer id, Integer folderId, String modelName);

    Boolean exsitModelFieldByModelIdAndFieldName(Integer modelId, String fieldName);

    void saveDataModule(DataModuleDTO dataModuleDTO);

    IPage<WaterfallModel> queryDataModulePage(Page<WaterfallModel> page, WaterfallModel waterfallModel);

    void updateModuleWithConditionById(DataModuleDTO dataModuleDTO);

    DataModuleDTO queryDataMoudle(Integer id);

    DataModuleDTO ddlToModel(DataModuleDTO dto);

    void modelPublish(Integer modelId);

    DataModuleDTO tableOrViewToModel(Integer folderId, Integer source, String tableName);

    void dbToModel(Integer folderId, Integer source);

    Map<String, String> modelToDb(Integer dbId, List<Integer> modelIds);

    void modelOff(Integer modelId);

    void deleteModelById(Integer id);
}
