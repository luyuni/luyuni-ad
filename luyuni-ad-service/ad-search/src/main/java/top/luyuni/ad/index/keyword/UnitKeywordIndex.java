package top.luyuni.ad.index.keyword;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import top.luyuni.ad.index.IndexAware;
import top.luyuni.ad.utils.CommonUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * String代表keyword
 * Set存储推广单元
 * 这是一个倒排索引
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {
    /**
     * 一个keyword会对应对个推广单元
     */
    private static Map<String, Set<Long>> keywordUnitMap;
    /**
     * 一个推广单元会会包含多个keyword
     */
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    /**
     * 通过关键词获取支持该关键词的推广单元
     * @param key
     * @return 支持的推广单元
     */
    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }
        Set<Long> res = keywordUnitMap.get(key);
        if (res == null) {
            return Collections.emptySet();
        }
        return res;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndex: before add -> {}", unitKeywordMap);
        /**
         * 如果keywordUtilMap不为空，返回原Set，否则新建一个Set
         */
        Set<Long> unitIdSet = CommonUtils.getorCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }
        log.info("UnitKeywordIndex: after add -> {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitKeywordIndex: before delete -> {}", unitKeywordMap);
        Set<Long> unitIds = CommonUtils.getorCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId : value){
            Set<String> keywordSet = CommonUtils.getorCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }
        log.info("UnitKeywordIndex: after delete -> {}", unitKeywordMap);
    }

    /**
     * 查看一个推广单元是否包含这些关键词
     * @param unitId
     * @param keywords
     * @return
     */
    public boolean match(Long unitId, List<String> keywords){
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {
            Set<String> unitKeyWords = unitKeywordMap.get(unitId);
            /**
             * a  b   a是b的子集是返回true
             */
            return CollectionUtils.isSubCollection(keywords, unitKeyWords);
        }
        return false;
    }
}
