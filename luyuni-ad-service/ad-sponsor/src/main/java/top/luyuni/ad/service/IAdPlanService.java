package top.luyuni.ad.service;

import top.luyuni.ad.entity.AdPlan;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.vo.AdPlanGetRequest;
import top.luyuni.ad.vo.AdPlanRequest;
import top.luyuni.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {
    /**
     * 创建推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
