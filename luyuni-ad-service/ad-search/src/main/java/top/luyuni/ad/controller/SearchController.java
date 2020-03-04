package top.luyuni.ad.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.luyuni.ad.client.SponsorClient;
import top.luyuni.ad.client.vo.AdPlan;
import top.luyuni.ad.client.vo.AdPlanGetRequest;
import top.luyuni.ad.search.ISearch;
import top.luyuni.ad.search.vo.SearchRequest;
import top.luyuni.ad.search.vo.SearchResponse;
import top.luyuni.ad.vo.CommonResponse;

import java.util.List;

@RestController
@Slf4j
public class SearchController {
    @Autowired
    private ISearch search;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SponsorClient sponsorClient;

    @PostMapping(value = "/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request){
        log.info("ad search: getAdPlans -> {}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }
    @PostMapping(value = "/getAdPlansByBibbon")
    public CommonResponse<List<AdPlan>> getAdplansByRibbon(@RequestBody AdPlanGetRequest request){
        log.info("ad search: getAdPlansByRibbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/get/adPlan", request, CommonResponse.class).getBody();
    }
    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request){
        log.info("ad-seach: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }
}
