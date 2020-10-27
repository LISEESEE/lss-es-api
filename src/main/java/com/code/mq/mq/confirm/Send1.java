package com.code.mq.mq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ruoyi.mq.simple.ConnectionUtils;

import java.io.IOException;

/**
 * comfirm-普通模式
 */
public class Send1 {
    public static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用confirmSelect将channle设置为confirm模式
        channel.confirmSelect();
        String s = "hello comfirm message batch";
        for (int i = 0; i < 10; i++) {

        channel.basicPublish("",QUEUE_NAME,null,s.getBytes());
        }
        if (!channel.waitForConfirms()){
            System.out.println("failed");
        }else {
            System.out.println("ok");
        }
        channel.close();
        connection.close();
    }
}
