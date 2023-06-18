package com.yuxian.yubi.Job;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yuxian&羽弦
 * date 2023/06/18 10:12
 * description:
 * @version 1.0
 **/
@SpringBootTest
public class MonitorTaskQueueJobTask {


	@Resource
	private ThreadPoolExecutor threadPoolExecutor;

	@Test
	public void test() throws InterruptedException {
		for (int i = 0; i < 12; i++) {
			threadPoolExecutor.execute(() -> {
				try {
					System.out.println(Thread.currentThread().getName());
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println(Thread.currentThread().getName() + "-" + "执行完毕");
			});
		}
		Thread.sleep(100000000);
	}
}
