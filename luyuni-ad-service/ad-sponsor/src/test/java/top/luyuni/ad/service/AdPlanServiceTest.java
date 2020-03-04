package top.luyuni.ad.service;

import top.luyuni.ad.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.luyuni.ad.exception.AdException;
import top.luyuni.ad.vo.AdPlanGetRequest;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException {

        System.out.println(
                planService.getAdPlanByIds(new AdPlanGetRequest(15L, Collections.singletonList(10L)))
        );
    }
}
