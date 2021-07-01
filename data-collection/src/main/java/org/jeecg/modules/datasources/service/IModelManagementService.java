package org.jeecg.modules.datasources.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.model.WaterfallFolder;
import org.jeecg.modules.datasources.model.WaterfallModel;

import java.util.List;

public interface IModelManagementService {

    IPage<WaterfallFolder> queryListWithParentId(Integer parentId, int pageSize, int pageNo);

    List<WaterfallFolder> queryListWithParentId(Integer parentId);

    void saveFolder(WaterfallFolder waterfallFolder);

    void updateFolderWithConditionById(WaterfallFolder delFolder);

    Boolean exsitById(Integer id);

    void saveDataModule(DataModuleDTO dataModuleDTO);

    IPage<WaterfallModel> queryDataModulePage(Page<WaterfallModel> page, WaterfallModel waterfallModel);

    void updateModuleWithConditionById(DataModuleDTO dataModuleDTO);

    DataModuleDTO queryDataMoudle(Integer id);
}
