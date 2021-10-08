package org.jeecg.modules.warehouse.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
    * 质量规则表
    */
@Data
public class WaterfallQualityRule {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("质量规则名")
    private String ruleName;

    @ApiModelProperty("质量规则类型")
    private Integer ruleType;

    @ApiModelProperty("所属模型id")
    private Integer modelId;

    @ApiModelProperty("是否启用")
    private Boolean enableFlag;

    @ApiModelProperty("删除标志")
    private Boolean delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除时间")
    private Date updateTime;

    @TableField(exist = false)
    private Integer hasSelect;
}