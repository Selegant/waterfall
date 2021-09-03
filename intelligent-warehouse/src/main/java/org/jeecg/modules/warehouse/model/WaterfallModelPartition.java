package org.jeecg.modules.warehouse.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Api(value = "模型分区字段")
public class WaterfallModelPartition {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模型id")
    private Integer modelId;

    @ApiModelProperty("分区名")
    private String partitionName;

    @ApiModelProperty("分区类型id")
    private Integer partitionTypeId;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("元数据id")
    private Integer metadataId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标识")
    private Boolean delFlag;
}