package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "数据模型字段")
public class WaterfallModelField {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模型id")
    private Integer modelId;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("字段类型名称")
    private String fieldTypeName;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("元数据id")
    private Integer metadataId;

    @ApiModelProperty("是否为主键")
    private Boolean primarykeyFlag;

    @ApiModelProperty("是否为空")
    private Boolean emptyFlag;

    @ApiModelProperty("字段长度")
    private Integer length;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("字段序号")
    private Integer fieldSort;

    @ApiModelProperty("删除标识")
    private Boolean delFlag;
}