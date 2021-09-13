package org.jeecg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("org.jeecg.executor.mapper")
public class CheckPlanExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckPlanExecutorApplication.class, args);
    }

}
