package top.luyuni.ad.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.IUserService;
import top.luyuni.ad.vo.CreateUserRequest;
import top.luyuni.ad.vo.CreateUserResponse;

@RestController
@Slf4j
public class UserOPController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException{
        log.info("ad-sponsor: create user -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
