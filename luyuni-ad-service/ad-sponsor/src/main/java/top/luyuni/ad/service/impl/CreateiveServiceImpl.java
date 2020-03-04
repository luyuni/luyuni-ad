package top.luyuni.ad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.luyuni.ad.dao.CreativeRepository;
import top.luyuni.ad.entity.Creative;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.service.ICreativeService;
import top.luyuni.ad.vo.CreativeRequest;
import top.luyuni.ad.vo.CreativeResponse;

@Service
public class CreateiveServiceImpl implements ICreativeService {

    @Autowired
    private CreativeRepository creativeRepository;
    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
