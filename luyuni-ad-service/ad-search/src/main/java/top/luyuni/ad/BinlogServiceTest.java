package top.luyuni.ad;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

public class BinlogServiceTest {
    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "123456"
        );
//        client.setBinlogFilename();
//        client.setBinlogPosition();
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if(data instanceof UpdateRowsEventData){
                System.out.println("Update Event -----");
                System.out.println();
                System.out.println(data);
            }else if(data instanceof WriteRowsEventData){
                System.out.println("Write Event -----");
                System.out.println(data);
            }else if (data instanceof DeleteRowsEventData){
                System.out.println("Delete Event -----");
                System.out.println(data);
            }
        });
        client.connect();
    }
}
