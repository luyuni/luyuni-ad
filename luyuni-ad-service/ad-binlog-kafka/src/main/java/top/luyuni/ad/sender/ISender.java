package top.luyuni.ad.sender;

import top.luyuni.ad.dto.MySqlRowData;

public interface ISender {
    /**
     * 投递增量数据
     * @param rowData
     */
    void sender(MySqlRowData rowData);
}
