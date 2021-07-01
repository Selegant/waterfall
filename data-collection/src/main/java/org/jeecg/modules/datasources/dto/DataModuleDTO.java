package org.jeecg.modules.datasources.dto;

import lombok.Data;
import org.jeecg.modules.datasources.model.WaterfallModel;
import org.jeecg.modules.datasources.model.WaterfallModelField;
import org.jeecg.modules.datasources.model.WaterfallModelPartition;

import java.util.Date;
import java.util.List;

/**
 * @Author liansongye
 * @create 2021/6/28 1:44 下午
 */
@Data
public class DataModuleDTO {

    private Integer id;

    /**
     * 层级id
     */
    private Integer folderId;

    /**
     * 数据模型名称
     */
    private String modelName;

    /**
     * 类目代码
     */
    private Integer modelTypeCode;

    /**
     * 发布状态代码
     */
    private Integer modelStatusCode;

    /**
     * 发布状态名
     */
    private String modelStatusName;

    /**
     * 创建方式代码
     */
    private Integer exportTypeCode;

    /**
     * 创建方式名
     */
    private String exportTypeName;

    /**
     * 模型表述
     */
    private String ramark;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    //数据字段
    private List<WaterfallModelField> modelFields;

    //分区字段
    private List<WaterfallModelPartition> modelPartitions;

}
