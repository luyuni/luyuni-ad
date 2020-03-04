package top.luyuni.ad.index;

import lombok.Getter;

/**
 * 状态常量，1表示有效
 * 0 表示无效
 */
@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
