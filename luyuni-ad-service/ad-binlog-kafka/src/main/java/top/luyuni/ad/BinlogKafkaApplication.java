package top.luyuni.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BinlogKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BinlogKafkaApplication.class, args);
    }
}
