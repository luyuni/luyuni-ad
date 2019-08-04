package top.luyuni.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitRequest {

    private List<CreativeUnitItem> unitItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreativeUnitItem{
        /**
         * 创意id
         */
        private Long creativeId;
        /**
         * 推广单元id
         */
        private Long unitId;
    }
}
