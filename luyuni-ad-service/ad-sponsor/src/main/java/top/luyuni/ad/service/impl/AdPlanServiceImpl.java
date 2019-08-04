package top.luyuni.ad.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.luyuni.ad.constant.CommonStatus;
import top.luyuni.ad.constant.Constants;
import top.luyuni.ad.dao.AdPlanRepository;
import top.luyuni.ad.dao.AdUserRepository;
import top.luyuni.ad.entity.AdPlan;
import top.luyuni.ad.entity.AdUser;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.IAdPlanService;
import top.luyuni.ad.utils.CommonUtils;
import top.luyuni.ad.vo.AdPlanGetRequest;
import top.luyuni.ad.vo.AdPlanRequest;
import top.luyuni.ad.vo.AdPlanResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AdPlanServiceImpl implements IAdPlanService {

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUserRepository userRepository;

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (! request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        // 确保关联的User对象存在
        Optional<AdUser> user = userRepository.findById(request.getUserId());
        if (! user.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan plan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (plan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newPlan = planRepository.save(new AdPlan(request.getUserId(), request.getPlanName(),
                        CommonUtils.parseStringDate(request.getStartTime()),
                        CommonUtils.parseStringDate(request.getEndTime())
                )
        );
        return new AdPlanResponse(newPlan.getId(), newPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdPlan> plans = planRepository.findAllByInAndUserId(request.getIds(), request.getUserId());
        return plans;
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (StringUtils.isNotEmpty(request.getStartTime())) {
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartTime()));
        }
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndTime()));
        }
        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }
}
