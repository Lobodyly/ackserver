package com.example.demo;

import com.example.demo.Serializable.TestObjSerializeAndDeserialize;
import com.example.demo.logback.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class DemoApplication {
	private static final Logger log=LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) throws Exception {
		log.info("Start CacheApplication..");
		SpringApplication.run(DemoApplication.class, args);
		Test test=new Test();
		test.test();
		TestObjSerializeAndDeserialize testObjSerializeAndDeserialize=new TestObjSerializeAndDeserialize();
		testObjSerializeAndDeserialize.test();
	}
}
