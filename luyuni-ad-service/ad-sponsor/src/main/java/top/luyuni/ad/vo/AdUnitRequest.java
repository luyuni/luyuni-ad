package top.luyuni.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {
    private Long planId;
    private String unitName;
    /**
     * 广告位类型(开屏, 贴片, 中贴, 暂停帖, 后贴)
     */
    private Integer positionType;
    /**
     * 预算
     */
    private Long budget;

    public boolean createValidate(){
        return planId != null && StringUtils.isNotEmpty(unitName)
                && positionType != null && budget != null;
    }
}
