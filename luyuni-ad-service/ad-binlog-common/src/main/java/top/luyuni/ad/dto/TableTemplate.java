package top.luyuni.ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.luyuni.ad.constant.OpType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {
    private String tableName;
    private String level;
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();
    /**
     * 字段索引  --> 字段名
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
