package com.yuxian.yubi.service;

import javax.annotation.Resource;

import com.yuxian.yubi.exception.ThrowUtils;
import com.yuxian.yubi.mapper.UserMapper;
import com.yuxian.yubi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 用户服务测试
 */
@SpringBootTest
public class UserServiceTest {

	@Resource
	private UserService userService;
	@Resource
	private UserMapper userMapper;

	@Test
	void userRegister() {
		String userAccount = "yuxian3";
		String userPassword = "12345678";
		User user = new User();
		user.setUserPassword(userPassword);
		user.setUserAccount(userAccount);
		System.out.println(userMapper.insert(user));
		System.out.println(user.getId());
	}

	@Test
	void testOneDayTime() {
		LocalTime midnight = LocalTime.of(23, 59, 59, 999);
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime midnightDateTime = currentDateTime.with(midnight);
		Duration duration = Duration.between(midnightDateTime, currentDateTime);
		long expireTime = duration.toMillis();

	}
}
