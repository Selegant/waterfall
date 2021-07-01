package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
    * 层级
    */
@Data
public class WaterfallFolder {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 父级层级id,顶层为0
    */
    private Integer parentId;

    /**
    * 层级名称
    */
    private String folderName;

    /**
    * 层级类型 1文件夹 2文件
    */
    private Integer folderType;

    /**
    * 层级标识
    */
    private String mark;

    /**
    * 描述
    */
    private String remark;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 最后修改时间
    */
    private Date updateTime;

    /**
     * 删除标识
     */
    private Boolean delFlag;
}