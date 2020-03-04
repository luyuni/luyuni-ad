package top.luyuni.ad.service;

import org.springframework.stereotype.Service;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.vo.*;

public interface IAdUnitService {
    /**
     * 创建推广单元
     * @param request
     * @return
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 创建推广单元与关键字维度限制
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建推广单元与兴趣维度限制
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 创建推广单元与地域维度限制
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    /**
     * 创建创意推广单元
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
