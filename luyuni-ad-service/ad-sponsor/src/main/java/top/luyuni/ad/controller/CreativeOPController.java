package top.luyuni.ad.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.ICreativeService;
import top.luyuni.ad.vo.CreativeRequest;
import top.luyuni.ad.vo.CreativeResponse;

@RestController
@Slf4j
public class CreativeOPController {
    @Autowired
    private ICreativeService creativeService;
    @PostMapping(value = "/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException{
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
