package org.jeecg.modules.datasources.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.dto.ModelFolderDTO;
import org.jeecg.modules.datasources.mapper.WaterfallFolderMapper;
import org.jeecg.modules.datasources.mapper.WaterfallModelFieldMapper;
import org.jeecg.modules.datasources.mapper.WaterfallModelMapper;
import org.jeecg.modules.datasources.mapper.WaterfallModelPartitionMapper;
import org.jeecg.modules.datasources.model.WaterfallFolder;
import org.jeecg.modules.datasources.model.WaterfallModel;
import org.jeecg.modules.datasources.model.WaterfallModelField;
import org.jeecg.modules.datasources.model.WaterfallModelPartition;
import org.jeecg.modules.datasources.service.IModelManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ModelManagementServiceImpl implements IModelManagementService {

    private final int ZERO = 0;

    @Autowired
    private WaterfallFolderMapper waterfallFolderMapper;

    @Autowired
    private WaterfallModelMapper waterfallModelMapper;

    @Autowired
    private WaterfallModelFieldMapper waterfallModelFieldMapper;

    @Autowired
    private WaterfallModelPartitionMapper waterfallModelPartitionMapper;

    @Override
    public IPage<WaterfallFolder> queryListWithParentId(Integer parentId, int pageSize, int pageNo) {
        return null;
    }

    @Override
    public List<WaterfallFolder> queryListWithParentId(Integer parentId) {
        LambdaQueryWrapper<WaterfallFolder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WaterfallFolder::getId, WaterfallFolder::getParentId, WaterfallFolder::getFolderName, WaterfallFolder::getFolderType,
                WaterfallFolder::getMark, WaterfallFolder::getRemark)
                .eq(WaterfallFolder::getParentId, parentId)
                .eq(WaterfallFolder::getDelFlag, false);
        return waterfallFolderMapper.selectList(queryWrapper);
    }

    @Override
    public List<ModelFolderDTO> queryList() {
        LambdaQueryWrapper<WaterfallFolder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WaterfallFolder::getId, WaterfallFolder::getParentId, WaterfallFolder::getFolderName, WaterfallFolder::getFolderType,
                WaterfallFolder::getMark, WaterfallFolder::getRemark)
                .eq(WaterfallFolder::getDelFlag, false);
        List<WaterfallFolder> list = waterfallFolderMapper.selectList(queryWrapper);
        List<WaterfallFolder> parentList = list.stream().filter(w->ZERO==w.getParentId()).collect(Collectors.toList());
        List<ModelFolderDTO> result = new ArrayList<>();
        parentList.forEach(p->{
            result.add(new ModelFolderDTO(p,list.stream().filter(w->w.getParentId().equals(p.getId())).collect(Collectors.toList())));
        });
        return result;
    }

    @Override
    public void saveFolder(WaterfallFolder waterfallFolder) {
        checkParams(waterfallFolder);
        if (StringUtils.isEmpty(waterfallFolder.getRemark())) {
            waterfallFolder.setRemark("");
        }
        waterfallFolder.setCreateTime(new Date());
        waterfallFolder.setUpdateTime(new Date());
        waterfallFolderMapper.insertSelective(waterfallFolder);
    }

    @Override
    public void updateFolderWithConditionById(WaterfallFolder folder) {
        folder.setUpdateTime(new Date());
        waterfallFolderMapper.updateById(folder);
    }

    @Override
    public Boolean exsitById(Integer id) {
        LambdaQueryWrapper<WaterfallFolder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WaterfallFolder::getId, id);
        return waterfallFolderMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void saveDataModule(DataModuleDTO dataModuleDTO) {
        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setFolderId(dataModuleDTO.getFolderId());
        waterfallModel.setModelName(dataModuleDTO.getModelName());
        waterfallModel.setModelTypeCode(dataModuleDTO.getModelTypeCode());
        waterfallModel.setModelStatusCode(dataModuleDTO.getModelStatusCode());
        waterfallModel.setModelStatusName(dataModuleDTO.getModelStatusName());
        waterfallModel.setExportTypeCode(dataModuleDTO.getExportTypeCode());
        waterfallModel.setExportTypeName(dataModuleDTO.getExportTypeName());
        waterfallModel.setRamark(StringUtils.isEmpty(dataModuleDTO.getRamark()) ? "" : dataModuleDTO.getRamark());
        waterfallModel.setCreateTime(new Date());
        waterfallModel.setUpdateTime(new Date());
        waterfallModel.setDelFlag(false);
        if (waterfallModelMapper.insertSelective(waterfallModel) > 0) {
            //字段信息
            if (dataModuleDTO.getModelFields() != null) {
                dataModuleDTO.getModelFields().stream().forEach(e -> {
                    WaterfallModelField modelField = new WaterfallModelField();
                    modelField.setModelId(waterfallModel.getId());
                    modelField.setFieldName(e.getFieldName());
                    modelField.setFieldTypeId(e.getFieldTypeId());
                    modelField.setMetadataId(e.getMetadataId());
                    modelField.setPrimarykeyFlag(e.getPrimarykeyFlag());
                    modelField.setEmptyFlag(e.getEmptyFlag());
                    modelField.setLength(e.getLength());
                    modelField.setRemark(StringUtils.isEmpty(e.getRemark()) ? "" : e.getRemark());
                    modelField.setFieldSort(e.getFieldSort());

                    modelField.setCreateTime(new Date());
                    modelField.setUpdateTime(new Date());
                    modelField.setDelFlag(false);
                    waterfallModelFieldMapper.insertSelective(modelField);
                });
            }
            //分区字段
            if (dataModuleDTO.getModelPartitions() != null) {
                dataModuleDTO.getModelPartitions().stream().forEach(e -> {
                    WaterfallModelPartition modelPartition = new WaterfallModelPartition();
                    modelPartition.setModelId(waterfallModel.getId());
                    modelPartition.setPartitionName(e.getPartitionName());
                    modelPartition.setPartitionTypeId(e.getPartitionTypeId());
                    modelPartition.setMetadataId(e.getMetadataId());
                    modelPartition.setRemark(StringUtils.isEmpty(e.getRemark()) ? "" : e.getRemark());
                    modelPartition.setCreateTime(new Date());
                    modelPartition.setUpdateTime(new Date());
                    modelPartition.setDelFlag(false);
                    waterfallModelPartitionMapper.insertSelective(modelPartition);
                });
            }
        }
    }

    @Override
    public IPage<WaterfallModel> queryDataModulePage(Page<WaterfallModel> page, WaterfallModel waterfallModel) {
        LambdaQueryWrapper<WaterfallModel> queryWrapper = modelQueryCondition(waterfallModel);
        return waterfallModelMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void updateModuleWithConditionById(DataModuleDTO dataModuleDTO) {

        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setId(dataModuleDTO.getId());
        waterfallModel.setFolderId(dataModuleDTO.getFolderId());
        waterfallModel.setModelName(dataModuleDTO.getModelName());
        waterfallModel.setModelTypeCode(dataModuleDTO.getModelTypeCode());
        waterfallModel.setModelStatusCode(dataModuleDTO.getModelStatusCode());
        waterfallModel.setModelStatusName(dataModuleDTO.getModelStatusName());
        waterfallModel.setExportTypeCode(dataModuleDTO.getExportTypeCode());
        waterfallModel.setExportTypeName(dataModuleDTO.getExportTypeName());
        waterfallModel.setRamark(dataModuleDTO.getRamark());
        waterfallModel.setDelFlag(dataModuleDTO.getDelFlag());
        waterfallModel.setUpdateTime(new Date());
        waterfallModelMapper.updateById(waterfallModel);
        if (!CollectionUtils.isEmpty(dataModuleDTO.getModelFields())) {
            dataModuleDTO.getModelFields().stream().forEach(e -> {
                e.setUpdateTime(new Date());
                waterfallModelFieldMapper.updateById(e);
            });
        }

        if (!CollectionUtils.isEmpty(dataModuleDTO.getModelPartitions())) {
            dataModuleDTO.getModelPartitions().stream().forEach(e -> {
                e.setUpdateTime(new Date());
                waterfallModelPartitionMapper.updateById(e);
            });
        }
    }

    @Override
    public DataModuleDTO queryDataMoudle(Integer id) {
        WaterfallModel model = waterfallModelMapper.selectById(id);

        List<WaterfallModelField> modelFields = waterfallModelFieldMapper.selectList(
                new LambdaQueryWrapper<WaterfallModelField>().eq(WaterfallModelField::getModelId, model.getId())
                        .eq(WaterfallModelField::getDelFlag, false)
        );

        List<WaterfallModelPartition> modulePartitions = waterfallModelPartitionMapper.selectList(
                new LambdaQueryWrapper<WaterfallModelPartition>().eq(WaterfallModelPartition::getModelId, model.getId())
                        .eq(WaterfallModelPartition::getDelFlag, false)
        );

        DataModuleDTO res = new DataModuleDTO();
        res.setId(model.getId());
        res.setFolderId(model.getFolderId());
        res.setModelName(model.getModelName());
        res.setModelTypeCode(model.getModelTypeCode());
        res.setModelStatusCode(model.getModelStatusCode());
        res.setModelStatusName(model.getModelStatusName());
        res.setModelFields(modelFields);
        res.setModelPartitions(modulePartitions);

        return res;
    }


    private void checkParams(WaterfallFolder waterfallFolder) {
    }


    /**
     * WaterfallModel查询条件
     *
     * @param waterfallModel
     * @return
     */
    private LambdaQueryWrapper<WaterfallModel> modelQueryCondition(WaterfallModel waterfallModel) {
        LambdaQueryWrapper<WaterfallModel> queryWrapper = new LambdaQueryWrapper<>();
        if (waterfallModel == null) {
            return queryWrapper;
        }
        if (waterfallModel.getId() != null) {
            queryWrapper.eq(WaterfallModel::getId, waterfallModel.getId());
        }
        if (waterfallModel.getFolderId() != null) {
            queryWrapper.eq(WaterfallModel::getFolderId, waterfallModel.getFolderId());
        }
        if (StringUtils.isNotBlank(waterfallModel.getModelName())) {
            queryWrapper.like(WaterfallModel::getModelName, waterfallModel.getModelName());
        }
        if (waterfallModel.getDelFlag() != null) {
            queryWrapper.eq(WaterfallModel::getDelFlag, waterfallModel.getDelFlag());
        }
        return queryWrapper;
    }


}
