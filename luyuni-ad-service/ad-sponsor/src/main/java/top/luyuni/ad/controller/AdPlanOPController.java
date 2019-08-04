package top.luyuni.ad.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.luyuni.ad.entity.AdPlan;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.IAdPlanService;
import top.luyuni.ad.vo.AdPlanGetRequest;
import top.luyuni.ad.vo.AdPlanRequest;
import top.luyuni.ad.vo.AdPlanResponse;

import java.util.List;

@RestController
@Slf4j
public class AdPlanOPController {
    @Autowired
    private IAdPlanService planService;
    @PostMapping(value = "/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(request));
        return planService.createAdPlan(request);
    }
    @DeleteMapping(value = "/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: deleteAdPlan -> {}", JSON.toJSONString(request));
        planService.deleteAdPlan(request);
    }
    @PutMapping(value = "/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: updateAdPlan -> {}", JSON.toJSONString(request));
        return planService.updateAdPlan(request);
    }
    @PostMapping(value = "/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException{
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(request));
        return planService.getAdPlanByIds(request);
    }
}
