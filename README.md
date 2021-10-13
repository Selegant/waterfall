Waterfall 数据平台
===============

当前最新版本： 1.0.0（发布日期：20211013）

## 项目说明

本项目基于jeecg-boot项目改造 致力于完成通用数据平台及数据仓库搭建 
包含数据采集 数据模型建立 数据质量稽查 算法编排 数据接口等功能实现
数据一体化治理

## 后端技术架构
- 基础框架：Spring Boot 2.3.5.RELEASE

- 持久层框架：Mybatis-plus 3.4.1

- 安全框架：Apache Shiro 1.7.0，Jwt 3.11.0

- 数据库连接池：阿里巴巴Druid 1.1.22

- 缓存框架：redis

- 日志打印：logback

- 调度框架: xxl-job

- 工作流任务框架: dolphinscheduler

- 其他：fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。


## 开发环境

- 语言：Java 8

- IDE(JAVA)： Eclipse安装lombok插件 或者 IDEA

- 依赖管理：Maven

- 数据库：MySQL5.7+

- 缓存：Redis
