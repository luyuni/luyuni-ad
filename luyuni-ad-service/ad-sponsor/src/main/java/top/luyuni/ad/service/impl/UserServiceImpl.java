package top.luyuni.ad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.luyuni.ad.constant.Constants;
import top.luyuni.ad.dao.AdUserRepository;
import top.luyuni.ad.entity.AdUser;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.IUserService;
import top.luyuni.ad.utils.CommonUtils;
import top.luyuni.ad.vo.CreateUserRequest;
import top.luyuni.ad.vo.CreateUserResponse;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private AdUserRepository userRepository;
    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (! request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
        ));
        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
