package com.code.mq.mq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receive {
   /* public static  final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        //定义消息队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msgStr = new String(delivery.getBody());

            System.out.println("------[Receive] msg :"+msgStr);
        }
    }*/
   public static  final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("--------------------new API ----------------------");
                System.out.println("------[Receive] msg :"+msg);
            }
        };
        // 监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
