package com.code.mq.mq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Send {

    public static  final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "Hello Simple Queue 啪啪啪 !";
        for (int i = 0; i < 2; i++) {
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }
        //exchange routingKey props  body

        System.out.println("------send msg :"+ msg);
        channel.close();
        connection.close();

    }
}

