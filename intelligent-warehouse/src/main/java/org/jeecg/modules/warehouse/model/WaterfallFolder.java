package org.jeecg.modules.warehouse.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@Api(value = "文件夹层级")
public class WaterfallFolder {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父级层级id,顶层为0")
    private Integer parentId;

    @ApiModelProperty("层级名称")
    private String folderName;

    @ApiModelProperty("层级类型 1文件夹 2文件")
    private Integer folderType;

    @ApiModelProperty("层级标识")
    private String mark;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后修改时间")
    private Date updateTime;

    @ApiModelProperty("删除标识")
    private Boolean delFlag;
}