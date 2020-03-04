package top.luyuni.ad.service;

import org.springframework.stereotype.Service;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.vo.CreativeRequest;
import top.luyuni.ad.vo.CreativeResponse;

public interface ICreativeService {
    /**
     * 增加创意
     * @param request
     * @return
     * @throws AdException
     */
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
