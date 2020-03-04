package top.luyuni.ad.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BinlogRowData {
    /**
     * 表名
     */
    private TableTemplate table;
    /**
     * 事件类型
     */
    private EventType eventType;

    private List<Map<String, String>> after;
    private List<Map<String, String>> before;
}
