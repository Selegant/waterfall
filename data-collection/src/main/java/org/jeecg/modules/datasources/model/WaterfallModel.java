package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "数据模型")
public class WaterfallModel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("层级id")
    private Integer folderId;

    @ApiModelProperty("数据模型名称")
    private String modelName;

    @ApiModelProperty("发布状态代码")
    private Integer modelStatusCode;

    @ApiModelProperty("发布状态名")
    private String modelStatusName;

    @ApiModelProperty("创建方式代码")
    private Integer exportTypeCode;

    @ApiModelProperty("创建方式名")
    private String exportTypeName;

    @ApiModelProperty("模型表述")
    private String remark;

    @ApiModelProperty("物理化数据库id")
    private Integer publishDb;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标识")
    private Boolean delFlag;
}
