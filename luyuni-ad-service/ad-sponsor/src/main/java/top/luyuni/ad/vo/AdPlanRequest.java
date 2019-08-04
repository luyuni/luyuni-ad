package top.luyuni.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanRequest {
    private Long id;
    private Long userId;
    private String planName;
    private String startTime;
    private String endTime;

    public boolean createValidate(){
        return userId != null
                && StringUtils.isNotEmpty(planName)
                && StringUtils.isNotEmpty(startTime)
                && StringUtils.isNotEmpty(endTime );
    }
    public boolean updateValidate(){
        return id != null && userId != null;
    }
    public boolean deleteValidate(){
        return id != null && userId != null;
    }
}
