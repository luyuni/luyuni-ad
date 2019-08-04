package top.luyuni.ad.service;

import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.vo.CreateUserRequest;
import top.luyuni.ad.vo.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
