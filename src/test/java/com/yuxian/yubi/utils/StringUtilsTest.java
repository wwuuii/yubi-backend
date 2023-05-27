package com.yuxian.yubi.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yuxian&羽弦
 * date 2023/05/25 22:10
 * description:
 * @version 1.0
 **/
@SpringBootTest
public class StringUtilsTest {

	@Test
	public void test() {
		System.out.println(RandomStringUtils.randomAlphanumeric(8));
	}
}
