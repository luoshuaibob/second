package cn.hey.second.rabbitmq;


import cn.hey.second.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * http://localhost:15672/#/queues
 * @author Long
 */
@Slf4j
@Service
public class MqReceiver {


    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        System.out.println(message);
    }



}
