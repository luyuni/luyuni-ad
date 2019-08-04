package top.luyuni.ad.service.impl;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.luyuni.ad.constant.Constants;
import top.luyuni.ad.dao.AdPlanRepository;
import top.luyuni.ad.dao.AdUnitRepository;
import top.luyuni.ad.dao.CreativeRepository;
import top.luyuni.ad.dao.unit_condition.AdUnitDistrictRepository;
import top.luyuni.ad.dao.unit_condition.AdUnitItRepository;
import top.luyuni.ad.dao.unit_condition.AdUnitKeywordRepository;
import top.luyuni.ad.dao.unit_condition.CreativeUnitRepository;
import top.luyuni.ad.entity.AdPlan;
import top.luyuni.ad.entity.AdUnit;
import top.luyuni.ad.entity.unit_condition.AdUnitDistrict;
import top.luyuni.ad.entity.unit_condition.AdUnitIt;
import top.luyuni.ad.entity.unit_condition.AdUnitKeyword;
import top.luyuni.ad.entity.unit_condition.CreativeUnit;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.IAdUnitService;
import top.luyuni.ad.vo.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;
    @Autowired
    private AdUnitItRepository unitItRepository;
    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (! request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> plan = planRepository.findById(request.getPlanId());
        if (! plan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdUnit unit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (unit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit adUnit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget()));
        return new AdUnitResponse(adUnit.getId(), adUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {

            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(AdUnitKeyword::getId)
                    .collect(Collectors.toList());
        }

        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add( 
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = unitItRepository.saveAll(unitIts).stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(
            AdUnitDistrictRequest request) throws AdException {

        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new AdUnitDistrict(d.getUnitId(), d.getProvince(),
                        d.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts)
                .stream().map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());

        if (!(isRelatedUnitExist(unitIds) && isRelatedUnitExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));

        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits)
                .stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    /**
     * 查看关联的创意是否存在
     * @param creativeIds
     * @return
     */
    private boolean isRelatedCreativeExist(List<Long> creativeIds){
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size() == new HashSet<Long>(creativeIds).size();
    }

    /**
     * 查看关联的推广单元是否存在
     * @param unitIds
     * @return
     */
    private boolean isRelatedUnitExist(List<Long> unitIds){
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        /**
         * id可能会重复
         */
        return unitRepository.findAllById(unitIds).size() == new HashSet<Long>(unitIds).size();
    }
}
