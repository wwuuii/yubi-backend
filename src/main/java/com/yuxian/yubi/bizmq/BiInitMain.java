package com.yuxian.yubi.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxian&羽弦
 * date 2023/06/26 10:35
 * description:
 * @version 1.0
 **/
public class BiInitMain {

	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			String EXCHANGE_NAME = BiMqConstant.BI_EXCHANGE_NAME;
			//创建图表分析任务交换机
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");
			// 创建死信交换机
			channel.exchangeDeclare(BiMqConstant.BI_DEAD_EXCHANGE_NAME, "direct");
			//创建死信队列
			channel.queueDeclare(BiMqConstant.BI_DEAD_QUEUE_NAME, true, false, false, null);
			channel.queueBind(BiMqConstant.BI_DEAD_QUEUE_NAME, BiMqConstant.BI_DEAD_EXCHANGE_NAME, BiMqConstant.BI_DEAD_ROUTING_KEY);
			// 创建图表分析任务队列
			String queueName = BiMqConstant.BI_QUEUE_NAME;
			Map<String, Object> arguments = new HashMap<>();
			arguments.put("x-dead-letter-exchange", BiMqConstant.BI_DEAD_EXCHANGE_NAME);
			arguments.put("x-dead-letter-routing-key", BiMqConstant.BI_DEAD_ROUTING_KEY);
			channel.queueDeclare(queueName, true, false, false, arguments);
			channel.queueBind(queueName, EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY);
			channel.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
