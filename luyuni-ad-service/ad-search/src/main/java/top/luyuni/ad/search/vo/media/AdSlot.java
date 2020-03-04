package top.luyuni.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {
    /**
     * 广告位编码
     */
    private String adSlotCode;

    /**
     * 流量类型
     */
    private Integer positionType;
    /**
     * 广告位的宽和高
     */
    private Integer width;
    private Integer height;
    /**
     * 广告位的物料类型：图片，视频...
     */
    private List<Integer> type;
    /**
     * 最低出价
     */
    private Integer minCpm;
}
