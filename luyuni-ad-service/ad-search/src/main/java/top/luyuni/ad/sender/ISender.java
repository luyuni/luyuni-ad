package top.luyuni.ad.sender;


import top.luyuni.ad.dto.MySqlRowData;

public interface ISender {

    void sender(MySqlRowData rowData);
}
