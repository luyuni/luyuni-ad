package top.luyuni.ad.search.vo;

import com.sun.corba.se.spi.ior.IdentifiableFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;
import top.luyuni.ad.search.vo.feature.DistrictFeature;
import top.luyuni.ad.search.vo.feature.FeatureRelation;
import top.luyuni.ad.search.vo.feature.ItFeature;
import top.luyuni.ad.search.vo.feature.KeywordFeature;
import top.luyuni.ad.search.vo.media.AdSlot;
import top.luyuni.ad.search.vo.media.App;
import top.luyuni.ad.search.vo.media.Device;
import top.luyuni.ad.search.vo.media.Geo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    /**
     * 媒体方的请求标识
     */
    private String mediaId;

    /**
     * 请求的基本信息
     */
    private RequestInfo requestInfo;

    /**
     * 请求的匹配信息
     */
    private FeatureInfo featureInfo;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo{
        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo{
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        /**
         * 默认AND
         */
        private FeatureRelation relation = FeatureRelation.AND;
    }
}
