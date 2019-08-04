package top.luyuni.ad.controller;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.luyuni.ad.entity.unit_condition.AdUnitDistrict;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.IAdUnitService;
import top.luyuni.ad.vo.*;

@RestController
@Slf4j
public class AdUnitOPController {
    @Autowired
    private IAdUnitService unitService;
    @PostMapping(value = "/create/adUnit")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException{
        log.info("ad-sponsot: createUnit -> {}", JSON.toJSONString(request));
        return unitService.createUnit(request);
    }
    @PostMapping(value = "/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException{
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));
        return unitService.createUnitKeyword(request);
    }
    @PostMapping(value = "/create/unitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException{
        log.info("ad-sponsor: createUnitIt -> {}", JSON.toJSONString(request));
        return unitService.createUnitIt(request);
    }
    @PostMapping(value = "/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException{
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(request));
        return unitService.createUnitDistrict(request);
    }
    @PostMapping(value = "/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException{
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(request));
        return unitService.createCreativeUnit(request);
    }
}
