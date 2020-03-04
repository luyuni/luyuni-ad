package top.luyuni.ad.search.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import top.luyuni.ad.index.CommonStatus;
import top.luyuni.ad.index.DataTable;
import top.luyuni.ad.index.adunit.AdUnitIndex;
import top.luyuni.ad.index.adunit.AdUnitObject;
import top.luyuni.ad.index.creative.CreativeIndex;
import top.luyuni.ad.index.creative.CreativeObject;
import top.luyuni.ad.index.creativeunit.CreativeUnitIndex;
import top.luyuni.ad.index.district.UnitDistrictIndex;
import top.luyuni.ad.index.interest.UnitItIndex;
import top.luyuni.ad.index.keyword.UnitKeywordIndex;
import top.luyuni.ad.search.ISearch;
import top.luyuni.ad.search.vo.SearchRequest;
import top.luyuni.ad.search.vo.SearchResponse;
import top.luyuni.ad.search.vo.feature.DistrictFeature;
import top.luyuni.ad.search.vo.feature.FeatureRelation;
import top.luyuni.ad.search.vo.feature.ItFeature;
import top.luyuni.ad.search.vo.feature.KeywordFeature;
import top.luyuni.ad.search.vo.media.AdSlot;

import java.util.*;

@Slf4j
@Service
public class SearchImpl implements ISearch {
    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        /**
         * 取出请求的广告位信息
         */
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        /**
         * 取出Feature
         */
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        /**
         * 取出Feature之间的关系
         */
        FeatureRelation relation = request.getFeatureInfo().getRelation();

        /**
         * 构造响应对象
         */
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();
        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            /**
             * 根据流量类型获取初始的AdUnit
             */
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
            if(relation == FeatureRelation.AND){//限制条件是and的关系
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);
                /**
                 * 过滤后保存
                 */
                targetUnitIdSet = adUnitIdSet;
            }else{//or关系
                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, itFeature);
            }
            /**
             * 拿到unitobject
             */
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            /**
             * 过滤掉无效状态
             */
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            /**
             * 拿到创意id
             */
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            /**
             * 拿到创意对象
             */
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);
            /**
             * 通过adSlot实现对
             */
            filterCreativeByAdSlot(creatives, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());
            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
        }
        log.info("ad-search: fetchAds: {} - {}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    /**
     * 得到or关系的set，取并集
     * @param adUnitIdSet
     * @param keywordFeature
     * @param districtFeature
     * @param itFeature
     * @return 推广单元的set集合
     */
    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }


    /**
     * 根据keywor过滤
     *
     * @param adUnitIds
     * @param keywordFeature
     */
    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords())
            );
        }
    }

    /**
     * 根据地域过滤
     * @param adUnitIds
     * @param districtFeature
     */
    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {

            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts())
            );
        }
    }

    /**
     * 根据兴趣过滤
     * @param adUnitIds
     * @param itFeature
     */
    private void filterItTagFeature(Collection<Long> adUnitIds, ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts())
            );
        }
    }

    /**
     * 根据推广计划的状态过滤
     * @param unitObjects
     * @param status
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    /**
     * 根据广告位过滤creative
     * @param creatives
     * @param width
     * @param height
     * @param type
     */
    private void filterCreativeByAdSlot(List<CreativeObject> creatives,
                                        Integer width,
                                        Integer height,
                                        List<Integer> type) {
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        CollectionUtils.filter(
                creatives,
                creative ->
                        creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                                && creative.getWidth().equals(width)
                                && creative.getHeight().equals(height)
                                && type.contains(creative.getType())
        );
    }

    /**
     * 任意返回一个创意信息
     * @param creatives
     * @return
     */
    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }
        CreativeObject randomObject = creatives.get(Math.abs(new Random().nextInt()) % creatives.size());
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
