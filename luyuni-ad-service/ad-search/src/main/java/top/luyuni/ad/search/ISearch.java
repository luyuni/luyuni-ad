package top.luyuni.ad.search;

import top.luyuni.ad.search.vo.SearchRequest;
import top.luyuni.ad.search.vo.SearchResponse;

public interface ISearch {
    /**
     * 拿取广告
     * @param request
     * @return
     */
    SearchResponse fetchAds(SearchRequest request);
}
