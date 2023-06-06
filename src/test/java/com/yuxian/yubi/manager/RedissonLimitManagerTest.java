package com.yuxian.yubi.manager;

import com.yuxian.yubi.annotation.RequestLimit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author yuxian&羽弦
 * date 2023/06/06 15:23
 * description:
 * @version 1.0
 **/
@SpringBootTest
public class RedissonLimitManagerTest {

	@Resource
	private RedissonLimitManager redissonLimitManager;

	@Test
	public void test() {
		for (int i = 0; i < 5; i++) {
			redissonLimitManager.doRateLimit("test222");
			System.out.println("success!");
		}
	}



}
