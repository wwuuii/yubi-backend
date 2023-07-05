package com.yuxian.yubi.manager;

import com.yuxian.yubi.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yuxian&羽弦
 * date 2023/07/04 14:01
 * description:
 * @version 1.0
 **/
@SpringBootTest
public class RedisTest {

	@Resource
	private RedisUtils redisUtils;

	@Test
	public void test() {
		redisUtils.setSet("test2", 1, 50, TimeUnit.SECONDS);
		System.out.println(redisUtils.hasKey("test2"));
	}
}
