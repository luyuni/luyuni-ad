package top.luyuni.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.luyuni.ad.mysql.listener.AggregationListener;

import java.io.IOException;

@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;
    @Autowired
    private BinlogConfig config;
    @Autowired
    private AggregationListener listener;

    /**
     * 应用程序启动时就开始监听
     */
    public void connect(){
        new Thread(() ->{
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (StringUtils.isNotEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(listener);
            try {
                log.info("connectint to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void close(){
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
