package com.yuxian.yubi.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
			String EXCHANGE_NAME =  BiMqConstant.BI_EXCHANGE_NAME;
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");

			// 创建队列，随机分配一个队列名称
			String queueName = BiMqConstant.BI_QUEUE_NAME;
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, EXCHANGE_NAME,  BiMqConstant.BI_ROUTING_KEY);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
