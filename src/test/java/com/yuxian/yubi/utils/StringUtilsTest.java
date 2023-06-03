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
		String s = "\n" +
				"```javascript\n" +
				"option = {\n" +
				"    title: {\n" +
				"        text: '用户数变化折线图'\n" +
				"    },\n" +
				"    xAxis: {\n" +
				"        type: 'category',\n" +
				"        data: ['1', '2', '3']\n" +
				"    },\n" +
				"    yAxis: {\n" +
				"        type: 'value'\n" +
				"    },\n" +
				"    series: [{\n" +
				"        data: ['10', '33', '55'],\n" +
				"        type: 'line'\n" +
				"    }]\n" +
				"};\n" +
				"```\n";
		System.out.println(s.substring(s.indexOf('{'), s.lastIndexOf('}') + 1));
	}
}
