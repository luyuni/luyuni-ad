package top.luyuni.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.luyuni.ad.constant.Constant;
import top.luyuni.ad.constant.OpType;
import top.luyuni.ad.dto.BinlogRowData;
import top.luyuni.ad.dto.MySqlRowData;
import top.luyuni.ad.dto.TableTemplate;
import top.luyuni.ad.sender.ISender;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增量Binlog的解析与监听
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "kafkaSender")
    private ISender sender;
    @Autowired
    private AggregationListener aggregationListener;


    @Override
    @PostConstruct
    public void register() {

        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) ->
        aggregationListener.register(v, k, this));
    }


    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }
        System.out.println(eventData.getAfter() + "  eventData.getAfter() -------------------");
        for (Map<String, String> afterMap : eventData.getAfter()) {

            Map<String, String> _afterMap = new HashMap<>();

            for (Map.Entry<String, String> entry : afterMap.entrySet()) {

                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }
            System.out.println(_afterMap + "   onEvent ----------------------------------------");
            rowData.getFieldValueMap().add(_afterMap);
        }

        sender.sender(rowData);
    }
}
