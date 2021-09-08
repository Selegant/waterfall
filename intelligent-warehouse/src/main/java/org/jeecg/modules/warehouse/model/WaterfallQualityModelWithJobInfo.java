package org.jeecg.modules.warehouse.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
    * 模型和计划中间表
    */
@Data
public class WaterfallQualityModelWithJobInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模型id")
    private Integer modelId;

    @ApiModelProperty("计划id")
    private Integer jobInfoId;

    @ApiModelProperty("删除标志")
    private Boolean delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}