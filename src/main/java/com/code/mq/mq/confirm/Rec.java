package com.code.mq.mq.confirm;

import com.rabbitmq.client.*;
import com.ruoyi.mq.simple.ConnectionUtils;

import java.io.IOException;

public class Rec {
    public static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive msg:"+new String(body,"utf-8"));
            }
        });
    }
}
