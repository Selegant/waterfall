package org.jeecg.modules.datasources.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyDatasourcePoolUtil {


    // 活动连接队列
    private static LinkedBlockingQueue<Connection> active = new LinkedBlockingQueue<>();

    // 空闲连接队列
    private static LinkedBlockingQueue<Connection> idle = new LinkedBlockingQueue<>();

    // 已创建的连接数
    private static AtomicInteger createdCount = new AtomicInteger(0);

    // 最大连接数 后面改成可配置
    private static int maxConnection = 100;

    // 最大等待毫秒数 后面改成可配置
    private static int maxWaitTimeout = 3000;

    /**
     * 创建连接
     */
    private Connection createConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 关闭连接
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("关闭连接失败:{}", e.getMessage());
        }
    }

    public Connection getConnection(DataSource dataSource) throws SQLException {
        // 尝试获取空闲连接
        Connection connection = idle.poll();
        if (connection == null) {
            if (createdCount.get() < maxConnection) {
                if (createdCount.incrementAndGet() <= maxConnection) {
                    connection = createConnection(dataSource);
                } else {
                    createdCount.decrementAndGet();
                }
            }
            // 尝试等待获取空闲连接，实现超时等待机制
            if (connection == null) {
                try {
                    connection = idle.poll(maxWaitTimeout, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    log.error("获取连接超时:{}", e.getMessage());
                }
                if (connection == null) {
                    log.warn("获取连接超时");
                    throw new RuntimeException("获取连接超时");
                }
            }
        }
        active.offer(connection);
        return connection;
    }

    /**
     * 释放连接
     */
    public void releaseConnection(Connection connection) {
        // 处理空连接
        if (connection == null) {
            createdCount.decrementAndGet();
            return;
        }
        // 处理移除失败的连接
        boolean removeResult = active.remove(connection);
        if (!removeResult) {
            closeConnection(connection);
            createdCount.decrementAndGet();
            return;
        }
        // 处理已经关闭的连接
        try {
            if (connection.isClosed()) {
                createdCount.decrementAndGet();
                return;
            }
        } catch (SQLException e) {
            log.error("释放连接失败:{}", e.getMessage());
        }
        // 处理添加失败的连接
        boolean offerResult = idle.offer(connection);
        if (!offerResult) {
            closeConnection(connection);
            createdCount.decrementAndGet();
        }
    }
}
