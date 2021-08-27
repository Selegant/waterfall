package org.jeecg.modules.datasources.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import lombok.Data;

@Data
public class WaterfallDataSource {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 数据库地址
     */
    private String jdbcUrl;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 是否可用 0不可用1可用
     */
    private Boolean aliveFlag;

    /**
     * 用途 1数据端2目标端
     */
    private Integer purpose;

    /**
     * 数据库host
     */
    private String host;

    /**
     * 数据库端口号
     */
    private String port;

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * oracle服务名
     */
    private String serverName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String defaultfs;

    /**
     * hive path
     */
    private String path;

    private String hadoopConfig;
}
