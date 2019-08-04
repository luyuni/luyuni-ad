package top.luyuni.ad.service;

import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.vo.CreativeRequest;
import top.luyuni.ad.vo.CreativeResponse;

public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
