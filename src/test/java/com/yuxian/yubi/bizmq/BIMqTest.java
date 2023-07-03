package com.yuxian.yubi.bizmq;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author yuxian&羽弦
 * date 2023/07/03 11:22
 * description:
 * @version 1.0
 **/

@SpringBootTest
public class BIMqTest {
	@Resource
	private BiMessageProducer messageProducer;

	@Test
	@SneakyThrows
	public void test() {
		messageProducer.sendMessage("1123");
		Thread.sleep(10000);
	}
}
