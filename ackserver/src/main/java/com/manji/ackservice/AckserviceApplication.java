package com.manji.ackservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableTransactionManagement
@EnableFeignClients//启动feign服务
@EnableDiscoveryClient
@MapperScan("com.manji.ackservice.mapper")
public class AckserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AckserviceApplication.class, args);
		System.out.println("------------start over----------");
	}
}
