package com.yuxian.yubi.manager;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author yuxian&羽弦
 * date 2023/06/15 19:10
 * description:
 * @version 1.0
 **/
@SpringBootTest
public class RetryTest {

	@Resource
	private Retryer<Boolean> retryer;
	private int i = 1;
	@Test
	public void test() {
		try {
			retryer.call(() -> {
				System.out.println(i++);
				int j = 1 / 0;
				return true;
			});
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (RetryException e) {
			throw new RuntimeException(e);
		}

	}
}
