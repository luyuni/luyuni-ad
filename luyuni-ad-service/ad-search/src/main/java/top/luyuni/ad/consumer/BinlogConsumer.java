package top.luyuni.ad.consumer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import top.luyuni.ad.dto.MySqlRowData;
import top.luyuni.ad.sender.ISender;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 消费BinlogMessage
 */
@Slf4j
@Component
public class BinlogConsumer {

    @Resource(name = "indexSender")
    private ISender sender;

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()){
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            log.info("kafka processMysqlRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }

    }
}
