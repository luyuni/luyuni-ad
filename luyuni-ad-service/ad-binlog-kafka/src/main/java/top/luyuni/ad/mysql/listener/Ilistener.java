package top.luyuni.ad.mysql.listener;

import top.luyuni.ad.dto.BinlogRowData;

public interface Ilistener {
    void register();
    void onEvent(BinlogRowData eventData);
}
