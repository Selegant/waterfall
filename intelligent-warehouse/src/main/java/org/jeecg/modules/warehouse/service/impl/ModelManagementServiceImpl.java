package org.jeecg.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.jeecg.modules.datasources.util.DataTypeUtil;
import org.jeecg.modules.warehouse.mapper.WaterfallFolderMapper;
import org.jeecg.modules.warehouse.mapper.WaterfallModelFieldMapper;
import org.jeecg.modules.warehouse.mapper.WaterfallModelMapper;
import org.jeecg.modules.warehouse.mapper.WaterfallModelPartitionMapper;
import org.jeecg.modules.warehouse.model.WaterfallFolder;
import org.jeecg.modules.warehouse.model.WaterfallModel;
import org.jeecg.modules.warehouse.model.WaterfallModelField;
import org.jeecg.modules.warehouse.model.WaterfallModelPartition;
import org.jeecg.modules.warehouse.service.IModelManagementService;
import org.jeecg.modules.warehouse.util.DdlConvertUtil;
import org.jeecg.modules.warehouse.util.RegularUtil;
import org.jeecg.modules.warehouse.constant.DataSourceConstant;

import org.jeecg.modules.warehouse.dto.DataModuleDTO;
import org.jeecg.modules.warehouse.dto.ModelFolderDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ModelManagementServiceImpl implements IModelManagementService {

    private final int ZERO = 0;

    @Autowired
    private IDataSourceService dataSourceService;

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
        List<WaterfallFolder> parentList = list.stream().filter(w -> ZERO == w.getParentId()).collect(Collectors.toList());
        List<ModelFolderDTO> result = new ArrayList<>();
        parentList.forEach(p -> {
            result.add(new ModelFolderDTO(p, list.stream().filter(w -> w.getParentId().equals(p.getId())).collect(Collectors.toList())));
        });
        return result;
    }

    @Override
    public void saveFolder(WaterfallFolder waterfallFolder) {
        //??????
        if (waterfallFolder.getParentId() != ZERO && !exsitFolderById(waterfallFolder.getParentId())) {
            throw new JeecgBootException("?????????????????????");
        }
        if (exsitFolderByParentIdAndFolderNameExcludeId(waterfallFolder.getId(), waterfallFolder.getParentId(), waterfallFolder.getFolderName())) {
            throw new JeecgBootException("????????????????????????????????????");
        }
        checkParams(waterfallFolder);
        if (StringUtils.isEmpty(waterfallFolder.getRemark())) {
            waterfallFolder.setRemark("");
        }
        waterfallFolder.setDelFlag(false);
        waterfallFolder.setCreateTime(new Date());
        waterfallFolder.setUpdateTime(new Date());
        waterfallFolderMapper.insertSelective(waterfallFolder);
    }

    @Override
    public void updateFolderWithConditionById(WaterfallFolder folder, boolean isDel) {
        if (!isDel) {
            if (folder.getParentId() != ZERO && !exsitFolderById(folder.getParentId())) {
                throw new JeecgBootException("?????????????????????");
            }

            if (exsitFolderByParentIdAndFolderNameExcludeId(folder.getId(), folder.getParentId(), folder.getFolderName())) {
                throw new JeecgBootException("????????????????????????????????????");
            }
        }
        folder.setUpdateTime(new Date());
        waterfallFolderMapper.updateById(folder);
    }

    @Override
    public Boolean exsitFolderById(Integer id) {
        LambdaQueryWrapper<WaterfallFolder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WaterfallFolder::getId).eq(WaterfallFolder::getId, id)
                .eq(WaterfallFolder::getDelFlag, false);
        return waterfallFolderMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public Boolean exsitFolderByParentIdAndFolderNameExcludeId(Integer id, Integer parentId, String folderName) {
        LambdaQueryWrapper<WaterfallFolder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WaterfallFolder::getParentId, parentId)
                .eq(WaterfallFolder::getFolderName, folderName)
                .eq(WaterfallFolder::getDelFlag, false);
        List<WaterfallFolder> waterfallFolders = waterfallFolderMapper.selectList(queryWrapper);
        return waterfallFolders.size() > 1 || (id != null && waterfallFolders.size() == 1 && !id.equals(waterfallFolders.get(0).getId()));
    }

    @Override
    public Boolean exsitModelByFolderIdAndModelNameExcludeId(Integer id, Integer folderId, String modelName) {
        LambdaQueryWrapper<WaterfallModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WaterfallModel::getId).eq(WaterfallModel::getFolderId, folderId)
                .eq(WaterfallModel::getModelName, modelName)
                .eq(WaterfallModel::getDelFlag, false);
        List<WaterfallModel> waterfallModels = waterfallModelMapper.selectList(queryWrapper);
        return waterfallModels.size() > 1 || (id != null && waterfallModels.size() == 1 && !id.equals(waterfallModels.get(0).getId()));
    }

    @Override
    public Boolean exsitModelFieldByModelIdAndFieldName(Integer modelId, String fieldName) {
        LambdaQueryWrapper<WaterfallModelField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WaterfallModelField::getModelId, modelId)
                .eq(WaterfallModelField::getFieldName, fieldName)
                .eq(WaterfallModelField::getDelFlag, false);
        return waterfallModelFieldMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void saveDataModule(DataModuleDTO dataModuleDTO) {
        if (!RegularUtil.isSqlColunmName(dataModuleDTO.getModelName())) {
            throw new JeecgBootException("??????????????????sql?????????");
        }
        if (dataModuleDTO.getModelName().startsWith("_")) {
            throw new JeecgBootException("??????????????? '_' ???");
        }
        if (!exsitFolderById(dataModuleDTO.getFolderId())) {
            throw new JeecgBootException("????????????????????????");
        }
        //??????????????? folderId+modelName
        if (exsitModelByFolderIdAndModelNameExcludeId(dataModuleDTO.getId(), dataModuleDTO.getFolderId(), dataModuleDTO.getModelName().toLowerCase())) {
            throw new JeecgBootException("????????????????????????");
        }
        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setFolderId(dataModuleDTO.getFolderId());
        waterfallModel.setModelName(dataModuleDTO.getModelName().toLowerCase());
        waterfallModel.setModelStatusCode(DataModuleDTO.UN_PUBLISHED);
        waterfallModel.setModelStatusName(DataModuleDTO.UN_PUBLISHED_NAME);
        waterfallModel.setExportTypeCode(dataModuleDTO.getExportTypeCode());
        waterfallModel.setExportTypeName(dataModuleDTO.getExportTypeName());
        waterfallModel.setRemark(StringUtils.isEmpty(dataModuleDTO.getRemark()) ? "" : dataModuleDTO.getRemark());
        waterfallModel.setCreateTime(new Date());
        waterfallModel.setUpdateTime(new Date());
        waterfallModel.setDelFlag(false);
        waterfallModel.setPublishDb(ZERO);
        if (waterfallModelMapper.insertSelective(waterfallModel) > 0) {
            dataModuleDTO.setId(waterfallModel.getId());
            //????????????
            if (dataModuleDTO.getModelFields() != null) {
                saveModelField(dataModuleDTO.getModelFields(), waterfallModel.getId());
            }
            //????????????
            if (dataModuleDTO.getModelPartitions() != null) {
                savePartition(dataModuleDTO.getModelPartitions(), waterfallModel.getId());
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

        WaterfallModel model = waterfallModelMapper.selectByPrimaryKey(dataModuleDTO.getId());
        if (model == null || model.getDelFlag() == true) {
            throw new JeecgBootException("??????????????????");
        }
        if (model.getModelStatusCode().equals(DataModuleDTO.PUBLISHED)) {
            throw new JeecgBootException("????????????????????????");
        }
        if (!exsitFolderById(dataModuleDTO.getFolderId())) {
            throw new JeecgBootException("????????????????????????");
        }
        //??????????????? folderId+modelName
        if (exsitModelByFolderIdAndModelNameExcludeId(dataModuleDTO.getId(), dataModuleDTO.getFolderId(), dataModuleDTO.getModelName().toLowerCase())) {
            throw new JeecgBootException("????????????????????????");
        }

        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setId(dataModuleDTO.getId());
        waterfallModel.setModelName(dataModuleDTO.getModelName());
        waterfallModel.setRemark(dataModuleDTO.getRemark());
        waterfallModel.setUpdateTime(new Date());
        waterfallModelMapper.updateByPrimaryKeySelective(waterfallModel);
        if (!CollectionUtils.isEmpty(dataModuleDTO.getModelFields())) {
            waterfallModelFieldMapper.deleteByModelId(dataModuleDTO.getId());
            saveModelField(dataModuleDTO.getModelFields(), dataModuleDTO.getId());
        }

        if (!CollectionUtils.isEmpty(dataModuleDTO.getModelPartitions())) {
            waterfallModelPartitionMapper.deleteByModelId(dataModuleDTO.getId());
            savePartition(dataModuleDTO.getModelPartitions(), dataModuleDTO.getId());
        }
    }

    @Override
    public DataModuleDTO queryDataMoudle(Integer id) {
        WaterfallModel model = waterfallModelMapper.selectById(id);
        if (model == null) return null;

        List<WaterfallModelField> modelFields = queryDataMoudleFields(model.getId());

        List<WaterfallModelPartition> modulePartitions = waterfallModelPartitionMapper.selectList(
                new LambdaQueryWrapper<WaterfallModelPartition>().eq(WaterfallModelPartition::getModelId, model.getId())
                        .eq(WaterfallModelPartition::getDelFlag, false)
        );

        DataModuleDTO res = new DataModuleDTO();
        res.setId(model.getId());
        res.setFolderId(model.getFolderId());
        res.setModelName(model.getModelName());
        res.setModelStatusCode(model.getModelStatusCode());
        res.setModelStatusName(model.getModelStatusName());
        res.setModelFields(modelFields);
        res.setModelPartitions(modulePartitions);
        res.setRemark(model.getRemark());
        res.setPublishDb(model.getPublishDb());

        return res;
    }

    @Override
    public DataModuleDTO ddlToModel(DataModuleDTO dto) {
        DataModuleDTO res = null;
        if (DataSourceConstant.MYSQL.equals(dto.getDbType().toUpperCase())) {
            res = DdlConvertUtil.mysqlToModel(dto.getSql());
        } else if (DataSourceConstant.ORACLE.equals(dto.getDbType().toUpperCase())) {
            res = DdlConvertUtil.oracleToModel(dto.getSql());
        } else if (DataSourceConstant.HIVE.equals(dto.getDbType().toUpperCase())) {

        } else {
            throw new JeecgBootException("?????????????????????");
        }
        if (StringUtils.isNotBlank(dto.getModelName())) {
            res.setModelName(dto.getModelName());
        }
        res.setRemark(dto.getRemark());
        res.setFolderId(dto.getFolderId());
        res.setSql(dto.getSql());
        res.setDbType(dto.getDbType());
        return res;
    }

    @Override
    public void modelPublish(Integer modelId) {
        DataModuleDTO dataModuleDTO = queryDataMoudle(modelId);
        if (!dataModuleDTO.getModelStatusCode().equals(DataModuleDTO.UN_PUBLISHED)) {
            throw new JeecgBootException("??????????????????????????????");
        }
        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setId(dataModuleDTO.getId());
        waterfallModel.setModelStatusCode(DataModuleDTO.PUBLISHED);
        waterfallModel.setModelStatusName(DataModuleDTO.PUBLISHED_NAME);
        waterfallModel.setUpdateTime(new Date());
        waterfallModelMapper.updateByPrimaryKeySelective(waterfallModel);
    }

    @Override
    public DataModuleDTO tableOrViewToModel(Integer folderId, Integer source, String tableName) {
        DataModuleDTO dataModule = getModel(folderId, source, tableName);
        return dataModule;
    }

    @Override
    public void dbToModel(Integer folderId, Integer source) {
        dataSourceService.getTables(source, 0).stream().forEach(tableOrView -> {
            saveDataModule(getModel(folderId, source, tableOrView));
        });
    }

    @Override
    public Map<String, String> modelToDb(Integer dbId, List<Integer> modelIds) {
        //??????hive defaul???
        WaterfallDataSource dataSourceDefault = dataSourceService.getDefault();
        dbId = dataSourceDefault.getId();

        Map<String, String> errorMsg = new HashMap<>(modelIds.size());
        final Integer finalDbId = dbId;
        modelIds.stream().forEach(e -> {
            DataModuleDTO dataModuleDTO = queryDataMoudle(e);
            if (!dataModuleDTO.getModelStatusCode().equals(DataModuleDTO.PUBLISHED)) {
                errorMsg.put(dataModuleDTO.getModelName(), "????????????????????????????????????");
            } else {
                String modelName = dataModuleDTO.getModelName().startsWith("_") ? dataModuleDTO.getFolderId() + dataModuleDTO.getModelName() :
                        dataModuleDTO.getFolderId() + "_" + dataModuleDTO.getModelName();
                dataModuleDTO.setModelName(modelName);
                String createSql = DdlConvertUtil.modelToHiveDdl(dataModuleDTO);
                boolean success = dataSourceService.executeHiveSqlNoInterrupt(finalDbId, errorMsg, modelName, createSql);
                if (success) {
                    WaterfallModel model = new WaterfallModel();
                    model.setId(dataModuleDTO.getId());
                    model.setPublishDb(finalDbId);
                    model.setUpdateTime(new Date());
                    waterfallModelMapper.updateByPrimaryKeySelective(model);
                }
            }
        });

        return errorMsg;
    }

    @Override
    public void modelOff(Integer modelId) {
        DataModuleDTO dataModuleDTO = queryDataMoudle(modelId);
        if (dataModuleDTO == null || dataModuleDTO.getModelStatusCode() == null) return;

        if (!dataModuleDTO.getModelStatusCode().equals(DataModuleDTO.PUBLISHED)) {
            throw new JeecgBootException("?????????????????????????????????");
        }
        if (dataModuleDTO.getPublishDb() != null) {
            //?????????????????????
            String modelName = dataModuleDTO.getFolderId() + "_" + dataModuleDTO.getModelName();
            dataSourceService.executeHiveSql(dataModuleDTO.getPublishDb(), DdlConvertUtil.delSql(modelName));
        }
        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setId(dataModuleDTO.getId());
        waterfallModel.setModelStatusCode(DataModuleDTO.UN_PUBLISHED);
        waterfallModel.setModelStatusName(DataModuleDTO.UN_PUBLISHED_NAME);
        waterfallModel.setPublishDb(ZERO);
        waterfallModel.setUpdateTime(new Date());
        waterfallModelMapper.updateByPrimaryKeySelective(waterfallModel);
    }

    @Override
    public void deleteModelById(Integer id) {
        DataModuleDTO dataModuleDTO = queryDataMoudle(id);
        if (dataModuleDTO == null || dataModuleDTO.getModelStatusCode() == null) return;

        if (!dataModuleDTO.getModelStatusCode().equals(DataModuleDTO.UN_PUBLISHED)) {
            throw new JeecgBootException("????????????????????????");
        }
        WaterfallModel waterfallModel = new WaterfallModel();
        waterfallModel.setId(dataModuleDTO.getId());
        waterfallModel.setDelFlag(true);
        waterfallModel.setUpdateTime(new Date());
        waterfallModelMapper.updateByPrimaryKeySelective(waterfallModel);
    }

    @Override
    public List<WaterfallModelField> queryDataMoudleFields(Integer modelId) {

        return waterfallModelFieldMapper.selectList(
                new LambdaQueryWrapper<WaterfallModelField>().eq(WaterfallModelField::getModelId, modelId)
                        .eq(WaterfallModelField::getDelFlag, false)
        );
    }


    private DataModuleDTO getModel(Integer folderId, Integer source, String tableName) {
        List<TableColumnInfoDTO> tableColumns = dataSourceService.getTableColumns(new TableColumnInput(source, tableName));
        DataModuleDTO dataModule = new DataModuleDTO();
        dataModule.setModelName(tableName);
        dataModule.setFolderId(folderId);
        List<WaterfallModelField> modelFields = new ArrayList<>(tableColumns.size());
        tableColumns.stream().forEach(e -> {
            WaterfallModelField tmp = new WaterfallModelField();
            tmp.setFieldName(e.getColumnName().toLowerCase());
            if (e.getColumnSize() != null) {
                if (e.getDecimalDigits() == null || e.getDecimalDigits() == 0) {
                    tmp.setLength("" + e.getColumnSize());
                } else {
                    tmp.setLength(e.getColumnSize() + "," + e.getDecimalDigits());
                }
            }
            if ("NUMBER".equalsIgnoreCase(e.getColumnType()) && e.getColumnSize() != null) {
                e.setColumnType(e.getColumnType() + "(" + tmp.getLength() + ")");
            }
            tmp.setFieldTypeName(DataTypeUtil.parseDataTypeOne(dataSourceService.getDbType(source), e.getColumnType()));
            if (tmp.getFieldTypeName() == null) {
                log.error("?????????{}  ????????????{}", tableName, e.getColumnType());
            }
            tmp.setPrimarykeyFlag(e.getPrimaryKey() == null ? false : e.getPrimaryKey());
            tmp.setEmptyFlag(e.getNullable());
            tmp.setRemark(e.getRemarks());

            modelFields.add(tmp);
        });
        dataModule.setModelFields(modelFields);
        return dataModule;
    }

    private void checkParams(WaterfallFolder waterfallFolder) {
    }

    private void savePartition(List<WaterfallModelPartition> modelPartitions, Integer modelId) {
        modelPartitions.stream().forEach(e -> {
            WaterfallModelPartition modelPartition = new WaterfallModelPartition();
            modelPartition.setModelId(modelId);
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

    private void saveModelField(List<WaterfallModelField> modelFields, Integer modelId) {
        Set<String> fieldSet = new HashSet<>();
        modelFields.stream().forEach(e -> {
            if (!RegularUtil.isSqlColunmName(e.getFieldName())) {
                throw new JeecgBootException("????????????????????????sql?????????");
            }
            fieldSet.add(e.getFieldName());
        });
        if (fieldSet.size() != modelFields.size()) {
            throw new JeecgBootException("??????????????????");
        }
        modelFields.stream().forEach(e -> {
            WaterfallModelField modelField = new WaterfallModelField();
            modelField.setModelId(modelId);
            modelField.setFieldName(e.getFieldName().toLowerCase());
            modelField.setFieldTypeName(e.getFieldTypeName());
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


    /**
     * WaterfallModel????????????
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
        if (waterfallModel.getFolderId() != null && ZERO != waterfallModel.getFolderId()) {
            queryWrapper.eq(WaterfallModel::getFolderId, waterfallModel.getFolderId());
        }
        if (StringUtils.isNotBlank(waterfallModel.getModelName())) {
            queryWrapper.like(WaterfallModel::getModelName, waterfallModel.getModelName());
        }
        if (waterfallModel.getModelStatusCode() != null) {
            queryWrapper.eq(WaterfallModel::getModelStatusCode, waterfallModel.getModelStatusCode());
        }
        if (waterfallModel.getDelFlag() != null) {
            queryWrapper.eq(WaterfallModel::getDelFlag, waterfallModel.getDelFlag());
        }
        return queryWrapper;
    }


}
