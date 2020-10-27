package com.code.mq.mq.workFair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ruoyi.mq.simple.ConnectionUtils;

import java.io.IOException;

/**
 * 工作队列的生产者
 */
public class Send {
    public static final  String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        /**
         * 队列只有在收到消费者发回的上一条消息 ack 确认后，才会向该消费者发送下一条消息
         * 发送窗口大小为 1
         * 即同一时刻服务器只会发送一条消息给消费者
         */
        channel.basicQos(1);
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 连续发送消息
        for (int i =0; i < 30; i++){

            String msg = "Hello Work Queues :"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            System.out.println("Send Msg : "+i);
            Thread.sleep(i*20);

        }
        // 释放连接
        channel.close();
        connection.close();

    }
}
