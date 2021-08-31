package org.jeecg.modules.datasources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.datasources.model.WaterfallModelField;
import org.jeecg.modules.datasources.model.WaterfallModelPartition;
import java.util.List;

/**
 * @Author liansongye
 * @create 2021/6/28 1:44 下午
 */
@NoArgsConstructor
@Data
public class DataModuleDTO{
    public static Integer UN_PUBLISHED = 1;
    public static String  UN_PUBLISHED_NAME = "未发布";
    public static Integer PUBLISHED = 2;
    public static String  PUBLISHED_NAME = "已发布";

    private Integer id;

    private Integer folderId;
    private String modelName;
    private Integer modelStatusCode;
    private String modelStatusName;
    private Integer exportTypeCode;
    private String exportTypeName;
    private String remark;
    private Integer publishDb;
    private Boolean delFlag;

    //数据字段
    private List<WaterfallModelField> modelFields;

    //分区字段
    private List<WaterfallModelPartition> modelPartitions;

    private String dbType;

    private String sql;



}
