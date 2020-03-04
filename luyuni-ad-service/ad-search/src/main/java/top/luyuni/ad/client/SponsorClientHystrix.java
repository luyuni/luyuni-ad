package top.luyuni.ad.client;

import org.springframework.stereotype.Component;
import top.luyuni.ad.client.vo.AdPlan;
import top.luyuni.ad.client.vo.AdPlanGetRequest;
import top.luyuni.ad.vo.CommonResponse;

import java.util.List;

@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
