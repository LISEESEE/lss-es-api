package com.code.mq.mq.tx;

import com.code.mq.mq.simple.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class TxRec {
    public static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive msg:"+new String(body,"utf-8"));
            }
        });
    }
}
