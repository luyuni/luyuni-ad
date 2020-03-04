package top.luyuni.ad.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

public enum  OpType {
    ADD,
    UPDATE,
    DELETE,
    OTHER;

    /**
     * 把EventType转为OpType
     * @param eventType
     * @return
     */
    public static OpType to(EventType eventType){
        switch (eventType){
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
