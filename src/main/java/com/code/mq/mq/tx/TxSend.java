package com.code.mq.mq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ruoyi.mq.simple.ConnectionUtils;

import java.io.IOException;

public class TxSend {
    public static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "Hello tx message !";
        try{
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            int xx = 1/0;
            System.out.println("send"+msg);
            channel.txCommit();
            System.out.println("------send message txRollback");
        }catch (Exception e){
            System.out.println("回滚了");
            channel.txRollback();
            e.printStackTrace();
        }finally {
            channel.close();
            connection.close();
        }
    }
}
